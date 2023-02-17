package zaftnotameni.creatania.manaiaccreate.omnibox;
import com.simibubi.create.content.contraptions.base.RotatedPillarKineticBlock;
import com.simibubi.create.foundation.block.ITE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;
import zaftnotameni.creatania.registry.BlockEntities;
public class OmniboxBlock extends RotatedPillarKineticBlock implements ITE<OmniboxBlockEntity> {
  public static final DirectionProperty FACING = BlockStateProperties.FACING;
  public OmniboxBlock(Properties properties) {
    super(properties);
  }
  @Override
  public PushReaction getPistonPushReaction(BlockState state) {
    return PushReaction.PUSH_ONLY;
  }
  @Nullable
  @Override
  public BlockState getStateForPlacement(BlockPlaceContext pContext) {
    if (pContext == null) return super.getStateForPlacement(pContext);
    var horizontalDirection = pContext.getHorizontalDirection();
    Direction facing;
    if (pContext.getPlayer() != null && pContext.getPlayer().isShiftKeyDown()) facing = horizontalDirection;
    else facing = horizontalDirection.getOpposite();

    return super.getStateForPlacement(pContext).setValue(FACING, facing).setValue(AXIS, facing.getAxis());
  }
  @Override
  public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
    return true;
  }
  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
    pBuilder.add(FACING).add(AXIS);
  }
  @Override
  public Direction.Axis getRotationAxis(BlockState state) {
    return state.getValue(AXIS);
  }
  @Override
  public Class<OmniboxBlockEntity> getTileEntityClass() {
    return OmniboxBlockEntity.class;
  }
  @Override
  public BlockEntityType<? extends OmniboxBlockEntity> getTileEntityType() {
    return BlockEntities.OMNIBOX_BLOCK_ENTITY.get();
  }
}
