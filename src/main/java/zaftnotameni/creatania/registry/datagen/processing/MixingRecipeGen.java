package zaftnotameni.creatania.registry.datagen.processing;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import net.minecraft.data.DataGenerator;
import zaftnotameni.creatania.registry.Blocks;
import zaftnotameni.creatania.registry.Fluids;

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
	}
	public MixingRecipeGen(DataGenerator p_i48262_1_) {
		super(p_i48262_1_); setupRecipes();
	}

	@Override
	protected AllRecipeTypes getRecipeType() {
		return AllRecipeTypes.MIXING;
	}

}
