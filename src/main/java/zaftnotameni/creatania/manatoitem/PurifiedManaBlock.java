package zaftnotameni.creatania.manatoitem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.effects.VanillaEffectConfiguration;
import zaftnotameni.creatania.registry.Advancements;
import zaftnotameni.creatania.registry.Items;
import zaftnotameni.creatania.util.ScanArea;
public class PurifiedManaBlock extends Block {
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

  public PurifiedManaBlock(Properties pProperties) {
    super(pProperties);
  }

  public void slimeConversion(Level level, LivingEntity entity) {
    if (level.isClientSide()) return;
    entity.is(EntityType.SLIME.create(level));
    var posAbove = entity.getOnPos().above();
    ScanArea.forEachPlayerInTheArea(level, entity.getOnPos(), 128, p -> true, Advancements.PRODUCE_MANA_GEL_FROM_SLIME::awardTo);
    var stack = new ItemStack(Items.MANA_GEL.get(),1);
    entity.kill();
    var itemEntity = new ItemEntity(level, posAbove.getX(), posAbove.getY(), posAbove.getZ(), stack);
    itemEntity.setDeltaMovement(0f, 1f, 0f);
    level.addFreshEntity(itemEntity);
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
    Advancements.BUFF_FROM_INERT_MANA_BLOCKS.awardTo(player);
  }
}
