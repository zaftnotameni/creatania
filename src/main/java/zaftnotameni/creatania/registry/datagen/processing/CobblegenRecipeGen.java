package zaftnotameni.creatania.registry.datagen.processing;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.tterrag.registrate.util.entry.FluidEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import java.util.HashMap;
import java.util.function.UnaryOperator;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.apache.commons.lang3.StringUtils;
import zaftnotameni.creatania.recipes.cobblegen.CobblegenRecipe;

import static com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes.*;
import static zaftnotameni.creatania.registry.Fluids.*;

public class CobblegenRecipeGen extends ForgeCreateProcessingRecipeProvider {
  public static HashMap<String, JsonObject> ALL = new HashMap<>();

	public void setupRecipes() {
		mffb(MOLTEN_COPPER, GRANITE);
		mffb(MOLTEN_ZINC, DIORITE);
		mffb(MOLTEN_ANDESITE, ANDESITE);
		mffb(MOLTEN_BRASS, LIMESTONE);
		mffb(MOLTEN_IRON, SCORCHIA);
		mffb(MOLTEN_ELEMENTIUM, SCORIA);
		mffb(MOLTEN_GAIA, CALCITE);
		mffb(MOLTEN_MANASTEEL, DRIPSTONE);
		mffb(MOLTEN_GOLD, DEEPSLATE);
		mffb(MOLTEN_TERRASTEEL, TUFF);

		mfsb(MOLTEN_COPPER, VERIDIUM);
		mfsb(MOLTEN_ZINC, ASURINE);
		mfsb(MOLTEN_ANDESITE, NonNullSupplier.of(() -> Blocks.BLACKSTONE));
		mfsb(MOLTEN_BRASS, NonNullSupplier.of(() -> Blocks.BASALT));
		mfsb(MOLTEN_IRON, CRIMSITE);
		mfsb(MOLTEN_ELEMENTIUM, NonNullSupplier.of(() -> Blocks.WARPED_NYLIUM));
		mfsb(MOLTEN_GAIA, NonNullSupplier.of(() -> Blocks.ANCIENT_DEBRIS));
		mfsb(MOLTEN_MANASTEEL, NonNullSupplier.of(() -> Blocks.NETHERRACK));
		mfsb(MOLTEN_GOLD, OCHRUM);
		mfsb(MOLTEN_TERRASTEEL, NonNullSupplier.of(() -> Blocks.CRIMSON_NYLIUM));
	}

	public void mfsb(FluidEntry<ForgeFlowingFluid.Flowing> b, AllPaletteStoneTypes c) {
		ffb("flowing_source_none", PURE_MANA, b, c.getBaseBlock(), 1, 1000, 1);
	}
	public void mfsb(FluidEntry<ForgeFlowingFluid.Flowing> b, NonNullSupplier<Block> c) {
		ffb("flowing_source_none", PURE_MANA, b, c, 1, 1000, 1);
	}
	public void mffb(FluidEntry<ForgeFlowingFluid.Flowing> b, AllPaletteStoneTypes c) {
		ffb("flowing_flowing_none", PURE_MANA, b, c.getBaseBlock(), 1, 1, 1);
	}
	public void ffb(String ns, FluidEntry<ForgeFlowingFluid.Flowing> a, FluidEntry<ForgeFlowingFluid.Flowing> b, NonNullSupplier<Block> c, int ai, int bi, int ri) {
		var suffix = StringUtils.replace(c.get().getRegistryName().getPath(), "/", "_");
		create(ns + "_" + suffix, x -> x
			.require(FluidIngredient.fromFluid(a.get(), ai))
			.require(FluidIngredient.fromFluid(b.get(), bi))
			.output(1f, c.get(), ri));
	}

  @Override <T extends ProcessingRecipe<?>> GeneratedRecipe create(String name, UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
    var r = super.create(name, transform);
    var j = JsonParser
      .parseString("{}").getAsJsonObject();
    r.register(f -> f.serializeRecipeData(j));
    ALL.putIfAbsent(name, j);
    return r;
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