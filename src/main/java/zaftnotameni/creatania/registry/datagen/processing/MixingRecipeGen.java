package zaftnotameni.creatania.registry.datagen.processing;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import java.util.function.BiFunction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.crafting.Ingredient;
import zaftnotameni.creatania.registry.Blocks;
import zaftnotameni.creatania.registry.Fluids;

import static com.simibubi.create.AllBlocks.DEEPSLATE_ZINC_ORE;
import static com.simibubi.create.AllBlocks.ZINC_ORE;
import static net.minecraft.world.item.Items.*;
import static zaftnotameni.creatania.registry.Fluids.*;
import static zaftnotameni.creatania.util.NamedItems.*;

public class MixingRecipeGen extends ForgeCreateProcessingRecipeProvider {

  public void setupRecipes() {
    create("mana_pylon", b -> b.require(FluidIngredient.fromFluid(MOLTEN_MANASTEEL.get(), 500))
      .require(AllItems.GOLDEN_SHEET.get())
      .require(AllItems.GOLDEN_SHEET.get())
      .require(AllItems.GOLDEN_SHEET.get())
      .require(AllItems.GOLDEN_SHEET.get())
      .output(itemLike("botania", "mana_pylon")));

    manaBasedOreReconstruction();
    manaBlocksMelting();
    botaniaIngotsMelting();
    createIngotsMelting();
    vanillaIngotsMelting();

    BiFunction<Integer, ProcessingRecipeBuilder, ProcessingRecipeBuilder> eachFlowerOutput = (i, b) -> {
      var mod3 = i / 3;
      for (var j = mod3 * 4; j < mod3 * 4 + 4; j++) {
        var flowerId = BOTANIA_MYSTICAL_FLOWERS[j];
        b.output(1f / 4f, itemLikeOf(flowerId), 1);
      }
      return b;
    };
    BiFunction<Integer, ProcessingRecipeBuilder, ProcessingRecipeBuilder> eachTallFlowerOutput = (i, b) -> {
      var mod4 = i;
      for (var j = mod4 * 4; j < mod4 * 4 + 4; j++) {
        var flowerId = BOTANIA_TALL_FLOWERS[j];
        b.output(1f / 4f, itemLikeOf(flowerId), 1);
      }
      return b;
    };

    for (var i = 0; i < MINECRAFT_SHORT_FLOWERS.length; i++) {
      var shortFlower = MINECRAFT_SHORT_FLOWERS[i];
      final int index = i;
      create("mystic_flowers_from_vanilla_flower_" + pathOfItem(shortFlower), b -> eachFlowerOutput.apply(index, b.require(shortFlower)).require(Fluids.PURE_MANA.get(), 125));
    }
    for (var i = 0; i < MINECRAFT_TALL_FLOWERS.length; i++) {
      var tallFlower = MINECRAFT_TALL_FLOWERS[i];
      final int index = i;
      create("tall_mystic_flowers_from_tall_vanilla_flower_" + pathOfItem(tallFlower), b -> eachTallFlowerOutput.apply(index, b.require(tallFlower)).require(Fluids.PURE_MANA.get(), 125));
    }

  }

