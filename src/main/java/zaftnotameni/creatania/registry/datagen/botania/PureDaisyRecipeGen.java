package zaftnotameni.creatania.registry.datagen.botania;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import zaftnotameni.creatania.registry.Blocks;
import zaftnotameni.creatania.registry.Fluids;
import zaftnotameni.creatania.registry.Index;

import java.io.IOException;
public class PureDaisyRecipeGen extends BotaniaBaseRecipeGen implements DataProvider {
  public PureDaisyRecipeGen(DataGenerator gen) {
    super(gen, "botania:pure_daisy", "output", "name");
  }
  @Override
  public void run(HashCache pCache) throws IOException {
    start()
      .inputTypeBlock(Fluids.CORRUPT_MANA.get().getSource().getRegistryName().toString())
      .time(1 * 10)
      .fluidResult(new FluidStack(Fluids.PURE_MANA.get().getSource(), 1))
      .build()
      .saveAs(Index.resource("purify_liquid_corrupt_mana"), pCache);

    start()
      .inputTypeBlock(Blocks.CORRUPT_MANA_BLOCK.get().getRegistryName().toString())
      .time(1 * 10)
      .itemResult(new ItemStack(Blocks.PURE_MANA_BLOCK.get(), 1))
      .build()
      .saveAs(Index.resource("purify_solid_corrupt_mana"), pCache);

    start()
      .inputTypeBlock(Fluids.PURE_MANA.get().getSource().getRegistryName().toString())
      .time(20 * 10)
      .fluidResult(new FluidStack(Fluids.CORRUPT_MANA.get().getSource(), 1))
      .successFunction("botania:ender_air_release")
      .build()
      .saveAs(Index.resource("corrupt_liquid_pure_mana"), pCache);

    start()
      .inputTypeBlock(Blocks.PURE_MANA_BLOCK.get().getRegistryName().toString())
      .time(20 * 10)
      .itemResult(new ItemStack(Blocks.CORRUPT_MANA_BLOCK.get(), 1))
      .successFunction("botania:ender_air_release")
      .build()
      .saveAs(Index.resource("corrupt_solid_pure_mana"), pCache);
  }
  @Override
  public String getName() {
    return "Creatania Mana Infusion Recipes";
  }
}
