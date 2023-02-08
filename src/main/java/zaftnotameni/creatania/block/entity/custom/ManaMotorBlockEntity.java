package zaftnotameni.creatania.block.entity.custom;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.base.GeneratingKineticTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.CenteredSideValueBoxTransform;
import com.simibubi.create.foundation.tileEntity.behaviour.scrollvalue.ScrollValueBehaviour;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.api.BotaniaForgeCapabilities;
import vazkii.botania.api.mana.IManaReceiver;
import zaftnotameni.creatania.block.custom.ManaMotorBlock;
import zaftnotameni.creatania.block.entity.ModBlockEntities;
import zaftnotameni.creatania.block.entity.custom.behavior.ManaMotorBehavior;
import zaftnotameni.creatania.util.Log;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ManaMotorBlockEntity extends GeneratingKineticTileEntity implements IManaReceiver {
  public static final int LAZY_TICK_RATE = 20;
  public static final float MANA_PER_TICK_ATTENUATION_FACTOR = 8;
  public static final float MOTOR_BASELINE_SPEED = 64;
  public static final boolean ENABLE_SCROLL_BEHAVIOR = false;
  public static final boolean UPDATE_MANA_ON_EVERY_TICK = true;
  public static final boolean UPDATE_MANA_ON_LAZY_TICK = false;
  public ManaMotorBehavior manaMotorBehavior;
  public CenteredSideValueBoxTransform shaftSlot;
  public ScrollValueBehaviour scrollValueBehaviour;
  public LazyOptional<IManaReceiver> lazyManaReceiver = LazyOptional.empty();
  public int manaCap = -1;
  public int mana = 0;
  public int manaPerRPM = 1;
  public int manaPerTick = 0;
  public boolean active = false;
  public boolean firstTick = true;


  public ManaMotorBlockEntity(BlockPos pPos, BlockState pBlockState) {
    super(ModBlockEntities.MANA_MOTOR_BLOCK_ENTITY.get(), pPos, pBlockState);
    this.setLazyTickRate(LAZY_TICK_RATE);
  }

  /**** Botania ****/
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
  public void updateMana(int newManaValue) {
    Log.LOGGER.trace("Mana changed from {} to {}", this.mana, newManaValue);
    this.mana = newManaValue;
    this.setChanged();
    this.reconsiderIfHasEnoughManaToProduceSU();
  }
  public void recomputeManaPerTick() {
    var previousManaPerTick = this.manaPerTick;
    var newManaPerTick = (int) Math.max(1f, (this.manaPerRPM / MANA_PER_TICK_ATTENUATION_FACTOR) * Math.abs(this.getGeneratedSpeed()));
    this.manaPerTick = newManaPerTick;
    if (previousManaPerTick != newManaPerTick) {
      Log.LOGGER.debug("Mana per tick changed from {} to {}", previousManaPerTick, newManaPerTick);
      this.setChanged();
    }
  }
  public void reconsiderIfHasEnoughManaToProduceSU() {
    var previousActiveState = this.active;
    var newActiveState = (this.mana > 0);
    this.active = newActiveState;
    if (previousActiveState != newActiveState) {
      Log.LOGGER.debug("Active state changed from {} to {}", previousActiveState, newActiveState);
      this.updateGeneratedRotation();
    }
  }
  @Override
  public boolean canReceiveManaFromBursts() { return !this.isFull(); }

  /*** Create ***/
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
    behavior.value = 32;
    behavior.scrollableValue = 32;
    behavior.withUnit(i -> new TextComponent("RPM"));
    behavior.withCallback(this::updateGeneratedRotation);
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
      if (magnitude >= 4) return step * 4;
      if (magnitude >= 32) return step * 4;
      if (magnitude >= 128) return step * 4;
    }
    return step;
  }

  @Override
  public void initialize() {
    super.initialize();
    if (!hasSource() || getGeneratedSpeed() > getTheoreticalSpeed()) updateGeneratedRotation();
  }

  @Override
  public float getGeneratedSpeed() {
    return convertToDirection(active ? MOTOR_BASELINE_SPEED : 0, getBlockState().getValue(ManaMotorBlock.FACING));
  }

  @Override
  protected Block getStressConfigKey() {
    super.getStressConfigKey();
    return AllBlocks.WATER_WHEEL.get();
  }

  @Override
  public void lazyTick() {
    super.lazyTick();
    if (this.level.isClientSide()) return;
    if (UPDATE_MANA_ON_LAZY_TICK) {
      this.updateMana(Math.max(0, this.mana - (this.manaPerTick * this.lazyTickRate)));
    }
  }

  public void updateGeneratedRotation() {
    super.updateGeneratedRotation();
    this.recomputeManaPerTick();
  }
  private void updateGeneratedRotation(int i) {
    this.updateGeneratedRotation();
  }

  public static void tick(Level level, BlockPos blockPos, BlockState blockState, ManaMotorBlockEntity self) {
    if (self.firstTick) {
      self.updateGeneratedRotation();
      self.initManaCap();
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
      self.updateMana(Math.max(0, self.mana - self.manaPerTick));
    }
  }

  @Override
  public void invalidateCaps() {
    super.invalidateCaps();
    this.lazyManaReceiver.invalidate();
  }

  @Override
  public void handleUpdateTag(CompoundTag tag) {
    super.handleUpdateTag(tag);
  }

  @Override
  public void onLoad() {
    super.onLoad();
    lazyManaReceiver = LazyOptional.of(() -> this);
  }


  public void initManaCap() {
    if (manaCap == -1) { this.manaCap = 10000000; }
  }

  @Override
  public void onSpeedChanged(float previousSpeed) {
    super.onSpeedChanged(previousSpeed);
    this.recomputeManaPerTick();
    Log.LOGGER.debug("Speed changed from {} to {}", previousSpeed, this.getSpeed());
  }
}
