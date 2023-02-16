package zaftnotameni.creatania.manatoitem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.effects.VanillaEffectConfiguration;
public class CorruptedManaBlock extends Block {
  public static final VanillaEffectConfiguration witherEffect = new VanillaEffectConfiguration(
    MobEffects.WITHER,
    CommonConfig.CORRUPTED_MANA_BLOCK_BUFF_WITHER_DURATION.get(),
    CommonConfig.CORRUPTED_MANA_BLOCK_BUFF_WITHER.get());
  public static final VanillaEffectConfiguration harmEffect = new VanillaEffectConfiguration(
    MobEffects.HARM,
    CommonConfig.CORRUPTED_MANA_BLOCK_BUFF_HARM_DURATION.get(),
    CommonConfig.CORRUPTED_MANA_BLOCK_BUFF_HARM.get());
  public static final VanillaEffectConfiguration poisonEffect = new VanillaEffectConfiguration(
    MobEffects.POISON,
    CommonConfig.CORRUPTED_MANA_BLOCK_BUFF_POISON_DURATION.get(),
    CommonConfig.CORRUPTED_MANA_BLOCK_BUFF_POISON.get());
  public static final VanillaEffectConfiguration hungerEffect = new VanillaEffectConfiguration(
    MobEffects.HUNGER,
    CommonConfig.CORRUPTED_MANA_BLOCK_BUFF_HUNGER_DURATION.get(),
    CommonConfig.CORRUPTED_MANA_BLOCK_BUFF_HUNGER.get());
  public CorruptedManaBlock(Properties pProperties) {
    super(pProperties);
  }

  @Override
  public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
    if (pLevel == null || pPos == null || pState == null || pEntity == null) {
      super.stepOn(pLevel, pPos, pState, pEntity);
      return;
    }
    harmEffect.applyTo(pLevel, pEntity);
    hungerEffect.applyTo(pLevel, pEntity);
    poisonEffect.applyTo(pLevel, pEntity);
    witherEffect.applyTo(pLevel, pEntity);
    super.stepOn(pLevel, pPos, pState, pEntity);
  }
}
