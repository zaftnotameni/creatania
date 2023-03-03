package zaftnotameni.creatania.util;

import com.simibubi.create.foundation.fluid.FluidIngredient.FluidStackIngredient;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;
import zaftnotameni.creatania.recipes.cobblegen.CobblegenRecipe;
import zaftnotameni.creatania.registry.Fluids.CreataniaFlowingFluidFlowing;
import zaftnotameni.creatania.registry.Fluids.CreataniaFlowingFluidSource;

import static zaftnotameni.creatania.recipes.cobblegen.AllCobblegenRecipes.getCobblegenRecipes;

public class Fluids {
  public static boolean specialCobblegenCanSpread(
    BlockGetter pLevel, BlockPos pFromPos, BlockState pFromBlockState, Direction pDirection, BlockPos pToPos, BlockState pToBlockState, FluidState pToFluidState, Fluid pFluid, ForgeFlowingFluid self
  ) {
    if (!(pLevel instanceof ServerLevel server)) return true;
    var fsBlock = getBlockToBeGeneratedFlowingToSource(pLevel,pToFluidState,self);
    if (fsBlock != null) {
      server.setBlockAndUpdate(pToPos, fsBlock.defaultBlockState());
      return false;
    }
    var sfBlock= getBlockToBeGeneratedSourceToFlowing(pLevel,pToFluidState,self);
    if (sfBlock != null) {
      server.setBlockAndUpdate(pFromPos, sfBlock.defaultBlockState());
      return false;
    }
    var ssBlock = getBlockToBeGeneratedSourceToSource(pLevel,pToFluidState,self);
    if (ssBlock != null) {
      server.setBlockAndUpdate(pToPos, ssBlock.defaultBlockState());
      return false;
    }
    var ffBlock = getBlockToBeGeneratedFlowingToFlowing(pLevel,pToFluidState,self);
    if (ffBlock != null) {
      server.setBlockAndUpdate(pFromPos, ffBlock.defaultBlockState());
      return false;
    }
    return true;
  }
  public static Block getBlockToBeGeneratedFlowingToSource(
    BlockGetter pLevel, FluidState pToFluidState, ForgeFlowingFluid self
  ) {
    if (!(pLevel instanceof ServerLevel server)) return null;
    if (!(pToFluidState.getType() instanceof CreataniaFlowingFluidSource source)) return null;
    if (!(self instanceof CreataniaFlowingFluidFlowing flowing)) return null;
    Predicate<FluidStackIngredient> testA = f -> f.test(new FluidStack(flowing, 1));
    Predicate<FluidStackIngredient> testB = f -> f.test(new FluidStack(source, 1));
    return getBlockToBeGenerated(pLevel, CobblegenRecipe::getFlowings, CobblegenRecipe::getSources, testA, testB);
  }
  public static Block getBlockToBeGeneratedSourceToFlowing(
    BlockGetter pLevel, FluidState pToFluidState, ForgeFlowingFluid self
  ) {
    if (!(pLevel instanceof ServerLevel server)) return null;
    if (!(self instanceof CreataniaFlowingFluidSource source)) return null;
    if (!(pToFluidState.getType() instanceof CreataniaFlowingFluidFlowing flowing)) return null;
    Predicate<FluidStackIngredient> testA = f -> f.test(new FluidStack(source, 1));
    Predicate<FluidStackIngredient> testB = f -> f.test(new FluidStack(flowing, 1));
    return getBlockToBeGenerated(pLevel, CobblegenRecipe::getSources, CobblegenRecipe::getFlowings, testA, testB);
  }
  public static Block getBlockToBeGeneratedFlowingToFlowing(
    BlockGetter pLevel, FluidState pToFluidState, ForgeFlowingFluid self
  ) {
    if (!(pLevel instanceof ServerLevel server)) return null;
    if (!(self instanceof CreataniaFlowingFluidFlowing flowingA)) return null;
    if (!(pToFluidState.getType() instanceof CreataniaFlowingFluidFlowing flowingB)) return null;
    Predicate<FluidStackIngredient> testA = f -> f.test(new FluidStack(flowingA, 1));
    Predicate<FluidStackIngredient> testB = f -> f.test(new FluidStack(flowingB, 1));
    return getBlockToBeGenerated(pLevel, CobblegenRecipe::getFlowings, CobblegenRecipe::getFlowings, testA, testB);
  }
  public static Block getBlockToBeGeneratedSourceToSource(
    BlockGetter pLevel, FluidState pToFluidState, ForgeFlowingFluid self
  ) {
    if (!(pLevel instanceof ServerLevel server)) return null;
    if (!(self instanceof CreataniaFlowingFluidSource sourceA)) return null;
    if (!(pToFluidState.getType() instanceof CreataniaFlowingFluidSource sourceB)) return null;
    Predicate<FluidStackIngredient> testA = f -> f.test(new FluidStack(sourceA, 1));
    Predicate<FluidStackIngredient> testB = f -> f.test(new FluidStack(sourceB, 1));
    return getBlockToBeGenerated(pLevel, CobblegenRecipe::getSources, CobblegenRecipe::getSources, testA, testB);
  }
  public static Block getBlockToBeGenerated(
    BlockGetter pLevel,
    Function<CobblegenRecipe, Collection<FluidStackIngredient>> getA,
    Function<CobblegenRecipe, Collection<FluidStackIngredient>> getB,
    Predicate<FluidStackIngredient> testA,
    Predicate<FluidStackIngredient> testB
  ) {
    if (!(pLevel instanceof ServerLevel server)) return null;
    var maybeRecipe = getCobblegenRecipes(server).stream().filter(r -> {
      var a = getA.apply(r).stream().filter(testA).findAny();
      var b = getB.apply(r).stream().filter(testB).findAny();
      if (a.isEmpty() || b.isEmpty()) return false;
      if (a.get() == b.get()) return false;
      return true;
    }).findAny();
    if (!maybeRecipe.isPresent()) return null;
    var recipe = maybeRecipe.get();
    var block = ForgeRegistries.BLOCKS.getValue(recipe.getResultItem().getItem().getRegistryName());
    if (block == null || block.defaultBlockState().isAir()) return null;
    return block;
  }

  public static boolean specialCobblegenSpread(LevelAccessor pLevel, BlockPos pPos, BlockState pBlockState, Direction pDirection, FluidState pFluidState, ForgeFlowingFluid self) {
    return false;
  }

}