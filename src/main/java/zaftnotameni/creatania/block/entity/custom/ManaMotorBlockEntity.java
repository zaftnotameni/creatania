package zaftnotameni.creatania.block.entity.custom;

import com.simibubi.create.content.contraptions.base.GeneratingKineticTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.CenteredSideValueBoxTransform;
import com.simibubi.create.foundation.tileEntity.behaviour.scrollvalue.ScrollValueBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.api.BotaniaForgeCapabilities;
import vazkii.botania.api.mana.IManaReceiver;
import zaftnotameni.creatania.block.custom.ManaMotorBlock;
import zaftnotameni.creatania.block.entity.ModBlockEntities;
import zaftnotameni.creatania.block.entity.custom.behavior.ManaMotorBehavior;
import zaftnotameni.creatania.config.ModCommonConfigs;
import zaftnotameni.creatania.util.Log;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ManaMotorBlockEntity extends GeneratingKineticTileEntity implements IManaReceiver {
  public static final boolean ENABLE_SCROLL_BEHAVIOR = true;
  public static final boolean UPDATE_MANA_ON_EVERY_TICK = true;
  public static final boolean UPDATE_MANA_ON_LAZY_TICK = !UPDATE_MANA_ON_EVERY_TICK;
  public ManaMotorBehavior manaMotorBehavior;
  public CenteredSideValueBoxTransform shaftSlot;
  public ScrollValueBehaviour scrollValueBehaviour;
  public LazyOptional<IManaReceiver> lazyManaReceiver = LazyOptional.empty();
  public int stressUnitsPerRPM;
  public int manaPerTickPerRPM;
  public int minimumSafeThreshold;
  public int minimumSafeThresholdPool;
  public int manaCap;
  public int mana = 0;
  public int manaPerTick = 0;
  public boolean active = false;
  public boolean firstTick = true;
  public boolean isAboveThreshold = false;
  public int isBelowThresholdCount = 0;


  public ManaMotorBlockEntity(BlockPos pPos, BlockState pBlockState) {
    super(ModBlockEntities.MANA_MOTOR_BLOCK_ENTITY.get(), pPos, pBlockState);
    this.stressUnitsPerRPM = ModCommonConfigs.MANA_MOTOR_SU_PER_RPM.get();
    this.manaPerTickPerRPM = ModCommonConfigs.MANA_MOTOR_MANA_PER_TICK_PER_RPM.get();
    this.minimumSafeThreshold = ModCommonConfigs.MANA_MOTOR_MIN_MANA_RESERVE_FACTOR.get();
    this.minimumSafeThresholdPool = ModCommonConfigs.MANA_MOTOR_MIN_MANA_RESERVE_POOL.get();
    this.manaCap = ModCommonConfigs.MANA_MOTOR_MAX_MANA_STORAGE.get();
    this.isAboveThreshold = false;
    this.setLazyTickRate(ModCommonConfigs.MANA_MOTOR_LAZY_TICK_RATE.get());
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
    return this.mana > this.manaCap;
  }
  @Override
  public void receiveMana(int pMana) { this.updateMana(this.mana + pMana); }

  public int getMinimumSafeThresholdForMana() {
    return Math.max(this.minimumSafeThreshold * this.getMinimumManaPerTick(), this.minimumSafeThresholdPool);
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
      if (this.isBelowThresholdCount < (this.minimumSafeThreshold / 4f)) Log.LOGGER.debug("Mana is below threshold for {} ticks {}", this.isBelowThresholdCount, this.mana);
    }
    this.setChanged();
    this.reconsiderIfHasEnoughManaToProduceSU();
  }

  public int getMinimumManaPerTick() {
    return (int) (this.manaPerTickPerRPM * Math.abs(this.getGeneratedSpeed()));
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
    var newActiveState = this.isBelowThresholdCount < (ModCommonConfigs.MANA_MOTOR_MIN_MANA_RESERVE_FACTOR.get() / 2);
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
    var behavior = new ScrollValueBehaviour(new TextComponent("speed"), this, this.shaftSlot);
    behavior.between(-256, 256);
    behavior.value = ModCommonConfigs.MANA_MOTOR_BASE_RPM.get();
    behavior.scrollableValue = behavior.value;
    behavior.withUnit(i -> new TextComponent("RPM"));
    behavior.withCallback(i -> this.updateGeneratedRotation());
    behavior.withStepFunction(ManaMotorBlockEntity::step);
    return behavior;
  }
  @Override
  public void addBehaviours(List<TileEntityBehaviour> behaviours) {
    super.addBehaviours(behaviours);
    this.shaftSlot = this.createShaftSlot();
    this.scrollValueBehaviour = this.createScrollBehavior();
    this.manaMotorBehavior = new ManaMotorBehavior(this);
    behaviours.add(manaMotorBehavior);
    if (ENABLE_SCROLL_BEHAVIOR) behaviours.add(scrollValueBehaviour);
  }

  public static int step(ScrollValueBehaviour.StepContext context) {
    int current = context.currentValue;
    int step = 1;

    if (!context.shift) {
      int magnitude = Math.abs(current) - (context.forward == current > 0 ? 0 : 1);

      if (magnitude >= 4)
        step *= 4;
      if (magnitude >= 32)
        step *= 4;
      if (magnitude >= 128)
        step *= 4;
    }

    return (int) (current + (context.forward ? step : -step) == 0 ? step + 1 : step);
  }

  @Override
  public void initialize() {
    super.initialize();
    if (!hasSource() || getGeneratedSpeed() > getTheoreticalSpeed()) updateGeneratedRotation();

    if (this.scrollValueBehaviour != null) {
      this.capacity = this.scrollValueBehaviour.getValue() * this.stressUnitsPerRPM;
    }
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

  private void updateGeneratedRotation(int i) { this.updateGeneratedRotation(); }
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

  @Override
  public float calculateAddedStressCapacity() {
    float capacity = Math.abs(this.scrollValueBehaviour.getValue() * (this.stressUnitsPerRPM/64f));
    this.lastCapacityProvided = capacity;
    return capacity;
  }
}
