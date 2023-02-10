package zaftnotameni.creatania.manatosu.manamotor;

import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.BehaviourType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import static zaftnotameni.creatania.manatosu.manamotor.ManaMotorBlockEntity.UPDATE_MANA_ON_EVERY_TICK;

public class ManaMotorBehavior extends TileEntityBehaviour {
  public static final BehaviourType<TileEntityBehaviour> TYPE = new BehaviourType<>();
  public ManaMotorBlockEntity motor;

  public ManaMotorBehavior(ManaMotorBlockEntity pMotor) {
    super(pMotor);
    this.motor = pMotor;
  }

  @Override
  public void read(CompoundTag nbt, boolean clientPacket) {
    super.read(nbt, clientPacket);
    this.motor.mana = nbt.getInt("mana");
    this.motor.active = nbt.getBoolean("active");
  }

  @Override
  public void write(CompoundTag nbt, boolean clientPacket) {
    nbt.putInt("mana", this.motor.mana);
    nbt.putBoolean("active", this.motor.active);
    super.write(nbt, clientPacket);
  }

  @Override
  public BehaviourType<?> getType() {
    return TYPE;
  }

  @Override
  public void tick() {
    super.tick();
    ManaMotorBehavior.tick(this.motor.getLevel(), this.motor.getBlockPos(), this.motor.getBlockState(), this.motor);
  }
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
}
