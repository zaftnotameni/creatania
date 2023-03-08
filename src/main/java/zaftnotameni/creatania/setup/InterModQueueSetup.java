package zaftnotameni.creatania.setup;

import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

import static zaftnotameni.creatania.util.LogKt.log;

public class InterModQueueSetup {
  public static void run(final InterModEnqueueEvent event) {
    // Some example code to dispatch IMC to another mod
    // InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    log(l -> l.debug("enqueue IMC"));

  }
}