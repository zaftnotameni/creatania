package zaftnotameni.creatania.network;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
public interface IAmProxy {
  void init();
  Level getClientWorld();
  Minecraft getMinecraft();
  Player getPlayer();
}
