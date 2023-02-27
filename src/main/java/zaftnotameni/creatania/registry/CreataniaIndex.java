package zaftnotameni.creatania.registry;
import net.minecraft.resources.ResourceLocation;
import zaftnotameni.creatania.Constants;

public class CreataniaIndex {

  public static final CreataniaRegistrate CREATANIA_INDEX = CreataniaRegistrate.forMod(Constants.MODID);
    //.creativeModeTabFor(creataniaCreativeTab);;

  public static ResourceLocation resource(String path) { return new ResourceLocation(Constants.MODID, path); }

  public static CreataniaRegistrate all() { return CREATANIA_INDEX; }
}
