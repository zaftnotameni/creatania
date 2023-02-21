package zaftnotameni.creatania.mana.flowers.blazunia;

import com.simibubi.create.foundation.block.ITE;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import zaftnotameni.creatania.registry.BlockEntities;

public class BlazuniaFunctionalFlowerBlock extends Block implements ITE<BlazuniaFunctionalFlowerBlockEntity> {

  public BlazuniaFunctionalFlowerBlock(Properties pProperties) {
    super(pProperties);
  }
  @Override
  public Class<BlazuniaFunctionalFlowerBlockEntity> getTileEntityClass() {
    return BlazuniaFunctionalFlowerBlockEntity.class;
  }
  @Override
  public BlockEntityType<? extends BlazuniaFunctionalFlowerBlockEntity> getTileEntityType() {
    return BlockEntities.BLAZUNIA_BLOCK_ENTITY.get();
  }

  @Override
  public <S extends BlockEntity> BlockEntityTicker<S> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<S> p_153214_) {
    return ITE.super.getTicker(p_153212_, p_153213_, p_153214_);
  }

}