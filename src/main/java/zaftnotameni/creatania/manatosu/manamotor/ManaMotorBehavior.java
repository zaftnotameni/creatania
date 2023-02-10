package zaftnotameni.creatania.manatosu.manamotor;

import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.BehaviourType;
import net.minecraft.nbt.CompoundTag;

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
}