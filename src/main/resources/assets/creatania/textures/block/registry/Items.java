package zaftnotameni.creatania.registry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyItem;
import com.simibubi.create.foundation.data.CreateRegistrate;
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

import static zaftnotameni.creatania.util.Humanity.digestResource;
import static zaftnotameni.creatania.util.Humanity.keyResource;

public class Items {
  public static final DeferredRegister<Item> INDEX = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MODID);

  public static final CreateRegistrate CREATE_REGISTRATE = Index.getCreateRegistrate().creativeModeTab(() -> CreativeModeTabs.CREATANIA_ITEMS);

  public static final RegistryObject<ManaGelItem> MANA_GEL = INDEX.register(Constants.MANA_GEL_ITEM_NAME, () -> new ManaGelItem());

  public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_MANA_MACHINE_COMPONENT = sequencedIngredient(Constants.INCOMPLETE_MANA_MACHINE_COMPONENT);

  public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_XOR_LEVER = sequencedIngredient(Constants.INCOMPLETE_XOR_LEVER);

  public static void register(IEventBus bus) {
    Log.LOGGER.debug("register items");
    INDEX.register(bus);
  }
  private static ItemEntry<SequencedAssemblyItem> sequencedIngredient(String name) {
    return CREATE_REGISTRATE.item(name, SequencedAssemblyItem::new).register();
  }
  public static JsonElement provideLangEntries() {
    var json = new JsonObject();
    INDEX.getEntries().forEach(entry -> json.addProperty(Humanity.keyItem(entry), Humanity.digestItem(entry)));
    Index.all().getAll(Item.class).forEach(entry -> json.addProperty("item." + keyResource(entry.getId()), digestResource(entry.getId())));
    return json;
  }
}
