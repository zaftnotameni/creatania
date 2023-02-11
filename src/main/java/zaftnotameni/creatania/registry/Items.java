package zaftnotameni.creatania.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.manatoitem.ManaGelItem;
import zaftnotameni.creatania.util.Log;

public class Items {
  public static final DeferredRegister<Item> INDEX = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MODID);

  public static final RegistryObject<ManaGelItem> MANA_GEL = INDEX.register(Constants.MANA_GEL_ITEM_NAME, () -> new ManaGelItem());

  public static void register(IEventBus bus) {
    Log.LOGGER.debug("register items");
    INDEX.register(bus);
  }
}
