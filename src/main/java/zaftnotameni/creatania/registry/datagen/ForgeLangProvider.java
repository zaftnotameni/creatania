package zaftnotameni.creatania.registry.datagen;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import org.apache.commons.lang3.StringUtils;
import zaftnotameni.creatania.registry.CreataniaIndex;
import zaftnotameni.creatania.util.Log;

import java.lang.reflect.Field;
import java.util.Map;
public class ForgeLangProvider extends RegistrateLangProvider {
  public RegistrateLangProvider rlp;
  public Field f;
  public ForgeLangProvider(DataGenerator gen) {
    super(CreataniaIndex.all(), gen);
    rlp = CreataniaIndex.all().getDataProvider(ProviderType.LANG).get();
    try {
      f = LanguageProvider.class.getDeclaredField("data");
      f.setAccessible(true);
    } catch (NoSuchFieldException e) {
      throw new RuntimeException("Could not change accessibility of data");
    }
  }
  public String old(String key) {
    try {
      return ((Map<String, String>) f.get(this)).getOrDefault(key, null);
    } catch (IllegalAccessException e) {
      throw new RuntimeException("Could not change accessibility of data");
    }
  }
  public String put(String key, String value) {
    try {
      return ((Map<String, String>) f.get(this)).put(key, value);
    } catch (IllegalAccessException e) {
      throw new RuntimeException("Could not change accessibility of data");
    }
  }
  public String remove(String key) {
    try {
      return ((Map<String, String>) f.get(this)).remove(key);
    } catch (IllegalAccessException e) {
      throw new RuntimeException("Could not change accessibility of data");
    }
  }
  @Override
  protected void addTranslations() {
    super.addTranslations();
  }
  @Override
  public void add(String key, String value) {
    var oldValue = old(key);
    if (!StringUtils.isAnyBlank(oldValue)){
      Log.LOGGER.info("duplicated key {} found, using {} instead of {}", key, value, oldValue);
    } else {
      super.add(key, value);
    }
  }
}
