package zaftnotameni.creatania.recipes.base;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import org.jetbrains.annotations.NotNull;
import zaftnotameni.creatania.registry.Index;
public abstract class CreataniaRecipeProcessingCategory<T extends ProcessingRecipe<Container>> implements IRecipeCategory<T> {
  public final IDrawableStatic background;
  public final IDrawable icon;
  public final static ResourceLocation TEXTURE = Index.resource("textures/gui/test.png");
  public CreataniaRecipeProcessingCategory(IGuiHelper helper, IDrawable pIcon) {
    this.icon = pIcon;
    this.background = helper.createDrawable(TEXTURE, 0, 0, 70, 85);
  }
  @Override
  public @NotNull IDrawable getBackground() {
    return this.background;
  }
  @Override
  public @NotNull IDrawable getIcon() {
    return this.icon;
  }
  @Override
  public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull T recipe, @NotNull IFocusGroup focuses) {
    IRecipeCategory.super.setRecipe(builder, recipe, focuses);
  }
}