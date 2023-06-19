package zaftnotameni.creatania.machines.manamotor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.api.BotaniaForgeCapabilities;
import vazkii.botania.api.block.IWandHUD;
import vazkii.botania.api.block.IWandable;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.api.mana.spark.IManaSpark;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.machines.manamachine.ActiveStateSynchronizerBehavior;
import zaftnotameni.creatania.machines.manamachine.IAmManaMachine;
import zaftnotameni.creatania.machines.manamachine.KineticManaMachine;

import static zaftnotameni.creatania.machines.manamotor.ManaMotorTooltipKt.gogglesTooltip;
import static zaftnotameni.creatania.util.LogKt.log;

/**
 * Generates SU (from Create) when provided Mana (from Botania).
 * Mana per Tick and SU are proportional to RPM.
 *
 * If there is not enough mana, it still rotates but produces 0 SU.
 */
public class ManaMotorBlockEntity extends GeneratingKineticBlockEntity implements IManaReceiver, IManaPool, ISparkAttachable, IAmManaMachine, IWandHUD, IWandable {
  public static final boolean UPDATE_MANA_ON_EVERY_TICK = true;
  public static final boolean UPDATE_MANA_ON_LAZY_TICK = !UPDATE_MANA_ON_EVERY_TICK;
  public ManaMotorBehavior manaMotorBehavior;
  public ScrollValueBehaviour scrollValueBehaviour;
  public ActiveStateSynchronizerBehavior activeStateSynchronizerBehavior;
  public LazyOptional<IManaReceiver> lazyManaReceiver = LazyOptional.empty();
  public LazyOptional<ISparkAttachable> lazySparkAttachable = LazyOptional.empty();
  public LazyOptional<IWandable> lazyWandable = LazyOptional.empty();
  public int mana = 0;
  public int manaPerTick = 0;
  public boolean active = false;
  public boolean firstTick = true;
  public KineticManaMachine manaMachine;

  public ManaMotorBlockEntity(BlockEntityType<? extends ManaMotorBlockEntity> type, BlockPos pos, BlockState state) {
    super(type, pos, state);
    this.setLazyTickRate(CommonConfig.MANA_MOTOR_LAZY_TICK_RATE.get());
  }
  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    var block = this.getBlockState().getBlock();
    if (!(block instanceof ManaMotorBlock motorBlock)) return super.getCapability(cap, side);
    if (cap == BotaniaForgeCapabilities.WANDABLE) return lazyWandable.cast();
    if (cap == BotaniaForgeCapabilities.SPARK_ATTACHABLE) return lazySparkAttachable.cast();

    var result = KineticManaMachine.handleBotaniaManaHudCapability(cap, this);
    if (result.isPresent()) return result.cast();

    if (side == null) return super.getCapability(cap, null);
    var sameAxisAsShaft =
      motorBlock.hasShaftTowards(this.level, this.worldPosition, this.getBlockState(), side.getOpposite()) ||
      motorBlock.hasShaftTowards(this.level, this.worldPosition, this.getBlockState(), side);

