package zaftnotameni.creatania.registry.datagen.processing;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.utility.RegisteredObjects;
import java.util.function.Supplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import zaftnotameni.creatania.registry.Index;

import static zaftnotameni.creatania.registry.Blocks.CORRUPT_MANA_BLOCK;
import static zaftnotameni.creatania.registry.Blocks.PURE_MANA_BLOCK;

public class HauntingRecipeGen extends ForgeCreateProcessingRecipeProvider {

  public void setupRecipes() {
    convert(PURE_MANA_BLOCK.get().asItem(), CORRUPT_MANA_BLOCK.get().asItem());
  }

  public GeneratedRecipe convert(ItemLike input, ItemLike result) {
    return convert(() -> Ingredient.of(input), () -> result);
  }

  public GeneratedRecipe convert(Supplier<Ingredient> input, Supplier<ItemLike> result) {
    return create(Index.resource(RegisteredObjects.getKeyOrThrow(result.get().asItem()).getPath()), p -> p.withItemIngredients(input.get()).output(result.get()));
  }

  public HauntingRecipeGen(DataGenerator p_i48262_1_) {
    super(p_i48262_1_);
    setupRecipes();
  }

  @Override protected AllRecipeTypes getRecipeType() {
    return AllRecipeTypes.HAUNTING;
  }

}