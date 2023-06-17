package zaftnotameni.creatania.registry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.simibubi.create.foundation.data.AssetLookup;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.food.ManaGelItem;

import static zaftnotameni.creatania.util.LogKt.log;

public class Items {
  public static final DeferredRegister<Item> INDEX = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MODID);

  public static final ItemEntry<ManaGelItem>  MANA_GEL = Index.all().item(Constants.MANA_GEL_ITEM_NAME, ManaGelItem::new)
    .properties(ManaGelItem::propertiesOfManaGel)
    .lang("Mana Gel")
    .model(AssetLookup.existingItemModel())
    .register();

  public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_MANA_MACHINE_COMPONENT = sequencedIngredient(Constants.INCOMPLETE_MANA_MACHINE_COMPONENT);

  public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_XOR_LEVER = sequencedIngredient(Constants.INCOMPLETE_XOR_LEVER);

  public static void register(IEventBus bus) {
    log(l -> l.debug("register items"));
    INDEX.register(bus);
  }
  private static ItemEntry<SequencedAssemblyItem> sequencedIngredient(String name) {
    return Index.all().item(name, SequencedAssemblyItem::new).register();
  }
  public static JsonElement provideLangEntries() {
    var json = new JsonObject();
    return json;
  }
}