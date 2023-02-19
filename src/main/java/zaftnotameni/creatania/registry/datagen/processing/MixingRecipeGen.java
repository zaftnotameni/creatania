package zaftnotameni.creatania.registry.datagen.processing;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.crafting.Ingredient;
import zaftnotameni.creatania.registry.Blocks;
import zaftnotameni.creatania.registry.Fluids;

import java.util.function.Function;

import static zaftnotameni.creatania.util.NamedItems.*;

public class MixingRecipeGen extends ForgeCreateProcessingRecipeProvider {
	public void setupRecipes() {
		create("real_botania_mana_fluid", b -> b.require(Blocks.BOTANIA_MANA_BLOCK.get())
			.output(Fluids.BOTANIA_MANA_FLUID.fluid.get(), 250)
			.requiresHeat(HeatCondition.SUPERHEATED));
		create("corrupted_inert_mana_fluid", b -> b.require(Blocks.CORRUPTED_INERT_MANA_BLOCK.get())
			.output(Fluids.CORRUPT_MANA_FLUID.fluid.get(), 250)
			.requiresHeat(HeatCondition.HEATED));
		create("purified_inert_mana_fluid", b -> b.require(Blocks.PURIFIED_INERT_MANA_BLOCK.get())
			.output(Fluids.PURIFIED_MANA_FLUID.fluid.get(), 250)
			.requiresHeat(HeatCondition.HEATED));


		create("molten_manasteel_from_ingot", b -> b.require(Ingredient.of(itemLike("botania", "manasteel_ingot")))
			.output(Fluids.MOLTEN_MANASTEEL.fluid.get(), 125)
			.requiresHeat(HeatCondition.HEATED));
		create("molten_terrasteel_from_ingot", b -> b.require(Ingredient.of(itemLike("botania", "terrasteel_ingot")))
			.output(Fluids.MOLTEN_MANASTEEL.fluid.get(), 125)
			.requiresHeat(HeatCondition.SUPERHEATED));
		create("molten_elementium_from_ingot", b -> b.require(Ingredient.of(itemLike("botania", "elementium_ingot")))
			.output(Fluids.MOLTEN_MANASTEEL.fluid.get(), 125)
			.requiresHeat(HeatCondition.SUPERHEATED));
		create("molten_gaia_from_ingot", b -> b.require(Ingredient.of(itemLike("botania", "gaia_ingot")))
			.output(Fluids.MOLTEN_MANASTEEL.fluid.get(), 125)
			.requiresHeat(HeatCondition.SUPERHEATED));

		create("molten_brass_ingot", b -> b.require(Ingredient.of(AllItems.BRASS_INGOT.get()))
			.output(Fluids.MOLTEN_BRASS_FLUID.fluid.get(), 125)
			.requiresHeat(HeatCondition.SUPERHEATED));
		create("molten_zinc_ingot", b -> b.require(Ingredient.of(AllItems.ZINC_INGOT.get()))
			.output(Fluids.MOLTEN_ZINC_FLUID.fluid.get(), 125)
			.requiresHeat(HeatCondition.SUPERHEATED));
		create("molten_andesite_alloy_from_ingot", b -> b.require(Ingredient.of(AllItems.ANDESITE_ALLOY.get()))
			.output(Fluids.MOLTEN_ANDESITE_ALLOY_FLUID.fluid.get(), 125)
			.requiresHeat(HeatCondition.SUPERHEATED));

		Function<ProcessingRecipeBuilder, ProcessingRecipeBuilder> eachlowerOutput = b -> {
			for (var flowerId : BOTANIA_MYSTICAL_FLOWERS) b.output(0.1f, itemLikeOf(flowerId), 1);
			return b;
		};
		Function<ProcessingRecipeBuilder, ProcessingRecipeBuilder> eachTallFlowerOutput = b -> {
			for (var flowerId : BOTANIA_TALL_FLOWERS) b.output(0.1f, itemLikeOf(flowerId), 1);
			return b;
		};

		create("mystic_flowers_from_vanilla_flowers", b -> eachTallFlowerOutput.apply(b
			.require(itemLikeOf("minecraft:poppy")))
			.require(Fluids.PURIFIED_MANA_FLUID.fluid.get(), 125));
		create("tall_mystic_flowers_from_tall_vanilla_flowers", b -> eachTallFlowerOutput.apply(b
			.require(itemLikeOf("minecraft:poppy")))
			.require(Fluids.PURIFIED_MANA_FLUID.fluid.get(), 125));

	}
	public MixingRecipeGen(DataGenerator p_i48262_1_) {
		super(p_i48262_1_); setupRecipes();
	}

	@Override
	protected AllRecipeTypes getRecipeType() {
		return AllRecipeTypes.MIXING;
	}

}
