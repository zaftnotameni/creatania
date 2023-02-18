package zaftnotameni.creatania.setup;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import zaftnotameni.creatania.util.Log;
public class InterModQueueSetup {
  public static void run(final InterModEnqueueEvent event) {
    // Some example code to dispatch IMC to another mod
    // InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    Log.LOGGER.debug("enqueue IMC");

  }
}
