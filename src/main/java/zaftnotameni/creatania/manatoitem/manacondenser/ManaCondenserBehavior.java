package zaftnotameni.creatania.manatoitem.manacondenser;

import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.BehaviourType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import zaftnotameni.creatania.manatosu.manamotor.ManaMotorBlockEntity;
import zaftnotameni.creatania.manatosu.manamotor.ManaMotorConfig;

import static zaftnotameni.creatania.manatosu.manamotor.ManaMotorBlockEntity.UPDATE_MANA_ON_EVERY_TICK;

public class ManaCondenserBehavior extends TileEntityBehaviour {
  public static final BehaviourType<TileEntityBehaviour> TYPE = new BehaviourType<>();
  public ManaCondenserBlockEntity condenser;

  public ManaCondenserBehavior(ManaCondenserBlockEntity pCondenser) {
    super(pCondenser);
    this.condenser = pCondenser;
  }

  @Override
  public void read(CompoundTag nbt, boolean clientPacket) {
    super.read(nbt, clientPacket);
    this.condenser.mana = nbt.getInt("mana");
    this.condenser.active = nbt.getBoolean("active");
  }

  @Override
  public void write(CompoundTag nbt, boolean clientPacket) {
    nbt.putInt("mana", this.condenser.mana);
    nbt.putBoolean("active", this.condenser.active);
    super.write(nbt, clientPacket);
  }

  @Override
  public BehaviourType<?> getType() {
    return TYPE;
  }

  @Override
  public void tick() {
    super.tick();
    ManaCondenserBehavior.tick(this.condenser.getLevel(), this.condenser.getBlockPos(), this.condenser.getBlockState(), this.condenser);
  }
  public static void tick(Level level, BlockPos blockPos, BlockState blockState, ManaCondenserBlockEntity self) {
    self.firstTick = false;
    if (level.isClientSide()) {
      tickClient(level, blockPos, blockState, self);
    } else {
      tickServer(level, blockPos, blockState, self);
    }
  }

  public static void tickClient(Level level, BlockPos blockPos, BlockState blockState, ManaCondenserBlockEntity self) {

  }

  public static void tickServer(Level level, BlockPos blockPos, BlockState blockState, ManaCondenserBlockEntity self) {
  }
}
