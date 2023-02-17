package zaftnotameni.creatania;

import com.simibubi.create.AllEntityDataSerializers;
import com.simibubi.create.AllParticleTypes;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.AllStructureProcessorTypes;
import com.simibubi.create.foundation.block.BlockStressValues;
import com.simibubi.create.foundation.config.AllConfigs;
import com.simibubi.create.foundation.networking.AllPackets;
import com.simibubi.create.foundation.worldgen.AllFeatures;
import com.simibubi.create.foundation.worldgen.AllOreFeatureConfigEntries;
import com.simibubi.create.foundation.worldgen.AllPlacementModifiers;
import com.simibubi.create.foundation.worldgen.BuiltinRegistration;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import zaftnotameni.creatania.event.ForgeEventBus;
import zaftnotameni.creatania.network.*;
import zaftnotameni.creatania.registry.*;
import zaftnotameni.creatania.config.ClientConfig;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.registry.datagen.ForgeAdvancementsProvider;
import zaftnotameni.creatania.util.Log;

import static zaftnotameni.creatania.Constants.MODID;

@Mod(MODID)
public class CreataniaMain {
  private static final String PROTOCOL = "2";
  public static final SimpleChannel Network = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(MODID, "main"))
    .clientAcceptedVersions(PROTOCOL::equals)
    .serverAcceptedVersions(PROTOCOL::equals)
    .networkProtocolVersion(() -> PROTOCOL)
    .simpleChannel();

  public static IAmProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

  public CreataniaMain() {
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

    Particles.register(bus);
    Items.register(bus);
    Blocks.register(bus);
    Fluids.register(bus);
    BlockEntities.register(bus);
    Potions.register(bus);
    Index.CREATE_REGISTRATE.registerEventListeners(bus);
    Recipes.register(bus);

    bus.addListener(this::setup);
    bus.addListener(this::client);
    bus.addListener(this::enqueueIMC);
    bus.addListener(this::processIMC);
    bus.addListener(this::postInit);

    ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC, "creatania-client.toml");
    ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC, "creatania-common.toml");

    Blocks.Partials.init();

    // Register ourselves for server and other game events we are interested in
    MinecraftForge.EVENT_BUS.register(this);
    MinecraftForge.EVENT_BUS.register(ForgeEventBus.class);
  }


  private void client(final FMLClientSetupEvent event) {
    Log.LOGGER.info("creatania client setup started");
    ItemBlockRenderTypes.setRenderLayer(Blocks.TERRASTEEL_MANADUCT_BLOCK.get(), RenderType.cutoutMipped());
    Log.LOGGER.info("creatania client setup finished");
  }

  private void setup(final FMLCommonSetupEvent event) {
    Log.LOGGER.info("creatania setup started");
    BlockStressValues.registerProvider(MODID, AllConfigs.SERVER.kinetics.stressValues);
    Networking.registerMessages();
    event.enqueueWork(() -> {
      Advancements.register();
      Triggers.register();
    });
    Log.LOGGER.info("creatania setup finished");
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

  public void postInit(FMLLoadCompleteEvent evt) {
    int i = 0;
    Network.registerMessage(i++, ObservePacket.class, ObservePacket::encode, ObservePacket::decode, ObservePacket::handle);
    Network.registerMessage(i++, EnergyNetworkPacket.class, EnergyNetworkPacket::encode, EnergyNetworkPacket::decode, EnergyNetworkPacket::handle);
    Log.LOGGER.debug("post init");
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