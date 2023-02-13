package zaftnotameni.creatania.config;
import net.minecraftforge.common.ForgeConfigSpec;
public class CommonConfig {
  public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
  public static final ForgeConfigSpec SPEC;

  public static final  ForgeConfigSpec.ConfigValue<Integer> MANA_MOTOR_SU_PER_RPM;
  public static final  ForgeConfigSpec.ConfigValue<Integer> MANA_MOTOR_MANA_PER_TICK_PER_RPM;
  public static final  ForgeConfigSpec.ConfigValue<Integer> MANA_MOTOR_BASE_RPM;
  public static final  ForgeConfigSpec.ConfigValue<Integer> MANA_MOTOR_MAX_MANA_STORAGE;
  public static final  ForgeConfigSpec.ConfigValue<Integer> MANA_MOTOR_LAZY_TICK_RATE;
  public static final  ForgeConfigSpec.ConfigValue<Boolean> MANA_MOTOR_CONSUME_MANA_EVEN_IF_NO_SU_IS_PRODUCED;

  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_GENERATOR_MINIMUM_RPM ;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_GENERATOR_MAXIMUM_RPM ;
  public static final  ForgeConfigSpec.ConfigValue<Integer> MANA_GENERATOR_MANA_PER_RPM_PER_TICK;
  public static final  ForgeConfigSpec.ConfigValue<Integer> MANA_GENERATOR_SU_PER_RPM;
  public static final  ForgeConfigSpec.ConfigValue<Integer> MANA_GENERATOR_MANA_CONVERSION_RATE;
  public static final  ForgeConfigSpec.ConfigValue<Integer> MANA_GENERATOR_LAZY_TICK_RATE;
  public static final  ForgeConfigSpec.ConfigValue<Integer> MANA_GENERATOR_MAX_MANA_FLUID_STORAGE;

  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_CONDENSER_SU_PER_RPM;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_CONDENSER_MANA_PER_ITEM;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_CONDENSER_MANA_PER_TICK_PER_RPM ;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_CONDENSER_MAX_MANA_STORAGE;
  static {
    BUILDER.push("mana_generator");
    MANA_GENERATOR_MANA_PER_RPM_PER_TICK = BUILDER.define("mana_per_rpm_per_tick", 1);
    MANA_GENERATOR_SU_PER_RPM = BUILDER.define("su_per_rpm", 512);
    MANA_GENERATOR_MINIMUM_RPM = BUILDER.define("minimum_rpm", 1);
    MANA_GENERATOR_MAXIMUM_RPM = BUILDER.define("maximum_rpm", 256);
    MANA_GENERATOR_MANA_CONVERSION_RATE = BUILDER.define("mana_conversion_rate", 2);
    MANA_GENERATOR_MAX_MANA_FLUID_STORAGE = BUILDER.define("mana_fluid_storage", 2000);
    MANA_GENERATOR_LAZY_TICK_RATE = BUILDER.define("lazy_tick_rate", 20);
    BUILDER.pop();

    BUILDER.push("mana_motor");
    MANA_MOTOR_BASE_RPM = BUILDER.define("base_rpm", 64);
    MANA_MOTOR_SU_PER_RPM = BUILDER.define("su_per_rpm", 1);
    MANA_MOTOR_MANA_PER_TICK_PER_RPM = BUILDER.define("mana_per_tick_per_rpm", 1);
    MANA_MOTOR_MAX_MANA_STORAGE = BUILDER.define("max_mana_storage", 1000);
    MANA_MOTOR_CONSUME_MANA_EVEN_IF_NO_SU_IS_PRODUCED = BUILDER.define("consume_mana_when_not_enough_to_produce_su", true);
    MANA_MOTOR_LAZY_TICK_RATE = BUILDER.define("lazy_tick_rate", 20);
    BUILDER.pop();

    BUILDER.push("mana_condenser");
    MANA_CONDENSER_SU_PER_RPM = BUILDER.define("su_per_rpm", 1);
    MANA_CONDENSER_MANA_PER_ITEM = BUILDER.define("mana_per_item", 100);
    MANA_CONDENSER_MANA_PER_TICK_PER_RPM = BUILDER.define("mana_per_tick_per_rpm", 0);
    MANA_CONDENSER_MAX_MANA_STORAGE = BUILDER.define("max_mana_storage", 1000);
    BUILDER.pop();

    SPEC = BUILDER.build();
  }
}
