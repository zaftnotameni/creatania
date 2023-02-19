package zaftnotameni.creatania.mana.manablock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import zaftnotameni.creatania.util.Voxel;
public abstract class BaseManaBlock  extends Block {
  public BaseManaBlock(Properties pProperties) {
    super(pProperties);
  }

  public static BlockBehaviour.Properties makeManablockProperties() {
    return BlockBehaviour.Properties.copy(Blocks.GLASS)
      .destroyTime(1f)
      .lightLevel(x -> 15)
      .hasPostProcess((x, y, z) -> true)
      .emissiveRendering((x, y, z) -> true)
      .noOcclusion()
      .isViewBlocking((x, y, z) -> false);
  }

  @Override
  public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
    return Voxel.FULL_BLOCK_VOXEL;
  }
}
