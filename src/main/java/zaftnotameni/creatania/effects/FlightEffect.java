package zaftnotameni.creatania.effects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import zaftnotameni.creatania.network.Networking;
import zaftnotameni.creatania.network.PacketUpdateFlight;
import zaftnotameni.creatania.registry.CreataniaPotions;
public class FlightEffect extends MobEffect {
  public FlightEffect() {
    super(MobEffectCategory.BENEFICIAL, 2039587);
  }
  @Override
  public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
    return true;
  }
  @Override
  public void applyEffectTick(LivingEntity entity, int p_76394_2_) {
    if (entity instanceof Player player) {
      player.getAbilities().mayfly = (player.isCreative() || entity.isSpectator()) || entity.getEffect(CreataniaPotions.FLIGHT_EFFECT.get()).getDuration() > 2;
    }
  }

  @Override
  public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
    super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    if (pLivingEntity instanceof ServerPlayer player) {
      boolean canFly = player.isCreative() || player.isSpectator();
      player.getAbilities().mayfly = canFly;
      player.getAbilities().flying = canFly;
      Networking.sendToPlayer(new PacketUpdateFlight(canFly, canFly), player);
    }
  }
}