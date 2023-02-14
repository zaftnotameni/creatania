package zaftnotameni.creatania.jei;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import zaftnotameni.creatania.Constants;
// @mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {
  @Override
  public ResourceLocation getPluginUid() {
    return new ResourceLocation(Constants.MODID, "jei_plugin");
  }

  @Override
  public void registerCategories(IRecipeCategoryRegistration registration) {
    registration.addRecipeCategories(new IRecipeCategory<Object>() {
      @Override
      public Component getTitle() {
        return null;
      }
      @Override
      public IDrawable getBackground() {
        return null;
      }
      @Override
      public IDrawable getIcon() {
        return null;
      }
      @Override
      public ResourceLocation getUid() {
        return null;
      }
      @Override
      public Class<?> getRecipeClass() {
        return null;
      }
    });
  }
  @Override
  public void registerRecipes(IRecipeRegistration registration) {
    IModPlugin.super.registerRecipes(registration);
  }
}
