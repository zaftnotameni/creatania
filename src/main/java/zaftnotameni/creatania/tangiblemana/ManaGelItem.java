package zaftnotameni.creatania.tangiblemana;
import net.minecraft.world.item.Item;
import zaftnotameni.creatania.registry.CreativeModeTabs;
public class ManaGelItem extends Item  {
  public ManaGelItem() {
    super(new Item.Properties().tab(CreativeModeTabs.CREATANIA_ITEMS)
      .stacksTo(16));
  }
}
