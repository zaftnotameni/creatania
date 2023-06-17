package zaftnotameni.creatania.stress.xorlever;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.CONDITIONAL;

public class XorLeverBlockEntity extends SmartBlockEntity {
  public int state = 0;
  public int lastChange;
  public LerpedFloat clientState;
  public XorLeverBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
    super(type, pos, state);
    this.clientState = LerpedFloat.linear();
  }
  @Override
  public void write(CompoundTag compound, boolean clientPacket) {
    compound.putInt("State", state);
    compound.putInt("ChangeTimer", lastChange);
    super.write(compound, clientPacket);
  }

  @Override
  protected void read(CompoundTag compound, boolean clientPacket) {
    state = compound.getInt("State");
    lastChange = compound.getInt("ChangeTimer");
    clientState.chase(state, 0.2f, LerpedFloat.Chaser.EXP);
    super.read(compound, clientPacket);
  }

  @Override
  public void tick() {
    super.tick();
    if (lastChange > 0) {
      lastChange--;
      if (lastChange == 0) updateOutput();
    }
    if (level != null && level.isClientSide) clientState.tickChaser();
  }

  @Override
  public void initialize() {
    super.initialize();
  }

  private void updateOutput() {
    if (level != null) {
      XorLeverBlock.updateNeighbors(getBlockState(), level, worldPosition);
      if (!level.isClientSide) {
        level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(CONDITIONAL, this.state == 15));
      }
    }
  }

  @Override
  public void addBehaviours(List<BlockEntityBehaviour> behaviours) {}
  public void toggleState() {
    int prevState = state;
    state = 15 - state;;
    state = Mth.clamp(state, 0, 15);
    if (prevState != state) lastChange = 15;
    sendData();
  }
  public int getState() { return state; }
}