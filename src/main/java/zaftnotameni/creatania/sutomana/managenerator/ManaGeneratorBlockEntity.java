package zaftnotameni.creatania.sutomana.managenerator;

import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import vazkii.botania.api.mana.IManaPool;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.util.Log;
import zaftnotameni.sharedbehaviors.IAmManaMachine;
import zaftnotameni.sharedbehaviors.IAmParticleEmittingMachine;
import zaftnotameni.sharedbehaviors.KineticManaMachine;

import javax.annotation.Nonnull;
import java.util.List;

public class ManaGeneratorBlockEntity extends KineticTileEntity implements IAmManaMachine, Log.IHasTickLogger, IAmParticleEmittingMachine {
  public boolean isFirstTick = true;
  public boolean active;
  public int mana;
  public KineticManaMachine manaMachine;

  public ManaGeneratorFluidHandler manaGeneratorFluidHandler;
  public ManaGeneratorBlockEntity(BlockEntityType<? extends ManaGeneratorBlockEntity> type, BlockPos pos, BlockState state) {
    super(type, pos, state);
    this.setLazyTickRate(CommonConfig.MANA_GENERATOR_LAZY_TICK_RATE.get());
  }
  public ManaGeneratorFluidHandler getManaGeneratorFluidHandler() {
    if (this.manaGeneratorFluidHandler == null) this.manaGeneratorFluidHandler = new ManaGeneratorFluidHandler(this);
    return this.manaGeneratorFluidHandler;
  }
  public KineticManaMachine<ManaGeneratorBlockEntity> getManaMachine() {
    if (this.manaMachine == null) this.manaMachine = new KineticManaMachine<>(this)
      .withManaCap(1000)
      .withManaPerRpmPerTick(CommonConfig.MANA_GENERATOR_MANA_PER_RPM_PER_TICK.get())
      .withStressUnitsPerRpm(CommonConfig.MANA_GENERATOR_SU_PER_RPM.get())
      .withBaseRpm(32);
    return this.manaMachine;
  }

