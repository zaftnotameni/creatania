package zaftnotameni.creatania.config;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
public class Index {
  public static void register() {
    ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC, "creatania-client.toml");
    ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC, "creatania-common.toml");
  }
}
