//package zaftnotameni.creatania.registry.datagen.processing;
//
//import com.simibubi.create.AllRecipeTypes;
//import net.minecraft.data.DataGenerator;
//import net.minecraft.world.item.Items;
//import org.apache.commons.lang3.StringUtils;
//import zaftnotameni.creatania.registry.Fluids;
//import zaftnotameni.creatania.util.NamedItems;
//
//import static zaftnotameni.creatania.util.NamedItems.itemLike;
//
//public class FillingRecipeGen extends ForgeCreateProcessingRecipeProvider {
//	public void setupRecipes() {
//
//		create("terra_pylon_from_mana_pylon", b -> b.require(Fluids.MOLTEN_TERRASTEEL.get(), 500)
//			.require(itemLike("botania", "mana_pylon"))
//			.output(itemLike("botania", "natura_pylon")));
//
//		create("ender_pearl_from_manasteel", b -> b.require(Fluids.MOLTEN_MANASTEEL.get(), 125)
//			.require(Items.SLIME_BALL)
//			.output(Items.ENDER_PEARL));
//
//		create("chorus_from_elementium", b -> b.require(Fluids.MOLTEN_ELEMENTIUM.get(), 125)
//			.require(Items.SLIME_BALL)
//			.output(Items.CHORUS_FRUIT));
//
//		for (var tall : NamedItems.BOTANIA_TALL_FLOWERS) {
//			var namespace = tall.split(":")[0];
//			var tallPath = tall.split(":")[1];
//			var mysticalPath = StringUtils.replace(tallPath, "double", "mystical");
//			var petalPath = StringUtils.replace(tallPath, "double_flower", "petal");
//
//			create(mysticalPath + "_from_" + petalPath, b -> b.require(Fluids.PURE_MANA.get(), 250)
//				.require(itemLike(namespace, petalPath))
//				.output(itemLike(namespace, mysticalPath)));
//			create(tallPath + "_from_" + mysticalPath, b -> b.require(net.minecraft.world.level.material.Fluids.WATER, 250)
//				.require(itemLike(namespace, mysticalPath))
//				.output(itemLike(namespace, tallPath)));
//			create(tallPath + "_from_" + petalPath, b -> b.require(Fluids.REAL_MANA.get(), 1000)
//				.require(itemLike(namespace, petalPath))
//				.output(itemLike(namespace, tallPath)));
//		}
//	}
//	public FillingRecipeGen(DataGenerator p_i48262_1_) {
//		super(p_i48262_1_);
//		setupRecipes();
//	}
//
//	@Override
//	protected AllRecipeTypes getRecipeType() {
//		return AllRecipeTypes.FILLING;
//	}
//
//
//}
