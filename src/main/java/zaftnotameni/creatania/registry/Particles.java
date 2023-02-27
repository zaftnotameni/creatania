package zaftnotameni.creatania.registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import zaftnotameni.creatania.Constants;
public class Particles {
  public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
    DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Constants.MODID);

//  public static final RegistryObject<SimpleParticleType> MANA_PARTICLES =
//    PARTICLE_TYPES.register(Constants.MANA_PARTICLES_NAME, () -> new SimpleParticleType(false));

  public static void register(IEventBus eventBus) {
    PARTICLE_TYPES.register(eventBus);
  }
}
