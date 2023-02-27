package zaftnotameni.creatania.event.forgebus;

import net.minecraft.network.chat.Component;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import zaftnotameni.creatania.Constants;
@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OnRightClickBlock {

  @SubscribeEvent(receiveCanceled = true)
  public static void onRickClickBlock(PlayerInteractEvent.RightClickBlock event) {
    event.getEntity().sendSystemMessage(Component.literal("right clicked"));
  }
}
