package zaftnotameni.creatania.mana.manablock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.effects.VanillaEffectConfiguration;
import zaftnotameni.creatania.network.Networking;
import zaftnotameni.creatania.network.PacketUpdateFlight;
import zaftnotameni.creatania.registry.Advancements;
import zaftnotameni.creatania.registry.Potions;
public class RealManaBlock extends BaseManaBlock {
  public static final String NAME = "mana/blocks/real";
  public static VanillaEffectConfiguration flightEffect;

  public static VanillaEffectConfiguration getFlightEffect() {
    if (flightEffect != null) return flightEffect;
    flightEffect = new VanillaEffectConfiguration(
      Potions.FLIGHT_EFFECT.get(),
      CommonConfig.BOTANIA_MANA_BLOCK_BUFF_FLIGHT_DURATION.get(),
      CommonConfig.BOTANIA_MANA_BLOCK_BUFF_FLIGHT.get(),
      CommonConfig.BOTANIA_MANA_BLOCK_BUFF_DISABLE_WHEN_SNEAKING.get());
    return flightEffect;
  }
  public RealManaBlock(Properties pProperties) {
    super(pProperties);
  }
  @Override
  public void stepOn(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, @NotNull Entity pEntity) {
    if (pLevel.isClientSide || !(pEntity instanceof ServerPlayer player) || !getFlightEffect().canApplyTo(pLevel, pEntity)) {
      super.stepOn(pLevel, pPos, pState, pEntity);
      return;
    }
    unlockAchievementBuffingPlayer(pLevel, player);
    var wasFlying = player.getAbilities().flying;
    player.addEffect(getFlightEffect().createInstance());
    player.getAbilities().mayfly = true;
    player.getAbilities().flying = wasFlying;
    Networking.sendToPlayer(new PacketUpdateFlight(true, wasFlying), player);
    super.stepOn(pLevel, pPos, pState, pEntity);
  }
  public void unlockAchievementBuffingPlayer(Level pLevel, LivingEntity livingEntity) {
    if (!(livingEntity instanceof ServerPlayer player)) return;
    Advancements.BUFF_FROM_REAL_MANA_BLOCKS.awardTo(player);
  }
}