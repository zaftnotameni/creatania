package zaftnotameni.creatania.registry;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.resources.ResourceLocation;
import zaftnotameni.creatania.Constants;
public class Index {
  public static final CreateRegistrate CREATE_REGISTRATE = CreateRegistrate.create(Constants.MODID).creativeModeTab(
    () -> CreativeModeTabs.CREATANIA_ITEMS);

  public static ResourceLocation resource(String path) { return new ResourceLocation(Constants.MODID, path); }

  public static CreateRegistrate getCreateRegistrate() { return CREATE_REGISTRATE; }
}
