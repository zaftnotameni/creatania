package zaftnotameni.creatania.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import zaftnotameni.creatania.recipes.ManaGeneratorRecipe;
import zaftnotameni.creatania.recipes.ManaGeneratorRecipeCategory;
import zaftnotameni.creatania.registry.Index;
@JeiPlugin
public class CreataniaJeiPlugin implements IModPlugin {
  @Override
  public ResourceLocation getPluginUid() {
    return Index.resource("jei_plugin");
  }
  @Override
  public void registerCategories(IRecipeCategoryRegistration registration) {
    registration.addRecipeCategories(new ManaGeneratorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    IModPlugin.super.registerCategories(registration);
  }
  @Override
  public void registerRecipes(IRecipeRegistration registration) {
    var manager = Minecraft.getInstance().level.getRecipeManager();
    var recipes = manager.getAllRecipesFor(ManaGeneratorRecipe.Type.INSTANCE);
    registration.addRecipes(new RecipeType<>(ManaGeneratorRecipeCategory.UID, ManaGeneratorRecipe.class), recipes);
    IModPlugin.super.registerRecipes(registration);
  }
}
