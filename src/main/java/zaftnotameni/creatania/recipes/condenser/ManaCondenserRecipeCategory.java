package zaftnotameni.creatania.recipes.condenser;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;
import zaftnotameni.creatania.recipes.base.CreataniaRecipeCategory;
import zaftnotameni.creatania.registry.CreataniaBlocks;
import zaftnotameni.creatania.registry.CreataniaIndex;
public class ManaCondenserRecipeCategory extends CreataniaRecipeCategory<ManaCondenserRecipe> {
  public final static ResourceLocation UID = CreataniaIndex.resource(ManaCondenserRecipe.Type.ID);
  public ManaCondenserRecipeCategory(IGuiHelper helper) {
    super(helper, helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(CreataniaBlocks.MANA_GENERATOR.get())));
  }
  @Override
  public Component getTitle() {
    return Component.literal("Mana Condenser");
  }
  public ResourceLocation getUid() {
    return UID;
  }
  @Override
  public RecipeType<ManaCondenserRecipe> getRecipeType() {
    return new RecipeType<>(UID, ManaCondenserRecipe.class);
  }
  @Override
  public void setRecipe(IRecipeLayoutBuilder builder, ManaCondenserRecipe recipe, IFocusGroup focuses) {
    builder.addSlot(RecipeIngredientRole.OUTPUT, 30, 50).addIngredients(Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft:chest")).asItem()))
      .addTooltipCallback((recipeSlotView, tooltip) -> tooltip.add(Component.literal("Any Inventory placed below the machine will serve as output")));
    builder.addSlot(RecipeIngredientRole.OUTPUT, 30, 30).addIngredients(Ingredient.of(recipe.outputs.items.get(0).getItem()))
      .addTooltipCallback((recipeSlotView, tooltip) -> tooltip.add(Component.literal("Corrupt mana is condensed in block form, it will need to be purified as part of a separate process")));
    builder.addSlot(RecipeIngredientRole.CATALYST, 30, 10).addIngredients(Ingredient.of(CreataniaBlocks.MANA_CONDENSER.get()))
      .addTooltipCallback((recipeSlotView, tooltip) -> tooltip.add(Component.literal("A mana condenser is used as the mechanism for this process")));
  }
}

