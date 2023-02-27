package zaftnotameni.creatania.recipes.condenser;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zaftnotameni.creatania.recipes.ItemFluidRecipeSerializer;
import zaftnotameni.creatania.recipes.base.Inputs;
import zaftnotameni.creatania.recipes.base.Outputs;
import zaftnotameni.creatania.registry.CreataniaIndex;

import java.util.function.Consumer;
public class ManaCondenserRecipeBuilder implements RecipeBuilder {
  public final Inputs inputs;
  public final Outputs outputs;
  public final Advancement.Builder advancement = Advancement.Builder.advancement();
  public final String recipeIdPrefix;

  public ManaCondenserRecipeBuilder(Inputs inputs, Outputs outputs, String pRecipeIdPrefix) {
    this.inputs = inputs;
    this.outputs = outputs;
    this.recipeIdPrefix = pRecipeIdPrefix;
  }
  @Override
  public RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
    this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
    return this;
  }
  @Override
  public RecipeBuilder group(@Nullable String pGroupName) {
    return this;
  }
  @Override
  public Item getResult() {
    if (this.outputs.items.isEmpty() || this.outputs.items.size() <= 0) return Items.AIR.asItem();
    return this.outputs.items.get(0).getItem();
  }
  @Override
  public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
    this.advancement.parent(new ResourceLocation("recipes/root"))
      .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId))
      .rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);

    pFinishedRecipeConsumer.accept(new ManaCondenserRecipeBuilder.Result(pRecipeId, this.inputs, this.outputs,
      this.advancement, createAdvancementId(pRecipeId), this.recipeIdPrefix));
  }
  @NotNull
  public ResourceLocation createAdvancementId(ResourceLocation pRecipeId) {
    var folder = "generic";
    return new ResourceLocation(pRecipeId.getNamespace(), "recipes/" +  folder + "/" + pRecipeId.getPath());
  }
  public static class Result implements FinishedRecipe {
    public final ResourceLocation id;
    public final Inputs inputs;
    public final Outputs outputs;
    public final Advancement.Builder advancement;
    public final ResourceLocation advancementId;
    public final String recipeIdPrefix;

    public Result(ResourceLocation pId, Inputs inputs, Outputs outputs, Advancement.Builder pAdvancement,
                  ResourceLocation pAdvancementId, String pRecipeIdPrefix) {
      this.id = pId;
      this.inputs = inputs;
      this.outputs = outputs;
      this.advancement = pAdvancement;
      this.advancementId = pAdvancementId;
      this.recipeIdPrefix = pRecipeIdPrefix;
    }

    @Override
    public void serializeRecipeData(JsonObject pJson) {
      ItemFluidRecipeSerializer.serializeRecipeData(pJson, this.inputs, this.outputs);
    }

    @Override
    public ResourceLocation getId() {
      return CreataniaIndex.resource(this.getRecipeIdPrefix() + "_from_mana_condenser");
    }

    public String getRecipeIdPrefix() {
      if (this.recipeIdPrefix != null && !this.recipeIdPrefix.isEmpty()) return this.recipeIdPrefix;
      return "missing_recipe_id_prefix";
    }

    @Override
    public RecipeSerializer<?> getType() {
      return ManaCondenserRecipe.Serializer.INSTANCE;
    }

    @javax.annotation.Nullable
    public JsonObject serializeAdvancement() {
      return this.advancement.serializeToJson();
    }

    @javax.annotation.Nullable
    public ResourceLocation getAdvancementId() {
      return this.advancementId;
    }
  }
}
