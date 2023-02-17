package zaftnotameni.creatania.util;
import com.simibubi.create.AllShapes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
public class Voxel {
  public static final VoxelShape FULL_BLOCK_VOXEL =  Block.box(0, 0, 0, 16, 16, 16);
  public static final VoxelShape ALMOST_FULL_BLOCK_VOXEL =  Block.box(2, 2, 2, 14, 14, 14);
  public static final VoxelShape HALF_BLOCK_VOXEL =  Block.box(0, 0, 0, 16, 8, 16);
  public static final VoxelShape TINY_BLOCK_VOXEL =  Block.box(2, 1, 2, 14, 2, 14);
  public static final VoxelShape ALMOST_NONE_VOXEL =  Block.box(7, 1, 7, 8, 2, 8);
  public static VoxelShape makeManaMotorShape() {
    return new AllShapes.Builder(Shapes.create(1, 1, 1, 15, 16, 15))
      .erase(2, 2, 2, 14, 16, 14).build();
  }
}
