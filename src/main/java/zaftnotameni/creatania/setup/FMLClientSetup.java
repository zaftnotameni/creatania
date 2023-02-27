package zaftnotameni.creatania.setup;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import zaftnotameni.creatania.util.Log;
public class FMLClientSetup {
  @SuppressWarnings("removal")
  public static void run(final FMLClientSetupEvent event) {
    Log.LOGGER.info("creatania client setup started");
//    setRenderLayer(Blocks.MANASTEEL_MANADUCT_BLOCK.get(), RenderType.cutoutMipped());
//    setRenderLayer(Blocks.TERRASTEEL_MANADUCT_BLOCK.get(), RenderType.cutoutMipped());
//    setRenderLayer(Blocks.GAIA_MANADUCT_BLOCK.get(), RenderType.cutoutMipped());
//    setRenderLayer(Blocks.CORRUPT_MANA_BLOCK.get(), RenderType.translucent());
//    setRenderLayer(Blocks.PURE_MANA_BLOCK.get(), RenderType.translucent());
//    setRenderLayer(Blocks.REAL_MANA_BLOCK.get(), RenderType.translucent());
    Log.LOGGER.info("creatania client setup finished");
  }
}
