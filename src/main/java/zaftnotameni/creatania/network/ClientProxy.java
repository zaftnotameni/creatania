package zaftnotameni.creatania.network;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
public class ClientProxy implements IAmProxy {

  @Override
  public void init() {
  }

  @Override
  public Level getClientWorld() {
    return Minecraft.getInstance().level;
  }

  @Override
  public Player getPlayer() {
    return Minecraft.getInstance().player;
  }

  @Override
  public Minecraft getMinecraft() {
    return Minecraft.getInstance();
  }

}
