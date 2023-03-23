package zaftnotameni.creatania.recipes.cobblegen

import mezz.jei.api.recipe.RecipeType
import mezz.jei.api.registration.IRecipeRegistration
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor

object AllCobblegenRecipes {
  var all : List<CobblegenRecipe>? = ArrayList()
  fun populateRecipes(clientLevel : Level) {
    all = clientLevel.recipeManager.getAllRecipesFor(CobblegenRecipe.TYPE).stream().filter { obj : CobblegenRecipe -> obj.isValid }
      .toList()
  }

  @JvmStatic
  fun getCobblegenRecipes(level : LevelAccessor?) : List<CobblegenRecipe>? {
    if ((all == null || all!!.isEmpty()) && level is Level) populateRecipes(level)
    return all
  }

  @JvmStatic
  fun register(registration : IRecipeRegistration, level : Level?) {
    registration.addRecipes(RecipeType(CobblegenRecipeCategory.UID, CobblegenRecipe::class.java), getCobblegenRecipes(level))
  }
}