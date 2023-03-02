package zaftnotameni.creatania.ponder;

import com.simibubi.create.content.palettes.AllPaletteStoneTypes;
import com.simibubi.create.foundation.ponder.PonderLocalization;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import java.lang.reflect.Field;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.registry.Blocks;
import zaftnotameni.creatania.registry.Index;
public class CreataniaPonderTag extends PonderTag {
  public static final PonderRegistrationHelper INDEX = new PonderRegistrationHelper(Constants.MODID);

  public static final CreataniaPonderTag start = null,
    MANA_MANIPULATION = create("mana_manipulation")
      .item(Blocks.PURE_MANA_BLOCK.get(), true, true)
      .defaultLang("Mana Manipulation", "Handling tangible mana")
      .addToIndex(),
    COBBLEGEN = create("cobblegen")
      .item(AllPaletteStoneTypes.CRIMSITE.baseBlock.get(), true, false)
      .defaultLang("Cobblegen", "Sticks and stones might break your bones")
      .addToIndex(),
    end = null;
  public CreataniaPonderTag(ResourceLocation id) {
    super(id);
  }
  private static CreataniaPonderTag create(String id) {
    return new CreataniaPonderTag(Index.resource(id));
  }
  public CreataniaPonderTag defaultLang(String title, String description) {
    PonderLocalization.registerTag(getId(), title, description);
    return this;
  }

  public CreataniaPonderTag addToIndex() {
    PonderRegistry.TAGS.listTag(this);
    return this;
  }

  public CreataniaPonderTag icon(ResourceLocation location) {
    set("icon", new ResourceLocation(location.getNamespace(), "textures/ponder/tag/" + location.getPath() + ".png"));
    return this;
  }

  public CreataniaPonderTag icon(String location) {
    set("icon", new ResourceLocation(getId().getNamespace(), "textures/ponder/tag/" + location + ".png"));
    return this;
  }

  public CreataniaPonderTag idAsIcon() {
    return icon(getId());
  }

  public void set(String field, Object value) {
    try {
      Field f = super.getClass().getField(field);
      f.setAccessible(true);
      f.set(this, value);
    } catch (Exception e) {}
  }
  public CreataniaPonderTag item(ItemLike item, boolean useAsIcon, boolean useAsMainItem) {
    if (useAsIcon)
      set("itemIcon", new ItemStack(item));
    if (useAsMainItem)
      set("mainItem", new ItemStack(item));
    return this;
  }

  public CreataniaPonderTag item(ItemLike item) {
    return this.item(item, true, true);
  }


}