package zaftnotameni.creatania.mana.flowers.blazunia;

import com.simibubi.create.foundation.block.ITE;
import java.util.function.Supplier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import zaftnotameni.creatania.registry.BlockEntities;

public class BlazuniaFunctionalFlowerBlock extends FlowerBlock implements ITE<BlazuniaFunctionalFlowerBlockEntity> {

  public BlazuniaFunctionalFlowerBlock(Properties pProperties) {
    this(() -> MobEffects.GLOWING, 0, pProperties);
  }
  public BlazuniaFunctionalFlowerBlock() {
    this(Properties.copy(Blocks.DANDELION));
  }
  public BlazuniaFunctionalFlowerBlock(Supplier<MobEffect> effectSupplier, int pEffectDuration, Properties pProperties) {
    super(effectSupplier, pEffectDuration, pProperties);
  }



  @Override
  public Class<BlazuniaFunctionalFlowerBlockEntity> getTileEntityClass() {
    return BlazuniaFunctionalFlowerBlockEntity.class;
  }
  @Override
  public BlockEntityType<? extends BlazuniaFunctionalFlowerBlockEntity> getTileEntityType() {
    return BlockEntities.BLAZUNIA_BLOCK_ENTITY.get();
  }
}