package zaftnotameni.creatania.registry.datagen.processing;

import com.simibubi.create.AllRecipeTypes;
import net.minecraft.data.DataGenerator;
import org.apache.commons.lang3.StringUtils;
import zaftnotameni.creatania.registry.Fluids;
import zaftnotameni.creatania.util.NamedItems;

import static zaftnotameni.creatania.util.NamedItems.itemLike;

public class FillingRecipeGen extends ForgeCreateProcessingRecipeProvider {
	public void setupRecipes() {
		for (var tall : NamedItems.BOTANIA_TALL_FLOWERS) {
			var namespace = tall.split(":")[0];
			var tallPath = tall.split(":")[1];
			var mysticalPath = StringUtils.replace(tallPath, "double", "mystical");
			var petalPath = StringUtils.replace(tallPath, "double_flower", "petal");

			create(mysticalPath + "_from_" + petalPath, b -> b.require(Fluids.MANA_FLUID.fluid.get(), 250)
				.require(itemLike(namespace, petalPath))
				.output(itemLike(namespace, mysticalPath)));
			create(tallPath + "_from_" + mysticalPath, b -> b.require(Fluids.MANA_FLUID.fluid.get(), 250)
				.require(itemLike(namespace, mysticalPath))
				.output(itemLike(namespace, tallPath)));
			create(tallPath + "_from_" + petalPath, b -> b.require(Fluids.MANA_FLUID.fluid.get(), 1000)
				.require(itemLike(namespace, petalPath))
				.output(itemLike(namespace, tallPath)));
		}
	}
	public FillingRecipeGen(DataGenerator p_i48262_1_) {
		super(p_i48262_1_);
		setupRecipes();
	}

	@Override
	protected AllRecipeTypes getRecipeType() {
		return AllRecipeTypes.FILLING;
	}


}
