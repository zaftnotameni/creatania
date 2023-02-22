package zaftnotameni.creatania.registry.datagen.botania;
import com.google.gson.*;
import net.minecraft.core.NonNullList;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;
import zaftnotameni.creatania.registry.Index;

import java.io.IOException;
import java.nio.file.Path;

public class BotaniaBaseRecipeGen {
  public DataGenerator generator;
  public String recipeType = "missing";
  public String resultName = "result";
  public String innerOutput = "item";
  public String typeNoNs() { return StringUtils.split(recipeType, ":")[1]; }
  public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
  public BotaniaBaseRecipeGen(DataGenerator gen, String recipeType) { this(gen, recipeType, "result"); }
  public BotaniaBaseRecipeGen(DataGenerator gen, String recipeType, String output) { this(gen, recipeType, output, "item"); }
  public BotaniaBaseRecipeGen(DataGenerator gen, String recipeType, String output, String innerOutput) { generator = gen; this.recipeType = recipeType; this.resultName = output; this.innerOutput = innerOutput; }

  public static JsonObject empty() { return JsonParser.parseString("{}").getAsJsonObject(); };

  public JsonObject recipe() {
    var j = empty();
    j.addProperty("type", recipeType);
    return j;
  }

  public static JsonObject jingredientOutputs(JsonObject root, NonNullList<Ingredient> ingrs) {
    var arr = new JsonArray();
    for (var i : ingrs) { arr.add(i.toJson()); }
    return jingredientOutputs(root, arr);
  }
  public static JsonObject jingredientOutputs(JsonObject root, JsonArray ingr) {
    root.add("output", ingr);
    return root;
  }
  public static JsonObject jingredients(JsonObject root, NonNullList<Ingredient> ingrs) {
    var arr = new JsonArray();
    for (var i : ingrs) { arr.add(i.toJson()); }
    return jingredients(root, arr);
  }
  public static JsonObject jingredients(JsonObject root, JsonArray ingr) {
    root.add("ingredients", ingr);
    return root;
  }
  public static JsonObject jcatalyst(JsonObject root, String type, String block) {
    var input = empty();
    input.addProperty("type", type);
    input.addProperty("block", block);
    root.add("catalyst", input);
    return root;
  }
  public static JsonObject jinputBlock(JsonObject root, String type, String block) {
    var input = empty();
    input.addProperty("type", type);
    input.addProperty("block", block);
    root.add("input", input);
    return root;
  }
  public static JsonObject jmana(JsonObject root, int mana) {
    root.addProperty("mana", mana);
    return root;
  }
  public static JsonObject jtime(JsonObject root, int time) {
    root.addProperty("time", time);
    return root;
  }
  public static JsonObject jsuccessFunction(JsonObject root, String fn) {
    root.addProperty("success_function", fn);
    return root;
  }
  public static JsonObject jinputItem(JsonObject root, ItemStack stack) {
    var in = empty();
    in.addProperty("item", stack.getItem().getRegistryName().toString());
    if (stack.getCount() > 1) in.addProperty("count", stack.getCount());
    root.add("input", in);
    return root;
  }
  public static JsonObject jfluidResult(JsonObject root, String resultName, String innerName, FluidStack stack) {
    var res = empty();
    res.addProperty(innerName, stack.getFluid().getRegistryName().toString());
    if (stack.getAmount() > 1) res.addProperty("amount", stack.getAmount());
    return jresult(root, resultName, innerName, res);
  }
  public static JsonObject jitemResult(JsonObject root, String resultName, String innerName, ItemStack stack) {
    var res = empty();
    res.addProperty(innerName, stack.getItem().getRegistryName().toString());
    if (stack.getCount() > 1) res.addProperty("count", stack.getCount());
    return jresult(root, resultName, innerName, res);
  }
  public static JsonObject jresult(JsonObject root, String resultName, String innerName, JsonElement result) {
    root.add(resultName, result);
    return root;
  }

  public Builder start() { return new Builder(recipe(), generator, typeNoNs(), resultName, innerOutput); }

  public static class Builder {
    public JsonObject root;
    public NonNullList<Ingredient> ingredients = NonNullList.create();
    public NonNullList<Ingredient> ingredientOutputs = NonNullList.create();
    public ItemStack itemResult = ItemStack.EMPTY;
    public FluidStack fluidResult = FluidStack.EMPTY;
    public int mana = 0;
    public int time = 0;
    public DataGenerator generator;
    public String typeName;
    public String resultName;
    public String innerOutput;
    public String inputType;
    public String inputBlock;
    public ItemStack inputItem;
    public String successFunction;
    public String catalystBlockName;
    public Builder(JsonObject root, DataGenerator generator, String typeName, String resultName, String innerOutput) {
      this.root = root; this.generator = generator; this.typeName = typeName; this.resultName = resultName; this.innerOutput = innerOutput;
    }
    public Builder build() {
      if (inputItem != null) jinputItem(root, inputItem);
      if (ingredients.size() > 0) jingredients(root, ingredients);
      if (!StringUtils.isAnyBlank(inputBlock, inputType)) jinputBlock(root, inputType, inputBlock);
      if (time > 0) jtime(root, time);
      if (mana > 0) jmana(root, mana);
      if (ingredientOutputs.size() > 0) jingredientOutputs(root, ingredientOutputs);
      if (!itemResult.isEmpty()) jitemResult(root, resultName, innerOutput, itemResult);
      if (!fluidResult.isEmpty()) jfluidResult(root, resultName, innerOutput, fluidResult);
      if (!StringUtils.isAnyBlank(successFunction)) jsuccessFunction(root, successFunction);
      if (!StringUtils.isAnyBlank(catalystBlockName)) jcatalyst(root, "block", catalystBlockName);
      return this;
    }
    public Builder inputTypeBlock(String block) {
      this.inputType = "block";
      this.inputBlock = block;
      return this;
    }
    public Builder itemResult(ItemStack i) {
      itemResult = i;
      return this;
    }
    public Builder fluidResult(FluidStack fs) {
      this.fluidResult = fs;
      return this;
    }
    public Builder inputItem(Ingredient i) {
      this.inputItem = i.getItems()[0];
      return this;
    }
    public Builder catalystBlockName(String c) {
      this.catalystBlockName = c;
      return this;
    }
    public Builder successFunction(String fn) {
      this.successFunction = fn;
      return this;
    }
    public Builder time(int i) { this.time = i; return this; }
    public Builder mana(int i) { this.mana = i; return this; }
    public Builder ingredient(ResourceLocation id) {
      return ingredient(Ingredient.of(ForgeRegistries.ITEMS.getValue(id)));
    }
    public Builder ingredient(Ingredient i) {
      ingredients.add(i);
      return this;
    }
    public Builder ingredientOutput(Ingredient i) {
      ingredientOutputs.add(i);
      return this;
    }
    public Builder saveAs(ResourceLocation id, String suffix, HashCache pCache) {
      try {
        DataProvider.save(GSON, pCache, root, getPath(generator.getOutputFolder(), typeName, id, suffix));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      return this;
    }
    public Builder saveAs(ResourceLocation id, HashCache pCache) {
      return saveAs(id, "", pCache);
    }
  }
  public static Path getPath(Path root, String typeName, String path) {
    return getPath(root, typeName, Index.resource(path), "");
  }
  public static  Path getPath(Path root, String typeName, ResourceLocation id, String suffix) {
    return root.resolve("data/" + id.getNamespace() + "/recipes/botanist/" + typeName + "/" + id.getPath() + suffix + ".json");
  }
}
