package zaftnotameni.creatania.mana.flowers.blazunia;

import com.simibubi.create.foundation.block.IBE;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import zaftnotameni.creatania.registry.BlockEntities;

import static zaftnotameni.creatania.mana.flowers.blazunia.BlazuniaBlockStates.HAS_MANA_SOURCE;
import static zaftnotameni.creatania.mana.flowers.blazunia.BlazuniaBlockStates.IS_SUPERHOT;

public class BlazuniaFunctionalFlowerBlock extends FlowerBlock implements IBE<BlazuniaFunctionalFlowerBlockEntity> {

  public BlazuniaFunctionalFlowerBlock(Properties pProperties) {
    this(() -> MobEffects.GLOWING, 0, pProperties);
  }
  public BlazuniaFunctionalFlowerBlock() {
    this(Properties.copy(Blocks.DANDELION));
  }
  public BlazuniaFunctionalFlowerBlock(Supplier<MobEffect> effectSupplier, int pEffectDuration, Properties pProperties) {
    super(effectSupplier, pEffectDuration, pProperties);
    registerDefaultState(defaultBlockState().setValue(HAS_MANA_SOURCE, false).setValue(IS_SUPERHOT, false));
  }

  @Override public OffsetType getOffsetType() {
    return OffsetType.NONE;
  }

  @Override
  public Class<BlazuniaFunctionalFlowerBlockEntity> getBlockEntityClass() {
    return BlazuniaFunctionalFlowerBlockEntity.class;
  }
  @Override
  public BlockEntityType<? extends BlazuniaFunctionalFlowerBlockEntity> getBlockEntityType() {
    return BlockEntities.BLAZUNIA_BLOCK_ENTITY.get();
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
    pBuilder.add(HAS_MANA_SOURCE);
    pBuilder.add(IS_SUPERHOT);
  }

  @Override @OnlyIn(Dist.CLIENT)
  public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
    var superhot = state.getOptionalValue(IS_SUPERHOT).orElse(false);
    var hasManaSource = state.getOptionalValue(HAS_MANA_SOURCE).orElse(false);
    if (!hasManaSource) return;

    if (random.nextInt(8) == 0) world.addAlwaysVisibleParticle(
      ParticleTypes.LARGE_SMOKE, true,
      (double) pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1),
      (double) pos.getY() + random.nextDouble() + random.nextDouble(),
      (double) pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1), 0.0D,
      0.07D, 0.0D);

    if (random.nextInt(10) == 0) {
      world.playLocalSound((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F),
        (double) ((float) pos.getZ() + 0.5F), SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS,
        0.25F + random.nextFloat() * .25f, random.nextFloat() * 0.7F + 0.6F, false);
    }

    if (superhot) {
      if (random.nextInt(8) == 0) {
        world.addParticle(ParticleTypes.SOUL,
          pos.getX() + 0.5F + random.nextDouble() / 4 * (random.nextBoolean() ? 1 : -1),
          pos.getY() + 0.3F + random.nextDouble() / 2,
          pos.getZ() + 0.5F + random.nextDouble() / 4 * (random.nextBoolean() ? 1 : -1),
          0.0, random.nextDouble() * 0.04 + 0.04, 0.0);
      }
      return;
    }

    if (random.nextInt(5) == 0) {
      for (int i = 0; i < random.nextInt(1) + 1; ++i) {
        world.addParticle(ParticleTypes.LAVA, (double) ((float) pos.getX() + 0.5F),
          (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F),
          (double) (random.nextFloat() / 2.0F), 5.0E-5D, (double) (random.nextFloat() / 2.0F));
      }
    }
  }
}