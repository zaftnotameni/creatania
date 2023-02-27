//package zaftnotameni.creatania.registry.datagen.processing;
//
//import com.simibubi.create.AllRecipeTypes;
//import com.simibubi.create.AllTags;
//import com.tterrag.registrate.util.entry.ItemEntry;
//import net.minecraft.data.DataGenerator;
//import net.minecraft.world.item.Item;
//import net.minecraftforge.common.crafting.conditions.NotCondition;
//import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;
//
//import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
//
//public class MillingRecipeGen extends ForgeCreateProcessingRecipeProvider {
//	protected GeneratedRecipe metalOre(String name, ItemEntry<? extends Item> crushed, int duration) {
//		return create(name + "_ore", b -> b.duration(duration)
//			.withCondition(new NotCondition(new TagEmptyCondition("forge", "ores/" + name)))
//			.require(AllTags.forgeItemTag("ores/" + name))
//			.output(crushed.get()));
//	}
//
//	public MillingRecipeGen(DataGenerator p_i48262_1_) {
//		super(p_i48262_1_);
//	}
//
//	@Override
//	protected AllRecipeTypes getRecipeType() {
//		return AllRecipeTypes.MILLING;
//	}
//
//}
