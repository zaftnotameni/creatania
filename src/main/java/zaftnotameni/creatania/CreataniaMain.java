package zaftnotameni.creatania;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import zaftnotameni.creatania.block.ModBlocks;
import zaftnotameni.creatania.block.entity.ModBlockEntities;
import zaftnotameni.creatania.item.ModItems;
import zaftnotameni.creatania.util.Log;

@Mod(Constants.MODID)
public class CreataniaMain {
  // Directly reference a slf4j logger

  public CreataniaMain() {
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

    ModItems.register(bus);
    ModBlocks.register(bus);
    ModBlockEntities.register(bus);

    // Register the setup method for modloading
    bus.addListener(this::setup);
    // Register the enqueueIMC method for modloading
    bus.addListener(this::enqueueIMC);
    // Register the processIMC method for modloading
    bus.addListener(this::processIMC);

    // Register ourselves for server and other game events we are interested in
    MinecraftForge.EVENT_BUS.register(this);
  }

  private void setup(final FMLCommonSetupEvent event) {
    Log.LOGGER.debug("pre init");
  }

  private void enqueueIMC(final InterModEnqueueEvent event) {
    // Some example code to dispatch IMC to another mod
    // InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    Log.LOGGER.debug("enqueue IMC");

  }

  private void processIMC(final InterModProcessEvent event) {
    // Some example code to receive and process InterModComms from other mods
    // LOGGER.info("Got IMC {}", event.getIMCStream().
    //        map(m->m.messageSupplier().get()).
    //        collect(Collectors.toList()));
    Log.LOGGER.debug("process IMC");
  }

  // You can use SubscribeEvent and let the Event Bus discover methods to call
  @SubscribeEvent
  public void onServerStarting(ServerStartingEvent event) {
    // Do something when the server starts
    // LOGGER.info("HELLO from server starting");
    Log.LOGGER.debug("server starting");
  }

  // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
  // Event bus for receiving Registry Events)
  @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class RegistryEvents {
    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
      Log.LOGGER.debug("block registry");
    }
  }
}