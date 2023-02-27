package zaftnotameni.creatania.registry.datagen.botania;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import zaftnotameni.creatania.registry.CreataniaBlocks;
import zaftnotameni.creatania.registry.CreataniaIndex;

import java.io.IOException;

import static zaftnotameni.creatania.registry.CreataniaFluids.ALL;
import static zaftnotameni.creatania.util.NamedItems.*;
public class ElvenTradeRecipeGen extends BotaniaBaseRecipeGen implements DataProvider {
  public ElvenTradeRecipeGen(DataGenerator gen) {
    super(gen, "botania:elven_trade");
  }
  @Override
  public void run(CachedOutput pCache) throws IOException {
    start()
      .ingredient(Ingredient.of(Items.REDSTONE))
      .ingredient(Ingredient.of(Items.QUARTZ))
      .ingredientOutput(Ingredient.of(AllItems.ROSE_QUARTZ.get()))
      .build()
      .saveAs(AllItems.ROSE_QUARTZ.getId(), pCache);

    start()
      .ingredient(Ingredient.of(AllBlocks.ANDESITE_CASING.get()))
      .ingredientOutput(Ingredient.of(CreataniaBlocks.MANA_CASING.get()))
      .build()
      .saveAs(CreataniaBlocks.MANA_CASING.getId(), pCache);

    start()
      .ingredient(Ingredient.of(CreataniaBlocks.MANA_CASING.get()))
      .ingredientOutput(Ingredient.of(AllBlocks.COPPER_CASING.get()))
      .build()
      .saveAs(AllBlocks.COPPER_CASING.getId(), pCache);

    start()
      .ingredient(Ingredient.of(AllBlocks.COPPER_CASING.get()))
      .ingredientOutput(Ingredient.of(AllBlocks.BRASS_CASING.get()))
      .build()
      .saveAs(AllBlocks.BRASS_CASING.getId(), pCache);

    start()
      .ingredient(Ingredient.of(AllBlocks.BRASS_CASING.get()))
      .ingredientOutput(Ingredient.of(AllBlocks.RAILWAY_CASING.get()))
      .build()
      .saveAs(AllBlocks.RAILWAY_CASING.getId(), pCache);

    start()
      .ingredient(Ingredient.of(AllBlocks.RAILWAY_CASING.get()))
      .ingredientOutput(Ingredient.of(AllBlocks.ANDESITE_CASING.get()))
      .build()
      .saveAs(AllBlocks.ANDESITE_CASING.getId(), pCache);

    start()
      .ingredient(Ingredient.of(AllBlocks.ANALOG_LEVER.get()))
      .ingredientOutput(Ingredient.of(CreataniaBlocks.XOR_LEVER.get()))
      .build()
      .saveAs(CreataniaBlocks.XOR_LEVER.getId(), pCache);

    start()
      .ingredient(Ingredient.of(CreataniaBlocks.MANASTEEL_MANADUCT_BLOCK.get()))
      .ingredientOutput(Ingredient.of(CreataniaBlocks.TERRASTEEL_MANADUCT_BLOCK.get()))
      .build()
      .saveAs(CreataniaBlocks.TERRASTEEL_MANADUCT_BLOCK.getId(), pCache);

    start()
      .ingredient(Ingredient.of(Items.SLIME_BALL))
      .ingredientOutput(Ingredient.of(zaftnotameni.creatania.registry.CreataniaItems.MANA_GEL.get()))
      .build()
      .saveAs(CreataniaIndex.resource("managel_via_elves_from_slime"), pCache);

    start()
      .ingredient(Ingredient.of(Items.MAGMA_CREAM))
      .ingredientOutput(Ingredient.of(zaftnotameni.creatania.registry.CreataniaItems.MANA_GEL.get()))
      .build()
      .saveAs(CreataniaIndex.resource("managel_via_elves_from_magmacream"), pCache);

    start()
      .ingredient(Ingredient.of(AllItems.ANDESITE_ALLOY.get()))
      .ingredientOutput(manasteelingot())
      .build()
      .saveAs(CreataniaIndex.resource("manasteel_via_andesite_alloy"), pCache);

    start()
      .ingredient(Ingredient.of(AllItems.ZINC_INGOT.get()))
      .ingredientOutput(terrasteelingot())
      .build()
      .saveAs(CreataniaIndex.resource("terrasteel_via_zinc"), pCache);

    start()
      .ingredient(Ingredient.of(AllItems.BRASS_INGOT.get()))
      .ingredientOutput(elementiumingot())
      .build()
      .saveAs(CreataniaIndex.resource("elementium_via_brass"), pCache);

    start()
      .ingredient(Ingredient.of(Items.BUCKET.asItem()))
      .ingredientOutput(Ingredient.of(ALL.CORRUPT_MANA.get().getBucket().asItem()))
      .build()
      .saveAs(CreataniaIndex.resource("corrupt_mana_from_nothing"), pCache);

    start()
      .ingredient(Ingredient.of(ALL.CORRUPT_MANA.get().getBucket().asItem()))
      .ingredientOutput(Ingredient.of(Items.LAVA_BUCKET.asItem()))
      .build()
      .saveAs(CreataniaIndex.resource("lava_via_corrupt_mana"), pCache);

    start()
      .ingredient(Ingredient.of(ALL.PURE_MANA.get().getBucket().asItem()))
      .ingredientOutput(Ingredient.of(ALL.REAL_MANA.get().getBucket().asItem()))
      .build()
      .saveAs(CreataniaIndex.resource("real_mana_via_pure_mana"), pCache);

  }
  @Override
  public String getName() {
    return "Creatania Elven Trade Recipes";
  }
}
