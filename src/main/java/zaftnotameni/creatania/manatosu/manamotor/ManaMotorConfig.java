package zaftnotameni.creatania.manatosu.manamotor;
import zaftnotameni.creatania.config.CommonConfig;
public class ManaMotorConfig {
  public static int getStressUnitsPerRPM() { return CommonConfig.MANA_MOTOR_SU_PER_RPM.get(); }
  public static int getManaPerTickPerRPM() { return  CommonConfig.MANA_MOTOR_MANA_PER_TICK_PER_RPM.get(); }
  public static int getMinimumSafeThreshold() { return  CommonConfig.MANA_MOTOR_MIN_MANA_RESERVE_FACTOR.get(); }
  public static int getMinimumSafeThresholdPool() { return  CommonConfig.MANA_MOTOR_MIN_MANA_RESERVE_POOL.get(); }
  public static int getManaCap() { return  CommonConfig.MANA_MOTOR_MAX_MANA_STORAGE.get(); }
}
