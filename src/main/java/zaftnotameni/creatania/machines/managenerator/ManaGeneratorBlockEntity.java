package zaftnotameni.creatania.machines.managenerator;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import vazkii.botania.api.block.IWandHUD;
import vazkii.botania.api.block.IWandable;
import vazkii.botania.api.mana.IManaReceiver;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.mana.manaduct.BaseManaductBlock;
import zaftnotameni.creatania.util.Log;
import zaftnotameni.sharedbehaviors.ActiveStateSynchronizerBehavior;
import zaftnotameni.sharedbehaviors.IAmManaMachine;
import zaftnotameni.sharedbehaviors.IAmParticleEmittingMachine;
import zaftnotameni.sharedbehaviors.KineticManaMachine;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static zaftnotameni.creatania.util.Text.*;

public class ManaGeneratorBlockEntity extends KineticTileEntity implements IAmManaMachine, Log.IHasTickLogger, IAmParticleEmittingMachine, IWandHUD {
  public boolean isFirstTick = true;
  public boolean active;
  public boolean duct;
  public int mana;
  public ActiveStateSynchronizerBehavior activeStateSynchronizerBehavior;
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
  public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
    super.addToGoggleTooltip(tooltip, isPlayerSneaking);

    purple("").forGoggles(tooltip);

    muted("Stress Units consumed per RPM:").space()
      .add(gray(String.valueOf(this.getManaMachine().stressUnitsPerRpm))).forGoggles(tooltip);

    purple("").forGoggles(tooltip);

    muted("Pure Mana Consumed at current speed:").space()
      .add(aqua(String.valueOf(this.getManaConsumedAtCurrentSpeed()))).forGoggles(tooltip);

    purple("").forGoggles(tooltip);

    muted("Pure Mana consumed per RPM:").space()
      .add(red(String.valueOf(this.getManaMachine().manaPerRpmPerTick))).forGoggles(tooltip);

    purple("").forGoggles(tooltip);

    muted("Real Mana Produced at current speed:").space()
      .add(red("LOW")).forGoggles(tooltip);

