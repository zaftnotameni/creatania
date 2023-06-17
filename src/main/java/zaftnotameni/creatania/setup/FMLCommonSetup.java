package zaftnotameni.creatania.setup;

import com.simibubi.create.content.fluids.tank.BoilerHeaters;
import com.simibubi.create.content.kinetics.BlockStressValues;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.network.Networking;
import zaftnotameni.creatania.registry.Advancements;
import zaftnotameni.creatania.registry.Blocks;
import zaftnotameni.creatania.registry.Triggers;

import static zaftnotameni.creatania.Constants.MODID;
import static zaftnotameni.creatania.util.LogKt.log;

public class FMLCommonSetup {
  public static void run(final FMLCommonSetupEvent event) {
    log(l -> l.info("creatania setup started"));
    // BlockStressValues.registerProvider(MODID, AllConfigs.SERVER.kinetics.stressValues);
    BlockStressValues.registerProvider(MODID, CommonConfig.stressProvider);
    BoilerHeaters.registerHeater(Blocks.REAL_MANA_BLOCK.get(), (level, pos, state) -> 4);
    BoilerHeaters.registerHeater(Blocks.PURE_MANA_BLOCK.get(), (level, pos, state) -> 2);
    Networking.registerMessages();
    event.enqueueWork(() -> {
      Advancements.register();
      Triggers.register();
      CommonConfig.stressProvider.registerAll();
    });
    log(l -> l.info("creatania setup finished"));
  }
}