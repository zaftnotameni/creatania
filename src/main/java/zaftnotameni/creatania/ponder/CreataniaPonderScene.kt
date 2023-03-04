package zaftnotameni.creatania.ponder

import com.simibubi.create.foundation.ponder.*
import net.minecraft.data.DataGenerator
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraftforge.fml.loading.FMLPaths
import zaftnotameni.creatania.Constants
import zaftnotameni.creatania.recipes.cobblegen.CobblegenRecipe
import zaftnotameni.creatania.registry.Index
import zaftnotameni.creatania.registry.datagen.processing.CobblegenRecipeGen

class CreataniaPonderScene(world : PonderWorld?, namespace : String?, component : ResourceLocation?, tags : Collection<PonderTag?>?) : PonderScene(world, namespace, component, tags) {
  companion object {
    val INDEX = PonderRegistrationHelper(Constants.MODID)
    @JvmStatic
    fun register() {
//    INDEX
//      .forComponents(Blocks.PURE_MANA_BLOCK)
//      .addStoryBoard("mana_superheated_mixer", ManaManipulationScenes::makeLiquidPureMana, CreataniaPonderTag.MANA_MANIPULATION);
//
//    INDEX
//      .forComponents(Blocks.CORRUPT_MANA_BLOCK)
//      .addStoryBoard("mana_superheated_mixer", ManaManipulationScenes::makeLiquidCorruptMana, CreataniaPonderTag.MANA_MANIPULATION);
      deferredRegister()
    }

    fun deferredRegister() {
      CobblegenRecipeGen(DataGenerator(FMLPaths.MODSDIR.get(), ArrayList())).setupRecipes()
      for (j in CobblegenRecipeGen.ALL.values) {
        val recipe = CobblegenRecipe.TYPE.getSerializer<RecipeSerializer<*>>().fromJson(Index.resource("none"), j)
        val id = recipe.resultItem.item.registryName
        val scene = CobblegenScenes((recipe as CobblegenRecipe))
        INDEX.addStoryBoard(id, "mana_superheated_mixer", { s : SceneBuilder?, u : SceneBuildingUtil? -> scene.makeCobblegenScene(s, u) }, CreataniaPonderTag.COBBLEGEN)
      }
    }
  }
}