package zaftnotameni.creatania.manatoitem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.effects.VanillaEffectConfiguration;
import zaftnotameni.creatania.registry.Advancements;
import zaftnotameni.creatania.registry.datagen.ForgeAdvancementsProvider;
import zaftnotameni.creatania.util.ScanArea;
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

  public void unlockAchievementDoingGodsWork(BlockPos position, Level level, LivingEntity entity) {
    if (!entity.getType().getRegistryName().getPath().equalsIgnoreCase("wandering_trader")) return;
    ScanArea.forEachPlayerInTheArea(level, position, 128, p -> true, Advancements.DOING_THE_LORDS_WORK::awardTo);
  }

  @Override
  public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
    super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
  }
  @Override
  public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
    if (pLevel == null || pPos == null || pState == null || pEntity == null || !(pEntity instanceof LivingEntity livingEntity)) {
      super.stepOn(pLevel, pPos, pState, pEntity);
      return;
    }
    unlockAchievementDoingGodsWork(pPos, pLevel, livingEntity);
    unlockAchievementCorruptionOfPlayer(pLevel, livingEntity);

    harmEffect.applyTo(pLevel, pEntity);
    hungerEffect.applyTo(pLevel, pEntity);
    poisonEffect.applyTo(pLevel, pEntity);
    witherEffect.applyTo(pLevel, pEntity);
    super.stepOn(pLevel, pPos, pState, pEntity);
  }
  public void unlockAchievementCorruptionOfPlayer(Level pLevel, LivingEntity livingEntity) {
  }
}
