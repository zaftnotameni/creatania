package zaftnotameni.creatania.registry.datagen.processing;

import com.simibubi.create.content.palettes.AllPaletteStoneTypes;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import com.tterrag.registrate.util.entry.FluidEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;
import zaftnotameni.creatania.recipes.cobblegen.CobblegenRecipe;

import static com.simibubi.create.content.palettes.AllPaletteStoneTypes.*;
import static zaftnotameni.creatania.registry.CreataniaFluids.ALL;

public class CobblegenRecipeGen extends ForgeCreateProcessingRecipeProvider {

	public void setupRecipes() {
		mffb(ALL.MOLTEN_COPPER, GRANITE);
		mffb(ALL.MOLTEN_ZINC, DIORITE);
		mffb(ALL.MOLTEN_ANDESITE, ANDESITE);
		mffb(ALL.MOLTEN_BRASS, LIMESTONE);
		mffb(ALL.MOLTEN_IRON, SCORCHIA);
		mffb(ALL.MOLTEN_ELEMENTIUM, SCORIA);
		mffb(ALL.MOLTEN_GAIA, CALCITE);
		mffb(ALL.MOLTEN_MANASTEEL, DRIPSTONE);
		mffb(ALL.MOLTEN_GOLD, DEEPSLATE);
		mffb(ALL.MOLTEN_TERRASTEEL, TUFF);

		mfsb(ALL.MOLTEN_COPPER, ASURINE);
		mfsb(ALL.MOLTEN_ZINC, NonNullSupplier.of(() -> Blocks.NETHERRACK));
		mfsb(ALL.MOLTEN_ANDESITE, NonNullSupplier.of(() -> Blocks.BLACKSTONE));
		mfsb(ALL.MOLTEN_BRASS, NonNullSupplier.of(() -> Blocks.BASALT));
		mfsb(ALL.MOLTEN_IRON, CRIMSITE);
		mfsb(ALL.MOLTEN_ELEMENTIUM, NonNullSupplier.of(() -> Blocks.WARPED_NYLIUM));
		mfsb(ALL.MOLTEN_GAIA, NonNullSupplier.of(() -> Blocks.ANCIENT_DEBRIS));
		mfsb(ALL.MOLTEN_MANASTEEL, VERIDIUM);
		mfsb(ALL.MOLTEN_GOLD, OCHRUM);
		mfsb(ALL.MOLTEN_TERRASTEEL, NonNullSupplier.of(() -> Blocks.CRIMSON_NYLIUM));
	}

	public void mfsb(FluidEntry<? extends ForgeFlowingFluid.Flowing> b, AllPaletteStoneTypes c) {
		ffb("flowing_source_none", ALL.PURE_MANA, b, c.getBaseBlock(), 1, 1000, 1);
	}
	public void mfsb(FluidEntry<? extends ForgeFlowingFluid.Flowing> b, NonNullSupplier<Block> c) {
		ffb("flowing_source_none", ALL.PURE_MANA, b, c, 1, 1000, 1);
	}
	public void mffb(FluidEntry<? extends ForgeFlowingFluid.Flowing> b, AllPaletteStoneTypes c) {
		ffb("flowing_flowing_none", ALL.PURE_MANA, b, c.getBaseBlock(), 1, 1, 1);
	}
	public void ffb(String ns, FluidEntry<? extends ForgeFlowingFluid.Flowing> a, FluidEntry<? extends ForgeFlowingFluid.Flowing> b, NonNullSupplier<Block> c, int ai, int bi, int ri) {
		var suffix = StringUtils.replace(ForgeRegistries.BLOCKS.getKey(c.get()).getPath(), "/", "_");
		create(ns + "_" + suffix, x -> x
			.require(FluidIngredient.fromFluid(a.get(), ai))
			.require(FluidIngredient.fromFluid(b.get(), bi))
			.output(1f, c.get(), ri));
	}

	public CobblegenRecipeGen(DataGenerator gen) {
		super(gen); setupRecipes();
	}

	@Override
	public String getName() {
		return "Creatania's cobblegen recipes";
	}
	@Override
	protected IRecipeTypeInfo getRecipeType() {
		return CobblegenRecipe.TYPE.asRecipeTypeInfo();
	}
}
