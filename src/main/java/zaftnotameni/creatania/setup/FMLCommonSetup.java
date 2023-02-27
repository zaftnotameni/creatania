package zaftnotameni.creatania.setup;
import com.simibubi.create.foundation.block.BlockStressValues;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.network.Networking;
import zaftnotameni.creatania.registry.CreataniaAdvancements;
import zaftnotameni.creatania.registry.Triggers;
import zaftnotameni.creatania.util.Log;

import static zaftnotameni.creatania.Constants.MODID;
public class FMLCommonSetup {
  public static void run(final FMLCommonSetupEvent event) {
    Log.LOGGER.info("creatania setup started");
//    BlockStressValues.registerProvider(MODID, AllConfigs.SERVER.kinetics.stressValues);
    BlockStressValues.registerProvider(MODID, CommonConfig.stressProvider);
    Networking.registerMessages();
    event.enqueueWork(() -> {
      CreataniaAdvancements.register();
      Triggers.register();
      CommonConfig.stressProvider.registerAll();
    });
    Log.LOGGER.info("creatania setup finished");
  }
}
