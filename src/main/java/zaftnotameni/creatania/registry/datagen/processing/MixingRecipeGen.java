package zaftnotameni.creatania.registry.datagen.processing;

import com.simibubi.create.AllRecipeTypes;
import net.minecraft.data.DataGenerator;

public class MixingRecipeGen extends ForgeCreateProcessingRecipeProvider {
	public MixingRecipeGen(DataGenerator p_i48262_1_) {
		super(p_i48262_1_);
	}

	@Override
	protected AllRecipeTypes getRecipeType() {
		return AllRecipeTypes.MIXING;
	}

}
