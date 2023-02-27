package zaftnotameni.creatania.registry.datagen.botania;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraftforge.registries.ForgeRegistries;
import zaftnotameni.creatania.registry.CreataniaFluids;

import java.io.IOException;
public class ManaInfusionRecipeGen extends BotaniaBaseRecipeGen implements DataProvider {
  public ManaInfusionRecipeGen(DataGenerator gen) {
    super(gen, "botania:mana_infusion");
  }
  @Override
  public void run(HashCache pCache) throws IOException {
    var x = ForgeRegistries.BLOCKS.getValue(CreataniaFluids.MOLTEN_BRASS.get().getSource().getRegistryName());
    var y = x;

  }
  @Override
  public String getName() {
    return "Creatania Mana Infusion Recipes";
  }
}
