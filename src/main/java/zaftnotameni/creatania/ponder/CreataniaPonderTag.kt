package zaftnotameni.creatania.ponder

import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes
import com.simibubi.create.foundation.ponder.PonderTag
import net.minecraft.resources.ResourceLocation
import zaftnotameni.creatania.registry.Index

class CreataniaPonderTag(id : ResourceLocation?) : PonderTag(id) {
  companion object {
    val start : PonderTag? = null

    //    MANA_MANIPULATION = create("mana_manipulation")
    //      .item(Blocks.PURE_MANA_BLOCK.get().asItem(), true, true)
    //      .defaultLang("Mana Manipulation", "Handling tangible mana")
    //      .addToIndex(),
    @JvmField
    val COBBLEGEN = create("cobblegen")
      .item(AllPaletteStoneTypes.CRIMSITE.baseBlock.get().asItem(), true, true)
      .defaultLang("Cobblegen", "Sticks and stones might break your bones")
      .addToIndex()
    fun create(id : String?) : PonderTag {
      return PonderTag(Index.resource(id))
    }

    //  private static CreataniaPonderTag create(String id) {
    //    return new CreataniaPonderTag(Index.resource(id));
    //  }
    //  public CreataniaPonderTag defaultLang(String title, String description) {
    //    PonderLocalization.registerTag(getId(), title, description);
    //    return this;
    //  }
    //
    //  public CreataniaPonderTag addToIndex() {
    //    PonderRegistry.TAGS.listTag(this);
    //    return this;
    //  }
    //
    //  public CreataniaPonderTag icon(ResourceLocation location) {
    //    set("icon", new ResourceLocation(location.getNamespace(), "textures/ponder/tag/" + location.getPath() + ".png"));
    //    return this;
    //  }
    //
    //  public CreataniaPonderTag icon(String location) {
    //    set("icon", new ResourceLocation(getId().getNamespace(), "textures/ponder/tag/" + location + ".png"));
    //    return this;
    //  }
    //
    //  public CreataniaPonderTag idAsIcon() {
    //    return icon(getId());
    //  }
    //
    //  public void set(String field, Object value) {
    //    try {
    //      Field f = super.getClass().getDeclaredField(field);
    //      f.setAccessible(true);
    //      f.set(this, value);
    //    } catch (Exception e) {}
    //  }
    //  public CreataniaPonderTag item(ItemLike item, boolean useAsIcon, boolean useAsMainItem) {
    //    if (useAsIcon)
    //      set("itemIcon", new ItemStack(item));
    //    if (useAsMainItem)
    //      set("mainItem", new ItemStack(item));
    //    return this;
    //  }
    //
    //  public CreataniaPonderTag item(ItemLike item) {
    //    return this.item(item, true, true);
    //  }
    @JvmStatic
    fun registerPonderTags() {}
  }
}