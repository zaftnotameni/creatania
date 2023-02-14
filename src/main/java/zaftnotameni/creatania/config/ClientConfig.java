package zaftnotameni.creatania.config;
import net.minecraftforge.common.ForgeConfigSpec;
public class ClientConfig {
  public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
  public static final ForgeConfigSpec.ConfigValue<Float> TICKS_PER_PARTICLE;
  public static final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_MANA_PARTICLES;
  public static final ForgeConfigSpec SPEC;

  static {
    BUILDER.push("rendering");
    TICKS_PER_PARTICLE = BUILDER.define("ticks_per_particle", 21.2f);
    ENABLE_MANA_PARTICLES = BUILDER.define("enable_mana_particles", true);
    BUILDER.pop();
    SPEC = BUILDER.build();
  }
}
