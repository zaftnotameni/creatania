package zaftnotameni.creatania.block.entity.custom;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.base.GeneratingKineticTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.tileEntity.behaviour.CenteredSideValueBoxTransform;
import com.simibubi.create.foundation.tileEntity.behaviour.scrollvalue.ScrollValueBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.api.BotaniaForgeCapabilities;
import vazkii.botania.api.mana.IManaReceiver;
import zaftnotameni.creatania.block.custom.ManaMotorBlock;
import zaftnotameni.creatania.block.entity.ModBlockEntities;
import zaftnotameni.creatania.block.entity.custom.behavior.ManaMotorBehavior;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ManaMotorBlockEntity extends GeneratingKineticTileEntity implements IManaReceiver {
  public ManaMotorBehavior manaMotorBehavior;
  public ScrollValueBehaviour scrollValueBehaviour;
  public LazyOptional<IManaReceiver> lazyManaReceiver = LazyOptional.empty();
  public int manaCap = -1;
  public int mana = 0;
  public boolean active = false;
  public boolean firstTick = true;
  public int manaConsumptionPerTick = 1;

  public ManaMotorBlockEntity(BlockPos pPos, BlockState pBlockState) {
    super(ModBlockEntities.MANA_MOTOR_BLOCK_ENTITY.get(), pPos, pBlockState);
  }

  public static final String TAG_MANA = "mana";
  public static final String TAG_MANA_CAP = "mana_cap";

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
  public int getCurrentMana() {
    return this.mana;
  }

  @Override
  public boolean isFull() {
    return this.mana > this.manaCap;
  }

  @Override
  public void receiveMana(int pMana) {
    this.mana += pMana;
    this.setChanged();
    if (this.mana > 0 && !this.active) {
      this.active = true;
      this.updateGeneratedRotation();
    }
  }

  @Override
  public boolean canReceiveManaFromBursts() {
    return !this.isFull();
  }

  @NotNull
  @Override
  public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
    return super.getCapability(cap);
  }

  @Override
  public void addBehaviours(List<TileEntityBehaviour> behaviours) {
    super.addBehaviours(behaviours);
    CenteredSideValueBoxTransform slot =
      new CenteredSideValueBoxTransform((motor, side) -> motor.getValue(ManaMotorBlock.FACING) == side.getOpposite());

    scrollValueBehaviour = new ScrollValueBehaviour(new TextComponent("speed"), this, slot);
    scrollValueBehaviour.between(-256, 256);
    scrollValueBehaviour.value = 32;
    scrollValueBehaviour.scrollableValue = 32;
    scrollValueBehaviour.withUnit(i -> new TextComponent("RPM"));
    scrollValueBehaviour.withCallback(this::updateGeneratedRotation);
    scrollValueBehaviour.withStepFunction(ManaMotorBlockEntity::step);
    
    manaMotorBehavior = new ManaMotorBehavior(this, slot);
    behaviours.add(manaMotorBehavior);
    behaviours.add(scrollValueBehaviour);
  }

  private void updateGeneratedRotation(Integer i) {
    this.updateGeneratedRotation();
  }

  private static Integer step(ScrollValueBehaviour.StepContext context) {
    int current = context.currentValue;
    int step = 1;
    if (!context.shift) {
      int magnitude = Math.abs(current) - (context.forward == current > 0 ? 0 : 1);
      if (magnitude >= 4) step *= 4;
      if (magnitude >= 32) step *= 4;
      if (magnitude >= 128) step *= 4;
    }
    return (int) step;
  }

  @Override
  public void initialize() {
    super.initialize();
    if (!hasSource() || getGeneratedSpeed() > getTheoreticalSpeed())
      updateGeneratedRotation();
  }

  @Override
  public float getGeneratedSpeed() {
    return convertToDirection(active ? 64 : 0, getBlockState().getValue(ManaMotorBlock.FACING));
  }

  @Override
  protected Block getStressConfigKey() {
    return AllBlocks.WATER_WHEEL.get();
  }

  @Override
  public void lazyTick() {
    super.lazyTick();
  }

  public float calculateAddedStressCapacity() {
    float capacity = (8 * 256f) / 256f;
    this.lastCapacityProvided = capacity;
    return capacity;
  }

  public void updateGeneratedRotation() {
    super.updateGeneratedRotation();
  }

  public void drops() {
  }

  public static void tick(Level level, BlockPos blockPos, BlockState blockState, ManaMotorBlockEntity self) {
    if (self.firstTick) {
      self.updateGeneratedRotation();
      self.firstTick = false;
    }
    self.initManaCap();
    if (level.isClientSide()) {
      tickClient(level, blockPos, blockState, self);
    } else {
      tickServer(level, blockPos, blockState, self);
    }
  }

  public static void tickClient(Level level, BlockPos blockPos, BlockState blockState, ManaMotorBlockEntity self) {

  }

  public static void tickServer(Level level, BlockPos blockPos, BlockState blockState, ManaMotorBlockEntity self) {
    self.mana = Math.max(0, self.mana - self.manaConsumptionPerTick);
    if (self.mana > 0 && !self.active) {
      self.active = true;
      self.updateGeneratedRotation();
      self.setChanged();
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
    if (manaCap == -1) {
      manaCap = 10000000;
    }
  }

  public int getGeneratedStress() {
    return (int) calculateAddedStressCapacity();
  }
}
