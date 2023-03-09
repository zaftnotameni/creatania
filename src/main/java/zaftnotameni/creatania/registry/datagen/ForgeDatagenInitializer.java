package zaftnotameni.creatania.registry.datagen;

import com.simibubi.create.foundation.data.LangMerger;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.registry.datagen.botania.*;
import zaftnotameni.creatania.registry.datagen.processing.ForgeCreateProcessingRecipeProvider;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeDatagenInitializer {
  @SubscribeEvent
  public static void configureForgeDatagen(GatherDataEvent evt) {
    var generator = evt.getGenerator();
    var helper = evt.getExistingFileHelper();
    // var helper = new ExistingFileHelper(Collections.emptyList(), Collections.emptySet(), false, null, null);
    generator.addProvider(new LangMerger(generator, Constants.MODID, "Creatania", LangPartials.values()));

    if (evt.includeServer()) {
      serverDataGen(generator, helper);
    }
  }
  public static void serverDataGen(DataGenerator generator, ExistingFileHelper helper) {
    var blockTagProvider = new ForgeBlockTagProvider(generator, helper);

    generator.addProvider(blockTagProvider);
    generator.addProvider(new ForgeItemTagProvider(generator, blockTagProvider, helper));
    generator.addProvider(new ForgeRecipeProvider(generator));
    generator.addProvider(new ForgeBlockLootProvider(generator));
    generator.addProvider(new ForgeFluidTagProvider(generator, helper));
    generator.addProvider(new ForgeBlockstatesProvider(generator, helper));
    generator.addProvider(new ForgeSequencedAssemblyRecipeProvider(generator));
    generator.addProvider(new ForgeAdvancementsProvider(generator));

    ForgeCreateProcessingRecipeProvider.registerAll(generator);
    generator.addProvider(new ElvenTradeRecipeGen(generator));
    generator.addProvider(new PetalApothecaryRecipeGen(generator));
    generator.addProvider(new ManaInfusionRecipeGen(generator));
    generator.addProvider(new RuneAltarRecipeGen(generator));
    generator.addProvider(new TerraPlateRecipeGen(generator));
    generator.addProvider(new PureDaisyRecipeGen(generator));
  }
}