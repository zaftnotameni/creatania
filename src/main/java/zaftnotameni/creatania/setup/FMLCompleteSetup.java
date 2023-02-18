package zaftnotameni.creatania.setup;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import zaftnotameni.creatania.network.EnergyNetworkPacket;
import zaftnotameni.creatania.network.ObservePacket;
import zaftnotameni.creatania.util.Log;

public class FMLCompleteSetup {
  public static void run(final FMLLoadCompleteEvent event) {
    int i = 0;
    StaticInit.Network.registerMessage(i++, ObservePacket.class, ObservePacket::encode, ObservePacket::decode, ObservePacket::handle);
    StaticInit.Network.registerMessage(i++, EnergyNetworkPacket.class, EnergyNetworkPacket::encode, EnergyNetworkPacket::decode, EnergyNetworkPacket::handle);
    Log.LOGGER.debug("post init");
  }
}