  private void manaBasedOreReconstruction() {
    create("iron_ore", b -> b.require(FluidIngredient.fromFluid(MOLTEN_MANASTEEL.get(), 125)).require(FluidIngredient.fromFluid(MOLTEN_IRON.get(), 125)).require(STONE).output(IRON_ORE));

    create("copper_ore", b -> b.require(FluidIngredient.fromFluid(MOLTEN_MANASTEEL.get(), 125)).require(FluidIngredient.fromFluid(MOLTEN_COPPER.get(), 125)).require(STONE).output(COPPER_ORE));

    create("zinc_ore", b -> b.require(FluidIngredient.fromFluid(MOLTEN_MANASTEEL.get(), 125)).require(FluidIngredient.fromFluid(MOLTEN_ZINC.get(), 125)).require(STONE).output(ZINC_ORE.get()));

    create("gold_ore", b -> b.require(FluidIngredient.fromFluid(MOLTEN_MANASTEEL.get(), 125)).require(FluidIngredient.fromFluid(MOLTEN_GOLD.get(), 125)).require(STONE).output(GOLD_ORE));

    create("nether_gold_ore", b -> b.require(FluidIngredient.fromFluid(MOLTEN_MANASTEEL.get(), 125))
      .require(FluidIngredient.fromFluid(MOLTEN_GOLD.get(), 125))
      .require(NETHERRACK)
      .output(NETHER_GOLD_ORE));

    create("deep_iron_ore", b -> b.require(FluidIngredient.fromFluid(MOLTEN_MANASTEEL.get(), 125))
      .require(FluidIngredient.fromFluid(MOLTEN_IRON.get(), 125))
      .require(DEEPSLATE)
      .output(DEEPSLATE_IRON_ORE));

    create("deep_copper_ore", b -> b.require(FluidIngredient.fromFluid(MOLTEN_MANASTEEL.get(), 125))
      .require(FluidIngredient.fromFluid(MOLTEN_COPPER.get(), 125))
      .require(DEEPSLATE)
      .output(DEEPSLATE_COPPER_ORE));

    create("deep_zinc_ore", b -> b.require(FluidIngredient.fromFluid(MOLTEN_MANASTEEL.get(), 125))
      .require(FluidIngredient.fromFluid(MOLTEN_ZINC.get(), 125))
      .require(DEEPSLATE)
      .output(DEEPSLATE_ZINC_ORE.get()));

    create("deep_gold_ore", b -> b.require(FluidIngredient.fromFluid(MOLTEN_MANASTEEL.get(), 125))
      .require(FluidIngredient.fromFluid(MOLTEN_GOLD.get(), 125))
      .require(DEEPSLATE)
      .output(DEEPSLATE_GOLD_ORE));

    create("diamond_ore", b -> b.require(FluidIngredient.fromFluid(MOLTEN_GAIA.get(), 125)).require(FluidIngredient.fromFluid(MOLTEN_MANASTEEL.get(), 125)).require(STONE).output(DIAMOND_ORE));

    create("emerald_ore", b -> b.require(FluidIngredient.fromFluid(MOLTEN_GAIA.get(), 125)).require(FluidIngredient.fromFluid(MOLTEN_TERRASTEEL.get(), 125)).require(STONE).output(EMERALD_ORE));

    create("deep_diamond_ore", b -> b.require(FluidIngredient.fromFluid(MOLTEN_GAIA.get(), 125))
      .require(FluidIngredient.fromFluid(MOLTEN_MANASTEEL.get(), 125))
      .require(DEEPSLATE)
      .output(DEEPSLATE_DIAMOND_ORE));

    create("deep_emerald_ore", b -> b.require(FluidIngredient.fromFluid(MOLTEN_GAIA.get(), 125))
      .require(FluidIngredient.fromFluid(MOLTEN_TERRASTEEL.get(), 125))
      .require(DEEPSLATE)
      .output(DEEPSLATE_EMERALD_ORE));

    create("redstone_ore", b -> b.require(FluidIngredient.fromFluid(MOLTEN_TERRASTEEL.get(), 125)).require(FluidIngredient.fromFluid(MOLTEN_IRON.get(), 125)).require(STONE).output(REDSTONE_ORE));

    create("deep_redstone_ore", b -> b.require(FluidIngredient.fromFluid(MOLTEN_TERRASTEEL.get(), 125))
      .require(FluidIngredient.fromFluid(MOLTEN_IRON.get(), 125))
      .require(DEEPSLATE)
      .output(DEEPSLATE_REDSTONE_ORE));

    create("lapis_ore", b -> b.require(FluidIngredient.fromFluid(MOLTEN_TERRASTEEL.get(), 125)).require(FluidIngredient.fromFluid(MOLTEN_MANASTEEL.get(), 125)).require(STONE).output(LAPIS_ORE));

    create("deep_lapis_ore", b -> b.require(FluidIngredient.fromFluid(MOLTEN_TERRASTEEL.get(), 125))
      .require(FluidIngredient.fromFluid(MOLTEN_MANASTEEL.get(), 125))
      .require(DEEPSLATE)
      .output(DEEPSLATE_LAPIS_ORE));

    create("glowstone", b -> b.require(FluidIngredient.fromFluid(MOLTEN_TERRASTEEL.get(), 125)).require(FluidIngredient.fromFluid(MOLTEN_GOLD.get(), 125)).require(STONE).output(GLOWSTONE));

    create("nether_quartz", b -> b.require(FluidIngredient.fromFluid(MOLTEN_ELEMENTIUM.get(), 125))
      .require(FluidIngredient.fromFluid(MOLTEN_ZINC.get(), 125))
      .require(NETHERRACK)
      .output(NETHER_QUARTZ_ORE));

    create("coal_ore", b -> b.require(FluidIngredient.fromFluid(MOLTEN_TERRASTEEL.get(), 125)).require(FluidIngredient.fromFluid(MOLTEN_COPPER.get(), 125)).require(STONE).output(COAL_ORE));

    create("deep_coal_ore", b -> b.require(FluidIngredient.fromFluid(MOLTEN_TERRASTEEL.get(), 125))
      .require(FluidIngredient.fromFluid(MOLTEN_COPPER.get(), 125))
      .require(DEEPSLATE)
      .output(DEEPSLATE_COAL_ORE));
  }

