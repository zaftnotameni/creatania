package zaftnotameni.creatania.machines.managenerator;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import vazkii.botania.api.block.IWandHUD;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.machines.manamachine.ActiveStateSynchronizerBehavior;
import zaftnotameni.creatania.machines.manamachine.IAmManaMachine;
import zaftnotameni.creatania.machines.manamachine.IAmParticleEmittingMachine;
import zaftnotameni.creatania.machines.manamachine.KineticManaMachine;

import static zaftnotameni.creatania.machines.managenerator.ManaGeneratorMathKt.pureManaAtRpm;
import static zaftnotameni.creatania.machines.managenerator.ManaGeneratorMathKt.realManaAtRpmWhileConsuming;
import static zaftnotameni.creatania.machines.managenerator.ManaGeneratorTooltipKt.gogglesTooltip;
import static zaftnotameni.creatania.machines.managenerator.ManaGeneratorWorldQueriesKt.addManaToTargetPool;
import static zaftnotameni.creatania.machines.managenerator.ManaGeneratorWorldQueriesKt.computeManaReceiverMatchAt;
import static zaftnotameni.creatania.machines.managenerator.ManaGeneratorWorldQueriesKt.getTargetManaReceiver;
import static zaftnotameni.creatania.machines.managenerator.ManaGeneratorWorldQueriesKt.specialHandlingViaManaduct;
import static zaftnotameni.creatania.machines.manamachine.KineticManaMachine.renderSimpleBotaniaHud;
import static zaftnotameni.creatania.util.LogKt.tlog;

public class ManaGeneratorBlockEntity extends KineticBlockEntity implements IAmManaMachine, IAmParticleEmittingMachine, IWandHUD {

