package zaftnotameni.creatania.recipes.cobblegen;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.fluid.FluidIngredient.FluidStackIngredient;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import zaftnotameni.creatania.registry.Index;
import zaftnotameni.creatania.registry.Recipes;

public class CobblegenRecipe extends ProcessingRecipe<Container> implements Recipe<Container> {

  public CobblegenRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams processingRecipeParams) {
    super(TYPE.asRecipeTypeInfo(), processingRecipeParams);
  }

  @Override
  protected int getMaxInputCount() {
    return 1;
  }

  @Override protected int getMaxFluidInputCount() { return 2; }

  @Override
  protected int getMaxOutputCount() {
    return 1;
  }

  @Override
  public boolean matches(@NotNull Container pContainer, @NotNull Level pLevel) {
    return false;
  }

  public static final RegistryObject<RecipeSerializer<?>> serializer = Recipes.SERIALIZERS.register(TypeInfo.name, Serializer::new);
  public static final TypeInfo TYPE = new TypeInfo();
  public static final RegistryObject<RecipeType<?>> type = Recipes.TYPES.register(TypeInfo.name, TypeInfo::recipeType);

  public static void init() { }

  public boolean hasSource() {
    return getInputFluidStacks().stream().anyMatch(f -> f.getRequiredAmount() > 900);
  }

  public boolean isValid() {
    return getInputFluidStacks().size() == 2 &&
      !getResultItem().isEmpty();
  }

  List<FluidStackIngredient> sources;
  public List<FluidStackIngredient> getSources() {
    if (sources == null) sources = getInputFluidStacks().stream().filter(f -> f.getRequiredAmount() >= 900).toList();
    return sources;
  }

  List<FluidStackIngredient> flowings;
  public List<FluidStackIngredient> getFlowings() {
    if (flowings == null) flowings = getInputFluidStacks().stream().filter(f -> f.getRequiredAmount() < 900).toList();
    return flowings;
  }

  public Tuple<FluidStackIngredient, FluidStackIngredient> asFlowingFlowing() {
    if (getFlowings().size() != 2 || getFlowings().isEmpty()) return null;
    return new Tuple<>(getFlowings().get(0), getFlowings().get(1));
  }
  public Tuple<FluidStackIngredient, FluidStackIngredient> asFlowingSource() {
    if (getFlowings().size() != 1 || getSources().size() != 1) return null;
    return new Tuple<>(getFlowings().get(0), getSources().get(0));
  }

  public NonNullList<FluidStackIngredient> getInputFluidStacks() {
    NonNullList<FluidStackIngredient> result = NonNullList.create();
    for (var fi : fluidIngredients) if (fi instanceof FluidStackIngredient fsi) result.add(fsi);
    return result;
  }

  public static class Serializer extends ProcessingRecipeSerializer<CobblegenRecipe> {

    public Serializer() { super(CobblegenRecipe::new); }

  }

  public static class TypeInfo implements IRecipeTypeInfo, RecipeType<CobblegenRecipe> {

    public static final String name = "cobblegen";
    public static final ResourceLocation id = Index.resource(name);

    @Override
    public ResourceLocation getId() { return id; }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecipeSerializer<?>> T getSerializer() {
      return (T) serializer.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecipeType<?>> T getType() { return (T) TYPE; }

    public static RecipeType<CobblegenRecipe> recipeType() { return TYPE.asRecipeType(); }

    public RecipeType<CobblegenRecipe> asRecipeType() { return this; }

    public IRecipeTypeInfo asRecipeTypeInfo() { return this; }

  }

}