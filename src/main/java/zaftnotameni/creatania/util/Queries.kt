package zaftnotameni.creatania.util

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.Level
import org.apache.commons.lang3.StringUtils
import zaftnotameni.creatania.registry.Fluids

object Queries {
  @JvmStatic
  fun isOnTopOrInsideManaFluid(level : Level, entity : LivingEntity, pos : BlockPos?) : Boolean {
    val posAbove = entity.onPos.above()
    val posBelow = entity.onPos.below()
    val fluidState = level.getFluidState(pos)
    if (fluidState.`is`(Fluids.PURE_MANA.get())) return true
    val fluidStateAbove = level.getFluidState(posAbove)
    if (fluidStateAbove.`is`(Fluids.PURE_MANA.get())) return true
    val fluidStateBelow = level.getFluidState(posBelow)
    return fluidStateBelow.`is`(Fluids.PURE_MANA.get())
  }

  @JvmStatic
  fun isSlimeEntity(level : Level?, entity : LivingEntity) : Boolean {
    if (StringUtils.containsIgnoreCase(entity.type.descriptionId, "slime")) return true
    return StringUtils.containsIgnoreCase(entity.type.descriptionId, "magma")
  }
}