package zaftnotameni.creatania.machines.manamotor;
import zaftnotameni.creatania.config.CommonConfig;
public class ManaMotorConfig {
  public static int getStressUnitsPerRPM() { return CommonConfig.MANA_MOTOR_SU_PER_RPM.get(); }
  public static int getManaPerTickPerRPM() { return  CommonConfig.MANA_MOTOR_MANA_PER_TICK_PER_RPM.get(); }
  public static int getRPMPerManaPerTick() { return  CommonConfig.MANA_MOTOR_RPM_PER_MANA_PER_TICK.get(); }
  public static boolean getShouldConsumeManaIfNotActive() { return  CommonConfig.MANA_MOTOR_CONSUME_MANA_EVEN_IF_NO_SU_IS_PRODUCED.get(); }
  public static int getManaCap() { return  CommonConfig.MANA_MOTOR_MAX_MANA_STORAGE.get(); }
}
