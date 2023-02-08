package zaftnotameni.creatania.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import zaftnotameni.creatania.Constants;

public class ModItems {
  public static final DeferredRegister<Item> ITEMS =
          DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MODID);

  public static void register(IEventBus bus) { ITEMS.register(bus); }
}
