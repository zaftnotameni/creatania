package zaftnotameni.creatania.mana.manablock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.effects.VanillaEffectConfiguration;

public class CorruptManaBlock extends BaseManaBlock {
  public static final String NAME = "mana/blocks/corrupt";
  public static final VanillaEffectConfiguration witherEffect = new VanillaEffectConfiguration(
    MobEffects.WITHER,
    CommonConfig.CORRUPTED_MANA_BLOCK_BUFF_WITHER_DURATION.get(),
    CommonConfig.CORRUPTED_MANA_BLOCK_BUFF_WITHER.get(),
    CommonConfig.CORRUPTED_MANA_BLOCK_BUFF_DISABLE_WHEN_SNEAKING.get());
  public static final VanillaEffectConfiguration harmEffect = new VanillaEffectConfiguration(
    MobEffects.HARM,
    CommonConfig.CORRUPTED_MANA_BLOCK_BUFF_HARM_DURATION.get(),
    CommonConfig.CORRUPTED_MANA_BLOCK_BUFF_HARM.get(),
    CommonConfig.CORRUPTED_MANA_BLOCK_BUFF_DISABLE_WHEN_SNEAKING.get());
  public static final VanillaEffectConfiguration poisonEffect = new VanillaEffectConfiguration(
    MobEffects.POISON,
    CommonConfig.CORRUPTED_MANA_BLOCK_BUFF_POISON_DURATION.get(),
    CommonConfig.CORRUPTED_MANA_BLOCK_BUFF_POISON.get(),
    CommonConfig.CORRUPTED_MANA_BLOCK_BUFF_DISABLE_WHEN_SNEAKING.get());
  public static final VanillaEffectConfiguration hungerEffect = new VanillaEffectConfiguration(
    MobEffects.HUNGER,
    CommonConfig.CORRUPTED_MANA_BLOCK_BUFF_HUNGER_DURATION.get(),
    CommonConfig.CORRUPTED_MANA_BLOCK_BUFF_HUNGER.get(),
    CommonConfig.CORRUPTED_MANA_BLOCK_BUFF_DISABLE_WHEN_SNEAKING.get());
  public CorruptManaBlock(Properties pProperties) {
    super(pProperties);
  }

  public void unlockAchievementDoingGodsWork(BlockPos position, Level level, LivingEntity entity) {
    if (!ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).getPath().equalsIgnoreCase("wandering_trader")) return;
    // TODO: ScanArea.forEachPlayerInTheArea(level, position, 128, p -> true, Advancements.DOING_THE_LORDS_WORK::awardTo);
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
    if (!(livingEntity instanceof ServerPlayer player)) return;
    // TODO: Advancements.DEBUFF_FROM_INERT_MANA_BLOCKS.awardTo(player);
  }
}