    return true;
  }
  @Override
  protected void read(CompoundTag compound, boolean clientPacket) {
    super.read(compound, clientPacket);
    this.active = compound.getBoolean("active");
    this.duct = compound.getBoolean("duct");
    this.mana = compound.getInt("mana");
    this.manaGeneratorFluidHandler.read(compound, clientPacket);
  }
  @Override
  protected void write(CompoundTag compound, boolean clientPacket) {
    this.manaGeneratorFluidHandler.write(compound, clientPacket);
    compound.putBoolean("active", this.active);
    compound.putBoolean("duct", this.duct);
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
    super.addBehaviours(behaviours);
    this.getManaGeneratorFluidHandler().addBehaviours(behaviours);
    this.activeStateSynchronizerBehavior = new ActiveStateSynchronizerBehavior(this);
    behaviours.add(this.activeStateSynchronizerBehavior);
  }
  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
    var result = KineticManaMachine.handleBotaniaManaHudCapability(cap, side, this);
    if (result.isPresent()) return result.cast();

    if (side == null) {
      Log.throttled(100).log(l -> l.info("no side provided when checking for capability {}", cap.getName()));
      return super.getCapability(cap, side);
    }
    var block = this.getBlockState().getBlock();
    if (!(block instanceof  ManaGeneratorBlock)){
      Log.throttled(100).log(l -> l.info("block is not a mana generator block when checking for capability {}", cap.getName()));
      return super.getCapability(cap, side);
    }
    if (!((ManaGeneratorBlock) block).hasShaftTowards(this.level, this.worldPosition, this.getBlockState(), side.getOpposite())) {
      Log.throttled(100).log(l -> l.info("side is not opposite to the shaft when checking for capability {}", cap.getName()));
      return super.getCapability(cap, side);
    }
    var foundCapability = this.getManaGeneratorFluidHandler().getCapability(cap, side);
    if (foundCapability != null) {
      Log.throttled(100).log(l -> l.debug("connection could be established when checking for capability {}", cap.getName()));
      return foundCapability;
    }
    return super.getCapability(cap, side);
  }
  public int getNormalizedRPM() {
    var min = CommonConfig.MANA_GENERATOR_MINIMUM_RPM.get();
    if (Math.abs(this.getSpeed()) < min) return 0;
    return Math.max(CommonConfig.MANA_GENERATOR_MINIMUM_RPM.get(),
      Math.min(CommonConfig.MANA_GENERATOR_MAXIMUM_RPM.get(), (int) Math.abs(this.getSpeed())));
  }

  public IManaReceiver manaReceiverAt(BlockPos pos) {
    var blockEntity = this.level.getBlockEntity(pos);
    if (blockEntity != null && blockEntity instanceof IManaReceiver receiver) { return receiver; }
    return null;
  }

  public Collection<BlockPos> blocksToCheckForManaReceivers() {
    var positions = new ArrayList<BlockPos>(18);
    var above1 = this.worldPosition.above();
    var above2 = this.worldPosition.above().above();
    positions.add(above1);
    positions.add(above2);
    positions.add(above1.north());
    positions.add(above1.north().east());
    positions.add(above1.north().west());
    positions.add(above1.south());
    positions.add(above1.south().east());
    positions.add(above1.south().west());
    positions.add(above1.east());
    positions.add(above1.west());
    positions.add(above2.north());
    positions.add(above2.north().east());
    positions.add(above2.north().west());
    positions.add(above2.south());
    positions.add(above2.south().east());
    positions.add(above2.south().west());
    positions.add(above2.east());
    positions.add(above2.west());
    return positions;
  }
  public IManaReceiver getManaPoolAbove() {
    if (this.level == null || this.worldPosition == null) return null;
    for (var pos : blocksToCheckForManaReceivers()) {
      var blockEntity = this.level.getBlockEntity(pos);
      if (blockEntity instanceof IManaReceiver receiver) return receiver;
    }
    return null;
  }

  public BlockState getManaductBlockAbove() {
    var state = this.level.getBlockState(this.worldPosition.above());
    if (state != null && state.getBlock() instanceof BaseManaductBlock manaDuct) return state;
    return null;
  }
  public int specialHandlingViaManaduct(int manaAmount, BlockState manaDuctBlockState) {
    if (!(manaDuctBlockState.getBlock() instanceof BaseManaductBlock manaDuctBlock)) return 0;
    var maybeAggloPlateBlockState = BaseManaductBlock.getMouthPointedAtBlockState(this.level, manaDuctBlockState, this.worldPosition.above());
    if (maybeAggloPlateBlockState == null || !maybeAggloPlateBlockState.hasBlockEntity()) return 0;
    var maybeAggloPlateBlockEntity = BaseManaductBlock.getMouthPointedAtBlockEntity(this.level, manaDuctBlockState, this.worldPosition.above());
    if (!(maybeAggloPlateBlockEntity instanceof IManaReceiver receiver) || receiver.isFull()) return 0;
    receiver.receiveMana(manaAmount * manaDuctBlock.manaMultiplier);
    Log.RateLimited.of(this, 20 * 30).log(logger -> logger.info("handled {} mana via manaduct with multiplier {}", manaAmount, manaDuctBlock.manaMultiplier));
    this.duct = true;
    return manaAmount;
  }
  public int addManaToTargetPool(int manaAmount, IManaReceiver pool) {
    if (pool == null || pool.isFull()) return 0;
    pool.receiveMana(manaAmount);
    if (pool instanceof IWandable wandable) wandable.onUsedByWand(null, ItemStack.EMPTY, Direction.UP);
    Log.RateLimited.of(this, 20 * 30).log(logger -> logger.info("handled {} mana via direct transfer with mana", manaAmount));
    this.duct = false;
    return manaAmount;
  }
  public int addManaToPool(int manaAmount) {
    var manaDuctBlockState = this.getManaductBlockAbove();
    if (manaDuctBlockState != null) {
      return specialHandlingViaManaduct(manaAmount, manaDuctBlockState);
    }
    return addManaToTargetPool(manaAmount, this.getManaPoolAbove());
  }
  public boolean shouldAbortServerTick() {
    var isInInvalidState = this.isOverStressed() || this.getNormalizedRPM() == 0 || this.worldPosition == null || this.level == null;
    if (isInInvalidState) {
      Log.RateLimited.of(this, 20 * 30).log((logger) -> logger.debug("server tick aborted because RPM is 0 or is overstressed"));
      return true;
    }
    var notEnoughSpeed = Math.abs(this.getSpeed()) <= 0 || !this.isSpeedRequirementFulfilled();
    if (notEnoughSpeed) {
      Log.RateLimited.of(this, 20 * 30).log((logger) -> logger.debug("server tick aborted because RPM {} does not satisfy minimum requirement", this.getSpeed()));
      return true;
    }
    return false;
  }

  public int getManaConversionRate() { return Math.max(Math.abs(CommonConfig.MANA_GENERATOR_MANA_CONVERSION_RATE.get()), 1);  }

  public Log.RateLimited logger;
  public Log.RateLimited getLogger() { return logger; }
  public void setLogger(Log.RateLimited pLogger) { logger = pLogger; }

  public int getManaConsumedAtCurrentSpeed() {
    var conversionRate = this.getManaConversionRate();
    var manaToBeGenerated = this.getManaProductionRate();
    var manaFluidRequired = manaToBeGenerated * conversionRate;
    return manaFluidRequired;
  }

  public int ductCheckTickCount = 0;
  public void serverTick() {
    this.active = false;
    if (this.ductCheckTickCount++ > CommonConfig.MANA_GENERATOR_LAZY_TICK_RATE.get()) {
      this.ductCheckTickCount = 0;
      this.duct = getManaductBlockAbove() != null;
    }
    this.getManaGeneratorFluidHandler().serverTick();
    if (this.shouldAbortServerTick()) return;

    var conversionRate = this.getManaConversionRate();
    var manaFluidRequired = this.getManaConsumedAtCurrentSpeed();
    var manaFluidAvailable = this.getManaGeneratorFluidHandler().getManaFluidAvailable();
    var hasEnoughManaFluidToProduceMana = manaFluidRequired <= manaFluidAvailable;
    if (hasEnoughManaFluidToProduceMana) {
      var manaFluidConsumed = this.getManaGeneratorFluidHandler().drainManaFluidFromTank(manaFluidRequired);
      var realManaToBeGenerated = manaFluidConsumed / conversionRate;
      Log.RateLimited.of(this, 20 * 30).log((logger) -> logger.debug("consumed {} mana fluid to generate {} mana", manaFluidConsumed, realManaToBeGenerated));
      addManaToPool(realManaToBeGenerated);
      this.active = true;
    }
    Log.RateLimited.of(this, 20 * 30).log((logger) -> logger.debug("not enough mana fluid to produce mana, required {}, available {}", manaFluidRequired, manaFluidAvailable));
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
  public boolean isManaMachineDuct() { return this.duct; }
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
  @Override
  public void renderHUD(PoseStack ms, Minecraft mc) {
    KineticManaMachine.renderSimpleBotaniaHud(level, getBlockState(), ms, 0xff0088aa, (int) this.getManaGeneratorFluidHandler().getManaFluidAvailable(), (int) this.getManaGeneratorFluidHandler().getManaTankCapacity());
  }
}

