package zaftnotameni.sharedbehaviors;
public interface IAmManaMachine {
  public boolean isManaMachineActive();
  public int getManaMachineMana();
  public void setManaMachineLastCapacityProvided(float value);
  public void setManaMachineLastStressImpact(float value);
  public void updateMana(int value);
  public void setManaMachineMana(int value);
  public int getManaMachineGeneratedSpeed();
  public void updateGeneratedRotation(int i);
}
