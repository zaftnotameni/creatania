package zaftnotameni.creatania.recipes.condenser;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zaftnotameni.creatania.recipes.ItemFluidRecipeSerializer;
import zaftnotameni.creatania.recipes.base.CreataniaRecipe;
import zaftnotameni.creatania.recipes.base.Inputs;
import zaftnotameni.creatania.recipes.base.Outputs;
import zaftnotameni.creatania.registry.Index;

public class ManaCondenserRecipe extends CreataniaRecipe {
  public ManaCondenserRecipe(ResourceLocation id, Inputs in, Outputs out) {
    super(id, in, out);
  }

  @Override
  public boolean matches(@NotNull SimpleContainer pContainer, Level pLevel) {
    if (pLevel.isClientSide()) { return false; }
    if (this.inputs.items.isEmpty()) { return pContainer.isEmpty(); }
    if (pContainer.isEmpty()) { return false; }
    return this.inputs.items.get(0).test(pContainer.getItem(0));
  }

  @Override
  public @NotNull NonNullList<Ingredient> getIngredients() {
    return this.inputs.items;
  }

  @Override
  public @NotNull ItemStack assemble(@NotNull SimpleContainer pContainer) {
    if (this.outputs.items.isEmpty()) return new ItemStack(Blocks.AIR.asItem());
    return this.outputs.items.get(0);
  }

  @Override
  public boolean canCraftInDimensions(int pWidth, int pHeight) {
    return true;
  }

  @Override
  public @NotNull ItemStack getResultItem() {
    if (this.outputs.items.isEmpty()) return new ItemStack(Blocks.AIR.asItem());
    return this.outputs.items.get(0).copy();
  }

  @Override
  public @NotNull ResourceLocation getId() {
    return id;
  }

  @Override
  public @NotNull RecipeSerializer<?> getSerializer() {
    return Serializer.INSTANCE;
  }

  @Override
  public @NotNull RecipeType<?> getType() {
    return Type.INSTANCE;
  }

  public static class Type implements RecipeType<ManaCondenserRecipe> {
    private Type() { }
    public static final Type INSTANCE = new Type();
    public static final String ID = "mana_condenser";
  }

  public static class Serializer implements RecipeSerializer<ManaCondenserRecipe> {
    public static final Serializer INSTANCE = new Serializer();
    public static final ResourceLocation ID = Index.resource(ManaCondenserRecipe.Type.ID);
    public static ResourceLocation name = Index.resource(ManaCondenserRecipe.Type.ID);

    @Override
    public @NotNull ManaCondenserRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
      return ItemFluidRecipeSerializer.fromJson(id, json, ManaCondenserRecipe::new);
    }

    @Override
    public ManaCondenserRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
      return ItemFluidRecipeSerializer.fromNetwork(id, buf, ManaCondenserRecipe::new);
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull ManaCondenserRecipe recipe) {
      ItemFluidRecipeSerializer.toNetwork(buf, recipe);
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