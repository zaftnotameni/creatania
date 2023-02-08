package zaftnotameni.creatania.block.custom;

import com.simibubi.create.content.contraptions.base.DirectionalKineticBlock;
import com.simibubi.create.foundation.block.ITE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import zaftnotameni.creatania.block.entity.custom.ManaGeneratorBlockEntity;
import zaftnotameni.creatania.block.entity.ModBlockEntities;

public class ManaGeneratorBlock extends DirectionalKineticBlock implements ITE<ManaGeneratorBlockEntity> {
  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    Direction preferred = getPreferredFacing(context);
    if ((context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) || preferred == null)
      return super.getStateForPlacement(context);
    return defaultBlockState().setValue(FACING, preferred);
  }

  public ManaGeneratorBlock(Properties properties) {
    super(properties);
    registerDefaultState(defaultBlockState());
  }

  @Override
  public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
    return face == state.getValue(FACING);
  }

  @Override
  public Axis getRotationAxis(BlockState state) {
    return state.getValue(FACING).getAxis();
  }

  @Override
  public BlockEntityType<? extends ManaGeneratorBlockEntity> getTileEntityType() {
    return ModBlockEntities.MANA_GENERATOR_BLOCK_ENTITY.get();
  }

  @Override
  public Class<ManaGeneratorBlockEntity> getTileEntityClass() {
    return ManaGeneratorBlockEntity.class;
  }

  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new ManaGeneratorBlockEntity(pos, state);
  }

  @Override
  public SpeedLevel getMinimumRequiredSpeedLevel() {
    return SpeedLevel.MEDIUM;
  }

  @Override
  public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
    BlockEntity tileentity = state.hasBlockEntity() ? worldIn.getBlockEntity(pos) : null;
    if(tileentity != null) {
      if(tileentity instanceof ManaGeneratorBlockEntity) {
        ((ManaGeneratorBlockEntity)tileentity).updateCache();
      }
    }
  }
}

