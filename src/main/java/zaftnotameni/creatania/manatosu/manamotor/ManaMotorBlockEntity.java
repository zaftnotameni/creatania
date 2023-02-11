package zaftnotameni.creatania.manatosu.manamotor;

import com.google.common.base.Predicates;
import com.simibubi.create.content.contraptions.base.GeneratingKineticTileEntity;
import com.simibubi.create.content.contraptions.components.motor.CreativeMotorTileEntity;
import com.simibubi.create.foundation.config.AllConfigs;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.CenteredSideValueBoxTransform;
import com.simibubi.create.foundation.tileEntity.behaviour.scrollvalue.ScrollValueBehaviour;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.api.BotaniaForgeCapabilities;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.api.mana.spark.IManaSpark;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.SparkHelper;
import vazkii.botania.api.mana.spark.SparkUpgradeType;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.util.Log;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Generates SU (from Create) when provided Mana (from Botania).
 * Mana per Tick and SU are proportional to RPM.
 *
 * If there is not enough mana, it still rotates but produces 0 SU.
 */
public class ManaMotorBlockEntity extends GeneratingKineticTileEntity implements IManaReceiver, ISparkAttachable {
  public static final boolean UPDATE_MANA_ON_EVERY_TICK = true;
  public static final boolean UPDATE_MANA_ON_LAZY_TICK = !UPDATE_MANA_ON_EVERY_TICK;
  public ManaMotorBehavior manaMotorBehavior;
  public CenteredSideValueBoxTransform shaftSlot;
  public ScrollValueBehaviour scrollValueBehaviour;
  public LazyOptional<IManaReceiver> lazyManaReceiver = LazyOptional.empty();
  public LazyOptional<ISparkAttachable> lazySparkAttachable = LazyOptional.empty();
  public int mana = 0;
  public int manaPerTick = 0;
  public boolean active = false;
  public boolean firstTick = true;

  public ManaMotorBlockEntity(BlockEntityType<? extends ManaMotorBlockEntity> type, BlockPos pos, BlockState state) {
    super(type, pos, state);
    this.setLazyTickRate(CommonConfig.MANA_MOTOR_LAZY_TICK_RATE.get());
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    if (cap == BotaniaForgeCapabilities.MANA_RECEIVER) return lazyManaReceiver.cast();
    if (cap == BotaniaForgeCapabilities.SPARK_ATTACHABLE) return lazySparkAttachable.cast();
    return super.getCapability(cap, side);
  }
  @Override
  public Level getManaReceiverLevel() { return this.getLevel(); }
  @Override
  public BlockPos getManaReceiverPos() { return this.getBlockPos(); }
  @Override
  public int getCurrentMana() { return this.mana; }
  @Override
  public boolean isFull() {return this.mana > ManaMotorConfig.getManaCap(); }
  @Override
  public void receiveMana(int pMana) { this.updateMana(this.mana + pMana); }

  public void updateMana(int newManaValue) {
    Log.LOGGER.trace("Mana changed from {} to {}", this.mana, newManaValue);
    this.mana = newManaValue;
    this.setChanged();
    this.reconsiderIfHasEnoughManaToProduceSU();
  }

  public int getMinimumManaPerTick() {
    return (int) (ManaMotorConfig.getManaPerTickPerRPM() * Math.abs(this.getGeneratedSpeed()));
  }
  public void recomputeManaPerTick() {
    var previousManaPerTick = this.manaPerTick;
    var newManaPerTick = this.getMinimumManaPerTick();
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
  public CenteredSideValueBoxTransform createShaftSlot() { return new CenteredSideValueBoxTransform((motor, side) -> motor.getValue(ManaMotorBlock.FACING) == side.getOpposite()); }
  public ScrollValueBehaviour createScrollBehavior() {
    var maxRPM = AllConfigs.SERVER.kinetics.maxMotorSpeed.get();
    var behavior = new ScrollValueBehaviour(Lang.translateDirect("generic.speed"), this, this.shaftSlot)
      .between(-maxRPM, maxRPM)
      .withUnit(i -> Lang.translateDirect("generic.unit.rpm"))
      .withCallback(this::updateGeneratedRotation)
      .withStepFunction(CreativeMotorTileEntity::step);
    behavior.value = CommonConfig.MANA_MOTOR_BASE_RPM.get();
    behavior.scrollableValue = behavior.value;
    return behavior;
  }
  @Override
  public void addBehaviours(List<TileEntityBehaviour> behaviours) {
    super.addBehaviours(behaviours);
    this.shaftSlot = this.createShaftSlot();
    this.scrollValueBehaviour = this.createScrollBehavior();
    this.manaMotorBehavior = new ManaMotorBehavior(this);
    behaviours.add(manaMotorBehavior);
    behaviours.add(scrollValueBehaviour);
  }

  @Override
  public void initialize() {
    super.initialize();
    if (!hasSource() || getGeneratedSpeed() > getTheoreticalSpeed()) updateGeneratedRotation();
  }
  public int getSpeedSafe() { return (this.scrollValueBehaviour != null) ? this.scrollValueBehaviour.getValue() : 0; }
  @Override
  public float getGeneratedSpeed() { return convertToDirection(this.getSpeedSafe(), getBlockState().getValue(ManaMotorBlock.FACING)); }
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
  public float calculateAddedStressCapacity() {
    float capacity = this.active ? ((float) ManaMotorConfig.getStressUnitsPerRPM()) : 0f;
    this.lastCapacityProvided = capacity;
    return capacity;
  }
  @Override
  public boolean canAttachSpark(ItemStack stack) { return true; }
  @Override
  public int getAvailableSpaceForMana() { return ManaMotorConfig.getManaCap() - this.mana; }
  @Override
  public IManaSpark getAttachedSpark() {
    var sparks = level.getEntitiesOfClass(Entity.class,
      new AABB(worldPosition.above(), worldPosition.above().offset(1, 1, 1)), Predicates.instanceOf(IManaSpark.class));

    if (sparks.isEmpty()) return null;
    return (IManaSpark) sparks.get(0);
  }
  @Override
  public boolean areIncomingTranfersDone() { return this.isFull(); }
}
