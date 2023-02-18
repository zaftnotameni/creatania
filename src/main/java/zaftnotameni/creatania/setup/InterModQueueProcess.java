package zaftnotameni.creatania.setup;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import zaftnotameni.creatania.util.Log;
public class InterModQueueProcess {
  public static void run(final InterModProcessEvent event) {
    // Some example code to receive and process InterModComms from other mods
    // LOGGER.info("Got IMC {}", event.getIMCStream().
    //        map(m->m.messageSupplier().get()).
    //        collect(Collectors.toList()));
    Log.LOGGER.debug("process IMC");

  }
}
