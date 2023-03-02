package zaftnotameni.creatania.ponder;

import java.nio.file.Path;
import java.util.ArrayList;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.loading.FMLPaths;
import zaftnotameni.creatania.recipes.cobblegen.CobblegenRecipe;
import zaftnotameni.creatania.registry.Index;
import zaftnotameni.creatania.registry.datagen.processing.CobblegenRecipeGen;

import static com.simibubi.create.AllBlocks.*;
import static com.simibubi.create.foundation.ponder.PonderRegistry.TAGS;
import static zaftnotameni.creatania.ponder.CreataniaPonderTag.COBBLEGEN;
import static zaftnotameni.creatania.ponder.CreataniaPonderTag.MANA_MANIPULATION;
import static zaftnotameni.creatania.registry.Blocks.*;

public class CreataniaTagMap {
  public static void associate() {
    new CobblegenRecipeGen(new DataGenerator(FMLPaths.MODSDIR.get(), new ArrayList<Path>())).setupRecipes();
    var cobblegenBuilder = TAGS.forTag(COBBLEGEN);
    for (var j : CobblegenRecipeGen.ALL.values()) {
      var recipe = CobblegenRecipe.TYPE.getSerializer().fromJson(Index.resource("none"), j);
      var id = recipe.getResultItem().getItem().getRegistryName();
      cobblegenBuilder = cobblegenBuilder.add(id);
    }

    TAGS.forTag(MANA_MANIPULATION)
      .add(MECHANICAL_MIXER)
      .add(BASIN)
      .add(BLAZE_BURNER)
      .add(REAL_MANA_BLOCK)
      .add(CORRUPT_MANA_BLOCK)
      .add(PURE_MANA_BLOCK);
  }
}