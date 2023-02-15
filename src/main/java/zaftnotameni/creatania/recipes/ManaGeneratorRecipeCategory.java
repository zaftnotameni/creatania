package zaftnotameni.creatania.recipes;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import zaftnotameni.creatania.registry.Blocks;
import zaftnotameni.creatania.registry.Index;
public class ManaGeneratorRecipeCategory implements IRecipeCategory<ManaGeneratorRecipe> {
  public final static ResourceLocation UID = Index.resource("mana_generator");
  public final static ResourceLocation TEXTURE = Index.resource("textures/gui/test.png");
  private final IDrawableStatic background;
  private final IDrawable icon;
  public ManaGeneratorRecipeCategory(IGuiHelper helper) {
    this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
    this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Blocks.MANA_GENERATOR.get()));
  }
  @Override
  public Component getTitle() {
    return new TextComponent("Mana Generator");
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
  public Class<? extends ManaGeneratorRecipe> getRecipeClass() {
    return ManaGeneratorRecipe.class;
  }
  @Override
  public void setRecipe(IRecipeLayoutBuilder builder, ManaGeneratorRecipe recipe, IFocusGroup focuses) {
    builder.addSlot(RecipeIngredientRole.INPUT, 57, 18).addIngredients(recipe.getIngredients().get(0));
    builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 60).addItemStack(recipe.getResultItem());
    IRecipeCategory.super.setRecipe(builder, recipe, focuses);

  }
}

