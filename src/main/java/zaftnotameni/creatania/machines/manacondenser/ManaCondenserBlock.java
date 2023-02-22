package zaftnotameni.creatania.machines.manacondenser;

import com.simibubi.create.content.contraptions.base.DirectionalAxisKineticBlock;
import com.simibubi.create.foundation.block.ITE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import zaftnotameni.creatania.registry.BlockEntities;
import zaftnotameni.creatania.sharedbehaviors.KineticManaMachine;

import static zaftnotameni.creatania.util.Voxel.ALMOST_FULL_BLOCK_VOXEL;
import static zaftnotameni.creatania.util.Voxel.FULL_BLOCK_VOXEL;

public class ManaCondenserBlock extends DirectionalAxisKineticBlock implements ITE<ManaCondenserBlockEntity> {
  public ManaCondenserBlock(Properties properties) { super(properties);  registerDefaultState(defaultBlockState()); }
  @Override
  public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) { return KineticManaMachine.hasShaftTowards(state, face); }
  @Override
  protected Direction getFacingForPlacement(BlockPlaceContext context) { return KineticManaMachine.getFacingForPlacement(context); }
  @Override
  public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) { return ALMOST_FULL_BLOCK_VOXEL; }
  @Override
  public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) { return FULL_BLOCK_VOXEL; }
  @Override
  public Axis getRotationAxis(BlockState state) {
    return state.getValue(FACING).getAxis();
  }
  @Override
  public BlockEntityType<? extends ManaCondenserBlockEntity> getTileEntityType() { return BlockEntities.MANA_CONDENSER_BLOCK_ENTITY.get();  }
  @Override
  public BlockState rotate(BlockState pState, Rotation pRotation) { return KineticManaMachine.rotate(pState, pRotation); }
  @Override
  public Class<ManaCondenserBlockEntity> getTileEntityClass() {
    return ManaCondenserBlockEntity.class;
  }
  @Override
  public BlockState getRotatedBlockState(BlockState originalState, Direction targetedFace) { return KineticManaMachine.rotate(originalState, targetedFace); }
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) { return new ManaCondenserBlockEntity(getTileEntityType(), pos, state); }
  @Override
  public SpeedLevel getMinimumRequiredSpeedLevel() {
    return SpeedLevel.NONE;
  }
}

