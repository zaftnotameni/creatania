package zaftnotameni.creatania.util

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import zaftnotameni.creatania.registry.Advancements
import zaftnotameni.creatania.registry.Items
import zaftnotameni.creatania.util.Queries.isSlimeEntity
import zaftnotameni.creatania.util.ScanArea.forEachPlayerInTheArea

object Actions {
  @JvmStatic
  fun killSlimeProduceManagelGrantAchievement(level : Level, entity : LivingEntity, pos : BlockPos) {
    if (level.isClientSide()) return
    if (!isSlimeEntity(level, entity)) return
    forEachPlayerInTheArea(level, pos, 128, { p : Player? -> true }) { player : Player? -> Advancements.PRODUCE_MANA_GEL_FROM_SLIME.awardTo(player) }
    val stack = ItemStack(Items.MANA_GEL.get(), 1)
    entity.kill()
    val itemEntity = ItemEntity(level, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), stack)
    itemEntity.setDeltaMovement(0.0, 0.5, 0.0)
    level.addFreshEntity(itemEntity)
  }
}