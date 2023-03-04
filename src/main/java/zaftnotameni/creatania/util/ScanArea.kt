package zaftnotameni.creatania.util

import com.google.common.collect.Lists
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import java.util.function.Consumer
import java.util.function.Function

object ScanArea {
  fun getNearbyPlayers(level : Level, pArea : AABB, fn : Function<Player?, Boolean>) : List<Player> {
    val list : MutableList<Player> = Lists.newArrayList()
    for (player in level.players()) {
      if (pArea.contains(player.x, player.y, player.z) && fn.apply(player)) list.add(player)
    }
    return list
  }

  fun areaAround(position : BlockPos, radius : Int) : AABB {
    return AABB.ofSize(Vec3(position.x.toDouble(), position.y.toDouble(), position.z.toDouble()), radius.toDouble(), radius.toDouble(), radius.toDouble())
  }

  @JvmStatic
  fun forEachPlayerInTheArea(level : Level, position : BlockPos, radius : Int, fn : Function<Player?, Boolean>, action : Consumer<Player?>) {
    val players = getNearbyPlayers(level, areaAround(position, 128), fn)
    for (player in players) action.accept(player)
  }
}