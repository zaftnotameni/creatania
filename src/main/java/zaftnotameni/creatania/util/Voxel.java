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
  public static VoxelShape makeManaMotorShape() {
    return new AllShapes.Builder(Shapes.create(1, 1, 1, 15, 16, 15))
      .erase(2, 2, 2, 14, 16, 14).build();
  }
}
