package zaftnotameni.creatania.registry.datagen.botania;
import com.simibubi.create.AllBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import zaftnotameni.creatania.registry.Blocks;

import java.io.IOException;

import static zaftnotameni.creatania.util.NamedItems.*;
public class TerraPlateRecipeGen extends BotaniaBaseRecipeGen implements DataProvider {
  public TerraPlateRecipeGen(DataGenerator gen) {
    super(gen, "botania:terra_plate");
  }
  @Override
  public void run(HashCache pCache) throws IOException {
    start()
      .ingredient(Ingredient.of(Blocks.PURE_MANA_BLOCK.get().asItem()))
      .mana(99999)
      .itemResult(new ItemStack(Blocks.REAL_MANA_BLOCK.get().asItem(), 1))
      .build()
      .saveAs(Blocks.REAL_MANA_BLOCK.getId(), pCache);

    start()
      .ingredient(Ingredient.of(Blocks.MANA_CASING.get().asItem()))
      .ingredient(Ingredient.of(AllBlocks.COGWHEEL.get().asItem()))
      .ingredient(Ingredient.of(AllBlocks.SHAFT.get().asItem()))
      .ingredient(Ingredient.of(AllBlocks.COGWHEEL.get().asItem()))
      .ingredient(Ingredient.of(AllBlocks.SHAFT.get().asItem()))
      .ingredient(Ingredient.of(AllBlocks.COGWHEEL.get().asItem()))
      .ingredient(Ingredient.of(AllBlocks.SHAFT.get().asItem()))
      .mana(10000)
      .itemResult(new ItemStack(Blocks.OMNIBOX.get().asItem(), 1))
      .build()
      .saveAs(Blocks.OMNIBOX.getId(), pCache);

    start()
      .ingredient(manasteelingot())
      .ingredient(Ingredient.of(Blocks.MANA_CASING.get().asItem()))
      .ingredient(Ingredient.of(AllBlocks.SMART_FLUID_PIPE.get().asItem()))
      .mana(11111)
      .itemResult(new ItemStack(Blocks.MANASTEEL_MANADUCT_BLOCK.get().asItem(), 1))
      .build()
      .saveAs(Blocks.MANASTEEL_MANADUCT_BLOCK.getId(), pCache);

    start()
      .ingredient(terrasteelingot())
      .ingredient(Ingredient.of(Blocks.MANA_CASING.get().asItem()))
      .ingredient(Ingredient.of(AllBlocks.SMART_FLUID_PIPE.get().asItem()))
      .mana(33333)
      .itemResult(new ItemStack(Blocks.TERRASTEEL_MANADUCT_BLOCK.get().asItem(), 1))
      .build()
      .saveAs(Blocks.TERRASTEEL_MANADUCT_BLOCK.getId(), "_base", pCache);

    start()
      .ingredient(elementiumingot())
      .ingredient(Ingredient.of(Blocks.MANA_CASING.get().asItem()))
      .ingredient(Ingredient.of(AllBlocks.SMART_FLUID_PIPE.get().asItem()))
      .mana(66666)
      .itemResult(new ItemStack(Blocks.ELEMENTIUM_MANADUCT_BLOCK.get().asItem(), 1))
      .build()
      .saveAs(Blocks.ELEMENTIUM_MANADUCT_BLOCK.getId(), pCache);

    start()
      .ingredient(gaiaingot())
      .ingredient(Ingredient.of(Blocks.MANA_CASING.get().asItem()))
      .ingredient(Ingredient.of(AllBlocks.SMART_FLUID_PIPE.get().asItem()))
      .mana(99999)
      .itemResult(new ItemStack(Blocks.GAIA_MANADUCT_BLOCK.get().asItem(), 1))
      .build()
      .saveAs(Blocks.GAIA_MANADUCT_BLOCK.getId(), pCache);





//    {
//      "type": "botania:terra_plate",
//      "ingredients": [{
//      "item": "create:smart_fluid_pipe"
//    }, {
//      "item": "botania:terrasteel_ingot"
//    }, {
//      "item": "botania:livingrock"
//    }],
//      "mana": 33333,
//      "result": {
//      "item": "creatania:terrasteel_manaduct_block"
//    }
//    }
  }
  @Override
  public String getName() {
    return "Creatania Terra Plate Recipes";
  }
}
