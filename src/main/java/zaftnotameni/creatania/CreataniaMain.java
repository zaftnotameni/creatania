package zaftnotameni.creatania;

import com.simibubi.create.foundation.data.LangMerger;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import zaftnotameni.creatania.event.ForgeEventBus;
import zaftnotameni.creatania.registry.*;
import zaftnotameni.creatania.registry.datagen.LangPartials;
import zaftnotameni.creatania.setup.*;

import static zaftnotameni.creatania.Constants.MODID;

@Mod(MODID)
public class CreataniaMain {
  public CreataniaMain() {
    StaticInit.run();
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    registrySetup(bus);
    modEventBusListeners(bus);
    zaftnotameni.creatania.config.Index.register();
    Blocks.Partials.init();
    forgeEventBusListeners();
  }

  public static void forgeEventBusListeners() {
    MinecraftForge.EVENT_BUS.register(ForgeEventBus.class);
  }
  public static void modEventBusListeners(IEventBus bus) {
    bus.addListener(FMLCommonSetup::run);
    bus.addListener(FMLClientSetup::run);
    bus.addListener(InterModQueueSetup::run);
    bus.addListener(InterModQueueProcess::run);
    bus.addListener(FMLCompleteSetup::run);
  }
  public static void registrySetup(IEventBus bus) {
    Index.all().registerEventListeners(bus);
    Potions.register(bus);
    Particles.register(bus);
    Items.register(bus);
    Blocks.register(bus);
    Fluids.register(bus);
    BlockEntities.register(bus);
    Index.CREATE_REGISTRATE.registerEventListeners(bus);
    Recipes.register(bus);
    bus.addListener(EventPriority.LOWEST, CreataniaMain::gatherData);
  }

  public static void gatherData(GatherDataEvent event) {
    // TagGen.datagen();
    DataGenerator gen = event.getGenerator();
    if (event.includeClient()) {
      gen.addProvider(new LangMerger(gen, MODID, "Creatania", LangPartials.values()));
    }
    if (event.includeServer()) {
//      gen.addProvider(new AllAdvancements(gen));
//      gen.addProvider(new StandardRecipeGen(gen));
//      gen.addProvider(new MechanicalCraftingRecipeGen(gen));
//      gen.addProvider(new SequencedAssemblyRecipeGen(gen));
//      ProcessingRecipeGen.registerAll(gen);
    }
  }
}