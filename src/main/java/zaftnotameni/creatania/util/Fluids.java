package zaftnotameni.creatania.util;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.util.Arrays;
import java.util.Optional;

import static zaftnotameni.creatania.recipes.cobblegen.CobblegenRecipe.CobblegenRecipeMatch.getCobbleGenRecipeMatchingTarget;
import static zaftnotameni.creatania.recipes.cobblegen.CobblegenRecipe.CobblegenRecipeMatch.getCobbleGenRecipesMatchingOriginator;
public class Fluids {
  public static boolean isMatch(Fluid ta, Fluid tb, FluidState...fs) {
    var match = matching(ta, tb, fs);
    return match.getA().isPresent() && match.getB().isPresent();
  }
  public static boolean isSourceMatchA(Fluid ta, Fluid tb, FluidState ...fs) {
    var match = matching(ta, tb, fs);
    return match.getA().isPresent() && match.getB().isPresent() && match.getA().get().isSource();
  }
  public static boolean isSourceMatchB(Fluid ta, Fluid tb, FluidState ...fs) {
    var match = matching(ta, tb, fs);
    return match.getA().isPresent() && match.getB().isPresent() && match.getB().get().isSource();
  }
  public static Tuple<Optional<FluidState>, Optional<FluidState>> matching(Fluid target, FluidState ...fs) {
    var first = Arrays.stream(fs).filter(x -> x.is(target)).findFirst();
    var second = Arrays.stream(fs).filter(x -> !x.is(target)).findFirst();
    return new Tuple<>(first, second);
  }
  public static Tuple<Optional<FluidState>, Optional<FluidState>> matching(Fluid ta, Fluid tb, FluidState ...fs) {
    var first = Arrays.stream(fs).filter(x -> x.is(ta)).findFirst();
    var second = Arrays.stream(fs).filter(x -> !x.is(tb)).findFirst();
    return new Tuple<>(first, second);
  }

  public static String nameOf(FluidEntry<ForgeFlowingFluid.Flowing> f) {
    return f.get().getSource().getRegistryName().toString();
  }

  public static boolean specialCobblegenSpread(LevelAccessor pLevel, BlockPos pPos, BlockState pBlockState, Direction pDirection, FluidState pFluidState, ForgeFlowingFluid self) {
    try {
      if (!(pLevel instanceof ServerLevel serverLevel)) return false;
      var recipes = getCobbleGenRecipesMatchingOriginator(pLevel, self);
      Log.LOGGER.debug("Special cobblegen found {} recipes matching originator {}", recipes.size(), self.getSource().getRegistryName().toString());
      var recipe = getCobbleGenRecipeMatchingTarget(pLevel, recipes, pPos, pBlockState, pDirection, pFluidState);
      if (recipe == null) return false;
      serverLevel.setBlockAndUpdate(pPos, recipe.output.defaultBlockState());
      if (recipe.targetRequiresSource) serverLevel.setBlockAndUpdate(recipe.sourcePos, Blocks.AIR.defaultBlockState());
      Log.LOGGER.debug("Special cobblegen {} + {} = {}", recipe.fluidA.serialize().toString(), recipe.fluidB.serialize().toString(), recipe.output.getRegistryName().toString());
      return true;
    } catch (RuntimeException e) {
      Log.LOGGER.error("Error performing special cobblegen operation at {} towards {}", pPos.toShortString(), pDirection.getName());
      return false;
    } catch (Exception e) {
      Log.LOGGER.error("Error performing special cobblegen operation at {} towards {}", pPos.toShortString(), pDirection.getName());
      return false;
    }
  }
}
