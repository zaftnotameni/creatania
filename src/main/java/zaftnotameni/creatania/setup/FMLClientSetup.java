package zaftnotameni.creatania.setup;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import zaftnotameni.creatania.ponder.CreataniaPonderIndex;
import zaftnotameni.creatania.registry.Blocks;
import zaftnotameni.creatania.util.Log;
public class FMLClientSetup {
  public static void run(final FMLClientSetupEvent event) {
    Log.LOGGER.info("creatania client setup started");
    ItemBlockRenderTypes.setRenderLayer(Blocks.MANASTEEL_MANADUCT_BLOCK.get(), RenderType.cutoutMipped());
    ItemBlockRenderTypes.setRenderLayer(Blocks.TERRASTEEL_MANADUCT_BLOCK.get(), RenderType.cutoutMipped());
    ItemBlockRenderTypes.setRenderLayer(Blocks.GAIA_MANADUCT_BLOCK.get(), RenderType.cutoutMipped());
    ItemBlockRenderTypes.setRenderLayer(Blocks.CORRUPT_MANA_BLOCK.get(), RenderType.translucent());
    ItemBlockRenderTypes.setRenderLayer(Blocks.PURE_MANA_BLOCK.get(), RenderType.translucent());
    ItemBlockRenderTypes.setRenderLayer(Blocks.REAL_MANA_BLOCK.get(), RenderType.translucent());

    CreataniaPonderIndex.register();
    Log.LOGGER.info("creatania client setup finished");
  }
}
