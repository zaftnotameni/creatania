package zaftnotameni.creatania.recipes.cobblegen;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import zaftnotameni.creatania.registry.CreataniaIndex;
import zaftnotameni.creatania.registry.CreataniaRecipes;

public class CobblegenRecipe extends ProcessingRecipe<Container> implements Recipe<Container> {
  public CobblegenRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams processingRecipeParams) {
    super(TYPE.asRecipeTypeInfo(), processingRecipeParams);
  }
  @Override
  protected int getMaxInputCount() {
    return 2;
  }
  @Override
  protected int getMaxOutputCount() {
    return 1;
  }
  @Override
  public boolean matches(Container pContainer, Level pLevel) {
    return false;
  }
  public static final RegistryObject<RecipeSerializer<?>> serializer = CreataniaRecipes.SERIALIZERS.register(TypeInfo.name, Serializer::new);
  public static final TypeInfo TYPE = new TypeInfo();
  public static final RegistryObject<RecipeType<?>> type = CreataniaRecipes.TYPES.register(TypeInfo.name, TypeInfo::recipeType);
  public static void init() {};
  public NonNullList<FluidIngredient.FluidStackIngredient> getInputFluidStacks() {
    NonNullList<FluidIngredient.FluidStackIngredient> result = NonNullList.create();
    for (var fi : fluidIngredients) if (fi instanceof FluidIngredient.FluidStackIngredient fsi) result.add(fsi);
    return result;
  }
  public static class Serializer extends ProcessingRecipeSerializer<CobblegenRecipe> {
    public Serializer() { super(CobblegenRecipe::new);  }
  }
  public static class TypeInfo implements IRecipeTypeInfo, RecipeType<CobblegenRecipe> {
    public static final String name = "cobblegen";
    public static final ResourceLocation id = CreataniaIndex.resource(name);
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
    public mezz.jei.api.recipe.RecipeType<CobblegenRecipe> asJeiRecipeType() { return new mezz.jei.api.recipe.RecipeType<>(TypeInfo.id, CobblegenRecipe.class); }
    public IRecipeTypeInfo asRecipeTypeInfo() { return this; }
  }
}
