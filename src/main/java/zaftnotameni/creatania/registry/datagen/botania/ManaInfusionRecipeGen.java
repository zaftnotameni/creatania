package zaftnotameni.creatania.registry.datagen.botania;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import zaftnotameni.creatania.registry.Blocks;

import java.io.IOException;

import static zaftnotameni.creatania.mana.ManaPoolValues.*;
public class ManaInfusionRecipeGen extends BotaniaBaseRecipeGen implements DataProvider {
  public ManaInfusionRecipeGen(DataGenerator gen) { super(gen, "botania:mana_infusion", "output", "item"); }
  @Override
  public void run(HashCache pCache) throws IOException {
    start()
      .inputItem(Ingredient.of(AllItems.ROSE_QUARTZ.get()))
      .mana(hundreth())
      .itemResult(new ItemStack(AllItems.POLISHED_ROSE_QUARTZ.get(), 1))
      .build()
      .saveAs(AllItems.POLISHED_ROSE_QUARTZ.getId(), pCache);

    start()
      .inputItem(Ingredient.of(AllBlocks.ANDESITE_CASING.get()))
      .mana(third())
      .itemResult(new ItemStack(Blocks.MANA_CASING.get(), 1))
      .build()
      .saveAs(Blocks.MANA_CASING.getId(), pCache);

    start()
      .inputItem(Ingredient.of(AllBlocks.ANALOG_LEVER.get()))
      .mana(sixteenth())
      .itemResult(new ItemStack(zaftnotameni.creatania.registry.Blocks.XOR_LEVER.get(), 1))
      .build()
      .saveAs(zaftnotameni.creatania.registry.Blocks.XOR_LEVER.getId(), pCache);

    start()
      .inputItem(Ingredient.of(Items.SLIME_BALL))
      .mana(full())
      .itemResult(new ItemStack(zaftnotameni.creatania.registry.Items.MANA_GEL.get(), 1))
      .catalystBlockName("botania:gaia_pylon")
      .build()
      .saveAs(zaftnotameni.creatania.registry.Items.MANA_GEL.getId(), pCache);
  }
  @Override
  public String getName() {
    return "Creatania Mana Infusion Recipes";
  }
}
