package zaftnotameni.creatania.block.custom;

import com.simibubi.create.content.contraptions.base.DirectionalKineticBlock;
import com.simibubi.create.foundation.block.ITE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import zaftnotameni.creatania.block.entity.ModBlockEntities;
import zaftnotameni.creatania.block.entity.custom.ManaMotorBlockEntity;

import java.util.Random;

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
    return new ManaMotorBlockEntity(pos, state);
  }

  @Override
  public Class<ManaMotorBlockEntity> getTileEntityClass() {
    return ManaMotorBlockEntity.class;
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

  @Override
  public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
    return false;
  }

  @Override
  public BlockEntityType<? extends ManaMotorBlockEntity> getTileEntityType() {
    return ModBlockEntities.MANA_MOTOR_BLOCK_ENTITY.get();
  }

  @Override
  public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos from, boolean b) {
    super.neighborChanged(state, world, pos, block, from, b);
    if (!world.isClientSide) {
      world.scheduleTick(pos, this, 4);
    }
  }

  @Override
  public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
    super.tick(state, world, pos, random);
  }

  public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> pServerType, BlockEntityType<E> pClientType, BlockEntityTicker<? super E> pTicker) {
    return pClientType == pServerType ? (BlockEntityTicker<A>)pTicker : null;
  }
  @Override
  public <S extends BlockEntity> BlockEntityTicker<S> getTicker(Level level, BlockState blockState, BlockEntityType<S> blockEntityType) {
    return createTickerHelper(blockEntityType, ModBlockEntities.MANA_MOTOR_BLOCK_ENTITY.get(), ManaMotorBlockEntity::tick);
  }
}