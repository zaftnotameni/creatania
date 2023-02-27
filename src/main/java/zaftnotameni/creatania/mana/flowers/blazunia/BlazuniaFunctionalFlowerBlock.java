package zaftnotameni.creatania.mana.flowers.blazunia;

import com.simibubi.create.foundation.block.ITE;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import zaftnotameni.creatania.registry.CreataniaBlockEntities;

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
    return CreataniaBlockEntities.BLAZUNIA_BLOCK_ENTITY.get();
  }
}