package zaftnotameni.creatania.registry.datagen.botania;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import zaftnotameni.creatania.registry.Blocks;
import zaftnotameni.creatania.registry.Index;

import java.io.IOException;

import static zaftnotameni.creatania.util.NamedItems.*;
public class ElvenTradeRecipeGen extends BotaniaBaseRecipeGen implements DataProvider {
  public ElvenTradeRecipeGen(DataGenerator gen) {
    super(gen, "botania:elven_trade");
  }
  @Override
  public void run(HashCache pCache) throws IOException {
    start()
      .ingredient(Ingredient.of(AllBlocks.ANDESITE_CASING.get()))
      .ingredientOutput(Ingredient.of(Blocks.MANA_CASING.get()))
      .build()
      .saveAs(Blocks.MANA_CASING.getId(), pCache);

    start()
      .ingredient(Ingredient.of(Blocks.MANA_CASING.get()))
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
      .ingredientOutput(Ingredient.of(Blocks.XOR_LEVER.get()))
      .build()
      .saveAs(Blocks.XOR_LEVER.getId(), pCache);

    start()
      .ingredient(Ingredient.of(Blocks.MANASTEEL_MANADUCT_BLOCK.get()))
      .ingredientOutput(Ingredient.of(Blocks.TERRASTEEL_MANADUCT_BLOCK.get()))
      .build()
      .saveAs(Blocks.TERRASTEEL_MANADUCT_BLOCK.getId(), pCache);

    start()
      .ingredient(Ingredient.of(Items.SLIME_BALL))
      .ingredientOutput(Ingredient.of(zaftnotameni.creatania.registry.Items.MANA_GEL.get()))
      .build()
      .saveAs(Index.resource("managel_via_elves_from_slime"), pCache);

    start()
      .ingredient(Ingredient.of(Items.MAGMA_CREAM))
      .ingredientOutput(Ingredient.of(zaftnotameni.creatania.registry.Items.MANA_GEL.get()))
      .build()
      .saveAs(Index.resource("managel_via_elves_from_magmacream"), pCache);

    start()
      .ingredient(Ingredient.of(AllItems.ANDESITE_ALLOY.get()))
      .ingredientOutput(manasteelingot())
      .build()
      .saveAs(Index.resource("manasteel_via_andesite_alloy"), pCache);

    start()
      .ingredient(Ingredient.of(AllItems.ZINC_INGOT.get()))
      .ingredientOutput(terrasteelingot())
      .build()
      .saveAs(Index.resource("terrasteel_via_zinc"), pCache);

    start()
      .ingredient(Ingredient.of(AllItems.BRASS_INGOT.get()))
      .ingredientOutput(elementiumingot())
      .build()
      .saveAs(Index.resource("elementium_via_brass"), pCache);

  }
  @Override
  public String getName() {
    return "Creatania Elven Trade Recipes";
  }
}
