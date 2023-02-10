package zaftnotameni.creatania.manatosu.manamotor;

import com.simibubi.create.content.contraptions.base.GeneratingKineticTileEntity;
import com.simibubi.create.content.contraptions.components.motor.CreativeMotorTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.CenteredSideValueBoxTransform;
import com.simibubi.create.foundation.tileEntity.behaviour.scrollvalue.ScrollValueBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.api.BotaniaForgeCapabilities;
import vazkii.botania.api.mana.IManaReceiver;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.util.Log;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ManaMotorBlockEntity extends GeneratingKineticTileEntity implements IManaReceiver {
  public static final boolean UPDATE_MANA_ON_EVERY_TICK = true;
  public static final boolean UPDATE_MANA_ON_LAZY_TICK = !UPDATE_MANA_ON_EVERY_TICK;
  public ManaMotorBehavior manaMotorBehavior;
  public CenteredSideValueBoxTransform shaftSlot;
  public ScrollValueBehaviour scrollValueBehaviour;
  public LazyOptional<IManaReceiver> lazyManaReceiver = LazyOptional.empty();
  public int mana = 0;
  public int manaPerTick = 0;
  public boolean active = false;
  public boolean firstTick = true;
  public boolean isAboveThreshold = false;
  public int isBelowThresholdCount = 0;

  public ManaMotorBlockEntity(BlockEntityType<? extends ManaMotorBlockEntity> type, BlockPos pos, BlockState state) {
    super(type, pos, state);
    this.isAboveThreshold = false;
    this.setLazyTickRate(CommonConfig.MANA_MOTOR_LAZY_TICK_RATE.get());
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    if (cap == BotaniaForgeCapabilities.MANA_RECEIVER) {
      return lazyManaReceiver.cast();
    }
    return super.getCapability(cap, side);
  }
  @Override
  public Level getManaReceiverLevel() {
    return this.getLevel();
  }
  @Override
  public BlockPos getManaReceiverPos() {
    return this.getBlockPos();
  }
  @Override
  public int getCurrentMana() { return this.mana; }
  @Override
  public boolean isFull() {
    return this.mana > ManaMotorConfig.getManaCap();
  }
  @Override
  public void receiveMana(int pMana) { this.updateMana(this.mana + pMana); }

  public int getMinimumSafeThresholdForMana() {
    return Math.max(ManaMotorConfig.getMinimumSafeThreshold() * this.getMinimumManaPerTick(), ManaMotorConfig.getMinimumSafeThresholdPool());
  }
  public void updateMana(int newManaValue) {
    Log.LOGGER.trace("Mana changed from {} to {}", this.mana, newManaValue);
    this.mana = newManaValue;
    if (this.mana > this.getMinimumSafeThresholdForMana()) {
      if (!this.isAboveThreshold) Log.LOGGER.debug("Mana is above threshold for the first time {}", this.mana);
      this.isAboveThreshold = true;
      this.isBelowThresholdCount = 0;
    } else {
      this.isAboveThreshold = false;
      this.isBelowThresholdCount += 1;
      if (this.isBelowThresholdCount < (ManaMotorConfig.getMinimumSafeThreshold() / 4f)) Log.LOGGER.debug("Mana is below threshold for {} ticks {}", this.isBelowThresholdCount, this.mana);
    }
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
    var newActiveState = this.isBelowThresholdCount < (CommonConfig.MANA_MOTOR_MIN_MANA_RESERVE_FACTOR.get() / 2);
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
  public CenteredSideValueBoxTransform createShaftSlot() {
    var slot = new CenteredSideValueBoxTransform((motor, side) -> motor.getValue(ManaMotorBlock.FACING) == side.getOpposite());
    return slot;
  }
  public ScrollValueBehaviour createScrollBehavior() {
    var behavior = new ScrollValueBehaviour(new TextComponent("speed"), this, this.shaftSlot)
      .between(-256, 256)
      .withUnit(i -> new TextComponent("RPM"))
      .withCallback(this::updateGeneratedRotation)
      .withClientCallback((i) -> Log.LOGGER.info("clientCallback"))
      .withStepFunction(CreativeMotorTileEntity::step);
    behavior.setValue(CommonConfig.MANA_MOTOR_BASE_RPM.get());
    return behavior;
  }
  @Override
  public void addBehaviours(List<TileEntityBehaviour> behaviours) {
    super.addBehaviours(behaviours);
    this.shaftSlot = this.createShaftSlot();
    this.scrollValueBehaviour = this.createScrollBehavior();
    // this.manaMotorBehavior = new ManaMotorBehavior(this);
    // behaviours.add(manaMotorBehavior);
    behaviours.add(scrollValueBehaviour);
  }

  @Override
  public void initialize() {
    super.initialize();
    if (!hasSource() || getGeneratedSpeed() > getTheoreticalSpeed()) updateGeneratedRotation();
  }
  @Override
  public float getGeneratedSpeed() {
    return convertToDirection((this.scrollValueBehaviour != null && this.active) ? this.scrollValueBehaviour.getValue() : 0, getBlockState().getValue(ManaMotorBlock.FACING));
  }
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
  public void updateGeneratedRotation(int i) {
    if (this.level != null) this.updateGeneratedRotation();
  }
  @Override
  public void updateGeneratedRotation() { super.updateGeneratedRotation(); }
  public static void tick(Level level, BlockPos blockPos, BlockState blockState, ManaMotorBlockEntity self) {
    if (self.firstTick) {
      self.updateGeneratedRotation();
      self.firstTick = false;
    }
    if (level.isClientSide()) {
      tickClient(level, blockPos, blockState, self);
    } else {
      tickServer(level, blockPos, blockState, self);
    }
  }

  public static void tickClient(Level level, BlockPos blockPos, BlockState blockState, ManaMotorBlockEntity self) {

  }

  public static void tickServer(Level level, BlockPos blockPos, BlockState blockState, ManaMotorBlockEntity self) {
    if (UPDATE_MANA_ON_EVERY_TICK) {
      if (self.active) self.updateMana(Math.max(0, self.mana - self.manaPerTick));
    }
  }

  @Override
  public void invalidateCaps() {
    super.invalidateCaps();
    this.lazyManaReceiver.invalidate();
  }

  @Override
  public void onLoad() {
    super.onLoad();
    lazyManaReceiver = LazyOptional.of(() -> this);
  }

  @Override
  public void onSpeedChanged(float previousSpeed) {
    super.onSpeedChanged(previousSpeed);
    this.recomputeManaPerTick();
    Log.LOGGER.debug("Speed changed from {} to {}", previousSpeed, this.scrollValueBehaviour.getValue());
  }
  @Override
  public float calculateStressApplied() {
    return 0f;
  }
}