  @Override
  protected void read(CompoundTag compound, boolean clientPacket) {
    super.read(compound, clientPacket);
    this.active = compound.getBoolean("active");
    this.mana = compound.getInt("mana");
    this.manaGeneratorFluidHandler.read(compound, clientPacket);
  }
  @Override
  protected void write(CompoundTag compound, boolean clientPacket) {
    this.manaGeneratorFluidHandler.write(compound, clientPacket);
    compound.putBoolean("active", this.active);
    compound.putInt("mana", this.mana);
    super.write(compound, clientPacket);
  }
  @Override
  public void invalidate() {
    super.invalidate();
    this.getManaGeneratorFluidHandler().invalidate();
  }
  @Override
  public void addBehaviours(List<TileEntityBehaviour> behaviours) {
    this.getManaGeneratorFluidHandler().addBehaviours(behaviours);
  }
  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
    if (side == null) {
      Log.LOGGER.info("no side provided when checking for capability {}", cap.getName());
      return super.getCapability(cap, side);
    }
    var block = this.getBlockState().getBlock();
    if (!(block instanceof  ManaGeneratorBlock)){
      Log.LOGGER.info("block is not a mana generator block when checking for capability {}", cap.getName());
      return super.getCapability(cap, side);
    }
    if (!((ManaGeneratorBlock) block).hasShaftTowards(this.level, this.worldPosition, this.getBlockState(), side.getOpposite())) {
      Log.LOGGER.info("side is not opposite to the shaft when checking for capability {}", cap.getName());
      return super.getCapability(cap, side);
    }
    var foundCapability = this.getManaGeneratorFluidHandler().getCapability(cap, side);
    if (foundCapability != null) {
      Log.RateLimited.of(this, 20).log((logger) -> logger.debug("connection could be established when checking for capability {}", cap.getName()));
      return foundCapability;
    }
    return super.getCapability(cap, side);
  }
  public int getNormalizedRPM() {
    var min = CommonConfig.MANA_GENERATOR_MINIMUM_RPM.get();
    if (this.getSpeed() < min) return 0;
    return Math.max(CommonConfig.MANA_GENERATOR_MINIMUM_RPM.get(),
      Math.min(CommonConfig.MANA_GENERATOR_MAXIMUM_RPM.get(), (int) Math.abs(this.getSpeed())));
  }
  public IManaPool getManaPoolAbove() {
    if (this.level == null || this.worldPosition == null) return null;
    var blockAbove = level.getBlockEntity(this.worldPosition.above());
    if (blockAbove != null && blockAbove instanceof IManaPool) { return (IManaPool)  blockAbove; }
    return null;
  }

  public int addManaToPool(int manaAmount) {
    var pool = this.getManaPoolAbove();
    if (pool == null || pool.isFull()) return 0;
    pool.receiveMana(manaAmount);
    return manaAmount;
  }

  public boolean shouldAbortServerTick() {
    var isInInvalidState = this.isOverStressed() || this.getNormalizedRPM() == 0 || this.worldPosition == null || this.level == null;
    if (isInInvalidState) {
      Log.RateLimited.of(this, 20).log((logger) -> logger.debug("server tick aborted because RPM is 0 or is overstressed"));
      return true;
    }
    var notEnoughSpeed = Math.abs(this.getSpeed()) <= 0 || !this.isSpeedRequirementFulfilled();
    if (notEnoughSpeed) {
      Log.RateLimited.of(this, 20).log((logger) -> logger.debug("server tick aborted because RPM {} does not satisfy minimum requirement", this.getSpeed()));
      return true;
    }
    return false;
  }

  public int getManaConversionRate() { return Math.max(Math.abs(CommonConfig.MANA_GENERATOR_MANA_CONVERSION_RATE.get()), 1);  }

  public Log.RateLimited logger;
  public Log.RateLimited getLogger() { return logger; }
  public void setLogger(Log.RateLimited pLogger) { logger = pLogger; }
  public void serverTick() {
    var wasActive = this.active;
    this.active = false;
    this.getManaGeneratorFluidHandler().serverTick();
    if (this.shouldAbortServerTick()) return;

    var conversionRate = this.getManaConversionRate();
    var manaToBeGenerated = this.getManaProductionRate();
    var manaFluidRequired = manaToBeGenerated * conversionRate;
    var manaFluidAvailable = this.getManaGeneratorFluidHandler().getManaFluidAvailable();
    var hasEnoughManaFluidToProduceMana = manaFluidRequired <= manaFluidAvailable;
    if (hasEnoughManaFluidToProduceMana) {
      var manaFluidConsumed = this.getManaGeneratorFluidHandler().drainManaFluidFromTank(manaFluidRequired);
      var realManaToBeGenerated = manaFluidConsumed / conversionRate;
      Log.RateLimited.of(this, 20).log((logger) -> logger.debug("consumed {} mana fluid to generate {} mana", manaFluidConsumed, realManaToBeGenerated));
      addManaToPool(realManaToBeGenerated);
      this.active = true;
    }
    Log.RateLimited.of(this, 20).log((logger) -> logger.debug("not enough mana fluid to produce mana, required {}, available {}", manaFluidRequired, manaFluidAvailable));
  }
  public void clientTick() {}

  @Override
  public void tick() {
    super.tick();
    isFirstTick = false;
    if (this.level == null || this.worldPosition == null) return;
    if (level.isClientSide()) this.clientTick();
    if (!level.isClientSide()) this.serverTick();
  }
  public int getManaProductionRate() {
    return getNormalizedRPM() * CommonConfig.MANA_GENERATOR_MANA_PER_RPM_PER_TICK.get();
  }
  @Override
  public float calculateStressApplied() { return this.getManaMachine().calculateStressRegardless(); }
  @Override
  public float calculateAddedStressCapacity() {
    return 0f;
  }
  @Override
  public boolean isManaMachineActive() { return this.active; }
  @Override
  public int getManaMachineMana() { return this.mana; }
  @Override
  public void setManaMachineLastCapacityProvided(float value) { this.lastCapacityProvided = value; }
  @Override
  public void setManaMachineLastStressImpact(float value) { this.lastStressApplied = value; }
  @Override
  public void updateMana(int value) { this.mana = value; }
  @Override
  public void setManaMachineMana(int value) { this.mana = value; }
  @Override
  public int getManaMachineGeneratedSpeed() { return 0; }
  @Override
  public void updateGeneratedRotation(int i) { }
  @Override
  public boolean shouldEmitParticles() { return true; }
}

