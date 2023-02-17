package zaftnotameni.creatania.manatosu.manamotor;

import com.simibubi.create.content.contraptions.base.GeneratingKineticTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.scrollvalue.ScrollValueBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.api.BotaniaForgeCapabilities;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.api.mana.spark.IManaSpark;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.util.Log;
import zaftnotameni.sharedbehaviors.ActiveStateSynchronizerBehavior;
import zaftnotameni.sharedbehaviors.IAmManaMachine;
import zaftnotameni.sharedbehaviors.KineticManaMachine;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Generates SU (from Create) when provided Mana (from Botania).
 * Mana per Tick and SU are proportional to RPM.
 *
 * If there is not enough mana, it still rotates but produces 0 SU.
 */
public class ManaMotorBlockEntity extends GeneratingKineticTileEntity implements IManaReceiver, ISparkAttachable, IAmManaMachine {
  public static final boolean UPDATE_MANA_ON_EVERY_TICK = true;
  public static final boolean UPDATE_MANA_ON_LAZY_TICK = !UPDATE_MANA_ON_EVERY_TICK;
  public ManaMotorBehavior manaMotorBehavior;
  public ScrollValueBehaviour scrollValueBehaviour;
  public ActiveStateSynchronizerBehavior activeStateSynchronizerBehavior;
  public LazyOptional<IManaReceiver> lazyManaReceiver = LazyOptional.empty();
  public LazyOptional<ISparkAttachable> lazySparkAttachable = LazyOptional.empty();
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

    if (cap == BotaniaForgeCapabilities.SPARK_ATTACHABLE) return lazySparkAttachable.cast();
    if (side == null) return super.getCapability(cap, side);
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
  public void receiveMana(int pMana) { this.getManaMachine().receiveMana(pMana); }
  public void updateMana(int newManaValue) {
    this.getManaMachine().updateMana(newManaValue);
    this.reconsiderIfHasEnoughManaToProduceSU();
  }
  @Override
  public void setManaMachineMana(int value) { this.mana = value; }
  @Override
  public int getManaMachineGeneratedSpeed() { return (int) this.getGeneratedSpeed(); }
  public void recomputeManaPerTick() {
    var previousManaPerTick = this.manaPerTick;
    var newManaPerTick = this.getManaMachine().getMinimumManaPerTick();
    this.manaPerTick = newManaPerTick;
    if (previousManaPerTick != newManaPerTick) {
      Log.LOGGER.debug("Mana per tick changed from {} to {}", previousManaPerTick, newManaPerTick);
      this.setChanged();
    }
  }
  public void reconsiderIfHasEnoughManaToProduceSU() {
    var previousActiveState = this.active;
    var newActiveState = this.mana > this.manaPerTick;
    this.active = newActiveState;
    if (previousActiveState != newActiveState) {
      Log.LOGGER.debug("Active state changed from {} to {}", previousActiveState, newActiveState);
      this.updateGeneratedRotation();
    }
  }
  @Override
  public boolean canReceiveManaFromBursts() { return !this.isFull(); }

  @NotNull
  @Override
  public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) { return super.getCapability(cap); }

  @Override
  public void addBehaviours(List<TileEntityBehaviour> behaviours) {
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
      Log.LOGGER.debug("Mana changing on lazy tick from {} to {}", this.mana, newMana);
      this.updateMana(newMana);
    }
  }
  public void updateGeneratedRotation(int i) { if (this.level != null) this.updateGeneratedRotation(); }

  @Override
  public void invalidateCaps() {
    super.invalidateCaps();
    this.lazyManaReceiver.invalidate();
    this.lazySparkAttachable.invalidate();
  }

  @Override
  public void onLoad() {
    super.onLoad();
    lazyManaReceiver = LazyOptional.of(() -> this);
    lazySparkAttachable = LazyOptional.of(() -> this);
  }
  @Override
  public void onSpeedChanged(float previousSpeed) {
    super.onSpeedChanged(previousSpeed);
    this.recomputeManaPerTick();
    Log.LOGGER.debug("Speed changed from {} to {}", previousSpeed, this.scrollValueBehaviour.getValue());
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
  public int getManaMachineMana() { return this.mana; }
  @Override
  public void setManaMachineLastCapacityProvided(float value) { this.lastCapacityProvided = value; }
  @Override
  public void setManaMachineLastStressImpact(float value) { this.lastStressApplied = value; }
}
