package zaftnotameni.creatania.machines.manamachine;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import net.minecraft.nbt.CompoundTag;
public class ActiveStateSynchronizerBehavior<X extends SmartBlockEntity & IAmManaMachine> extends BlockEntityBehaviour{
  public static final BehaviourType<BlockEntityBehaviour> TYPE = new BehaviourType<>();
  public final X x;
  public boolean active;
  public boolean duct;
  public boolean wasActive1;
  public boolean wasActive2;
  public boolean wasDuct1;
  public boolean wasDuct2;
  public ActiveStateSynchronizerBehavior(X te) {
    super(te);
    this.x = te;
  }
  @Override
  public BehaviourType<?> getType() { return TYPE; }
  @Override
  public void read(CompoundTag nbt, boolean clientPacket) {
    super.read(nbt, clientPacket);
    this.active = nbt.getBoolean("active");
    this.duct = nbt.getBoolean("duct");
  }
  @Override
  public void write(CompoundTag nbt, boolean clientPacket) {
    nbt.putBoolean("active", this.active);
    nbt.putBoolean("duct", this.duct);
    super.write(nbt, clientPacket);
  }
  @Override
  public void tick() {
    this.wasActive1 = this.wasActive2;
    this.wasActive2 = this.active;
    this.wasDuct1 = this.wasDuct2;
    this.wasDuct2 = this.duct;
    this.active = x.isManaMachineActive();
    this.duct = x.isManaMachineDuct();
    if (this.wasActive2 != this.active || this.wasDuct2 != this.duct) {
      this.blockEntity.setChanged();
      this.blockEntity.sendData();
    }
    super.tick();
  }
}
