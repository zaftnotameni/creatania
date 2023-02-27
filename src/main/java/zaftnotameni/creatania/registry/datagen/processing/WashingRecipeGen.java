//package zaftnotameni.creatania.registry.datagen.processing;
//
//import com.simibubi.create.AllRecipeTypes;
//import com.simibubi.create.foundation.data.recipe.CompatMetals;
//import com.simibubi.create.foundation.data.recipe.Mods;
//import com.tterrag.registrate.util.entry.ItemEntry;
//import net.minecraft.data.DataGenerator;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.crafting.Ingredient;
//import net.minecraft.world.level.ItemLike;
//import net.minecraft.world.level.block.Block;
//
//import java.util.function.Supplier;
//
//import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
//
//public class WashingRecipeGen extends ForgeCreateProcessingRecipeProvider {
//	public GeneratedRecipe convert(Block block, Block result) {
//		return create(() -> block, b -> b.output(result));
//	}
//
//	public GeneratedRecipe crushedOre(ItemEntry<Item> crushed, Supplier<ItemLike> nugget, Supplier<ItemLike> secondary,
//		float secondaryChance) {
//		return create(crushed::get, b -> b.output(nugget.get(), 9)
//			.output(secondaryChance, secondary.get(), 1));
//	}
//
//	public GeneratedRecipe moddedCrushedOre(ItemEntry<? extends Item> crushed, CompatMetals metal) {
//		String metalName = metal.getName();
//		for (Mods mod : metal.getMods()) {
//			ResourceLocation nugget = mod.nuggetOf(metalName);
//			create(mod.getId() + "/" + crushed.getId()
//				.getPath(),
//				b -> b.withItemIngredients(Ingredient.of(crushed::get))
//					.output(1, nugget, 9)
//					.whenModLoaded(mod.getId()));
//		}
//		return null;
//	}
//
//	public WashingRecipeGen(DataGenerator dataGenerator) {
//		super(dataGenerator);
//	}
//
//	@Override
//	protected AllRecipeTypes getRecipeType() {
//		return AllRecipeTypes.SPLASHING;
//	}
//
//}
