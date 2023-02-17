package zaftnotameni.creatania.config;
import net.minecraftforge.common.ForgeConfigSpec;
import org.openjdk.nashorn.api.linker.NashornLinkerExporter;
public class ClientConfig {
  public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
  public static final ForgeConfigSpec.ConfigValue<Float> TICKS_PER_PARTICLE;
  public static final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_MANA_PARTICLES;
  public static final ForgeConfigSpec.ConfigValue<Float> MANA_MOTOR_MANA_FILL_TOP_PADDING;
  public static final ForgeConfigSpec.ConfigValue<Float> MANA_MOTOR_MANA_FILL_HORIZONTAL_PADDING;
  public static final ForgeConfigSpec.ConfigValue<Float> MANA_MOTOR_MANA_FILL_BOTTOM_PADDING;
  public static final ForgeConfigSpec SPEC;


  static {
    BUILDER.push("rendering");
    TICKS_PER_PARTICLE = BUILDER.define("ticks_per_particle", 2.1f);
    ENABLE_MANA_PARTICLES = BUILDER.define("enable_mana_particles", true);
    MANA_MOTOR_MANA_FILL_HORIZONTAL_PADDING = BUILDER.define("mana_motor_mana_fill_horizontal_padding", 0.15f);
    MANA_MOTOR_MANA_FILL_TOP_PADDING = BUILDER.define("mana_motor_mana_fill_top_padding", 0.075f);
    MANA_MOTOR_MANA_FILL_BOTTOM_PADDING = BUILDER.define("mana_motor_mana_fill_top_padding", 0.1f);
    BUILDER.pop();
    SPEC = BUILDER.build();
  }
}
