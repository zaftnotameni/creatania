package zaftnotameni.creatania.registry.datagen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.foundation.data.LangPartial;
import com.simibubi.create.foundation.utility.Lang;
import java.util.function.Supplier;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.registry.Blocks;
import zaftnotameni.creatania.registry.Fluids;
import zaftnotameni.creatania.registry.Items;

import static zaftnotameni.creatania.util.LogKt.log;

public enum LangPartials implements LangPartial {

  ADVANCEMENTS("Advancements", ForgeAdvancementsProvider::provideLangEntries),
  INTERFACE("UI & Messages"),
  SUBTITLES("Subtitles"),
  TOOLTIPS("Item Descriptions"),
  PONDER("Ponder Content"),
  FLUIDS("Fluids", Fluids::provideLangEntries),
  ITEMS("Items", Items::provideLangEntries),
  BLOCKS("Blocks", Blocks::provideLangEntries),
  ;
  public final String displayName;
  public Supplier<JsonElement> provider;

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
    log(l -> l.info("provider running for {}", this.displayName));
    try {
      return provider.get();
    } catch (Exception e) {
      return new JsonObject();
    }
  }

}