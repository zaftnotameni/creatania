package zaftnotameni.creatania.recipes;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import zaftnotameni.creatania.Constants;
public class ManaCondenserRecipeCategory implements IRecipeCategory<ManaCondenserRecipe> {
  public static final ResourceLocation UID = new ResourceLocation(Constants.MODID, "mana_condenser_recipe_category");
  public static final ResourceLocation TEXTURE = new ResourceLocation(Constants.MODID, "textures/gui/mana_condenser_recipe_category");

  public final IDrawable background;
  public final IDrawable icon;

  public ManaCondenserRecipeCategory() {
    this.background = new IDrawable() {
      @Override
      public int getWidth() {
        return 100;
      }
      @Override
      public int getHeight() {
        return 100;
      }
      @Override
      public void draw(PoseStack poseStack, int xOffset, int yOffset) {
        return;
      }
    };
    this.icon = new IDrawable() {
      @Override
      public int getWidth() {
        return 16;
      }
      @Override
      public int getHeight() {
        return 16;
      }
      @Override
      public void draw(PoseStack poseStack, int xOffset, int yOffset) {
        return;
      }
    };
  }

  @Override
  public Component getTitle() {
    return new TextComponent("Mana Condenser");
  }
  @Override
  public IDrawable getBackground() {
    return this.background;
  }
  @Override
  public IDrawable getIcon() {
    return this.icon;
  }
  @Override
  public ResourceLocation getUid() {
    return UID;
  }
  @Override
  public Class<? extends ManaCondenserRecipe> getRecipeClass() {
    return ManaCondenserRecipe.class;
  }
}
