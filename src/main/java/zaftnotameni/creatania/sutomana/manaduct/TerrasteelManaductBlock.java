package zaftnotameni.creatania.sutomana.manaduct;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import zaftnotameni.creatania.util.Voxel;

import java.util.function.Function;
public class TerrasteelManaductBlock extends Block {
  public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

  public TerrasteelManaductBlock(Properties pProperties) {
    super(pProperties);
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) { pBuilder.add(FACING); }

  @Nullable
  @Override
  public BlockState getStateForPlacement(BlockPlaceContext pContext) {
    if (pContext == null) return super.getStateForPlacement(pContext);
    var horizontalDirection = pContext.getHorizontalDirection();
    Direction facing;
    if (pContext.getPlayer() != null && pContext.getPlayer().isShiftKeyDown()) facing = horizontalDirection;
    else facing = horizontalDirection.getOpposite();
    return super.getStateForPlacement(pContext).setValue(FACING, facing);
  }
  @Override
  public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
    return Voxel.TINY_BLOCK_VOXEL;
  }
  @Override
  public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
    return Voxel.ALMOST_NONE_VOXEL;
  }
  @Override
  public int getLightBlock(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
    return 1;
  }
  @Override
  protected ImmutableMap<BlockState, VoxelShape> getShapeForEachState(Function<BlockState, VoxelShape> shapeFactory) {
    return super.getShapeForEachState(x -> Voxel.TINY_BLOCK_VOXEL);
  }
}
