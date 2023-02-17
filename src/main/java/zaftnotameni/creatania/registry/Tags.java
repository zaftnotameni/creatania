package zaftnotameni.creatania.registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import zaftnotameni.creatania.Constants;
public class Tags {
  public static class Blocks {
    public static final TagKey<Block> PURE_INERT_MANA = tag("pure_inert_mana");
    public static final TagKey<Block> CORRUPT_INERT_MANA = tag("corrupt_inert_mana");
    public static final TagKey<Block> BOTANIA_MANA = tag("botania_mana");
    public static final TagKey<Block> PREVENTS_ENDER_TELEPORTATION = tag("prevents_ender_teleportation");
    public static final TagKey<Block> BLACKLISTED_FOR_WRENCH_PICKUP = tag("blacklisted_for_wrench_pickup");
    public static final TagKey<Block> MANADUCT = tag("manaduct");
    public static final TagKey<Block> TIER_1 = tag("tier_1");
    public static final TagKey<Block> TIER_2 = tag("tier_2");
    public static final TagKey<Block> TIER_3 = tag("tier_3");
    public static final TagKey<Block> TIER_4 = tag("tier_4");
    public static final TagKey<Block> MANA_MACHINE = tag("mana_machine");
    public static final TagKey<Block> FORGE_LAPIS = forgeTag("storage_blocks/lapis");
    public static final TagKey<Block> BOTANIA_TERRA_PLATE_BASE = botaniaTag("terra_plate_base");

    public static TagKey<Block> tag(String name) { return BlockTags.create(new ResourceLocation(Constants.MODID, name));  }
    public static TagKey<Block> forgeTag(String name) { return BlockTags.create(new ResourceLocation("forge", name)); }
    public static TagKey<Block> botaniaTag(String name) { return BlockTags.create(new ResourceLocation("botania", name)); }
  }
  public static class Fluids {
    public static final TagKey<Fluid> PURE_INERT_MANA = tag("pure_inert_mana");
    public static final TagKey<Fluid> CORRUPT_INERT_MANA = tag("corrupt_inert_mana");
    public static TagKey<Fluid> tag(String name) { return FluidTags.create(new ResourceLocation(Constants.MODID, name)); }
    public static TagKey<Fluid> forgeTag(String name) { return FluidTags.create(new ResourceLocation("forge", name)); }
  }
  public static class Items {
    public static TagKey<Item> tag(String name) { return ItemTags.create(new ResourceLocation(Constants.MODID, name)); }
    public static TagKey<Item> forgeTag(String name) { return ItemTags.create(new ResourceLocation("forge", name)); }
  }
}
