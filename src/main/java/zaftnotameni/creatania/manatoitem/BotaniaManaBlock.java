package zaftnotameni.creatania.manatoitem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.effects.VanillaEffectConfiguration;
import zaftnotameni.creatania.network.Networking;
import zaftnotameni.creatania.network.PacketUpdateFlight;
import zaftnotameni.creatania.registry.Potions;
public class BotaniaManaBlock extends Block {
  public static final VanillaEffectConfiguration flightEffect = new VanillaEffectConfiguration(
    Potions.FLIGHT_EFFECT.get(),
    CommonConfig.BOTANIA_MANA_BLOCK_BUFF_FLIGHT_DURATION.get(),
    CommonConfig.BOTANIA_MANA_BLOCK_BUFF_FLIGHT.get()
  );
  public BotaniaManaBlock(Properties pProperties) {
    super(pProperties);
  }
  @Override
  public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
    if (pLevel == null || pLevel.isClientSide || !(pEntity instanceof ServerPlayer player) || !flightEffect.canApplyTo(pLevel, pEntity)) {
      super.stepOn(pLevel, pPos, pState, pEntity);
      return;
    }
    var wasFlying = player.getAbilities().flying;
    player.addEffect(flightEffect.createInstance());
    player.getAbilities().mayfly = true;
    player.getAbilities().flying = wasFlying;
    Networking.sendToPlayer(new PacketUpdateFlight(true, wasFlying), player);
    super.stepOn(pLevel, pPos, pState, pEntity);
  }
}
