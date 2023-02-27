package zaftnotameni.creatania.registry;
import net.minecraft.resources.ResourceLocation;
import zaftnotameni.creatania.Constants;

import static zaftnotameni.creatania.registry.CreativeModeTabs.creataniaCreativeTab;

public class CreataniaIndex {

  public static final CreataniaRegistrate CREATANIA_INDEX = CreataniaRegistrate.forMod(Constants.MODID).creativeModeTabFor(creataniaCreativeTab);
    //.creativeModeTabFor(creataniaCreativeTab);;

  public static ResourceLocation resource(String path) { return new ResourceLocation(Constants.MODID, path); }

  public static CreataniaRegistrate all() { return CREATANIA_INDEX; }
}
