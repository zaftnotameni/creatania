package zaftnotameni.creatania.sutomana.managenerator;

import com.simibubi.create.content.contraptions.base.DirectionalKineticBlock;
import com.simibubi.create.foundation.block.ITE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import zaftnotameni.creatania.registry.BlockEntities;

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
    return BlockEntities.MANA_GENERATOR_BLOCK_ENTITY.get();
  }

  @Override
  public Class<ManaGeneratorBlockEntity> getTileEntityClass() {
    return ManaGeneratorBlockEntity.class;
  }

  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new ManaGeneratorBlockEntity(getTileEntityType(), pos, state);
  }

  @Override
  public SpeedLevel getMinimumRequiredSpeedLevel() {
    return SpeedLevel.NONE;
  }
}

