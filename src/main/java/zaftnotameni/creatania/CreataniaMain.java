package zaftnotameni.creatania;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import zaftnotameni.creatania.event.forgebus.OnRightClickBlock;
import zaftnotameni.creatania.registry.*;
import zaftnotameni.creatania.registry.datagen.ForgeDatagenInitializer;
import zaftnotameni.creatania.setup.*;

import static zaftnotameni.creatania.Constants.MODID;

@Mod(MODID)
public class CreataniaMain {
  public CreataniaMain() {
    StaticInit.run();
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    registrySetup(bus);
    modEventBusListeners(bus);
//    Blocks.Partials.init();
    forgeEventBusListeners();
  }

  public static void forgeEventBusListeners() {
    MinecraftForge.EVENT_BUS.register(OnRightClickBlock.class);
  }
  public static void modEventBusListeners(IEventBus bus) {
    bus.addListener(FMLCommonSetup::run);
    bus.addListener(FMLClientSetup::run);
    bus.addListener(InterModQueueSetup::run);
    bus.addListener(InterModQueueProcess::run);
    bus.addListener(FMLCompleteSetup::run);
    bus.addListener(EventPriority.LOWEST, ForgeDatagenInitializer::onGatherDataEvent);
  }
  public static void registrySetup(IEventBus bus) {
    CreataniaIndex.all().registerEventListeners(bus);
    CreataniaPotions.register(bus);
    Particles.register(bus);
    CreataniaItems.register(bus);
    CreataniaBlocks.register(bus);
    CreataniaBlocks.Partials.init();
    CreataniaFluids.ALL.register(bus);
    CreataniaBlockEntities.register(bus);
    CreataniaRecipes.register(bus);
  }
}