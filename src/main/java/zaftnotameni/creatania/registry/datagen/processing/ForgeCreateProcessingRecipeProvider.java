package zaftnotameni.creatania.registry.datagen.processing;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import com.simibubi.create.foundation.utility.RegisteredObjects;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fluids.FluidAttributes;
import org.jetbrains.annotations.NotNull;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.registry.Index;

public abstract class ForgeCreateProcessingRecipeProvider extends CreateRecipeProvider {

	protected static final List<ForgeCreateProcessingRecipeProvider> GENERATORS = new ArrayList<>();
	protected static final int BUCKET = FluidAttributes.BUCKET_VOLUME;
	protected static final int BOTTLE = 250;

	public static void registerAll(DataGenerator gen) {
		GENERATORS.add(new CrushingRecipeGen(gen));
		GENERATORS.add(new MillingRecipeGen(gen));
		GENERATORS.add(new CuttingRecipeGen(gen));
		GENERATORS.add(new WashingRecipeGen(gen));
		GENERATORS.add(new CobblegenRecipeGen(gen));
		GENERATORS.add(new DeployingRecipeGen(gen));
		GENERATORS.add(new MixingRecipeGen(gen));
		GENERATORS.add(new CompactingRecipeGen(gen));
		GENERATORS.add(new PressingRecipeGen(gen));
		GENERATORS.add(new FillingRecipeGen(gen));
		// GENERATORS.add(new EmptyingRecipeGen(gen));
		GENERATORS.add(new HauntingRecipeGen(gen));
		// GENERATORS.add(new ItemApplicationRecipeGen(gen));

		gen.addProvider(new DataProvider() {

			@Override
			public @NotNull String getName() {
				return "Creatania's Processing Recipes";
			}

			@Override
			public void run(@NotNull HashCache dc) throws IOException {
				GENERATORS.forEach(g -> {
					try {
						g.run(dc);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
		});
	}

	public ForgeCreateProcessingRecipeProvider(DataGenerator generator) {
		super(generator);
	}

	/**
	 * Create a processing recipe with a single itemstack ingredient, using its id
	 * as the name of the recipe
	 */
	public <T extends ProcessingRecipe<?>> GeneratedRecipe create(String namespace,
		Supplier<ItemLike> singleIngredient, UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
		ProcessingRecipeSerializer<T> serializer = getSerializer();
		GeneratedRecipe generatedRecipe = c -> {
			ItemLike iItemProvider = singleIngredient.get();
			transform
				.apply(new ProcessingRecipeBuilder<>(serializer.getFactory(),
					new ResourceLocation(namespace, RegisteredObjects.getKeyOrThrow(iItemProvider.asItem())
						.getPath())).withItemIngredients(Ingredient.of(iItemProvider)))
				.build(c);
		};
		all.add(generatedRecipe);
		return generatedRecipe;
	}

	/**
	 * Create a processing recipe with a single itemstack ingredient, using its id
	 * as the name of the recipe
	 */
	<T extends ProcessingRecipe<?>> GeneratedRecipe create(Supplier<ItemLike> singleIngredient,
		UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
		return create(Constants.MODID, singleIngredient, transform);
	}

	protected <T extends ProcessingRecipe<?>> GeneratedRecipe createWithDeferredId(Supplier<ResourceLocation> name,
		UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
		ProcessingRecipeSerializer<T> serializer = getSerializer();
		GeneratedRecipe generatedRecipe =
			c -> transform.apply(new ProcessingRecipeBuilder<>(serializer.getFactory(), name.get()))
				.build(c);
		all.add(generatedRecipe);
		return generatedRecipe;
	}

	/**
	 * Create a new processing recipe, with recipe definitions provided by the
	 * function
	 */
	protected <T extends ProcessingRecipe<?>> GeneratedRecipe create(ResourceLocation name,
		UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
		return createWithDeferredId(() -> name, transform);
	}

	/**
	 * Create a new processing recipe, with recipe definitions provided by the
	 * function
	 */
	<T extends ProcessingRecipe<?>> GeneratedRecipe create(String name,
		UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
		return create(Index.resource(name), transform);
	}

	protected abstract IRecipeTypeInfo getRecipeType();

	protected <T extends ProcessingRecipe<?>> ProcessingRecipeSerializer<T> getSerializer() {
		return getRecipeType().getSerializer();
	}

	protected Supplier<ResourceLocation> idWithSuffix(Supplier<ItemLike> item, String suffix) {
		return () -> {
			ResourceLocation registryName = RegisteredObjects.getKeyOrThrow(item.get()
				.asItem());
			return Index.resource(registryName.getPath() + suffix);
		};
	}

	@Override
	public @NotNull String getName() {
		return "Creatania's Processing Recipes: " + getRecipeType().getId()
			.getPath();
	}

}