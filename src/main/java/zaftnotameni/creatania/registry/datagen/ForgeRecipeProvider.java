package zaftnotameni.creatania.registry.datagen;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.world.item.Items;
import zaftnotameni.creatania.registry.Blocks;

import java.util.function.Consumer;

import static net.minecraft.data.recipes.ShapedRecipeBuilder.shaped;
import static net.minecraft.data.recipes.ShapelessRecipeBuilder.shapeless;
public class ForgeRecipeProvider extends RecipeProvider {
  public ForgeRecipeProvider(DataGenerator generator) { super(generator); }

  @Override
  protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
    InventoryChangeTrigger.TriggerInstance x = inventoryTrigger(
      ItemPredicate.Builder.item().of(Items.STICK).build()
    );

    shapeless(Blocks.MANA_CONDENSER.get())
      .requires(Items.STICK)
      .unlockedBy("has_stick", x)
      .save(pFinishedRecipeConsumer);

    shaped(Blocks.MANA_MOTOR.get())
      .define('S', Items.STICK)
      .pattern("SS")
      .unlockedBy("has_stick", x)
      .save(pFinishedRecipeConsumer);

    shaped(Blocks.MANA_GENERATOR.get())
      .define('S', Items.STICK)
      .pattern("SSS")
      .unlockedBy("has_stick", x)
      .save(pFinishedRecipeConsumer);
  }
}
