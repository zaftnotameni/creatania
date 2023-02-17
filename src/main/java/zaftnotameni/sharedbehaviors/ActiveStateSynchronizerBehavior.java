package zaftnotameni.sharedbehaviors;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.BehaviourType;
import net.minecraft.nbt.CompoundTag;
public class ActiveStateSynchronizerBehavior<X extends SmartTileEntity & IAmManaMachine> extends TileEntityBehaviour{
  public static final BehaviourType<TileEntityBehaviour> TYPE = new BehaviourType<>();
  public final X x;
  public boolean active;
  public boolean wasActive;
  public boolean wasActive1;
  public boolean wasActive2;
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
  }
  @Override
  public void write(CompoundTag nbt, boolean clientPacket) {
    nbt.putBoolean("active", this.active);
    super.write(nbt, clientPacket);
  }
  @Override
  public void tick() {
    this.wasActive1 = this.wasActive2;
    this.wasActive2 = this.active;
    this.active = x.isManaMachineActive();
    if (this.wasActive2 != this.active) {
      this.tileEntity.setChanged();
      this.tileEntity.sendData();
    }
    super.tick();
  }
}