  public boolean isFirstTick = true;
  public boolean active;
  public boolean duct;
  public int mana;
  public ActiveStateSynchronizerBehavior<ManaGeneratorBlockEntity> activeStateSynchronizerBehavior;
  public KineticManaMachine<ManaGeneratorBlockEntity> manaMachine;
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
    if (this.manaMachine == null) {
      var manaMachine = new KineticManaMachine<>(this).withManaCap(1000)
                                                       .withManaPerRpmPerTick(CommonConfig.MANA_GENERATOR_MANA_PER_RPM_PER_TICK.get())
                                                       .withStressUnitsPerRpm(CommonConfig.MANA_GENERATOR_SU_PER_RPM.get())
                                                       .withBaseRpm(32);
      if (manaMachine != null) { this.manaMachine = manaMachine; }
    }
    return this.manaMachine;
  }

  @Override public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
    super.addToGoggleTooltip(tooltip, isPlayerSneaking);
    gogglesTooltip(tooltip, isPlayerSneaking,this);
    return true;
  }

  @Override protected void read(CompoundTag compound, boolean clientPacket) {
    super.read(compound, clientPacket);
    this.active = compound.getBoolean("active");
    this.duct = compound.getBoolean("duct");
    this.mana = compound.getInt("mana");
    this.manaGeneratorFluidHandler.read(compound, clientPacket);
  }

  @Override protected void write(CompoundTag compound, boolean clientPacket) {
    this.manaGeneratorFluidHandler.write(compound, clientPacket);
    compound.putBoolean("active", this.active);
    compound.putBoolean("duct", this.duct);
    compound.putInt("mana", this.mana);
    super.write(compound, clientPacket);
  }

  @Override public void invalidate() {
    super.invalidate();
    this.getManaGeneratorFluidHandler().invalidate();
  }

  @Override public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    super.addBehaviours(behaviours);
    this.getManaGeneratorFluidHandler().addBehaviours(behaviours);
    this.activeStateSynchronizerBehavior = new ActiveStateSynchronizerBehavior(this);
    behaviours.add(this.activeStateSynchronizerBehavior);
  }

  @Nonnull @Override public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
    var result = KineticManaMachine.handleBotaniaManaHudCapability(cap, this);
    if (result.isPresent()) return result.cast();

    if (side == null) {
      return super.getCapability(cap, null);
    }
    var block = this.getBlockState().getBlock();
    if (!(block instanceof ManaGeneratorBlock)) {
      return super.getCapability(cap, side);
    }
    if (!((ManaGeneratorBlock) block).hasShaftTowards(this.level, this.worldPosition, this.getBlockState(), side.getOpposite())) {
      return super.getCapability(cap, side);
    }
    var foundCapability = this.getManaGeneratorFluidHandler().getCapability(cap, side);
    if (foundCapability != null) {
      return foundCapability;
    }
    return super.getCapability(cap, side);
  }

  public int getNormalizedRPM() {
    return Mth.clamp(this.getManaMachineAbsoluteSpeed(), CommonConfig.MANA_GENERATOR_MINIMUM_RPM.get(), CommonConfig.MANA_GENERATOR_MAXIMUM_RPM.get());
  }

  @Override public int getManaMachineAbsoluteSpeed() {
    var min = CommonConfig.MANA_GENERATOR_MINIMUM_RPM.get();
    if (Math.abs(this.getSpeed()) < min) return 0;
    return (int) Math.abs(this.getSpeed());
  }

  public int addManaToPool(int manaAmount, ManaReceiverMatch targetReceiver) {
    if (this.level == null) return 0;
    if (targetReceiver == null) { return 0; }
    if (targetReceiver.isManaDuct()) {
      return specialHandlingViaManaduct(manaAmount, targetReceiver.getBlockstate(), this);
    }
    return addManaToTargetPool(manaAmount, targetReceiver.getReceiver(), this);
  }

  public boolean shouldAbortServerTick() {
    var isInInvalidState = this.isOverStressed() || this.getNormalizedRPM() == 0 || this.level == null;
    if (isInInvalidState) return true;
    return Math.abs(this.getSpeed()) <= 0 || !this.isSpeedRequirementFulfilled();
  }

  public int getManaConversionRate() { return Math.max(Math.abs(CommonConfig.MANA_GENERATOR_MANA_CONVERSION_RATE.get()), 1); }

  @Override public int getManaConsumptionRate() { return pureManaAtRpm(this.getSpeed()); }

  public int ductCheckTickCount = 0;

  public void serverTick() {
    this.active = false;
    if (this.level == null) return;
    if (this.ductCheckTickCount++ > CommonConfig.MANA_GENERATOR_LAZY_TICK_RATE.get()) {
      this.ductCheckTickCount = 0;
      var match = computeManaReceiverMatchAt(this.worldPosition, this.level, 0, 1, 0);
      this.duct = match != null && match.isManaDuct();
    }
    this.getManaGeneratorFluidHandler().serverTick();
    if (this.shouldAbortServerTick()) return;

    var maxFluidConsumedPossibleAtRpm = ManaGeneratorMathKt.pureManaAtRpm(getSpeed());
    var manaFluidAvailable = this.getManaGeneratorFluidHandler().getManaFluidAvailable();
    var hasEnoughManaFluidToProduceMana = manaFluidAvailable > 0;
    var targetReceiver = getTargetManaReceiver(this.worldPosition, this.level);
    if (hasEnoughManaFluidToProduceMana && targetReceiver != null) {
      var manaFluidConsumed = this.getManaGeneratorFluidHandler().drainManaFluidFromTank(maxFluidConsumedPossibleAtRpm);
      tlog(t -> t.debug("consumed " + manaFluidConsumed + " of a max possible " + maxFluidConsumedPossibleAtRpm));
      var realManaToBeGenerated = realManaAtRpmWhileConsuming(this.getSpeed(), manaFluidConsumed);
      addManaToPool(realManaToBeGenerated, targetReceiver);
      this.active = true;
    }
  }

  public void clientTick() { }

  @Override public void tick() {
    super.tick();
    isFirstTick = false;
    if (this.level == null) return;
    if (level.isClientSide()) this.clientTick();
    if (!level.isClientSide()) this.serverTick();
  }

  @Override public float calculateStressApplied() { return this.getManaMachine().calculateStressRegardless(); }

  @Override public float calculateAddedStressCapacity() {
    return 0f;
  }

  @Override public boolean isManaMachineActive() { return this.active; }

  @Override public boolean isManaMachineDuct() { return this.duct; }

  @Override public int getManaMachineMana() { return this.mana; }

  @Override public void setManaMachineLastCapacityProvided(float value) { this.lastCapacityProvided = value; }

  @Override public void setManaMachineLastStressImpact(float value) { this.lastStressApplied = value; }

  @Override public void updateMana(int value) { this.mana = value; }

  @Override public void setManaMachineMana(int value) { this.mana = value; }

  @Override public int getManaMachineGeneratedSpeed() { return 0; }

  @Override public void updateGeneratedRotation(int i) { }

  @Override public boolean shouldEmitParticles() { return true; }

  @Override public void renderHUD(PoseStack ms, Minecraft mc) {
    renderSimpleBotaniaHud(level, getBlockState(), ms, 0xff0088aa, (int) this.getManaGeneratorFluidHandler().getManaFluidAvailable(), (int) this.getManaGeneratorFluidHandler().getManaTankCapacity());
  }

}