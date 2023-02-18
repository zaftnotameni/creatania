package zaftnotameni.creatania.network;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import zaftnotameni.creatania.setup.StaticInit;

import java.util.function.Supplier;

public class PacketUpdateFlight {
  public boolean canFly;
  public boolean wasFlying;
  public PacketUpdateFlight(FriendlyByteBuf buf) {
    canFly = buf.readBoolean();
    wasFlying = buf.readBoolean();
  }
  public void toBytes(FriendlyByteBuf buf) {
    buf.writeBoolean(canFly);
    buf.writeBoolean(wasFlying);
  }
  public PacketUpdateFlight(boolean canFly) {
    this.canFly = canFly;
  }
  public PacketUpdateFlight(boolean canFly, boolean wasFlying) {
    this.canFly = canFly;
    this.wasFlying = wasFlying;
  }
  public void handle(Supplier<NetworkEvent.Context> ctx) {
    ctx.get().enqueueWork(() -> {
      StaticInit.proxy.getPlayer().getAbilities().mayfly = canFly;
      StaticInit.proxy.getPlayer().getAbilities().flying = wasFlying;
    });
    ctx.get().setPacketHandled(true);
  }
}

