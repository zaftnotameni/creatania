package zaftnotameni.creatania.registry.datagen;
import com.google.common.base.Supplier;
import com.google.gson.JsonElement;
import com.simibubi.create.foundation.data.LangPartial;
import com.simibubi.create.foundation.utility.Lang;
import zaftnotameni.creatania.Constants;
public enum LangPartials implements LangPartial {

  ADVANCEMENTS("Advancements", ForgeAdvancementsProvider::provideLangEntries)
  ;
  private final String displayName;
  private final Supplier<JsonElement> provider;

  private LangPartials(String displayName) {
    this.displayName = displayName;
    String fileName = Lang.asId(name());
    this.provider = () -> LangPartial.fromResource(Constants.MODID, fileName);
  }

  private LangPartials(String displayName, Supplier<JsonElement> provider) {
    this.displayName = displayName;
    this.provider = provider;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

  @Override
  public JsonElement provide() {
    return provider.get();
  }

}

