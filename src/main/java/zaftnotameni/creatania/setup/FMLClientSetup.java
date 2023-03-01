package zaftnotameni.creatania.setup;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import zaftnotameni.creatania.ponder.CreataniaPonderIndex;
import zaftnotameni.creatania.util.Log;

import static net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer;
import static net.minecraft.client.renderer.RenderType.cutoutMipped;
import static net.minecraft.client.renderer.RenderType.translucent;
import static zaftnotameni.creatania.registry.Blocks.*;
import static zaftnotameni.creatania.registry.Fluids.*;

public class FMLClientSetup {
  public static void run(final FMLClientSetupEvent event) {
    Log.LOGGER.info("creatania client setup started");
    setRenderLayer(MANASTEEL_MANADUCT_BLOCK.get(), cutoutMipped());
    setRenderLayer(TERRASTEEL_MANADUCT_BLOCK.get(), cutoutMipped());
    setRenderLayer(GAIA_MANADUCT_BLOCK.get(), cutoutMipped());
    setRenderLayer(CORRUPT_MANA_BLOCK.get(), translucent());
    setRenderLayer(PURE_MANA_BLOCK.get(), translucent());
    setRenderLayer(REAL_MANA_BLOCK.get(), translucent());
    setRenderLayer(PURE_MANA.get(), translucent());
    setRenderLayer(CORRUPT_MANA.get(), translucent());
    setRenderLayer(REAL_MANA.get(), translucent());
    setRenderLayer(MOLTEN_COPPER.get(), translucent());
    setRenderLayer(MOLTEN_IRON.get(), translucent());
    setRenderLayer(MOLTEN_GOLD.get(), translucent());
    setRenderLayer(MOLTEN_ZINC.get(), translucent());
    setRenderLayer(MOLTEN_ANDESITE.get(), translucent());
    setRenderLayer(MOLTEN_BRASS.get(), translucent());
    setRenderLayer(MOLTEN_TERRASTEEL.get(), translucent());
    setRenderLayer(MOLTEN_MANASTEEL.get(), translucent());
    setRenderLayer(MOLTEN_GAIA.get(), translucent());
    setRenderLayer(MOLTEN_ELEMENTIUM.get(), translucent());
    CreataniaPonderIndex.register();
    Log.LOGGER.info("creatania client setup finished");
  }
}