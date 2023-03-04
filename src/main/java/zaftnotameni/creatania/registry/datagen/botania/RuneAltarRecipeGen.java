package zaftnotameni.creatania.registry.datagen.botania;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import java.io.IOException;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import zaftnotameni.creatania.registry.Blocks;

import static zaftnotameni.creatania.util.NamedItems.*;
public class RuneAltarRecipeGen extends BotaniaBaseRecipeGen implements DataProvider {
  public RuneAltarRecipeGen(DataGenerator gen) {
    super(gen, "botania:runic_altar", "output");
  }
  @Override
  public void run(@NotNull HashCache pCache) throws IOException {
    start()
      .ingredient(Ingredient.of(Blocks.MANA_CASING.get().asItem()))
      .ingredient(Ingredient.of(itemLike("botania", "mana_powder")))
      .ingredient(Ingredient.of(itemLike("minecraft", "iron_bars")))
      .ingredient(Ingredient.of(AllBlocks.SHAFT.get().asItem()))
      .ingredient(Ingredient.of(AllItems.PROPELLER.get().asItem()))
      .mana(5000)
      .itemResult(new ItemStack(Blocks.MANA_CONDENSER.get().asItem(), 1))
      .build()
      .saveAs(Blocks.MANA_CONDENSER.getId(), pCache);
    start()
      .ingredient(Ingredient.of(Blocks.MANA_CASING.get().asItem()))
      .ingredient(Ingredient.of(itemLike("botania", "mana_powder")))
      .ingredient(Ingredient.of(AllBlocks.SHAFT.get().asItem()))
      .ingredient(Ingredient.of(AllBlocks.FLUID_PIPE.get().asItem()))
      .ingredient(livingrock())
      .mana(5000)
      .itemResult(new ItemStack(Blocks.MANA_GENERATOR.get().asItem(), 1))
      .build()
      .saveAs(Blocks.MANA_GENERATOR.getId(), pCache);
    start()
      .ingredient(Ingredient.of(Blocks.MANA_CASING.get().asItem()))
      .ingredient(Ingredient.of(itemLike("botania", "mana_powder")))
      .ingredient(Ingredient.of(AllBlocks.SHAFT.get().asItem()))
      .ingredient(Ingredient.of(AllItems.IRON_SHEET.get().asItem()))
      .ingredient(Ingredient.of(AllItems.IRON_SHEET.get().asItem()))
      .ingredient(Ingredient.of(AllItems.IRON_SHEET.get().asItem()))
      .ingredient(Ingredient.of(AllItems.IRON_SHEET.get().asItem()))
      .ingredient(Ingredient.of(itemLike("minecraft", "iron_bars")))
      .mana(5000)
      .itemResult(new ItemStack(Blocks.MANA_MOTOR.get().asItem(), 1))
      .build()
      .saveAs(Blocks.MANA_MOTOR.getId(), pCache);

    start()
      .ingredient(Ingredient.of(Blocks.MANA_CASING.get().asItem()))
      .ingredient(Ingredient.of(AllBlocks.GEARBOX.get().asItem()))
      .ingredient(Ingredient.of(AllItems.VERTICAL_GEARBOX.get().asItem()))
      .mana(5000)
      .itemResult(new ItemStack(Blocks.OMNIBOX.get().asItem(), 1))
      .build()
      .saveAs(Blocks.OMNIBOX.getId(), pCache);

    start()
      .ingredient(terrasteelingot())
      .ingredient(Ingredient.of(Blocks.MANASTEEL_MANADUCT_BLOCK.get()))
      .mana(22222)
      .itemResult(new ItemStack(Blocks.TERRASTEEL_MANADUCT_BLOCK.get().asItem(), 1))
      .build()
      .saveAs(Blocks.TERRASTEEL_MANADUCT_BLOCK.getId(), "_upgrade", pCache);

    start()
      .ingredient(elementiumingot())
      .ingredient(Ingredient.of(Blocks.TERRASTEEL_MANADUCT_BLOCK.get()))
      .mana(55555)
      .itemResult(new ItemStack(Blocks.ELEMENTIUM_MANADUCT_BLOCK.get().asItem(), 1))
      .build()
      .saveAs(Blocks.ELEMENTIUM_MANADUCT_BLOCK.getId(),"_upgrade", pCache);

    start()
      .ingredient(gaiaingot())
      .ingredient(Ingredient.of(Blocks.ELEMENTIUM_MANADUCT_BLOCK.get()))
      .mana(88888)
      .itemResult(new ItemStack(Blocks.GAIA_MANADUCT_BLOCK.get().asItem(), 1))
      .build()
      .saveAs(Blocks.GAIA_MANADUCT_BLOCK.getId(),"_upgrade", pCache);
  }
  @Override
  public @NotNull String getName() {
    return "Creatania Rune Altar Recipes";
  }
}