    if (cap == BotaniaForgeCapabilities.MANA_RECEIVER && !sameAxisAsShaft) return lazyManaReceiver.cast();
    return super.getCapability(cap, side);
  }
  @Override
  public Level getManaReceiverLevel() { return this.getLevel(); }
  @Override
  public BlockPos getManaReceiverPos() { return this.getBlockPos(); }
  @Override
  public int getCurrentMana() { return this.mana; }
  @Override
  public boolean isFull() { return this.getManaMachine().isFull(); }
  public KineticManaMachine<ManaMotorBlockEntity> getManaMachine() {
     if (this.manaMachine == null) this.manaMachine = new KineticManaMachine<>(this)
      .withManaCap(ManaMotorConfig.getManaCap())
      .withManaPerRpmPerTick(ManaMotorConfig.getManaPerTickPerRPM())
      .withRpmPerManaPerTick(ManaMotorConfig.getRPMPerManaPerTick())
      .withStressUnitsPerRpm(ManaMotorConfig.getStressUnitsPerRPM())
      .withBaseRpm(CommonConfig.MANA_MOTOR_BASE_RPM.get());
     return this.manaMachine;
  }
  @Override
  public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
    var parent = super.addToGoggleTooltip(tooltip, isPlayerSneaking);
    gogglesTooltip(tooltip, isPlayerSneaking, this);
    return parent;
  }


  @Override
  protected void read(CompoundTag nbt, boolean clientPacket) {
    super.read(nbt, clientPacket);
    this.mana = nbt.getInt("mana");
    this.manaPerTick = nbt.getInt("manaPerTick");
    this.active = nbt.getBoolean("active");
  }

  @Override
  protected void write(CompoundTag nbt, boolean clientPacket) {
    nbt.putInt("mana", this.mana);
    nbt.putInt("manaPerTick", this.manaPerTick);
    nbt.putBoolean("active", this.active);
    super.write(nbt, clientPacket);
  }

  @Override
  public void receiveMana(int pMana) { this.getManaMachine().receiveMana(pMana); }
  public void updateMana(int newManaValue) {
    this.getManaMachine().updateMana(newManaValue);
    this.reconsiderIfHasEnoughManaToProduceSU();
  }
  @Override
  public void setManaMachineMana(int value) {
    var changed = this.mana != value;
    this.mana = value;
    if (changed) sendData();
  }
  @Override
  public int getManaMachineAbsoluteSpeed() {
    return (int) Math.abs(this.getSpeed());
  }
  @Override
  public int getManaMachineGeneratedSpeed() { return (int) this.getGeneratedSpeed(); }
  public void recomputeManaPerTick() {
    var previousManaPerTick = this.manaPerTick;
    var newManaPerTick = this.getManaMachine().getMinimumManaPerTick();
    this.manaPerTick = newManaPerTick;
    if (previousManaPerTick != newManaPerTick) {
      this.setChanged();
    }
  }
  public void reconsiderIfHasEnoughManaToProduceSU() {
    var previousActiveState = this.active;
    var newActiveState = this.mana > this.manaPerTick;
    this.active = newActiveState;
    if (previousActiveState != newActiveState) {
      this.updateGeneratedRotation();
    }
  }
  @Override
  public boolean canReceiveManaFromBursts() { return !this.isFull(); }

  @NotNull
  @Override
  public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) { return super.getCapability(cap); }

  @Override
  public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    super.addBehaviours(behaviours);
    this.scrollValueBehaviour = this.getManaMachine().createScrollBehavior(BlockStateProperties.FACING);
    this.manaMotorBehavior = new ManaMotorBehavior(this);
    this.activeStateSynchronizerBehavior = new ActiveStateSynchronizerBehavior(this);
    behaviours.add(manaMotorBehavior);
    behaviours.add(scrollValueBehaviour);
    behaviours.add(activeStateSynchronizerBehavior);
  }

  @Override
  public void initialize() {
    super.initialize();
    if (!hasSource() || getGeneratedSpeed() > getTheoreticalSpeed()) updateGeneratedRotation();
  }
  @Override
  public float getGeneratedSpeed() { return this.getManaMachine().getGeneratedSpeed(BlockStateProperties.FACING); }
  @Override
  public void lazyTick() {
    super.lazyTick();
    if (this.level == null || this.level.isClientSide()) return;
    if (UPDATE_MANA_ON_LAZY_TICK) {
      var newMana = Math.max(0, this.mana - (this.manaPerTick * this.lazyTickRate));
      this.updateMana(newMana);
    }
  }
  public void updateGeneratedRotation(int i) { if (this.level != null) this.updateGeneratedRotation(); }

  @Override
  public void invalidateCaps() {
    super.invalidateCaps();
    this.lazyManaReceiver.invalidate();
    this.lazySparkAttachable.invalidate();
    this.lazyWandable.invalidate();
  }

  @Override
  public void onLoad() {
    super.onLoad();
    lazyManaReceiver = LazyOptional.of(() -> this);
    lazySparkAttachable = LazyOptional.of(() -> this);
    lazyWandable = LazyOptional.of(() -> this);
  }
  @Override
  public void onSpeedChanged(float previousSpeed) {
    super.onSpeedChanged(previousSpeed);
    this.recomputeManaPerTick();
    log(l -> l.debug("Speed changed from {} to {}", previousSpeed, this.scrollValueBehaviour.getValue()));
  }
  @Override
  public float calculateStressApplied() { return 0f; }
  @Override
  public float calculateAddedStressCapacity() { return this.getManaMachine().calculateStressActiveOnly(); }
  @Override
  public boolean canAttachSpark(ItemStack stack) { return true; }
  @Override
  public int getAvailableSpaceForMana() { return this.getManaMachine().getAvailableSpaceForMana(); }
  @Override
  public IManaSpark getAttachedSpark() { return this.getManaMachine().getAttachedSpark(); }
  @Override
  public boolean areIncomingTranfersDone() { return this.isFull(); }
  @Override
  public boolean isManaMachineActive() { return this.active; }
  @Override
  public boolean isManaMachineDuct() { return false; }
  @Override
  public int getManaMachineMana() { return this.mana; }
  @Override
  public void setManaMachineLastCapacityProvided(float value) { this.lastCapacityProvided = value; }
  @Override
  public void setManaMachineLastStressImpact(float value) { this.lastStressApplied = value; }
  @Override
  public void renderHUD(PoseStack ms, Minecraft mc) {
    KineticManaMachine.renderSimpleBotaniaHud(level, getBlockState(), ms, 0xffff0000, this.getCurrentMana(), this.getManaMachine().manaCap);
  }
  public boolean isValidBinding() {
    // noinspection ConstantConditions,deprecation,deprecation
    return true;
  }
  @Override
  public boolean onUsedByWand(@org.jetbrains.annotations.Nullable Player player, ItemStack stack, Direction side) {
    return true;
  }
  @Override
  public boolean isOutputtingPower() {
    return false;
  }
  @Override
  public DyeColor getColor() {
    return DyeColor.PURPLE;
  }
  @Override
  public void setColor(DyeColor color) {

  }
  @Override
  public int getManaConsumptionRate() {
    return this.manaPerTick;
  }
}