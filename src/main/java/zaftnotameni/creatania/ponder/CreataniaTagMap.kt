package zaftnotameni.creatania.ponder

import com.simibubi.create.foundation.ponder.PonderRegistry
import net.minecraft.data.DataGenerator
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraftforge.fml.loading.FMLPaths
import zaftnotameni.creatania.recipes.cobblegen.CobblegenRecipe
import zaftnotameni.creatania.registry.Index
import zaftnotameni.creatania.registry.datagen.processing.CobblegenRecipeGen

object CreataniaTagMap {
  @JvmStatic
  fun associate() {
    CobblegenRecipeGen(DataGenerator(FMLPaths.MODSDIR.get(), ArrayList())).setupRecipes()
    var cobblegenBuilder = PonderRegistry.TAGS.forTag(CreataniaPonderTag.COBBLEGEN)
    for (j in CobblegenRecipeGen.ALL.values) {
      val recipe = CobblegenRecipe.TYPE.getSerializer<RecipeSerializer<*>>().fromJson(Index.resource("none"), j)
      val id = recipe.resultItem.item.registryName
      cobblegenBuilder = cobblegenBuilder.add(id)
    }

//    TAGS.forTag(MANA_MANIPULATION)
//      .add(MECHANICAL_MIXER)
//      .add(BASIN)
//      .add(BLAZE_BURNER)
//      .add(REAL_MANA_BLOCK)
//      .add(CORRUPT_MANA_BLOCK)
//      .add(PURE_MANA_BLOCK);
  }
}