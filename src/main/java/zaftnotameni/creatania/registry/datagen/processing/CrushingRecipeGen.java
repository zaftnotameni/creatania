//package zaftnotameni.creatania.registry.datagen.processing;
//
//import com.simibubi.create.AllItems;
//import com.simibubi.create.AllRecipeTypes;
//import com.simibubi.create.AllTags;
//import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
//import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
//import com.simibubi.create.content.palettes.AllPaletteStoneTypes;
//import com.simibubi.create.foundation.data.recipe.CompatMetals;
//import com.simibubi.create.foundation.utility.Lang;
//import net.minecraft.data.DataGenerator;
//import net.minecraft.util.Mth;
//import net.minecraft.world.level.ItemLike;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraftforge.common.crafting.conditions.NotCondition;
//import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;
//import org.apache.commons.lang3.StringUtils;
//import zaftnotameni.creatania.util.NamedItems;
//
//import java.util.function.Supplier;
//import java.util.function.UnaryOperator;
//
//import static zaftnotameni.creatania.util.NamedItems.itemLike;
//
//import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
//
//public class CrushingRecipeGen extends ForgeCreateProcessingRecipeProvider {
//	public void setupRecipes() {
//		for (var tall : NamedItems.BOTANIA_TALL_FLOWERS) {
//			var namespace = tall.split(":")[0];
//			var tallPath = tall.split(":")[1];
//			var mysticalPath = StringUtils.replace(tallPath, "double", "mystical");
//			var petalPath = StringUtils.replace(tallPath, "double_flower", "petal");
//			create("botania_flower_crushing_tall_to_mystical",
//				() -> itemLike(namespace, tallPath),
//				b -> b.duration(150)
//				.output(1f, itemLike(namespace, mysticalPath), 4)
//				.output(0.1f, itemLike(namespace, mysticalPath), 1)
//				.output(0.1f, itemLike(namespace, mysticalPath), 1));
//			create("botania_flower_crushing_tall_to_petal",
//				() -> itemLike(namespace, tallPath),
//				b -> b.duration(150)
//					.output(1f, itemLike(namespace, petalPath), 6)
//					.output(0.1f, itemLike(namespace, petalPath), 1)
//					.output(0.1f, itemLike(namespace, petalPath), 1)
//					.output(0.1f, itemLike(namespace, petalPath), 1));
//			create("botania_flower_crushing_mystical_to_petal",
//				() -> itemLike(namespace, mysticalPath),
//				b -> b.duration(150)
//					.output(1f, itemLike(namespace, petalPath), 4)
//					.output(0.1f, itemLike(namespace, petalPath), 1)
//					.output(0.1f, itemLike(namespace, petalPath), 1));
//		}
//	}
//	protected GeneratedRecipe stoneOre(Supplier<ItemLike> ore, Supplier<ItemLike> raw, float expectedAmount,
//		int duration) {
//		return ore(Blocks.COBBLESTONE, ore, raw, expectedAmount, duration);
//	}
//
//	protected GeneratedRecipe deepslateOre(Supplier<ItemLike> ore, Supplier<ItemLike> raw, float expectedAmount,
//		int duration) {
//		return ore(Blocks.COBBLED_DEEPSLATE, ore, raw, expectedAmount, duration);
//	}
//
//	protected GeneratedRecipe netherOre(Supplier<ItemLike> ore, Supplier<ItemLike> raw, float expectedAmount,
//		int duration) {
//		return ore(Blocks.NETHERRACK, ore, raw, expectedAmount, duration);
//	}
//
//	protected GeneratedRecipe mineralRecycling(AllPaletteStoneTypes type, Supplier<ItemLike> crushed,
//		Supplier<ItemLike> nugget, float chance) {
//		return mineralRecycling(type, b -> b.duration(250)
//			.output(chance, crushed.get(), 1)
//			.output(chance, nugget.get(), 1));
//	}
//
//	protected GeneratedRecipe mineralRecycling(AllPaletteStoneTypes type,
//		UnaryOperator<ProcessingRecipeBuilder<ProcessingRecipe<?>>> transform) {
//		create(Lang.asId(type.name()) + "_recycling", b -> transform.apply(b.require(type.materialTag)));
//		return create(type.getBaseBlock()::get, transform);
//	}
//
//	protected GeneratedRecipe ore(ItemLike stoneType, Supplier<ItemLike> ore, Supplier<ItemLike> raw,
//		float expectedAmount, int duration) {
//		return create(ore, b -> {
//			ProcessingRecipeBuilder<ProcessingRecipe<?>> builder = b.duration(duration)
//				.output(raw.get(), Mth.floor(expectedAmount));
//			float extra = expectedAmount - Mth.floor(expectedAmount);
//			if (extra > 0)
//				builder.output(extra, raw.get(), 1);
//			builder.output(.75f, AllItems.EXP_NUGGET.get(), 1);
//			return builder.output(.125f, stoneType);
//		});
//	}
//
//	protected GeneratedRecipe rawOre(Supplier<ItemLike> input, Supplier<ItemLike> result, int amount) {
//		return create(input, b -> b.duration(400)
//			.output(result.get(), amount)
//			.output(.75f, AllItems.EXP_NUGGET.get(), amount));
//	}
//
//	protected GeneratedRecipe moddedRawOre(CompatMetals metal, Supplier<ItemLike> result, int amount) {
//		String name = metal.getName();
//		return create("raw_" + name + (amount == 1 ? "_ore" : "_block"), b -> {
//			String prefix = amount == 1 ? "raw_materials/" : "storage_blocks/raw_";
//			return b.duration(400)
//				.withCondition(new NotCondition(new TagEmptyCondition("forge", prefix + name)))
//				.require(AllTags.forgeItemTag(prefix + name))
//				.output(result.get(), amount)
//				.output(.75f, AllItems.EXP_NUGGET.get(), amount);
//		});
//	}
//
//	protected GeneratedRecipe moddedOre(CompatMetals metal, Supplier<ItemLike> result) {
//		String name = metal.getName();
//		return create(name + "_ore", b -> {
//			String prefix = "ores/";
//			return b.duration(400)
//				.withCondition(new NotCondition(new TagEmptyCondition("forge", prefix + name)))
//				.require(AllTags.forgeItemTag(prefix + name))
//				.output(result.get(), 1)
//				.output(.75f, result.get(), 1)
//				.output(.75f, AllItems.EXP_NUGGET.get(), 1);
//		});
//	}
//
//	public CrushingRecipeGen(DataGenerator dataGenerator) {
//		super(dataGenerator);
//		setupRecipes();
//	}
//
//	@Override
//	protected AllRecipeTypes getRecipeType() {
//		return AllRecipeTypes.CRUSHING;
//	}
//
//}
