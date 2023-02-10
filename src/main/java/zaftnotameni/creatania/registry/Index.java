package zaftnotameni.creatania.registry;
import com.simibubi.create.foundation.data.CreateRegistrate;
import zaftnotameni.creatania.Constants;
public class Index {
  public static final CreateRegistrate CREATE_REGISTRATE = CreateRegistrate.create(Constants.MODID).creativeModeTab(
    () -> CreativeModeTabs.CREATANIA_ITEMS);

  public static CreateRegistrate getCreateRegistrate() { return CREATE_REGISTRATE; }
}
