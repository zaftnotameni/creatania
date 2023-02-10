package zaftnotameni.creatania.config;
import net.minecraftforge.common.ForgeConfigSpec;
public class ClientConfig {
  public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
  public static final ForgeConfigSpec SPEC;

  static {
    BUILDER.push("Configs for creatania");
    BUILDER.pop();
    SPEC = BUILDER.build();
  }
}
