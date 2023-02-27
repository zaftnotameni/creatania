package zaftnotameni.creatania.setup;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.network.ClientProxy;
import zaftnotameni.creatania.network.IAmProxy;
import zaftnotameni.creatania.network.ServerProxy;

import static zaftnotameni.creatania.Constants.MODID;
public class StaticInit {
  public static final String PROTOCOL = "2";
  public static final SimpleChannel Network = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(MODID, "main"))
    .clientAcceptedVersions(PROTOCOL::equals)
    .serverAcceptedVersions(PROTOCOL::equals)
    .networkProtocolVersion(() -> PROTOCOL)
    .simpleChannel();

  public static IAmProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

  public static void run() {
    zaftnotameni.creatania.config.Index.register();
    CommonConfig.instance.load();
  }
}
