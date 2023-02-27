package zaftnotameni.creatania.recipes.base;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.resources.ResourceLocation;
import zaftnotameni.creatania.registry.CreataniaIndex;
public abstract class CreataniaRecipeCategory<T extends CreataniaRecipe> implements IRecipeCategory<T> {
  public final IDrawableStatic background;
  public final IDrawable icon;
  public final static ResourceLocation TEXTURE = CreataniaIndex.resource("textures/gui/test.png");
  public CreataniaRecipeCategory(IGuiHelper helper, IDrawable pIcon) {
    this.icon = pIcon;
    this.background = helper.createDrawable(TEXTURE, 0, 0, 60, 85);
  }
  @Override
  public IDrawable getBackground() {
    return this.background;
  }
  @Override
  public IDrawable getIcon() {
    return this.icon;
  }
}
