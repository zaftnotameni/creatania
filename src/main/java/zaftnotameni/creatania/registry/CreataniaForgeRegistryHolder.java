package zaftnotameni.creatania.registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

import java.util.Collection;
public abstract class CreataniaForgeRegistryHolder<T extends DeferredRegister> {
  public IEventBus bus;
  public abstract Collection<T> getDeferredRegistries();
  public void register(IEventBus bus) {
    this.bus = bus;
    if (getDeferredRegistries() == null) return;
    for (var r : getDeferredRegistries()) bus.register(r);
  }
}
