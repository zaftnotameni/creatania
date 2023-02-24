package zaftnotameni.creatania.recipes.cobblegen;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;
import zaftnotameni.creatania.registry.Fluids;

import java.util.ArrayList;
import java.util.List;

import static zaftnotameni.creatania.recipes.cobblegen.AllCobblegenRecipes.getCobblegenRecipes;
public class CobblegenRecipeMatch {
  public List<FluidIngredient.FluidStackIngredient> fluidsRequireSource = new ArrayList<>();
  public List<BlockPos> sourcesToBeErased = new ArrayList<>();
  public FluidIngredient.FluidStackIngredient fluidA;
  public boolean fluidARequiresSource;
  public FluidIngredient.FluidStackIngredient fluidB;
  public boolean fluidBRequiresSource;
  public Ingredient requiredCatalyst;
  public Block output;
  public ForgeFlowingFluid originator;
  public FluidIngredient.FluidStackIngredient target;
  public boolean targetRequiresSource;
  public CobblegenRecipeMatch() {}
  public static CobblegenRecipeMatch fromRecipe(CobblegenRecipe in) {
    var fluidIngredients = in.getFluidIngredients();
    var ingredients = in.getIngredients();
    var result = in.getResultItem();
    if (fluidIngredients == null || fluidIngredients.size() < 2) return null;
    if (result == null || result.isEmpty()) return null;
    var out = new CobblegenRecipeMatch();
    if (fluidIngredients.get(0) instanceof FluidIngredient.FluidStackIngredient fsa) out.fluidA = fsa;
    if (fluidIngredients.get(1) instanceof FluidIngredient.FluidStackIngredient fsb) out.fluidB = fsb;
    if (out.fluidA == null || out.fluidB == null) return null;
    out.fluidARequiresSource = out.fluidA.getRequiredAmount() > 999;
    out.fluidBRequiresSource = out.fluidB.getRequiredAmount() > 999;
    out.output = ForgeRegistries.BLOCKS.getValue(result.getItem().getRegistryName());
    out.requiredCatalyst = (ingredients == null || ingredients.isEmpty()) ? null : ingredients.get(0);
    return out;
  }
  public static CobblegenRecipeMatch getCobbleGenRecipeMatchingTarget(LevelAccessor level, List<CobblegenRecipeMatch> recipes, BlockPos pPos, BlockState pBlockState, Direction pDirection, FluidState pFluidState) {
    if (!(level instanceof ServerLevel serverLevel)) return null;
    if (!pBlockState.isAir()) return null;
    var fs = level.getFluidState(pPos);
    for (var r : recipes) {
      if (!r.target.test(new FluidStack(fs.getType(), 1))) continue;
      for (var f : r.fluidsRequireSource) addSourceToBeErased(pPos, fs, r, f);
      if (r.sourcesToBeErased.size() == r.fluidsRequireSource.size()) return r;
    }
    for (var d : Direction.values()) {
      if (d == pDirection.getOpposite()) continue;
      var pos = pPos.relative(d);
      var neighbor = serverLevel.getFluidState(pos);
      for (var r : recipes) {
        if (!matchesEitherOriginatorOrTarget(fs, neighbor, r)) continue;
        for (var f : r.fluidsRequireSource) {
          addSourceToBeErased(pPos, fs, r, f);
          addSourceToBeErased(pos, neighbor, r, f);
          addSourceToBeErased(pos, r, f);
        }
        if (r.sourcesToBeErased.size() == r.fluidsRequireSource.size()) return r;
      }
    }
    return null;
  }
  private static boolean matchesEitherOriginatorOrTarget(FluidState fs, FluidState neighbor, CobblegenRecipeMatch r) {
    return r.target.test(new FluidStack(neighbor.getType(), 1)) ||
      r.target.test(new FluidStack(fs.getType(), 1)) ||
      r.originator.isSame(fs.getType()) ||
      r.originator.isSame(neighbor.getType());
  }
  private static void addSourceToBeErased(BlockPos pos, FluidState neighbor, CobblegenRecipeMatch r, FluidIngredient.FluidStackIngredient f) {
    if (r.sourcesToBeErased.contains(pos)) return;
    if (f.test(new FluidStack(neighbor.getType(), 1)) && neighbor.isSource()) {
      r.sourcesToBeErased.add(pos);
    }
  }
  private static void addSourceToBeErased(BlockPos pos, CobblegenRecipeMatch r, FluidIngredient.FluidStackIngredient f) {
    if (r.sourcesToBeErased.contains(pos)) return;
    if (f.test(new FluidStack(r.originator, 1)) && (r.originator instanceof Fluids.CreataniaFlowingFluidSource)) {
      r.sourcesToBeErased.add(pos);
    }
  }
  public static List<CobblegenRecipeMatch> getCobbleGenRecipesMatchingOriginator(LevelAccessor level, ForgeFlowingFluid originator) {
    var matchingRecipes = new ArrayList<CobblegenRecipeMatch>();
    var recipes = getCobblegenRecipes(level);
    recipes.stream().forEach(r -> {
      var m = fromRecipe(r);
      if (m == null) return;
      if (m.fluidA.test(new FluidStack(originator, 1))) {
        m.originator = originator;
        m.target = m.fluidB;
      }
      if (m.fluidB.test(new FluidStack(originator, 1))) {
        m.originator = originator;
        m.target = m.fluidA;
      }
      if (m.fluidARequiresSource) m.fluidsRequireSource.add(m.fluidA);
      if (m.fluidBRequiresSource) m.fluidsRequireSource.add(m.fluidB);
      m.targetRequiresSource = m.fluidARequiresSource || m.fluidBRequiresSource;
      if (m.target == null) return;
      matchingRecipes.add(m);
    });
    matchingRecipes.sort((a, b) -> {
      if (a.targetRequiresSource) return -1;
      if (b.targetRequiresSource) return 1;
      return 0;
    });
    return matchingRecipes;
  }
}
