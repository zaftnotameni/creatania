package zaftnotameni.creatania.machines.managenerator

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import vazkii.botania.api.mana.IManaReceiver
import zaftnotameni.creatania.mana.manaduct.BaseManaductBlock

fun getTargetManaReceiver(initial : BlockPos, level : Level) : ManaReceiverMatch? {
  if (level == null) return null
  if (level.isClientSide) return null
  if (initial == null) return null
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

fun computeManaReceiverMatchAt(initial : BlockPos, level : Level, x : Int, y: Int, z : Int) : ManaReceiverMatch? {
  if (level == null) return null
  if (level.isClientSide) return null
  if (initial == null) return null
  val position = initial.offset(x, y, z)
  val entity = level.getBlockEntity(position) as? IManaReceiver
  val blockstate = level.getBlockState(position)
  val isManaDuct = blockstate.block is BaseManaductBlock
  return ManaReceiverMatch(receiver = entity, position = position, blockstate = blockstate, isManaDuct = isManaDuct)
}

data class ManaReceiverMatch(val receiver : IManaReceiver?, val position : BlockPos, val blockstate : BlockState, val isManaDuct : Boolean)