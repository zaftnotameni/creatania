package zaftnotameni.creatania.ponder;

import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.foundation.ponder.PonderScene;
import com.simibubi.create.foundation.ponder.PonderTag;
import com.simibubi.create.foundation.ponder.PonderWorld;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.loading.FMLPaths;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.recipes.cobblegen.CobblegenRecipe;
import zaftnotameni.creatania.registry.Blocks;
import zaftnotameni.creatania.registry.Index;
import zaftnotameni.creatania.registry.datagen.processing.CobblegenRecipeGen;

public class CreataniaPonderScene extends PonderScene {
  public static final PonderRegistrationHelper INDEX = new PonderRegistrationHelper(Constants.MODID);

  public CreataniaPonderScene(PonderWorld world, String namespace, ResourceLocation component, Collection<PonderTag> tags) {
    super(world, namespace, component, tags);
  }
  public static void register() {
    INDEX
      .forComponents(Blocks.PURE_MANA_BLOCK)
      .addStoryBoard("mana_superheated_mixer", ManaManipulationScenes::makeLiquidPureMana, CreataniaPonderTag.MANA_MANIPULATION);

    INDEX
      .forComponents(Blocks.CORRUPT_MANA_BLOCK)
      .addStoryBoard("mana_superheated_mixer", ManaManipulationScenes::makeLiquidCorruptMana, CreataniaPonderTag.MANA_MANIPULATION);

    deferredRegister();
  }

  public static void deferredRegister() {
    new CobblegenRecipeGen(new DataGenerator(FMLPaths.MODSDIR.get(), new ArrayList<Path>())).setupRecipes();
    for (var j : CobblegenRecipeGen.ALL.values()) {
      var recipe = CobblegenRecipe.TYPE.getSerializer().fromJson(Index.resource("none"), j);
      var id = recipe.getResultItem().getItem().getRegistryName();
      var scene = new CobblegenScenes((CobblegenRecipe) recipe);
      INDEX.addStoryBoard(id, "mana_superheated_mixer", scene::makeCobblegenScene, CreataniaPonderTag.COBBLEGEN);
    }
  }
}