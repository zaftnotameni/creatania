package zaftnotameni.creatania.util

import com.simibubi.create.AllShapes
import net.minecraft.world.level.block.Block
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

object Voxel {
  @JvmField
  val FULL_BLOCK_VOXEL = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
  @JvmField
  val ALMOST_FULL_BLOCK_VOXEL = Block.box(2.0, 2.0, 2.0, 14.0, 14.0, 14.0)
  val HALF_BLOCK_VOXEL = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0)
  @JvmField
  val TINY_BLOCK_VOXEL = Block.box(2.0, 1.0, 2.0, 14.0, 2.0, 14.0)
  @JvmField
  val ALMOST_NONE_VOXEL = Block.box(7.0, 1.0, 7.0, 8.0, 2.0, 8.0)
  fun makeManaMotorShape() : VoxelShape {
    return AllShapes.Builder(Shapes.create(1.0, 1.0, 1.0, 15.0, 16.0, 15.0))
      .erase(2.0, 2.0, 2.0, 14.0, 16.0, 14.0).build()
  }
}