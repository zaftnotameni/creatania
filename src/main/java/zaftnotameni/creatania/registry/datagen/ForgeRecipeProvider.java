package zaftnotameni.creatania.registry.datagen;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import zaftnotameni.creatania.recipes.Inputs;
import zaftnotameni.creatania.recipes.ManaGeneratorRecipeBuilder;
import zaftnotameni.creatania.recipes.Outputs;
import zaftnotameni.creatania.registry.Blocks;
import zaftnotameni.creatania.registry.Fluids;

import java.util.function.Consumer;

import static net.minecraft.data.recipes.ShapedRecipeBuilder.shaped;
import static net.minecraft.data.recipes.ShapelessRecipeBuilder.shapeless;
public class ForgeRecipeProvider extends RecipeProvider {
  public ForgeRecipeProvider(DataGenerator generator) { super(generator); }

  @Override
  protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
    InventoryChangeTrigger.TriggerInstance x = inventoryTrigger(
      ItemPredicate.Builder.item().of(Blocks.MANA_MACHINE_COMPONENT.get().asItem()).build()
    );

    new ManaGeneratorRecipeBuilder(
      Inputs.fromIngredient(Ingredient.of(Blocks.PURIFIED_INERT_MANA_BLOCK.get())),
      Outputs.fromFluidStack(new FluidStack(Fluids.BOTANIA_MANA_FLUID.get(), 1000)),
      "botania_mana")
      .unlockedBy("has_mana_machine_component", x)
      .save(pFinishedRecipeConsumer);
  }
}
