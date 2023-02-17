package zaftnotameni.sharedbehaviors;
import net.minecraft.world.level.block.state.BlockState;
public interface IAmManaMachine {
  public boolean isManaMachineActive();
  public boolean isManaMachineDuct();
  public int getManaMachineMana();
  public void setManaMachineLastCapacityProvided(float value);
  public void setManaMachineLastStressImpact(float value);
  public void updateMana(int value);
  public void setManaMachineMana(int value);
  public int getManaMachineGeneratedSpeed();
  public KineticManaMachine getManaMachine();
  public void updateGeneratedRotation(int i);
  public BlockState getBlockState();
}
