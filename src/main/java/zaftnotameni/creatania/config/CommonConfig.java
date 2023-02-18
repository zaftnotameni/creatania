package zaftnotameni.creatania.config;
import net.minecraftforge.common.ForgeConfigSpec;
public class CommonConfig {
  public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
  public static final ForgeConfigSpec SPEC;
  public static final ForgeConfigSpec.ConfigValue<Integer> PURIFIED_MANA_BLOCK_BUFF_HEAL_DURATION;
  public static final ForgeConfigSpec.ConfigValue<Integer> PURIFIED_MANA_BLOCK_BUFF_ABSORPTION_DURATION;
  public static final ForgeConfigSpec.ConfigValue<Integer> PURIFIED_MANA_BLOCK_BUFF_SATURATION_DURATION;
  public static final ForgeConfigSpec.ConfigValue<Integer> PURIFIED_MANA_BLOCK_BUFF_HEAL;
  public static final ForgeConfigSpec.ConfigValue<Integer> PURIFIED_MANA_BLOCK_BUFF_ABSORPTION;
  public static final ForgeConfigSpec.ConfigValue<Integer> PURIFIED_MANA_BLOCK_BUFF_SATURATION;
  public static final ForgeConfigSpec.ConfigValue<Boolean> PURIFIED_MANA_BLOCK_BUFF_DISABLE_WHEN_SNEAKING;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_MOTOR_SU_PER_RPM;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_MOTOR_MANA_PER_TICK_PER_RPM;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_MOTOR_RPM_PER_MANA_PER_TICK;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_MOTOR_BASE_RPM;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_MOTOR_MAX_MANA_STORAGE;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_MOTOR_LAZY_TICK_RATE;
  public static final ForgeConfigSpec.ConfigValue<Boolean> MANA_MOTOR_CONSUME_MANA_EVEN_IF_NO_SU_IS_PRODUCED;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_GENERATOR_MINIMUM_RPM ;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_GENERATOR_MAXIMUM_RPM ;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_GENERATOR_MANA_PER_RPM_PER_TICK;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_GENERATOR_SU_PER_RPM;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_GENERATOR_MANA_CONVERSION_RATE;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_GENERATOR_LAZY_TICK_RATE;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_GENERATOR_MAX_MANA_FLUID_STORAGE;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_CONDENSER_SU_PER_RPM;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_CONDENSER_MANA_PER_ITEM;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_CONDENSER_MANA_PER_TICK_PER_RPM;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_CONDENSER_THROTTLE_PER_RPM_BELOW_MAX;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_CONDENSER_MAX_MANA_STORAGE;
  public static final ForgeConfigSpec.ConfigValue<Integer> CORRUPTED_MANA_BLOCK_BUFF_HARM;
  public static final ForgeConfigSpec.ConfigValue<Integer> CORRUPTED_MANA_BLOCK_BUFF_POISON;
  public static final ForgeConfigSpec.ConfigValue<Integer> CORRUPTED_MANA_BLOCK_BUFF_WITHER;
  public static final ForgeConfigSpec.ConfigValue<Integer> CORRUPTED_MANA_BLOCK_BUFF_HUNGER;
  public static final ForgeConfigSpec.ConfigValue<Integer> CORRUPTED_MANA_BLOCK_BUFF_HARM_DURATION;
  public static final ForgeConfigSpec.ConfigValue<Integer> CORRUPTED_MANA_BLOCK_BUFF_POISON_DURATION;
  public static final ForgeConfigSpec.ConfigValue<Integer> CORRUPTED_MANA_BLOCK_BUFF_WITHER_DURATION;
  public static final ForgeConfigSpec.ConfigValue<Integer> CORRUPTED_MANA_BLOCK_BUFF_HUNGER_DURATION;
  public static final ForgeConfigSpec.ConfigValue<Boolean> CORRUPTED_MANA_BLOCK_BUFF_DISABLE_WHEN_SNEAKING;
  public static final ForgeConfigSpec.ConfigValue<Integer> BOTANIA_MANA_BLOCK_BUFF_FLIGHT;
  public static final ForgeConfigSpec.ConfigValue<Integer> BOTANIA_MANA_BLOCK_BUFF_FLIGHT_DURATION;
  public static final ForgeConfigSpec.ConfigValue<Boolean> BOTANIA_MANA_BLOCK_BUFF_DISABLE_WHEN_SNEAKING;
  public static final ForgeConfigSpec.ConfigValue<String> BLACKLISTED_WRENCH_BLOCKS;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_DUCT_TIER_1_MULTIPLIER;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_DUCT_TIER_2_MULTIPLIER;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_DUCT_TIER_3_MULTIPLIER;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANA_DUCT_TIER_4_MULTIPLIER;
  public static final ForgeConfigSpec.ConfigValue<Boolean> MANAGEL_CAN_ALWAYS_EAT;
  public static final ForgeConfigSpec.ConfigValue<Boolean> MANAGEL_FAST_EATING;
  public static final ForgeConfigSpec.ConfigValue<Float> MANAGEL_FLIGHT_PROBABILITY;
  public static final ForgeConfigSpec.ConfigValue<Float> MANAGEL_SATURATION_MODIFIER;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANAGEL_NUTRITION;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANAGEL_STACKS_TO;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANAGEL_FLIGHT_DURATION;
  public static final ForgeConfigSpec.ConfigValue<Integer> MANAGEL_FLIGHT_MODIFIER;
  public static final ForgeConfigSpec.ConfigValue<Integer> XOR_LEVER_SIGNAL_STRENGTH;

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
    MANA_MOTOR_RPM_PER_MANA_PER_TICK = BUILDER.define("rpm_per_mana_per_tick", 8);
    MANA_MOTOR_MAX_MANA_STORAGE = BUILDER.define("max_mana_storage", 1000);
    MANA_MOTOR_CONSUME_MANA_EVEN_IF_NO_SU_IS_PRODUCED = BUILDER.define("consume_mana_when_not_enough_to_produce_su", true);
    MANA_MOTOR_LAZY_TICK_RATE = BUILDER.define("lazy_tick_rate", 20);
    BUILDER.pop();

