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
    public static final TagKey<Block> ALL_MANA = tag("mana/all");
    public static final TagKey<Block> INERT_MANA = tag("mana/inert");
    public static final TagKey<Block> REAL_MANA = tag("mana/real");
    public static final TagKey<Block> PURE_MANA = tag("mana/pure");
    public static final TagKey<Block> CORRUPT_MANA = tag("mana/corrupt");
    public static final TagKey<Block> ACTIVE_MANA = tag("mana/active");
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
    public static final TagKey<Block> BOTANIA_DOUBLE_MYSTICAL_FLOWERS = botaniaTag("double_mystical_flowers");
    public static final TagKey<Block> BOTANIA_MYSTICAL_FLOWERS = botaniaTag("mystical_flowers");

    public static TagKey<Block> tag(String name) { return BlockTags.create(new ResourceLocation(Constants.MODID, name));  }
    public static TagKey<Block> forgeTag(String name) { return BlockTags.create(new ResourceLocation("forge", name)); }
    public static TagKey<Block> botaniaTag(String name) { return BlockTags.create(new ResourceLocation("botania", name)); }
  }
  public static class Fluids {
    public static final TagKey<Fluid> PURE_MANA = tag("fluid/mana/pure");
    public static final TagKey<Fluid> CORRUPT_MANA = tag("fluid/mana/corrupt");
    public static final TagKey<Fluid> REAL_MANA = tag("fluid/mana/real");
    public static final TagKey<Fluid> ALL_MANA = tag("fluid/mana/all");
    public static final TagKey<Fluid> MOLTEN = tag("fluid/molten");
    public static TagKey<Fluid> tag(String name) { return FluidTags.create(new ResourceLocation(Constants.MODID, name)); }
    public static TagKey<Fluid> forgeTag(String name) { return FluidTags.create(new ResourceLocation("forge", name)); }
  }
  public static class Items {
    public static final TagKey<Item> BOTANIA_DOUBLE_MYSTICAL_FLOWERS = botaniaTag("double_mystical_flowers");
    public static final TagKey<Item> BOTANIA_MYSTICAL_FLOWERS = botaniaTag("mystical_flowers");
    public static final TagKey<Item> MINECRAFT_FLOWERS = minecraftTag("items/flowers");
    public static final TagKey<Item> MINECRAFT_TALL_FLOWERS = minecraftTag("items/tall_flowers");

    public static TagKey<Item> tag(String name) { return ItemTags.create(new ResourceLocation(Constants.MODID, name)); }
    public static TagKey<Item> forgeTag(String name) { return ItemTags.create(new ResourceLocation("forge", name)); }
    public static TagKey<Item> botaniaTag(String name) { return ItemTags.create(new ResourceLocation("botania", name)); }
    public static TagKey<Item> minecraftTag(String name) { return ItemTags.create(new ResourceLocation("minecraft", name)); }
  }
}
