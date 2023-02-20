package zaftnotameni.creatania.registry;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
public class CreataniaRegistrate extends AbstractRegistrate<CreataniaRegistrate> {
  public CreataniaRegistrate(String modid) {
    super(modid);
  }
  public static CreataniaRegistrate create(String id) { return new CreataniaRegistrate(id); }
  @Override
  public CreataniaRegistrate registerEventListeners(IEventBus bus) { return super.registerEventListeners(bus); }


  public static <I extends BlockItem> NonNullBiConsumer<DataGenContext<Item, I>, RegistrateItemModelProvider> sameAsBlockItemModel(
    String... folders) {
    return (c, p) -> {
      String path = "block";
      for (String string : folders)
        path += "/" + ("_".equals(string) ? c.getName() : string);
      p.withExistingParent("item/" + c.getName(), p.modLoc(path));
    };
  }
}
