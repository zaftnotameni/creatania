package zaftnotameni.creatania.registry;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.resources.ResourceLocation;
import zaftnotameni.creatania.Constants;

import static zaftnotameni.creatania.registry.CreativeModeTabs.creataniaCreativeTab;
public class Index {

  public static final CreateRegistrate CREATE_REGISTRATE = CreateRegistrate.create(Constants.MODID).creativeModeTab(creataniaCreativeTab);

  public static final CreataniaRegistrate CREATANIA_INDEX = CreataniaRegistrate.create(Constants.MODID).creativeModeTab(creataniaCreativeTab);;

  public static ResourceLocation resource(String path) { return new ResourceLocation(Constants.MODID, path); }

  public static CreateRegistrate getCreateRegistrate() { return CREATE_REGISTRATE; }
  public static CreataniaRegistrate all() { return CREATANIA_INDEX; }
}
