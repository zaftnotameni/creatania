package zaftnotameni.creatania.stress.omnibox;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.foundation.block.IBE;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zaftnotameni.creatania.registry.BlockEntities;
public class OmniboxBlock extends RotatedPillarKineticBlock implements IBE<OmniboxBlockEntity> {
  public static final DirectionProperty FACING = BlockStateProperties.FACING;
  public OmniboxBlock(Properties properties) {
    super(properties);
  }
  @Override
  public @NotNull PushReaction getPistonPushReaction(@NotNull BlockState state) {
    return PushReaction.PUSH_ONLY;
  }
  @Nullable
  @Override
  public BlockState getStateForPlacement(BlockPlaceContext pContext) {
    return super.getStateForPlacement(pContext);
    // .setValue(FACING, Direction.SOUTH).setValue(AXIS, Direction.Axis.Z);
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
  public Class<OmniboxBlockEntity> getBlockEntityClass() {
    return OmniboxBlockEntity.class;
  }
  @Override
  public BlockEntityType<? extends OmniboxBlockEntity> getBlockEntityType() {
    return BlockEntities.OMNIBOX_BLOCK_ENTITY.get();
  }
}