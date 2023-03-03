package zaftnotameni.creatania.ponder

import net.minecraft.core.Direction
import net.minecraft.world.level.block.Blocks
import zaftnotameni.creatania.ponder.CreataniaPonderUtils.XYZ
import kotlin.math.abs

fun checkerboard(u : CreataniaPonderUtils, start : XYZ, delta : XYZ) {
  for (x in 0..abs(delta.x))
    for (y in 0..abs(delta.y))
      for (z in 0..abs(delta.z)) {
        val even = (x + z + y) % 2 == 0
        val block = if (even) Blocks.BLACK_CONCRETE else Blocks.GRAY_CONCRETE
        val v : XYZ = u.at(start.x + x, start.y + y, start.z + z)
        u.scene.world.setBlock(v.asBlockPos(), block.defaultBlockState(), false)
      }

  val end = u.at(start.x + delta.x + 1, start.y + delta.y + 1, start.z + delta.z + 1)
  val selection = u.util.select.fromTo(start.asBlockPos(), end.asBlockPos())
  u.scene.world.showSection(selection, Direction.UP)
  u.scene.idle(20)
}