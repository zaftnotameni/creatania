package zaftnotameni.creatania.recipes.cobblegen

import com.simibubi.create.foundation.fluid.FluidIngredient.FluidStackIngredient
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.FluidState
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.ForgeFlowingFluid
import net.minecraftforge.registries.ForgeRegistries
import zaftnotameni.creatania.recipes.cobblegen.AllCobblegenRecipes.getCobblegenRecipes
import zaftnotameni.creatania.registry.Fluids.CreataniaFlowingFluidSource

class CobblegenRecipeMatch {
  var fluidsRequireSource : MutableList<FluidStackIngredient?> = ArrayList()
  var sourcesToBeErased : MutableList<BlockPos> = ArrayList()
  var matchingA : MutableList<BlockPos> = ArrayList()
  var matchingB : MutableList<BlockPos> = ArrayList()
  var fluidA : FluidStackIngredient? = null
  var fluidARequiresSource = false
  var fluidB : FluidStackIngredient? = null
  var fluidBRequiresSource = false
  var requiredCatalyst : Ingredient? = null
  var output : Block? = null
  var originator : ForgeFlowingFluid? = null
  var target : FluidStackIngredient? = null
  var targetRequiresSource = false

  companion object {
    fun fromRecipe(`in` : CobblegenRecipe) : CobblegenRecipeMatch? {
      val fluidIngredients = `in`.fluidIngredients
      val ingredients = `in`.ingredients
      val result = `in`.resultItem
      if (fluidIngredients == null || fluidIngredients.size < 2) return null
      if (result == null || result.isEmpty) return null
      val out = CobblegenRecipeMatch()
      if (fluidIngredients[0] is FluidStackIngredient) out.fluidA = fluidIngredients[0] as FluidStackIngredient
      if (fluidIngredients[1] is FluidStackIngredient) out.fluidB = fluidIngredients[1] as FluidStackIngredient
      if (out.fluidA == null || out.fluidB == null) return null
      out.fluidARequiresSource = out.fluidA!!.requiredAmount > 999
      out.fluidBRequiresSource = out.fluidB!!.requiredAmount > 999
      out.output = ForgeRegistries.BLOCKS.getValue(
        result.item.registryName
      )
      out.requiredCatalyst = if (ingredients == null || ingredients.isEmpty()) null else ingredients[0]
      return out
    }

    fun getCobbleGenRecipeMatchingTarget(
      level : LevelAccessor, recipes : List<CobblegenRecipeMatch>, pPos : BlockPos, pBlockState : BlockState, pDirection : Direction?, pFluidState : FluidState?
    ) : CobblegenRecipeMatch? {
      if (level !is ServerLevel) return null
      if (!pBlockState.isAir) return null
      val fs = level.getFluidState(pPos)
      for (r in recipes) {
        for (f in r.fluidsRequireSource) addSourceToBeErased(pPos, fs, r, f)
        if (fs.type.isSame(
            r.fluidA?.getMatchingFluidStacks()?.first()?.fluid
          )
        ) {
          r.matchingA.add(pPos)
        }
        if (fs.type.isSame(
            r.fluidB?.getMatchingFluidStacks()?.first()?.fluid
          )
        ) {
          r.matchingB.add(pPos)
        }
        for (d in Direction.values()) {
          val pos = pPos.relative(d)
          val neighbor = level.getFluidState(pos)
          if (neighbor.type.isSame(
              r.fluidA?.getMatchingFluidStacks()?.first()?.fluid
            )
          ) {
            r.matchingA.add(pos)
          }
          if (neighbor.type.isSame(
              r.fluidB?.getMatchingFluidStacks()?.first()?.fluid
            )
          ) {
            r.matchingB.add(pos)
          }
          for (f in r.fluidsRequireSource) {
            addSourceToBeErased(pPos, fs, r, f)
            addSourceToBeErased(pos, neighbor, r, f)
            addSourceToBeErased(pos, r, f)
          }
          if (isComplete(r)) {
            return r
          }
        }
      }
      return null
    }

    private fun isComplete(r : CobblegenRecipeMatch) : Boolean {
      return r.matchingA.size > 0 && r.matchingB.size > 0 && r.sourcesToBeErased.size == r.fluidsRequireSource.size
    }

    private fun matchesEitherOriginatorOrTarget(fs : FluidState, neighbor : FluidState, r : CobblegenRecipeMatch) : Boolean {
      return r.target!!.test(FluidStack(neighbor.type, 1)) || r.target!!.test(FluidStack(fs.type, 1)) || r.originator!!.isSame(fs.type) || r.originator!!.isSame(neighbor.type)
    }

    private fun addSourceToBeErased(pos : BlockPos, neighbor : FluidState, r : CobblegenRecipeMatch, f : FluidStackIngredient?) {
      if (r.sourcesToBeErased.contains(pos)) return
      if (f!!.test(FluidStack(neighbor.type, 1)) && neighbor.isSource) {
        r.sourcesToBeErased.add(pos)
      }
    }

    private fun addSourceToBeErased(pos : BlockPos, r : CobblegenRecipeMatch, f : FluidStackIngredient?) {
      if (r.sourcesToBeErased.contains(pos)) return
      if (f!!.test(FluidStack(r.originator, 1)) && r.originator is CreataniaFlowingFluidSource) {
        r.sourcesToBeErased.add(pos)
      }
    }

    fun getCobbleGenRecipesMatchingOriginator(level : LevelAccessor?, originator : ForgeFlowingFluid?) : List<CobblegenRecipeMatch> {
      val matchingRecipes = ArrayList<CobblegenRecipeMatch>()
      val recipes = getCobblegenRecipes(level)
      recipes?.stream()?.forEach { r : CobblegenRecipe ->
        val m = fromRecipe(r) ?: return@forEach
        if (m.fluidA!!.test(FluidStack(originator, 1))) {
          m.originator = originator
          m.target = m.fluidB
        }
        if (m.fluidB!!.test(FluidStack(originator, 1))) {
          m.originator = originator
          m.target = m.fluidA
        }
        if (m.fluidARequiresSource) m.fluidsRequireSource.add(m.fluidA)
        if (m.fluidBRequiresSource) m.fluidsRequireSource.add(m.fluidB)
        m.targetRequiresSource = m.fluidARequiresSource || m.fluidBRequiresSource
        if (m.target == null) return@forEach
        matchingRecipes.add(m)
      }
      matchingRecipes.sortWith { a : CobblegenRecipeMatch, b : CobblegenRecipeMatch ->
        if (a.targetRequiresSource) return@sortWith -1
        if (b.targetRequiresSource) return@sortWith 1
        0
      }
      return matchingRecipes
    }
  }
}