    BUILDER.push("mana_condenser");
    MANA_CONDENSER_SU_PER_RPM = BUILDER.define("su_per_rpm", 1);
    MANA_CONDENSER_MANA_PER_ITEM = BUILDER.define("mana_per_item", 100);
    MANA_CONDENSER_MANA_PER_TICK_PER_RPM = BUILDER.define("mana_per_tick_per_rpm", 0);
    MANA_CONDENSER_MAX_MANA_STORAGE = BUILDER.define("max_mana_storage", 1000);
    MANA_CONDENSER_THROTTLE_PER_RPM_BELOW_MAX = BUILDER.define("mana_condenser_throttle_per_rpm_below_max", 1);
    BUILDER.pop();

    BUILDER.push("mana_duct");
    MANA_DUCT_TIER_1_MULTIPLIER = BUILDER.define("tier_1_multiplier", 4);
    MANA_DUCT_TIER_2_MULTIPLIER = BUILDER.define("tier_2_multiplier", 8);
    MANA_DUCT_TIER_3_MULTIPLIER = BUILDER.define("tier_3_multiplier", 16);
    MANA_DUCT_TIER_4_MULTIPLIER = BUILDER.define("tier_4_multiplier", 32);
    BUILDER.pop();

    BUILDER.push("purified_inert_mana_block");
    PURIFIED_MANA_BLOCK_BUFF_HEAL = BUILDER.define("buff_heal", 1);
    PURIFIED_MANA_BLOCK_BUFF_ABSORPTION = BUILDER.define("buff_absorption", 1);
    PURIFIED_MANA_BLOCK_BUFF_SATURATION = BUILDER.define("buff_saturation", 1);
    PURIFIED_MANA_BLOCK_BUFF_HEAL_DURATION = BUILDER.define("buff_heal_duration", 30 * 20);
    PURIFIED_MANA_BLOCK_BUFF_ABSORPTION_DURATION = BUILDER.define("buff_absorption_duration", 30 * 20);
    PURIFIED_MANA_BLOCK_BUFF_SATURATION_DURATION = BUILDER.define("buff_saturation_duration", 30 * 20);
    PURIFIED_MANA_BLOCK_BUFF_DISABLE_WHEN_SNEAKING = BUILDER.define("buff_prevent_while_sneaking", true);
    BUILDER.pop();

    BUILDER.push("corrupted_inert_mana_block");
    CORRUPTED_MANA_BLOCK_BUFF_HARM = BUILDER.define("buff_harm", 1);
    CORRUPTED_MANA_BLOCK_BUFF_POISON = BUILDER.define("buff_poison", 1);
    CORRUPTED_MANA_BLOCK_BUFF_WITHER = BUILDER.define("buff_wither", 1);
    CORRUPTED_MANA_BLOCK_BUFF_HUNGER = BUILDER.define("buff_hunger", 1);
    CORRUPTED_MANA_BLOCK_BUFF_HARM_DURATION = BUILDER.define("buff_harm_duration", 30 * 20);
    CORRUPTED_MANA_BLOCK_BUFF_POISON_DURATION = BUILDER.define("buff_poison_duration", 30 * 20);
    CORRUPTED_MANA_BLOCK_BUFF_WITHER_DURATION = BUILDER.define("buff_wither_duration", 30 * 20);
    CORRUPTED_MANA_BLOCK_BUFF_HUNGER_DURATION = BUILDER.define("buff_hunger_duration", 30 * 20);
    CORRUPTED_MANA_BLOCK_BUFF_DISABLE_WHEN_SNEAKING = BUILDER.define("buff_prevent_while_sneaking", true);
    BUILDER.pop();

    BUILDER.push("botania_mana_block");
    BOTANIA_MANA_BLOCK_BUFF_FLIGHT = BUILDER.define("buff_flight", 1);
    BOTANIA_MANA_BLOCK_BUFF_FLIGHT_DURATION = BUILDER.define("buff_flight_duration", 60 * 20);
    BOTANIA_MANA_BLOCK_BUFF_DISABLE_WHEN_SNEAKING = BUILDER.define("buff_prevent_while_sneaking", true);
    BUILDER.pop();

    BUILDER.push("managel");
    MANAGEL_CAN_ALWAYS_EAT = BUILDER.define("can_always_eat", true);
    MANAGEL_FAST_EATING = BUILDER.define("can_eat_fast", true);
    MANAGEL_FLIGHT_PROBABILITY = BUILDER.define("chance_of_providing_flight", 1f);
    MANAGEL_SATURATION_MODIFIER = BUILDER.define("saturation_modifier", 0.1f);
    MANAGEL_NUTRITION = BUILDER.define("nutrition_value", 1);
    MANAGEL_FLIGHT_DURATION = BUILDER.define("flight_duration", 15 * 20);
    MANAGEL_FLIGHT_MODIFIER = BUILDER.define("flight_modifier", 1);
    MANAGEL_STACKS_TO = BUILDER.define("stacks_to", 32);
    BUILDER.pop();

    BUILDER.push("misc");
    BLACKLISTED_WRENCH_BLOCKS = BUILDER.define("blacklisted_wrench_blocks", "");
    XOR_LEVER_SIGNAL_STRENGTH = BUILDER.define("xor_lever_signal_strength", 2);
    BUILDER.pop();

    SPEC = BUILDER.build();
  }
}
