package zaftnotameni.creatania.registry.datagen.botania;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import zaftnotameni.creatania.registry.CreataniaBlocks;

import java.io.IOException;

import static zaftnotameni.creatania.util.NamedItems.*;
public class RuneAltarRecipeGen extends BotaniaBaseRecipeGen implements DataProvider {
  public RuneAltarRecipeGen(DataGenerator gen) {
    super(gen, "botania:runic_altar", "output");
  }
  @Override
  public void run(CachedOutput pCache) throws IOException {
    start()
      .ingredient(Ingredient.of(CreataniaBlocks.MANA_CASING.get().asItem()))
      .ingredient(Ingredient.of(itemLike("botania", "mana_powder")))
      .ingredient(Ingredient.of(itemLike("minecraft", "iron_bars")))
      .ingredient(Ingredient.of(AllBlocks.SHAFT.get().asItem()))
      .ingredient(Ingredient.of(AllItems.PROPELLER.get().asItem()))
      .mana(5000)
      .itemResult(new ItemStack(CreataniaBlocks.MANA_CONDENSER.get().asItem(), 1))
      .build()
      .saveAs(CreataniaBlocks.MANA_CONDENSER.getId(), pCache);
    start()
      .ingredient(Ingredient.of(CreataniaBlocks.MANA_CASING.get().asItem()))
      .ingredient(Ingredient.of(itemLike("botania", "mana_powder")))
      .ingredient(Ingredient.of(AllBlocks.SHAFT.get().asItem()))
      .ingredient(Ingredient.of(AllBlocks.FLUID_PIPE.get().asItem()))
      .ingredient(livingrock())
      .mana(5000)
      .itemResult(new ItemStack(CreataniaBlocks.MANA_GENERATOR.get().asItem(), 1))
      .build()
      .saveAs(CreataniaBlocks.MANA_GENERATOR.getId(), pCache);
    start()
      .ingredient(Ingredient.of(CreataniaBlocks.MANA_CASING.get().asItem()))
      .ingredient(Ingredient.of(itemLike("botania", "mana_powder")))
      .ingredient(Ingredient.of(AllBlocks.SHAFT.get().asItem()))
      .ingredient(Ingredient.of(AllItems.IRON_SHEET.get().asItem()))
      .ingredient(Ingredient.of(AllItems.IRON_SHEET.get().asItem()))
      .ingredient(Ingredient.of(AllItems.IRON_SHEET.get().asItem()))
      .ingredient(Ingredient.of(AllItems.IRON_SHEET.get().asItem()))
      .ingredient(Ingredient.of(itemLike("minecraft", "iron_bars")))
      .mana(5000)
      .itemResult(new ItemStack(CreataniaBlocks.MANA_MOTOR.get().asItem(), 1))
      .build()
      .saveAs(CreataniaBlocks.MANA_MOTOR.getId(), pCache);

    start()
      .ingredient(Ingredient.of(CreataniaBlocks.MANA_CASING.get().asItem()))
      .ingredient(Ingredient.of(AllBlocks.GEARBOX.get().asItem()))
      .ingredient(Ingredient.of(AllItems.VERTICAL_GEARBOX.get().asItem()))
      .mana(5000)
      .itemResult(new ItemStack(CreataniaBlocks.OMNIBOX.get().asItem(), 1))
      .build()
      .saveAs(CreataniaBlocks.OMNIBOX.getId(), pCache);

    start()
      .ingredient(terrasteelingot())
      .ingredient(Ingredient.of(CreataniaBlocks.MANASTEEL_MANADUCT_BLOCK.get()))
      .mana(22222)
      .itemResult(new ItemStack(CreataniaBlocks.TERRASTEEL_MANADUCT_BLOCK.get().asItem(), 1))
      .build()
      .saveAs(CreataniaBlocks.TERRASTEEL_MANADUCT_BLOCK.getId(), "_upgrade", pCache);

    start()
      .ingredient(elementiumingot())
      .ingredient(Ingredient.of(CreataniaBlocks.TERRASTEEL_MANADUCT_BLOCK.get()))
      .mana(55555)
      .itemResult(new ItemStack(CreataniaBlocks.ELEMENTIUM_MANADUCT_BLOCK.get().asItem(), 1))
      .build()
      .saveAs(CreataniaBlocks.ELEMENTIUM_MANADUCT_BLOCK.getId(),"_upgrade", pCache);

    start()
      .ingredient(gaiaingot())
      .ingredient(Ingredient.of(CreataniaBlocks.ELEMENTIUM_MANADUCT_BLOCK.get()))
      .mana(88888)
      .itemResult(new ItemStack(CreataniaBlocks.GAIA_MANADUCT_BLOCK.get().asItem(), 1))
      .build()
      .saveAs(CreataniaBlocks.GAIA_MANADUCT_BLOCK.getId(),"_upgrade", pCache);
  }
  @Override
  public String getName() {
    return "Creatania Rune Altar Recipes";
  }
}
