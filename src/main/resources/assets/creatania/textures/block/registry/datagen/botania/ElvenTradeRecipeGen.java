package zaftnotameni.creatania.registry.datagen.botania;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;

import java.io.IOException;
public class ElvenTradeRecipeGen extends BotaniaBaseRecipeGen implements DataProvider {
  public ElvenTradeRecipeGen(DataGenerator gen) {
    super(gen, "botania:elven_trade");
  }
  @Override
  public void run(HashCache pCache) throws IOException {

  }
  @Override
  public String getName() {
    return "Creatania Elven Trade Recipes";
  }
}
