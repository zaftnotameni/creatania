package zaftnotameni.creatania.util;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;
public class Voxel {
  public static final VoxelShape FULL_BLOCK_VOXEL =  Block.box(0, 0, 0, 16, 16, 16);
  public static final VoxelShape HALF_BLOCK_VOXEL =  Block.box(0, 0, 0, 16, 8, 16);
}
