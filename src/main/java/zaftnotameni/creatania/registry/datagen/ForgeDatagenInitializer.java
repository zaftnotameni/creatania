package zaftnotameni.creatania.registry.datagen;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import zaftnotameni.creatania.Constants;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeDatagenInitializer {
  @SubscribeEvent
  public static void configureForgeDatagen(GatherDataEvent evt) {
    var generator = evt.getGenerator();
    var helper = evt.getExistingFileHelper();
    // var helper = new ExistingFileHelper(Collections.emptyList(), Collections.emptySet(), false, null, null);
    var blockTagProvider = new ForgeBlockTagProvider(generator, helper);

    generator.addProvider(blockTagProvider);
    generator.addProvider(new ForgeItemTagProvider(generator, blockTagProvider, helper));
    generator.addProvider(new ForgeRecipeProvider(generator));
    generator.addProvider(new ForgeBlockLootProvider(generator));
    generator.addProvider(new ForgeFluidTagProvider(generator, helper));
    generator.addProvider(new ForgeBlockstatesProvider(generator, helper));
  }
}