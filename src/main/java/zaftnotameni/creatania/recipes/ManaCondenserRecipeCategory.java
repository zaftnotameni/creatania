package zaftnotameni.creatania.recipes;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import zaftnotameni.creatania.registry.Blocks;
import zaftnotameni.creatania.registry.Fluids;
import zaftnotameni.creatania.registry.Index;

import java.util.List;
public class ManaCondenserRecipeCategory extends CreataniaRecipeCategory<ManaCondenserRecipe> {
  public final static ResourceLocation UID = Index.resource(ManaCondenserRecipe.Type.ID);
  public ManaCondenserRecipeCategory(IGuiHelper helper) {
    super(helper, helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Blocks.MANA_GENERATOR.get())));
  }
  @Override
  public Component getTitle() {
    return new TextComponent("Mana Condenser");
  }
  @Override
  public ResourceLocation getUid() {
    return UID;
  }
  @Override
  public Class<? extends ManaCondenserRecipe> getRecipeClass() {
    return ManaCondenserRecipe.class;
  }
  @Override
  public void setRecipe(IRecipeLayoutBuilder builder, ManaCondenserRecipe recipe, IFocusGroup focuses) {
    builder.addSlot(RecipeIngredientRole.OUTPUT, 30, 50).addIngredients(Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft:chest")).asItem()))
      .addTooltipCallback((recipeSlotView, tooltip) -> tooltip.add(new TextComponent("Any Inventory placed below the machine will serve as output")));
    builder.addSlot(RecipeIngredientRole.OUTPUT, 30, 30).addIngredients(Ingredient.of(recipe.outputs.items.get(0).getItem()))
      .addTooltipCallback((recipeSlotView, tooltip) -> tooltip.add(new TextComponent("Corrupt mana is condensed in block form, it will need to be purified as part of a separate process")));
    builder.addSlot(RecipeIngredientRole.CATALYST, 30, 10).addIngredients(Ingredient.of(Blocks.MANA_CONDENSER.get()))
      .addTooltipCallback((recipeSlotView, tooltip) -> tooltip.add(new TextComponent("A mana condenser is used as the mechanism for this process")));
    super.setRecipe(builder, recipe, focuses);
  }
}

