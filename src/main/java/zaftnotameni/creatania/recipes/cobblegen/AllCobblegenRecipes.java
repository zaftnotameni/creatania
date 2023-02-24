package zaftnotameni.creatania.recipes.cobblegen;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

import java.util.ArrayList;
import java.util.List;
public class AllCobblegenRecipes {
  public static List<CobblegenRecipe> all = new ArrayList<>();
  public static void populateRecipes(ServerLevel serverLevel) {
    all = serverLevel.getRecipeManager().getAllRecipesFor(CobblegenRecipe.TYPE);
  }
  public static void populateRecipes(ClientLevel clientLevel) {
    all = clientLevel.getRecipeManager().getAllRecipesFor(CobblegenRecipe.TYPE);
  }
  public static List<CobblegenRecipe> getCobblegenRecipes(LevelAccessor level) {
    if ((all == null || all.isEmpty()) && level instanceof ClientLevel clientLevel) populateRecipes(clientLevel);
    if ((all == null || all.isEmpty()) && level instanceof ServerLevel serverLevel) populateRecipes(serverLevel);
    return all;
  }
  public static void register(IRecipeRegistration registration, Level level) {
    registration.addRecipes(new RecipeType<>(CobblegenRecipeCategory.UID, CobblegenRecipe.class), getCobblegenRecipes(level));
  }
}