  private void vanillaIngotsMelting() {
    create("molten_iron_from_ingot", b -> b.require(Ingredient.of(itemLikeOf("minecraft:iron_ingot"))).output(MOLTEN_IRON.get(), 125).requiresHeat(HeatCondition.SUPERHEATED));
    create("molten_gold_from_ingot", b -> b.require(Ingredient.of(itemLikeOf("minecraft:gold_ingot"))).output(MOLTEN_GOLD.get(), 125).requiresHeat(HeatCondition.HEATED));
    create("molten_copper_from_ingot", b -> b.require(Ingredient.of(itemLikeOf("minecraft:copper_ingot"))).output(MOLTEN_COPPER.get(), 125).requiresHeat(HeatCondition.SUPERHEATED));
  }

  private void createIngotsMelting() {
    create("molten_brass_flued", b -> b.require(FluidIngredient.fromFluid(MOLTEN_COPPER.get(), 125))
      .require(FluidIngredient.fromFluid(MOLTEN_ZINC.get(), 125))
      .output(Fluids.MOLTEN_BRASS.get(), 250)
      .requiresHeat(HeatCondition.SUPERHEATED));
    create("molten_brass_ingot", b -> b.require(Ingredient.of(AllItems.BRASS_INGOT.get())).output(Fluids.MOLTEN_BRASS.get(), 125).requiresHeat(HeatCondition.SUPERHEATED));
    create("molten_zinc_ingot", b -> b.require(Ingredient.of(AllItems.ZINC_INGOT.get())).output(MOLTEN_ZINC.get(), 125).requiresHeat(HeatCondition.SUPERHEATED));
    create("molten_andesite_alloy_from_ingot", b -> b.require(Ingredient.of(AllItems.ANDESITE_ALLOY.get())).output(Fluids.MOLTEN_ANDESITE.get(), 125).requiresHeat(HeatCondition.SUPERHEATED));
  }

  private void botaniaIngotsMelting() {
    create("molten_manasteel_from_ingot", b -> b.require(Ingredient.of(itemLike("botania", "manasteel_ingot"))).output(MOLTEN_MANASTEEL.get(), 125).requiresHeat(HeatCondition.HEATED));
    create("molten_terrasteel_from_ingot", b -> b.require(Ingredient.of(itemLike("botania", "terrasteel_ingot"))).output(MOLTEN_TERRASTEEL.get(), 125).requiresHeat(HeatCondition.SUPERHEATED));
    create("molten_elementium_from_ingot", b -> b.require(Ingredient.of(itemLike("botania", "elementium_ingot"))).output(MOLTEN_ELEMENTIUM.get(), 125).requiresHeat(HeatCondition.SUPERHEATED));
    create("molten_gaia_from_ingot", b -> b.require(Ingredient.of(itemLike("botania", "gaia_ingot"))).output(Fluids.MOLTEN_GAIA.get(), 125).requiresHeat(HeatCondition.SUPERHEATED));
  }

  private void manaBlocksMelting() {
    create("real_botania_mana_fluid", b -> b.require(Blocks.REAL_MANA_BLOCK.get()).output(Fluids.REAL_MANA.get(), 250).requiresHeat(HeatCondition.SUPERHEATED));
    create("corrupted_inert_mana_fluid", b -> b.require(Blocks.CORRUPT_MANA_BLOCK.get()).output(Fluids.CORRUPT_MANA.get(), 250).requiresHeat(HeatCondition.HEATED));
    create("purified_inert_mana_fluid", b -> b.require(Blocks.PURE_MANA_BLOCK.get()).output(Fluids.PURE_MANA.get(), 250).requiresHeat(HeatCondition.HEATED));
  }

  public MixingRecipeGen(DataGenerator p_i48262_1_) {
    super(p_i48262_1_);
    setupRecipes();
  }

  @Override protected AllRecipeTypes getRecipeType() {
    return AllRecipeTypes.MIXING;
  }

}