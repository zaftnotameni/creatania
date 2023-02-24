package zaftnotameni.creatania.recipes.cobblegen;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import zaftnotameni.creatania.registry.Fluids;
import zaftnotameni.creatania.registry.Index;
import zaftnotameni.creatania.registry.Recipes;

import java.util.ArrayList;
import java.util.List;

import static com.simibubi.create.AllRecipeTypes.simpleType;

public class CobblegenRecipe<C extends Container> extends ProcessingRecipe<C> {
  public CobblegenRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams processingRecipeParams) {
    super(TypeInfo.instance.get(), processingRecipeParams);
  }
  @Override
  protected int getMaxInputCount() {
    return 0;
  }
  @Override
  protected int getMaxOutputCount() {
    return 0;
  }
  @Override
  public boolean matches(Container pContainer, Level pLevel) {
    return false;
  }
  public static final RegistryObject<RecipeSerializer<?>> serializer = Recipes.SERIALIZERS.register(TypeInfo.name, Serializer::new);
  public static final RegistryObject<RecipeType<?>> type = Recipes.TYPES.register(TypeInfo.name, () -> simpleType(TypeInfo.id));
  public static void init() {};

  public static List<CobblegenRecipe> all = new ArrayList<>();
  public static void populateRecipes(ServerLevel serverLevel) {
    serverLevel.getRecipeManager().getRecipes().stream().forEach(r -> { if (r instanceof CobblegenRecipe cgr) all.add(cgr); });
  }
  public static List<CobblegenRecipe> getCobblegenRecipes(LevelAccessor level) {
    if ((all == null || all.isEmpty()) && level instanceof ServerLevel serverLevel) populateRecipes(serverLevel);
    return all;
  }
  public static class CobblegenRecipeMatch {
    public List<FluidIngredient.FluidStackIngredient> fluidsRequireSource = new ArrayList<>();
    public List<BlockPos> sourcesToBeErased = new ArrayList<>();
    public FluidIngredient.FluidStackIngredient fluidA;
    public boolean fluidARequiresSource;
    public FluidIngredient.FluidStackIngredient fluidB;
    public boolean fluidBRequiresSource;
    public Ingredient requiredCatalyst;
    public Block output;
    public ForgeFlowingFluid originator;
    public FluidState sourceState;
    public BlockPos sourcePos;
    public FluidIngredient.FluidStackIngredient target;
    public boolean targetRequiresSource;
    public CobblegenRecipeMatch() {}
    public static CobblegenRecipeMatch fromRecipe(CobblegenRecipe<?> in) {
      if (in.fluidIngredients == null || in.fluidIngredients.size() < 2) return null;
      if (in.results == null || in.results.isEmpty()) return null;
      var out = new CobblegenRecipeMatch();
      if (in.fluidIngredients.get(0) instanceof FluidIngredient.FluidStackIngredient fsa) out.fluidA = fsa;
      if (in.fluidIngredients.get(1) instanceof FluidIngredient.FluidStackIngredient fsb) out.fluidB = fsb;
      if (out.fluidA == null || out.fluidB == null) return null;
      out.fluidARequiresSource = out.fluidA.getRequiredAmount() > 999;
      out.fluidBRequiresSource = out.fluidB.getRequiredAmount() > 999;
      out.output = ForgeRegistries.BLOCKS.getValue(in.results.get(0).getStack().getItem().getRegistryName());
      out.requiredCatalyst = (in.ingredients == null || in.ingredients.isEmpty()) ? null : in.ingredients.get(0);
      return out;
    }
    public static CobblegenRecipeMatch getCobbleGenRecipeMatchingTarget(LevelAccessor level, List<CobblegenRecipeMatch> recipes, BlockPos pPos, BlockState pBlockState, Direction pDirection, FluidState pFluidState) {
      if (!(level instanceof ServerLevel serverLevel)) return null;
      if (!pBlockState.isAir()) return null;
      var fs = level.getFluidState(pPos);
      for (var r : recipes) {
        if (!r.target.test(new FluidStack(fs.getType(), 1))) continue;
        for (var f : r.fluidsRequireSource) if (!r.sourcesToBeErased.contains(pPos)) if (f.test(new FluidStack(fs.getType(), 1)) && fs.isSource()) {
          r.sourcesToBeErased.add(pPos);
        }
        if (r.sourcesToBeErased.size() == r.fluidsRequireSource.size()) return r;
      }
      for (var d : Direction.values()) {
        if (d == pDirection.getOpposite()) continue;
        var pos = pPos.relative(d);
        var neighbor = serverLevel.getFluidState(pos);
        for (var r : recipes) {
          if (!r.target.test(new FluidStack(neighbor.getType(), 1)) &&
            !r.target.test(new FluidStack(fs.getType(), 1)) &&
            !r.originator.isSame(fs.getType()) &&
            !r.originator.isSame(neighbor.getType())) continue;
          for (var f : r.fluidsRequireSource) {
            if (!r.sourcesToBeErased.contains(pPos)) if (f.test(new FluidStack(fs.getType(), 1)) && fs.isSource()) {
               r.sourcesToBeErased.add(pPos);
            }
            if (r.sourcesToBeErased.contains(pos)) continue;
            if (f.test(new FluidStack(neighbor.getType(), 1)) && neighbor.isSource()) {
              r.sourcesToBeErased.add(pos);
            }
            if (r.sourcesToBeErased.contains(pos)) continue;
            if (f.test(new FluidStack (r.originator, 1)) && (r.originator instanceof Fluids.CreataniaFlowingFluidSource)) {
              r.sourcesToBeErased.add(pos);
            }
          }
          if (r.sourcesToBeErased.size() == r.fluidsRequireSource.size()) return r;
        }
      }
      return null;
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
  public static class Serializer extends ProcessingRecipeSerializer<CobblegenRecipe<?>> {
    public Serializer() { super(CobblegenRecipe::new);  }
  }
  public static class TypeInfo implements IRecipeTypeInfo  {
    public static final String name = "cobblegen";
    public static final ResourceLocation id = Index.resource(name);
    public static final Lazy<TypeInfo> instance = Lazy.of(TypeInfo::new);
    @Override
    public ResourceLocation getId() { return id; }
    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecipeSerializer<?>> T getSerializer() {
      return (T) serializer.get();
    }
    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecipeType<?>> T getType() { return (T) type.get(); }
  }
}
