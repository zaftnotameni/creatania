package zaftnotameni.creatania.registry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.food.ManaGelItem;
import zaftnotameni.creatania.util.Humanity;
import zaftnotameni.creatania.util.Log;

import static zaftnotameni.creatania.util.Humanity.keyResource;
import static zaftnotameni.creatania.util.Humanity.lang;

public class Items {
  public static final DeferredRegister<Item> INDEX = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MODID);

  public static final RegistryObject<ManaGelItem> MANA_GEL = INDEX.register(Constants.MANA_GEL_ITEM_NAME, () -> new ManaGelItem());

  public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_MANA_MACHINE_COMPONENT = sequencedIngredient(Constants.INCOMPLETE_MANA_MACHINE_COMPONENT);

  public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_XOR_LEVER = sequencedIngredient(Constants.INCOMPLETE_XOR_LEVER);

  public static void register(IEventBus bus) {
    Log.LOGGER.debug("register items");
    INDEX.register(bus);
  }
  private static ItemEntry<SequencedAssemblyItem> sequencedIngredient(String name) {
    return Index.all().item(name, SequencedAssemblyItem::new).register();
  }
  public static JsonElement provideLangEntries() {
    var json = new JsonObject();
    INDEX.getEntries().forEach(entry -> json.addProperty(Humanity.keyItem(entry), Humanity.digestItem(entry)));
    Index.all().getAll(Item.class).forEach(
      entry -> json.addProperty("item." + keyResource(entry.getId()),
        Humanity.slashes(lang.get().getAutomaticName (entry))));
    return json;
  }
}
