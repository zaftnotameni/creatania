//package zaftnotameni.creatania.registry.datagen.processing;
//
//import com.simibubi.create.AllRecipeTypes;
//import com.simibubi.create.foundation.block.CopperBlockSet;
//import com.simibubi.create.foundation.block.CopperBlockSet.Variant;
//import net.minecraft.data.DataGenerator;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.level.ItemLike;
//import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
//
//import java.util.function.Supplier;
//
//import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
//
//public class DeployingRecipeGen extends ForgeCreateProcessingRecipeProvider {
//	public GeneratedRecipe copperChain(CopperBlockSet set) {
//		for (Variant<?> variant : set.getVariants())
//			for (WeatherState state : WeatherState.values())
//				addWax(set.get(variant, state, true)::get, set.get(variant, state, false)::get);
//		return null;
//	}
//
//	public GeneratedRecipe addWax(Supplier<ItemLike> waxed, Supplier<ItemLike> nonWaxed) {
//		return createWithDeferredId(idWithSuffix(waxed, "_from_adding_wax"), b -> b.require(nonWaxed.get())
//			.require(Items.HONEYCOMB_BLOCK)
//			.toolNotConsumed()
//			.output(waxed.get()));
//	}
//
//	public DeployingRecipeGen(DataGenerator p_i48262_1_) {
//		super(p_i48262_1_);
//	}
//
//	@Override
//	protected AllRecipeTypes getRecipeType() {
//		return AllRecipeTypes.DEPLOYING;
//	}
//
//}
