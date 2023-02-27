package zaftnotameni.creatania.registry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.effects.FlightEffect;
public class CreataniaPotions {

  public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Constants.MODID);
  public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, Constants.MODID);

  public static final RegistryObject<MobEffect> FLIGHT_EFFECT = EFFECTS.register("creatania_flight", FlightEffect::new);

  public static void addRecipes() {
  }

  public static void register(IEventBus bus) {
    EFFECTS.register(bus);
    POTIONS.register(bus);
  }
}
