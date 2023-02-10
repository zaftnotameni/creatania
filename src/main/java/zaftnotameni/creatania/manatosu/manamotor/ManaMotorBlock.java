package zaftnotameni.creatania.manatosu.manamotor;

import com.simibubi.create.content.contraptions.base.DirectionalKineticBlock;
import com.simibubi.create.foundation.block.ITE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import zaftnotameni.creatania.registry.BlockEntities;

public class ManaMotorBlock extends DirectionalKineticBlock implements ITE<ManaMotorBlockEntity> {

  public ManaMotorBlock(Properties properties) {
    super(properties);
    registerDefaultState(defaultBlockState());
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    Direction preferred = getPreferredFacing(context);
    if ((context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) || preferred == null)
      return super.getStateForPlacement(context);
    return defaultBlockState().setValue(FACING, preferred);
  }

  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return BlockEntities.MANA_MOTOR_BLOCK_ENTITY.create(pos, state);
  }

  @Override
  public Class<ManaMotorBlockEntity> getTileEntityClass() {
    return ManaMotorBlockEntity.class;
  }
  @Override
  public BlockEntityType<? extends ManaMotorBlockEntity> getTileEntityType() {
    return BlockEntities.MANA_MOTOR_BLOCK_ENTITY.get();
  }

  @Override
  public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
    return face == state.getValue(FACING);
  }

  @Override
  public Direction.Axis getRotationAxis(BlockState state) {
    return state.getValue(FACING).getAxis();
  }

  @Override
  public boolean hideStressImpact() {
    return false;
  }

  public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> pServerType, BlockEntityType<E> pClientType, BlockEntityTicker<? super E> pTicker) {
    return pClientType == pServerType ? (BlockEntityTicker<A>)pTicker : null;
  }
  @Override
  public <S extends BlockEntity> BlockEntityTicker<S> getTicker(Level level, BlockState blockState, BlockEntityType<S> blockEntityType) {
    return createTickerHelper(blockEntityType, BlockEntities.MANA_MOTOR_BLOCK_ENTITY.get(), ManaMotorBlockEntity::tick);
  }

  private static final VoxelShape SHAPE =  Block.box(0, 0, 0, 16, 8, 16);
  @Override
  public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
    return SHAPE;
  }

  @Override
  public BlockState rotate(BlockState pState, Rotation pRotation) {
    return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
  }

  @Override
  public BlockState mirror(BlockState pState, Mirror pMirror) {
    return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
    pBuilder.add(FACING);
  }

  @Override
  public RenderShape getRenderShape(BlockState pState) {
    return RenderShape.MODEL;
  }
}