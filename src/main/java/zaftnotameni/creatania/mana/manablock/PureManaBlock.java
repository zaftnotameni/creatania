package zaftnotameni.creatania.mana.manablock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.effects.VanillaEffectConfiguration;
public class PureManaBlock extends BaseManaBlock {
  public static final String NAME = "mana/blocks/pure";
  public static final VanillaEffectConfiguration absorptionEffect = new VanillaEffectConfiguration(
    MobEffects.ABSORPTION,
    CommonConfig.PURIFIED_MANA_BLOCK_BUFF_ABSORPTION_DURATION.get(),
    CommonConfig.PURIFIED_MANA_BLOCK_BUFF_ABSORPTION.get(),
    CommonConfig.PURIFIED_MANA_BLOCK_BUFF_DISABLE_WHEN_SNEAKING.get());
  public static final VanillaEffectConfiguration saturationEffect = new VanillaEffectConfiguration(
    MobEffects.SATURATION,
    CommonConfig.PURIFIED_MANA_BLOCK_BUFF_SATURATION_DURATION.get(),
    CommonConfig.PURIFIED_MANA_BLOCK_BUFF_SATURATION.get(),
    CommonConfig.PURIFIED_MANA_BLOCK_BUFF_DISABLE_WHEN_SNEAKING.get());
  public static final VanillaEffectConfiguration healEffect = new VanillaEffectConfiguration(
    MobEffects.HEAL,
    CommonConfig.PURIFIED_MANA_BLOCK_BUFF_HEAL_DURATION.get(),
    CommonConfig.PURIFIED_MANA_BLOCK_BUFF_HEAL.get(),
    CommonConfig.PURIFIED_MANA_BLOCK_BUFF_DISABLE_WHEN_SNEAKING.get());

  public PureManaBlock(Properties pProperties) {
    super(pProperties);
  }

  public void slimeConversion(Level level, LivingEntity entity) {
    // TODO: killSlimeProduceManagelGrantAchievement(level, entity, entity.getOnPos());
  }

  @Override
  public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
    if (pLevel == null || pPos == null || pState == null || pEntity == null || !(pEntity instanceof LivingEntity livingEntity)) {
      super.stepOn(pLevel, pPos, pState, pEntity);
      return;
    }
    slimeConversion(pLevel, livingEntity);
    unlockAchievementBuffingPlayer(pLevel, livingEntity);
    healEffect.applyTo(pLevel, pEntity);
    absorptionEffect.applyTo(pLevel, pEntity);
    saturationEffect.applyTo(pLevel, pEntity);
    super.stepOn(pLevel, pPos, pState, pEntity);
  }

  public void unlockAchievementBuffingPlayer(Level pLevel, LivingEntity livingEntity) {
    if (!(livingEntity instanceof ServerPlayer player)) return;
    // TODO: Advancements.BUFF_FROM_INERT_MANA_BLOCKS.awardTo(player);
  }
}
