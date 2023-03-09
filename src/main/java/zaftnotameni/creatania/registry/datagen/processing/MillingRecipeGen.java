package zaftnotameni.creatania.registry.datagen.processing;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.AllTags;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;

import static zaftnotameni.creatania.registry.Blocks.REAL_MANA_BLOCK;
import static zaftnotameni.creatania.registry.Items.MANA_GEL;

public class MillingRecipeGen extends ForgeCreateProcessingRecipeProvider {
	private void setupRecipes() {
		create(() -> REAL_MANA_BLOCK.get().asItem(), (b) -> b.output(MANA_GEL.get().asItem()));
	}


	protected GeneratedRecipe metalOre(String name, ItemEntry<? extends Item> crushed, int duration) {
		return create(name + "_ore", b -> b.duration(duration)
			.withCondition(new NotCondition(new TagEmptyCondition("forge", "ores/" + name)))
			.require(AllTags.forgeItemTag("ores/" + name))
			.output(crushed.get()));
	}

	public MillingRecipeGen(DataGenerator p_i48262_1_) {
		super(p_i48262_1_); setupRecipes();
	}

	@Override
	protected AllRecipeTypes getRecipeType() {
		return AllRecipeTypes.MILLING;
	}

}