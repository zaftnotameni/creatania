package zaftnotameni.creatania.registry.datagen;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.registry.datagen.processing.ForgeCreateProcessingRecipeProvider;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeDatagenInitializer {
  @SubscribeEvent
  public static void onGatherDataEvent(GatherDataEvent evt) {
    var generator = evt.getGenerator();
    var helper = evt.getExistingFileHelper();
    // var helper = new ExistingFileHelper(Collections.emptyList(), Collections.emptySet(), false, null, null);
    // generator.addProvider(true,new LangMerger(generator, Constants.MODID, "Creatania", LangPartials.values()));

    if (evt.includeServer()) {
      serverDataGen(generator, helper);
    }

  }
  public static void serverDataGen(DataGenerator generator, ExistingFileHelper helper) {
//    var blockTagProvider = new ForgeBlockTagProvider(generator, helper);

//    generator.addProvider(true,blockTagProvider);
//    generator.addProvider(true,new ForgeItemTagProvider(generator, blockTagProvider, helper));
//    generator.addProvider(true,new ForgeRecipeProvider(generator));
//    generator.addProvider(true,new ForgeBlockLootProvider(generator));
//    generator.addProvider(true,new ForgeFluidTagProvider(generator, helper));
//    generator.addProvider(true,new ForgeBlockstatesProvider(generator, helper));
//    generator.addProvider(true,new ForgeSequencedAssemblyRecipeProvider(generator));
//    generator.addProvider(true,new ForgeAdvancementsProvider(generator));
//
    ForgeCreateProcessingRecipeProvider.registerAll(generator);
//    generator.addProvider(true,new ElvenTradeRecipeGen(generator));
//    generator.addProvider(true,new ManaInfusionRecipeGen(generator));
//    generator.addProvider(true,new RuneAltarRecipeGen(generator));
//    generator.addProvider(true,new TerraPlateRecipeGen(generator));
//    generator.addProvider(true,new PureDaisyRecipeGen(generator));
  }
}