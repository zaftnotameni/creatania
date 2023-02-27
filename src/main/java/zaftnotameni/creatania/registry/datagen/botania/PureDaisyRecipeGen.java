package zaftnotameni.creatania.registry.datagen.botania;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import zaftnotameni.creatania.registry.CreataniaBlocks;
import zaftnotameni.creatania.registry.CreataniaIndex;

import java.io.IOException;

import static zaftnotameni.creatania.registry.CreataniaFluids.ALL;
public class PureDaisyRecipeGen extends BotaniaBaseRecipeGen implements DataProvider {
  public PureDaisyRecipeGen(DataGenerator gen) {
    super(gen, "botania:pure_daisy", "output", "name");
  }
  @Override
  public void run(CachedOutput pCache) throws IOException {
    start()
      .inputTypeBlock(ForgeRegistries.FLUIDS.getKey(ALL.CORRUPT_MANA.get().getSource()).toString())
      .time(1 * 10)
      .fluidResult(new FluidStack(ALL.PURE_MANA.get().getSource(), 1))
      .build()
      .saveAs(CreataniaIndex.resource("purify_liquid_corrupt_mana"), pCache);

    start()
      .inputTypeBlock(ForgeRegistries.BLOCKS.getKey(CreataniaBlocks.CORRUPT_MANA_BLOCK.get()).toString())
      .time(1 * 10)
      .itemResult(new ItemStack(CreataniaBlocks.PURE_MANA_BLOCK.get(), 1))
      .build()
      .saveAs(CreataniaIndex.resource("purify_solid_corrupt_mana"), pCache);

    start()
      .inputTypeBlock(ForgeRegistries.FLUIDS.getKey(ALL.PURE_MANA.get().getSource()).toString())
      .time(20 * 10)
      .fluidResult(new FluidStack(ALL.CORRUPT_MANA.get().getSource(), 1))
      .successFunction("botania:ender_air_release")
      .build()
      .saveAs(CreataniaIndex.resource("corrupt_liquid_pure_mana"), pCache);

    start()
      .inputTypeBlock(ForgeRegistries.BLOCKS.getKey(CreataniaBlocks.PURE_MANA_BLOCK.get()).toString())
      .time(20 * 10)
      .itemResult(new ItemStack(CreataniaBlocks.CORRUPT_MANA_BLOCK.get(), 1))
      .successFunction("botania:ender_air_release")
      .build()
      .saveAs(CreataniaIndex.resource("corrupt_solid_pure_mana"), pCache);

    start()
      .inputTypeBlock(ForgeRegistries.FLUIDS.getKey(ALL.MOLTEN_ANDESITE.get().getSource()).toString())
      .time(20 * 1200)
      .fluidResult(new FluidStack(ALL.MOLTEN_MANASTEEL.get().getSource(), 1))
      .build()
      .saveAs(CreataniaIndex.resource("andesite_to_manasteel"), pCache);

    start()
      .inputTypeBlock(ForgeRegistries.FLUIDS.getKey(ALL.MOLTEN_ZINC.get().getSource()).toString())
      .time(20 * 2400)
      .fluidResult(new FluidStack(ALL.MOLTEN_TERRASTEEL.get().getSource(), 1))
      .build()
      .saveAs(CreataniaIndex.resource("zinc_to_terrasteel"), pCache);

    start()
      .inputTypeBlock(ForgeRegistries.FLUIDS.getKey(ALL.MOLTEN_BRASS.get().getSource()).toString())
      .time(20 * 3600)
      .fluidResult(new FluidStack(ALL.MOLTEN_ELEMENTIUM.get().getSource(), 1))
      .build()
      .saveAs(CreataniaIndex.resource("brass_to_elementium"), pCache);
  }
  @Override
  public String getName() {
    return "Creatania Mana Infusion Recipes";
  }
}
