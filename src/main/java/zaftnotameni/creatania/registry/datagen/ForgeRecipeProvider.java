package zaftnotameni.creatania.registry.datagen;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import zaftnotameni.creatania.recipes.Inputs;
import zaftnotameni.creatania.recipes.ManaCondenserRecipeBuilder;
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
    InventoryChangeTrigger.TriggerInstance trigger = inventoryTrigger(
      ItemPredicate.Builder.item().of(Blocks.MANA_MACHINE_COMPONENT.get().asItem()).build()
    );

    var botaniaManaFluid = new FluidStack(Fluids.BOTANIA_MANA_FLUID.get(), 1000);
    var purifiedManaFluid = new FluidStack(Fluids.MANA_FLUID.get(), 1000);
    var air = net.minecraft.world.level.block.Blocks.AIR;
    var corruptedInertManaBlock = new ItemStack(Blocks.CORRUPTED_INERT_MANA_BLOCK.get(), 1);

    new ManaGeneratorRecipeBuilder(
      Inputs.fromFluidIngredient(FluidIngredient.fromFluidStack(purifiedManaFluid)),
      Outputs.fromFluidStack(botaniaManaFluid),
      "botania_mana")
      .unlockedBy("has_mana_machine_component", trigger)
      .save(pFinishedRecipeConsumer);

    new ManaCondenserRecipeBuilder(
      Inputs.empty(),
      Outputs.fromItemStack(corruptedInertManaBlock),
      "corrupted_inert_mana_block")
      .unlockedBy("has_mana_machine_component", trigger)
      .save(pFinishedRecipeConsumer);
  }
}
