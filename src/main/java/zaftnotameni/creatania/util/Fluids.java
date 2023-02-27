//package zaftnotameni.creatania.util;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.material.FluidState;
//import net.minecraftforge.fluids.ForgeFlowingFluid;
//import net.minecraftforge.registries.ForgeRegistries;
//
//import static zaftnotameni.creatania.recipes.cobblegen.CobblegenRecipeMatch.getCobbleGenRecipeMatchingTarget;
//import static zaftnotameni.creatania.recipes.cobblegen.CobblegenRecipeMatch.getCobbleGenRecipesMatchingOriginator;
public class Fluids {
//  public static boolean specialCobblegenSpread(LevelAccessor pLevel, BlockPos pPos, BlockState pBlockState, Direction pDirection, FluidState pFluidState, ForgeFlowingFluid self) {
//    try {
//      if (!(pLevel instanceof ServerLevel serverLevel)) return false;
//      var recipes = getCobbleGenRecipesMatchingOriginator(pLevel, self);
//      Log.LOGGER.debug("Special cobblegen found {} recipes matching originator {}", recipes.size(),
//        ForgeRegistries.FLUIDS.getKey(self.getSource()).toString());
//      var recipe = getCobbleGenRecipeMatchingTarget(pLevel, recipes, pPos, pBlockState, pDirection, pFluidState);
//      if (recipe == null) return false;
//      serverLevel.setBlockAndUpdate(pPos, recipe.output.defaultBlockState());
//      for (var s : recipe.sourcesToBeErased) serverLevel.setBlockAndUpdate(s, Blocks.AIR.defaultBlockState());
//      Log.LOGGER.debug("Special cobblegen {} + {} = {}", recipe.fluidA.serialize().toString(), recipe.fluidB.serialize().toString(),
//        ForgeRegistries.BLOCKS.getKey(recipe.output).toString());
//      return true;
//    } catch (RuntimeException e) {
//      Log.LOGGER.error("Error performing special cobblegen operation at {} towards {}", pPos.toShortString(), pDirection.getName());
//      return false;
//    } catch (Exception e) {
//      Log.LOGGER.error("Error performing special cobblegen operation at {} towards {}", pPos.toShortString(), pDirection.getName());
//      return false;
//    }
//  }
}
