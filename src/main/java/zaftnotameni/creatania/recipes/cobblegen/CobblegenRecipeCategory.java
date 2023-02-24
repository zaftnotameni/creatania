package zaftnotameni.creatania.recipes.cobblegen;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import zaftnotameni.creatania.recipes.base.CreataniaRecipeProcessingCategory;
import zaftnotameni.creatania.registry.Blocks;
public class CobblegenRecipeCategory extends CreataniaRecipeProcessingCategory<CobblegenRecipe> {
  public final static ResourceLocation UID = CobblegenRecipe.TypeInfo.id;
  public CobblegenRecipeCategory(IGuiHelper helper) {
    super(helper, helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Blocks.REAL_MANA_BLOCK.get())));
  }
  public static void register(IRecipeCategoryRegistration registration) {
    registration.addRecipeCategories(new CobblegenRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
  }
  @Override
  public Component getTitle() {
    return new TextComponent("Mysterious Cobblegen");
  }
  @SuppressWarnings("removal")
  @Override
  public ResourceLocation getUid() {
    return UID;
  }
  @SuppressWarnings("removal")
  @Override
  public Class<? extends CobblegenRecipe> getRecipeClass() {
    return CobblegenRecipe.class;
  }
  @Override
  public void setRecipe(IRecipeLayoutBuilder builder, CobblegenRecipe recipe, IFocusGroup focuses) {
    try {
      NonNullList<FluidIngredient.FluidStackIngredient> ifs = recipe.getInputFluidStacks();
      var f1 = ifs.get(0).getMatchingFluidStacks().get(0);
      var f2 = ifs.get(1).getMatchingFluidStacks().get(0);
      if (f1.getAmount() < 1000) f1.setAmount(500);
      if (f2.getAmount() < 1000) f2.setAmount(500);
      var flowingTooltip = "Flowing fluid";
      var sourceTooltip = "Fluid still source (will be consumed)";
      builder.addSlot(RecipeIngredientRole.INPUT, 5, 30).addIngredient(
        ForgeTypes.FLUID_STACK,
        f1
      ).addTooltipCallback((recipeSlotView, tooltip) ->
        tooltip.add(new TextComponent(f1.getAmount() > 999 ? sourceTooltip : flowingTooltip)));
      builder.addSlot(RecipeIngredientRole.INPUT, 45, 30).addIngredient(
        ForgeTypes.FLUID_STACK,
        f2
      ).addTooltipCallback((recipeSlotView, tooltip) ->
        tooltip.add(new TextComponent(f2.getAmount() > 999 ? sourceTooltip : flowingTooltip)));
      builder.addSlot(RecipeIngredientRole.OUTPUT, 25, 30).addItemStack(
        recipe.getResultItem()
      );
    } catch (RuntimeException e) {} catch (Exception e) {}
    super.setRecipe(builder, recipe, focuses);
  }
}
