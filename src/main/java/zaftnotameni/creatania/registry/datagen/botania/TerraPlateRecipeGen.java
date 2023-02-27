package zaftnotameni.creatania.registry.datagen.botania;
import com.simibubi.create.AllBlocks;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import zaftnotameni.creatania.registry.CreataniaBlocks;

import java.io.IOException;

import static zaftnotameni.creatania.util.NamedItems.*;
public class TerraPlateRecipeGen extends BotaniaBaseRecipeGen implements DataProvider {
  public TerraPlateRecipeGen(DataGenerator gen) {
    super(gen, "botania:terra_plate");
  }
  @Override
  public void run(CachedOutput pCache) throws IOException {
    start()
      .ingredient(Ingredient.of(CreataniaBlocks.PURE_MANA_BLOCK.get().asItem()))
      .mana(99999)
      .itemResult(new ItemStack(CreataniaBlocks.REAL_MANA_BLOCK.get().asItem(), 1))
      .build()
      .saveAs(CreataniaBlocks.REAL_MANA_BLOCK.getId(), pCache);

    start()
      .ingredient(Ingredient.of(CreataniaBlocks.MANA_CASING.get().asItem()))
      .ingredient(Ingredient.of(AllBlocks.COGWHEEL.get().asItem()))
      .ingredient(Ingredient.of(AllBlocks.SHAFT.get().asItem()))
      .ingredient(Ingredient.of(AllBlocks.COGWHEEL.get().asItem()))
      .ingredient(Ingredient.of(AllBlocks.SHAFT.get().asItem()))
      .ingredient(Ingredient.of(AllBlocks.COGWHEEL.get().asItem()))
      .ingredient(Ingredient.of(AllBlocks.SHAFT.get().asItem()))
      .mana(10000)
      .itemResult(new ItemStack(CreataniaBlocks.OMNIBOX.get().asItem(), 1))
      .build()
      .saveAs(CreataniaBlocks.OMNIBOX.getId(), pCache);

    start()
      .ingredient(manasteelingot())
      .ingredient(Ingredient.of(CreataniaBlocks.MANA_CASING.get().asItem()))
      .ingredient(Ingredient.of(AllBlocks.SMART_FLUID_PIPE.get().asItem()))
      .mana(11111)
      .itemResult(new ItemStack(CreataniaBlocks.MANASTEEL_MANADUCT_BLOCK.get().asItem(), 1))
      .build()
      .saveAs(CreataniaBlocks.MANASTEEL_MANADUCT_BLOCK.getId(), pCache);

    start()
      .ingredient(terrasteelingot())
      .ingredient(Ingredient.of(CreataniaBlocks.MANA_CASING.get().asItem()))
      .ingredient(Ingredient.of(AllBlocks.SMART_FLUID_PIPE.get().asItem()))
      .mana(33333)
      .itemResult(new ItemStack(CreataniaBlocks.TERRASTEEL_MANADUCT_BLOCK.get().asItem(), 1))
      .build()
      .saveAs(CreataniaBlocks.TERRASTEEL_MANADUCT_BLOCK.getId(), "_base", pCache);

    start()
      .ingredient(elementiumingot())
      .ingredient(Ingredient.of(CreataniaBlocks.MANA_CASING.get().asItem()))
      .ingredient(Ingredient.of(AllBlocks.SMART_FLUID_PIPE.get().asItem()))
      .mana(66666)
      .itemResult(new ItemStack(CreataniaBlocks.ELEMENTIUM_MANADUCT_BLOCK.get().asItem(), 1))
      .build()
      .saveAs(CreataniaBlocks.ELEMENTIUM_MANADUCT_BLOCK.getId(), pCache);

    start()
      .ingredient(gaiaingot())
      .ingredient(Ingredient.of(CreataniaBlocks.MANA_CASING.get().asItem()))
      .ingredient(Ingredient.of(AllBlocks.SMART_FLUID_PIPE.get().asItem()))
      .mana(99999)
      .itemResult(new ItemStack(CreataniaBlocks.GAIA_MANADUCT_BLOCK.get().asItem(), 1))
      .build()
      .saveAs(CreataniaBlocks.GAIA_MANADUCT_BLOCK.getId(), pCache);





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
