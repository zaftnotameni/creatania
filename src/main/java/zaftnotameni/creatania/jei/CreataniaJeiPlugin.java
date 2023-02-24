package zaftnotameni.creatania.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import zaftnotameni.creatania.recipes.cobblegen.CobblegenRecipe;
import zaftnotameni.creatania.recipes.cobblegen.CobblegenRecipeCategory;
import zaftnotameni.creatania.recipes.condenser.ManaCondenserRecipe;
import zaftnotameni.creatania.recipes.condenser.ManaCondenserRecipeCategory;
import zaftnotameni.creatania.recipes.generator.ManaGeneratorRecipe;
import zaftnotameni.creatania.recipes.generator.ManaGeneratorRecipeCategory;
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
    registration.addRecipeCategories(new ManaCondenserRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    registration.addRecipeCategories(new CobblegenRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    IModPlugin.super.registerCategories(registration);
  }
  @Override
  public void registerRecipes(IRecipeRegistration registration) {
    var manager = Minecraft.getInstance().level.getRecipeManager();
    var generatorRecipes = manager.getAllRecipesFor(ManaGeneratorRecipe.Type.INSTANCE);
    registration.addRecipes(new RecipeType<>(ManaGeneratorRecipeCategory.UID, ManaGeneratorRecipe.class), generatorRecipes);
    var condenserRecipes = manager.getAllRecipesFor(ManaCondenserRecipe.Type.INSTANCE);
    registration.addRecipes(new RecipeType<>(ManaCondenserRecipeCategory.UID, ManaCondenserRecipe.class), condenserRecipes);
    var cobblegenRecipes = manager.getAllRecipesFor(CobblegenRecipe.TYPE);
    registration.addRecipes(new RecipeType<>(CobblegenRecipeCategory.UID, CobblegenRecipe.class), cobblegenRecipes);
    IModPlugin.super.registerRecipes(registration);
  }


}
