package zaftnotameni.creatania.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.GlowParticle;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.fx.particle.ManaParticles;
import zaftnotameni.creatania.registry.Particles;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBus {

  @SubscribeEvent
  public static void registerParticleFactories(final ParticleFactoryRegisterEvent evt) {
    Minecraft.getInstance().particleEngine.register(Particles.MANA_PARTICLES.get(), GlowParticle.ElectricSparkProvider::new);
  }
}
