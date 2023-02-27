//package zaftnotameni.creatania.registry.datagen.botania;
//import net.minecraft.data.CachedOutput;
//import net.minecraft.data.DataGenerator;
//import net.minecraft.data.DataProvider;
//import net.minecraft.world.item.ItemStack;
//import net.minecraftforge.fluids.FluidStack;
//import net.minecraftforge.registries.ForgeRegistries;
//import zaftnotameni.creatania.registry.Blocks;
//import zaftnotameni.creatania.registry.Fluids;
//import zaftnotameni.creatania.registry.Index;
//
//import java.io.IOException;
//public class PureDaisyRecipeGen extends BotaniaBaseRecipeGen implements DataProvider {
//  public PureDaisyRecipeGen(DataGenerator gen) {
//    super(gen, "botania:pure_daisy", "output", "name");
//  }
//  @Override
//  public void run(CachedOutput pCache) throws IOException {
//    start()
//      .inputTypeBlock(ForgeRegistries.FLUIDS.getKey(Fluids.CORRUPT_MANA.get().getSource()).toString())
//      .time(1 * 10)
//      .fluidResult(new FluidStack(Fluids.PURE_MANA.get().getSource(), 1))
//      .build()
//      .saveAs(Index.resource("purify_liquid_corrupt_mana"), pCache);
//
//    start()
//      .inputTypeBlock(ForgeRegistries.BLOCKS.getKey(Blocks.CORRUPT_MANA_BLOCK.get()).toString())
//      .time(1 * 10)
//      .itemResult(new ItemStack(Blocks.PURE_MANA_BLOCK.get(), 1))
//      .build()
//      .saveAs(Index.resource("purify_solid_corrupt_mana"), pCache);
//
//    start()
//      .inputTypeBlock(ForgeRegistries.FLUIDS.getKey(Fluids.PURE_MANA.get().getSource()).toString())
//      .time(20 * 10)
//      .fluidResult(new FluidStack(Fluids.CORRUPT_MANA.get().getSource(), 1))
//      .successFunction("botania:ender_air_release")
//      .build()
//      .saveAs(Index.resource("corrupt_liquid_pure_mana"), pCache);
//
//    start()
//      .inputTypeBlock(ForgeRegistries.BLOCKS.getKey(Blocks.PURE_MANA_BLOCK.get()).toString())
//      .time(20 * 10)
//      .itemResult(new ItemStack(Blocks.CORRUPT_MANA_BLOCK.get(), 1))
//      .successFunction("botania:ender_air_release")
//      .build()
//      .saveAs(Index.resource("corrupt_solid_pure_mana"), pCache);
//
//    start()
//      .inputTypeBlock(ForgeRegistries.FLUIDS.getKey(Fluids.MOLTEN_ANDESITE.get().getSource()).toString())
//      .time(20 * 1200)
//      .fluidResult(new FluidStack(Fluids.MOLTEN_MANASTEEL.get().getSource(), 1))
//      .build()
//      .saveAs(Index.resource("andesite_to_manasteel"), pCache);
//
//    start()
//      .inputTypeBlock(ForgeRegistries.FLUIDS.getKey(Fluids.MOLTEN_ZINC.get().getSource()).toString())
//      .time(20 * 2400)
//      .fluidResult(new FluidStack(Fluids.MOLTEN_TERRASTEEL.get().getSource(), 1))
//      .build()
//      .saveAs(Index.resource("zinc_to_terrasteel"), pCache);
//
//    start()
//      .inputTypeBlock(ForgeRegistries.FLUIDS.getKey(Fluids.MOLTEN_BRASS.get().getSource()).toString())
//      .time(20 * 3600)
//      .fluidResult(new FluidStack(Fluids.MOLTEN_ELEMENTIUM.get().getSource(), 1))
//      .build()
//      .saveAs(Index.resource("brass_to_elementium"), pCache);
//  }
//  @Override
//  public String getName() {
//    return "Creatania Mana Infusion Recipes";
//  }
//}
