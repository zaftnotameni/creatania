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
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.food.ManaGelItem;
import zaftnotameni.creatania.util.Log;


public class CreataniaItems {
  public static final DeferredRegister<Item> INDEX = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MODID);

  public static final ItemEntry<ManaGelItem> MANA_GEL = CreataniaIndex.all()
    .item(Constants.MANA_GEL_ITEM_NAME, (p) -> ManaGelItem.create(p))
    .register();
  public static final RegistryObject<ManaGelItem> MANA_GEL_ITEM = INDEX.register(
    "mana_gel", () -> ManaGelItem.create(new Item.Properties().stacksTo(
      CommonConfig.MANAGEL_STACKS_TO.get() // <------ gets called too soon
    )));

  public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_MANA_MACHINE_COMPONENT = sequencedIngredient(Constants.INCOMPLETE_MANA_MACHINE_COMPONENT);

  public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_XOR_LEVER = sequencedIngredient(Constants.INCOMPLETE_XOR_LEVER);

  public static void register(IEventBus bus) {
    Log.LOGGER.debug("register items");
    INDEX.register(bus);
  }
  private static ItemEntry<SequencedAssemblyItem> sequencedIngredient(String name) {
    return CreataniaIndex.all().item(name, SequencedAssemblyItem::new).register();
  }
  public static JsonElement provideLangEntries() {
    var json = new JsonObject();
//    INDEX.getEntries().forEach(entry -> json.addProperty(Humanity.keyItem(entry), Humanity.digestItem(entry)));
//    CreataniaIndex.all().getAll(ForgeRegistries.ITEMS.getRegistryKey()).forEach(
//      entry -> json.addProperty("item." + keyResource(entry.getId()),
//        Humanity.slashes(lang.get().getAutomaticName (entry, ForgeRegistries.ITEMS.getRegistryKey()))));
    return json;
  }
}
