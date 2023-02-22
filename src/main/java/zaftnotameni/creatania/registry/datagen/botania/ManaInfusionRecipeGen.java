package zaftnotameni.creatania.registry.datagen.botania;
import com.simibubi.create.AllBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import zaftnotameni.creatania.registry.Blocks;

import java.io.IOException;
public class ManaInfusionRecipeGen extends BotaniaBaseRecipeGen implements DataProvider {
  public ManaInfusionRecipeGen(DataGenerator gen) { super(gen, "botania:mana_infusion", "output", "item"); }
  @Override
  public void run(HashCache pCache) throws IOException {
    start()
      .inputItem(Ingredient.of(AllBlocks.ANDESITE_CASING.get()))
      .mana(5000)
      .itemResult(new ItemStack(Blocks.MANA_CASING.get(), 1))
      .build()
      .saveAs(Blocks.MANA_CASING.getId(), pCache);

    start()
      .inputItem(Ingredient.of(Items.SLIME_BALL))
      .mana(5000)
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
