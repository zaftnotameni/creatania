package zaftnotameni.creatania.recipes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import zaftnotameni.creatania.registry.Index;

public class ManaGeneratorRecipe extends CreataniaRecipe {
  public ManaGeneratorRecipe(ResourceLocation id, Inputs in, Outputs out) {
    super(id, in, out);
  }

  @Override
  public boolean matches(SimpleContainer pContainer, Level pLevel) {
    if (pLevel.isClientSide()) { return false; }
    if (this.inputs.items.isEmpty()) { return pContainer.isEmpty(); }
    if (pContainer.isEmpty()) { return false; }
    return this.inputs.items.get(0).test(pContainer.getItem(0));
  }

  @Override
  public NonNullList<Ingredient> getIngredients() {
    return this.inputs.items;
  }

  @Override
  public ItemStack assemble(SimpleContainer pContainer) {
    if (this.outputs.items.isEmpty()) return new ItemStack(Blocks.AIR.asItem());
    return this.outputs.items.get(0);
  }

  @Override
  public boolean canCraftInDimensions(int pWidth, int pHeight) {
    return true;
  }

  @Override
  public ItemStack getResultItem() {
    if (this.outputs.items.isEmpty()) return new ItemStack(Blocks.AIR.asItem());
    return this.outputs.items.get(0).copy();
  }

  @Override
  public ResourceLocation getId() {
    return id;
  }

  @Override
  public RecipeSerializer<?> getSerializer() {
    return Serializer.INSTANCE;
  }

  @Override
  public RecipeType<?> getType() {
    return Type.INSTANCE;
  }

  public static class Type implements RecipeType<ManaGeneratorRecipe> {
    private Type() { }
    public static final Type INSTANCE = new Type();
    public static final String ID = "mana_generator";
  }

  public static class Serializer implements RecipeSerializer<ManaGeneratorRecipe> {
    public static final Serializer INSTANCE = new Serializer();
    public static final ResourceLocation ID = Index.resource("mana_generator");
    public static ResourceLocation name = Index.resource("mana_generator");

    @Override
    public ManaGeneratorRecipe fromJson(ResourceLocation id, JsonObject json) {
      JsonArray itemIngredients = GsonHelper.getAsJsonArray(json, "itemIngredients");
      JsonArray fluidIngredients = GsonHelper.getAsJsonArray(json, "fluidIngredients");

      JsonArray itemResults = GsonHelper.getAsJsonArray(json, "itemResults");
      JsonArray fluidResults = GsonHelper.getAsJsonArray(json, "fluidResults");

      NonNullList<Ingredient> itemInputs = NonNullList.create();
      NonNullList<ItemStack> itemOutputs = NonNullList.create();

      NonNullList<Ingredient> fluidInputs = NonNullList.create();
      NonNullList<FluidStack> fluidOutputs = NonNullList.create();

      for (int i = 0; i < itemIngredients.size(); i++) {
        itemInputs.add(Ingredient.fromJson(itemIngredients.get(i)));
      }
      for (int i = 0; i < itemResults.size(); i++) {
        var stack = new ItemStack(
          ForgeRegistries.ITEMS.getValue(
            new ResourceLocation(itemResults.get(i).getAsJsonObject().get("item").getAsString())
          )
        );
        itemOutputs.add(stack);
      }

      for (int i = 0; i < fluidIngredients.size(); i++) {
        fluidInputs.add(Ingredient.fromJson(fluidIngredients.get(i)));
      }
      for (int i = 0; i < fluidResults.size(); i++) {
        var stack = new FluidStack(
          ForgeRegistries.FLUIDS.getValue(
            new ResourceLocation(fluidResults.get(i).getAsJsonObject().get("fluid").getAsString())
          ), 1000
        );
        fluidOutputs.add(stack);
      }

      return new ManaGeneratorRecipe(id,
        Inputs.fromIngredientList(itemInputs).withIngredientList(fluidInputs),
        Outputs.fromItemStackList(itemOutputs).withFluidStackList(fluidOutputs));
    }

    @Override
    public ManaGeneratorRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
      NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
      for (int i = 0; i < inputs.size(); i++) {
        inputs.set(i, Ingredient.fromNetwork(buf));
      }
      ItemStack output = buf.readItem();
      return new ManaGeneratorRecipe(id, Inputs.fromIngredientList(inputs), Outputs.fromItemStack(output));
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, ManaGeneratorRecipe recipe) {
      buf.writeInt(recipe.getIngredients().size());
      for (Ingredient ing : recipe.getIngredients()) {
        ing.toNetwork(buf);
      }
      buf.writeItemStack(recipe.getResultItem(), false);
    }

    @SuppressWarnings("unchecked") // Need this wrapper, because generics
    private static <G> Class<G> castClass(Class<?> cls) {
      return (Class<G>)cls;
    }

    @Override
    public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
      this.name = name;
      return this;
    }
    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
      return ID;
    }
    @Override
    public Class<RecipeSerializer<?>> getRegistryType() {
      return castClass(this.getClass());
    }
  }
}

