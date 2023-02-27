package zaftnotameni.creatania.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import zaftnotameni.creatania.recipes.cobblegen.AllCobblegenRecipes;
import zaftnotameni.creatania.recipes.cobblegen.CobblegenRecipeCategory;
import zaftnotameni.creatania.recipes.condenser.ManaCondenserRecipe;
import zaftnotameni.creatania.recipes.condenser.ManaCondenserRecipeCategory;
import zaftnotameni.creatania.recipes.generator.ManaGeneratorRecipe;
import zaftnotameni.creatania.recipes.generator.ManaGeneratorRecipeCategory;
import zaftnotameni.creatania.registry.CreataniaIndex;
@JeiPlugin
public class CreataniaJeiPlugin implements IModPlugin {
  @Override
  public ResourceLocation getPluginUid() {
    return CreataniaIndex.resource("jei_plugin");
  }
  @Override
  public void registerCategories(IRecipeCategoryRegistration registration) {
    registration.addRecipeCategories(new ManaGeneratorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    registration.addRecipeCategories(new ManaCondenserRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    CobblegenRecipeCategory.register(registration);
    IModPlugin.super.registerCategories(registration);
  }
  @Override
  public void registerRecipes(IRecipeRegistration registration) {
    var level = Minecraft.getInstance().level;
    var manager = level.getRecipeManager();
    registerManaGeneratorRecipes(registration, manager);
    registerManaCondenserRecipes(registration, manager);
    AllCobblegenRecipes.register(registration, level);
    IModPlugin.super.registerRecipes(registration);
  }
  private static void registerManaGeneratorRecipes(IRecipeRegistration registration, RecipeManager manager) {
    var generatorRecipes = manager.getAllRecipesFor(ManaGeneratorRecipe.Type.INSTANCE);
    registration.addRecipes(new RecipeType<>(ManaGeneratorRecipeCategory.UID, ManaGeneratorRecipe.class), generatorRecipes);
  }
  private static void registerManaCondenserRecipes(IRecipeRegistration registration, RecipeManager manager) {
    var condenserRecipes = manager.getAllRecipesFor(ManaCondenserRecipe.Type.INSTANCE);
    registration.addRecipes(new RecipeType<>(ManaCondenserRecipeCategory.UID, ManaCondenserRecipe.class), condenserRecipes);
  }

}
