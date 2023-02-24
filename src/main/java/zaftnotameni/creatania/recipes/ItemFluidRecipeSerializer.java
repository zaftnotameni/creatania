package zaftnotameni.creatania.recipes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import zaftnotameni.creatania.recipes.base.CreataniaRecipe;
import zaftnotameni.creatania.recipes.base.Inputs;
import zaftnotameni.creatania.recipes.base.Outputs;
public class ItemFluidRecipeSerializer {
  public static <T extends CreataniaRecipe> T fromJson(ResourceLocation id, JsonObject json, CreataniaRecipe.Factory<T> factory) {
    JsonArray itemIngredients = GsonHelper.getAsJsonArray(json, "itemIngredients");
    JsonArray fluidIngredients = GsonHelper.getAsJsonArray(json, "fluidIngredients");

    JsonArray itemResults = GsonHelper.getAsJsonArray(json, "itemResults");
    JsonArray fluidResults = GsonHelper.getAsJsonArray(json, "fluidResults");

    NonNullList<Ingredient> itemInputs = NonNullList.create();
    NonNullList<ItemStack> itemOutputs = NonNullList.create();

    NonNullList<FluidIngredient> fluidInputs = NonNullList.create();
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
      fluidInputs.add(FluidIngredient.deserialize(fluidIngredients.get(i)));
    }
    for (int i = 0; i < fluidResults.size(); i++) {
      var stack = new FluidStack(
        ForgeRegistries.FLUIDS.getValue(
          new ResourceLocation(fluidResults.get(i).getAsJsonObject().get("fluid").getAsString())
        ), 1000
      );
      fluidOutputs.add(stack);
    }

    return factory.apply(id,
      Inputs.fromIngredientList(itemInputs).withFluidIngredientList(fluidInputs),
      Outputs.fromItemStackList(itemOutputs).withFluidStackList(fluidOutputs));
  }
  public static <T extends CreataniaRecipe> T fromNetwork(ResourceLocation id, FriendlyByteBuf buf, CreataniaRecipe.Factory<T> factory) {
    NonNullList<Ingredient> itemInputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
    for (int i = 0; i < itemInputs.size(); i++) { itemInputs.set(i, Ingredient.fromNetwork(buf)); }
    NonNullList<FluidIngredient> fluidInputs = NonNullList.withSize(buf.readInt(), FluidIngredient.EMPTY);
    for (int i = 0; i < fluidInputs.size(); i++) { fluidInputs.set(i, FluidIngredient.read(buf)); }
    NonNullList<ItemStack> itemOutputs = NonNullList.withSize(buf.readInt(), ItemStack.EMPTY);
    for (int i = 0; i < itemOutputs.size(); i++) { itemOutputs.set(i, buf.readItem()); }
    NonNullList<FluidStack> fluidOutputs = NonNullList.withSize(buf.readInt(), FluidStack.EMPTY);
    for (int i = 0; i < fluidOutputs.size(); i++) { fluidOutputs.set(i, FluidStack.readFromPacket(buf)); }
    return factory.apply(id,
      Inputs.fromIngredientList(itemInputs).withFluidIngredientList(fluidInputs),
      Outputs.fromItemStackList(itemOutputs).withFluidStackList(fluidOutputs));
  }
  public static void toNetwork(FriendlyByteBuf buf, CreataniaRecipe recipe) {
    buf.writeInt(recipe.inputs.items.size());
    for (Ingredient ing : recipe.inputs.items) {
      ing.toNetwork(buf);
    }
    buf.writeInt(recipe.inputs.fluids.size());
    for (FluidIngredient ing : recipe.inputs.fluids) {
      ing.write(buf);
    }
    buf.writeInt(recipe.outputs.items.size());
    for (ItemStack s : recipe.outputs.items) {
      buf.writeItemStack(s, false);
    }
    buf.writeInt(recipe.outputs.fluids.size());
    for (FluidStack s : recipe.outputs.fluids) {
      s.writeToPacket(buf);
    }
  }
  public static void serializeRecipeData(JsonObject pJson, Inputs inputs, Outputs outputs) {
    serializeItemIngredients(pJson, inputs);
    serializeFluidIngredients(pJson, inputs);
    serializeItemResults(pJson, outputs);
    serializeFluidResults(pJson, outputs);
  }
  private static void serializeFluidResults(JsonObject pJson, Outputs outputs) {
    JsonArray fluidResultsJson = new JsonArray();
    pJson.add("fluidResults", fluidResultsJson);
    for (var fluid : outputs.fluids) {
      var resultJson = new JsonObject();
      resultJson.addProperty("fluid", ForgeRegistries.FLUIDS.getKey(fluid.getFluid()).toString());
      resultJson.addProperty("amount", fluid.getAmount());
      fluidResultsJson.add(resultJson);
    }
  }
  private static void serializeItemResults(JsonObject pJson, Outputs outputs) {
    JsonArray itemResultsJson = new JsonArray();
    pJson.add("itemResults", itemResultsJson);
    for (var item : outputs.items) {
      var resultJson = new JsonObject();
      resultJson.addProperty("item", ForgeRegistries.ITEMS.getKey(item.getItem()).toString());
      resultJson.addProperty("count", item.getCount());
      itemResultsJson.add(resultJson);
    }
  }
  private static void serializeFluidIngredients(JsonObject pJson, Inputs inputs) {
    JsonArray fluidIngredientsJson = new JsonArray();
    pJson.add("fluidIngredients", fluidIngredientsJson);
    for (var fluid : inputs.fluids) {
      fluidIngredientsJson.add(fluid.serialize());
    }
  }
  private static void serializeItemIngredients(JsonObject pJson, Inputs inputs) {
    JsonArray itemIngredientsJson = new JsonArray();
    pJson.add("itemIngredients", itemIngredientsJson);
    for (var item : inputs.items) {
      itemIngredientsJson.add(item.toJson());
    }
  }
}
