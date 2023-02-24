package zaftnotameni.creatania.recipes.cobblegen;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;

import java.util.ArrayList;
import java.util.List;
public class AllCobblegenRecipes {
  public static List<CobblegenRecipe> all = new ArrayList<>();
  public static void populateRecipes(ServerLevel serverLevel) {
    all = serverLevel.getRecipeManager().getAllRecipesFor(CobblegenRecipe.TYPE);
  }
  public static List<CobblegenRecipe> getCobblegenRecipes(LevelAccessor level) {
    if ((all == null || all.isEmpty()) && level instanceof ServerLevel serverLevel) populateRecipes(serverLevel);
    return all;
  }
}
