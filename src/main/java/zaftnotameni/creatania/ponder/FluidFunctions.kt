package zaftnotameni.creatania.ponder

import com.simibubi.create.foundation.fluid.FluidIngredient.FluidStackIngredient
import net.minecraft.core.Direction
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.LiquidBlock
import net.minecraft.world.level.block.state.BlockState

fun fluidWithLevelAt(u : CreataniaPonderUtils, level : Int, fsi : FluidStackIngredient, x : Int, y : Int, z : Int) : CreataniaPonderUtils.XYZ? {
  val v = u.at(x, y, z)
  val block = blockOf(fsi)
  val pos = v.asBlockPos()
  val selection = v.asSelection()
  with(u.scene.world) {
    setBlock(pos, block, false)
    modifyBlock(pos, { bs : BlockState -> bs.setValue(LiquidBlock.LEVEL, level) }, false)
    showSection(selection, Direction.DOWN)
  }
  u.scene.idle(20)
  return v
}

fun blockOf(fsi : FluidStackIngredient) : BlockState
  = fsi
    ?.getMatchingFluidStacks()
    ?.first()
    ?.fluid
    ?.defaultFluidState()
    ?.createLegacyBlock() ?: Blocks.AIR.defaultBlockState()