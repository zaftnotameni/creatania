package zaftnotameni.creatania.machines.managenerator

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import vazkii.botania.api.block.IWandable
import vazkii.botania.api.mana.IManaReceiver
import zaftnotameni.creatania.mana.manaduct.BaseManaductBlock

fun getTargetManaReceiver(initial : BlockPos, level : Level) : ManaReceiverMatch? {
  if (level.isClientSide) return null
  for (y in 1..2) {
    val match = computeManaReceiverMatchAt(initial, level, 0, y, 0)
    if (match?.isManaDuct == true) return match
    if (match?.receiver != null) return match
  }
  for (y in 1..2) for (x in -1..1) for (z in -1..1) {
    val match = computeManaReceiverMatchAt(initial, level, x, y, z)
    if (match?.isManaDuct == true) return match
    if (match?.receiver != null) return match
  }
  return null
}

fun computeManaReceiverMatchAt(initial : BlockPos, level : Level, x : Int, y : Int, z : Int) : ManaReceiverMatch? {
  if (level.isClientSide) return null
  val position = initial.offset(x, y, z)
  val entity = level.getBlockEntity(position) as? IManaReceiver
  val blockstate = level.getBlockState(position)
  val isManaDuct = blockstate.block is BaseManaductBlock
  return ManaReceiverMatch(receiver = entity, position = position, blockstate = blockstate, isManaDuct = isManaDuct)
}

fun specialHandlingViaManaduct(manaAmount : Int, manaDuctBlockState : BlockState, generator : ManaGeneratorBlockEntity) : Int {
  if (manaDuctBlockState.block !is BaseManaductBlock) return 0
  val manaDuctBlock = manaDuctBlockState.block as BaseManaductBlock
  val maybeAggloPlateBlockState = BaseManaductBlock.getMouthPointedAtBlockState(generator.level, manaDuctBlockState, generator.blockPos.above())
  if (maybeAggloPlateBlockState == null || !maybeAggloPlateBlockState.hasBlockEntity()) return 0
  val maybeAggloPlateBlockEntity = BaseManaductBlock.getMouthPointedAtBlockEntity(generator.level, manaDuctBlockState, generator.blockPos.above())
  if (maybeAggloPlateBlockEntity !is IManaReceiver || maybeAggloPlateBlockEntity.isFull) return 0
  maybeAggloPlateBlockEntity.receiveMana(manaAmount * manaDuctBlock.manaMultiplier)
  generator.duct = true
  return manaAmount
}

fun addManaToTargetPool(manaAmount : Int, pool : IManaReceiver?, generator : ManaGeneratorBlockEntity) : Int {
  if (pool == null || pool.isFull) return 0
  pool.receiveMana(manaAmount)
  if (pool is IWandable) pool.onUsedByWand(null, ItemStack.EMPTY, Direction.UP)
  generator.duct = false
  return manaAmount
}

data class ManaReceiverMatch(val receiver : IManaReceiver?, val position : BlockPos, val blockstate : BlockState, val isManaDuct : Boolean)