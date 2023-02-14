package zaftnotameni.creatania.recipes;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import zaftnotameni.creatania.Constants;
public class ManaCondenserRecipe implements Recipe<SimpleContainer> {
  private final ResourceLocation id;
  private final ItemStack output;

  public ManaCondenserRecipe(ResourceLocation id, ItemStack output) {
    this.id = id;
    this.output = output;
  }

  @Override
  public boolean matches(SimpleContainer pContainer, Level pLevel) {
    if(pLevel.isClientSide()) {
      return false;
    }
    return true;
  }

  @Override
  public NonNullList<Ingredient> getIngredients() { return NonNullList.create();  }

  @Override
  public ItemStack assemble(SimpleContainer pContainer) {
    return output;
  }

  @Override
  public boolean canCraftInDimensions(int pWidth, int pHeight) {
    return true;
  }

  @Override
  public ItemStack getResultItem() {
    return output.copy();
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

  public static class Type implements RecipeType<ManaCondenserRecipe> {
    private Type() { }
    public static final Type INSTANCE = new Type();
    public static final String ID = "mana_condenser";
  }

  public static class Serializer implements RecipeSerializer<ManaCondenserRecipe> {
    public static final Serializer INSTANCE = new Serializer();
    public static final ResourceLocation ID =
      new ResourceLocation(Constants.MODID,"mana_condenser");

    @Override
    public ManaCondenserRecipe fromJson(ResourceLocation id, JsonObject json) {
      ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));

      return new ManaCondenserRecipe(id, output);
    }

    @Override
    public ManaCondenserRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
      ItemStack output = buf.readItem();
      return new ManaCondenserRecipe(id, output);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, ManaCondenserRecipe recipe) {
      buf.writeItemStack(recipe.getResultItem(), false);
    }

    @SuppressWarnings("unchecked") // Need this wrapper, because generics
    private static <G> Class<G> castClass(Class<?> cls) {
      return (Class<G>)cls;
    }
    @Override
    public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
      return null;
    }
    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
      return null;
    }
    @Override
    public Class<RecipeSerializer<?>> getRegistryType() {
      return null;
    }
  }
}
