package zaftnotameni.creatania.util;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;
public class NamedItems {
  public static String namespaceOf(String id) { return id.split(":")[0]; }
  public static String pathOf(String id) { return id.split(":")[1]; }
  public static ResourceLocation resourceOf(String id) { return new ResourceLocation(namespaceOf(id), pathOf(id)); }
  public static ItemLike itemLikeOf(String id) { return itemLike(namespaceOf(id), pathOf(id)); }
  public static String idToTallFlower(String id) { return id.split(":")[1]; }
  public static String tallToMysticFlower(String tallPath) { return StringUtils.replace(tallPath, "double", "mystical"); }
  public static String tallToFlowerPetal(String tallPath) { return StringUtils.replace(tallPath, "double_flower", "petal"); }
  public static final String[] MINECRAFT_SHORT_FLOWERS = {
    "minecraft:poppy",
    "minecraft:dandelion"
  };
  public static final String[] MINECRAFT_TALL_FLOWERS = {
    "minecraft:rose_bush"
  };
  public static final String[] BOTANIA_TALL_FLOWERS = {
    "botania:black_double_flower",
    "botania:blue_double_flower",
    "botania:brown_double_flower",
    "botania:cyan_double_flower",
    "botania:gray_double_flower",
    "botania:green_double_flower",
    "botania:light_blue_double_flower",
    "botania:light_gray_double_flower",
    "botania:lime_double_flower",
    "botania:magenta_double_flower",
    "botania:orange_double_flower",
    "botania:pink_double_flower",
    "botania:purple_double_flower",
    "botania:red_double_flower",
    "botania:white_double_flower",
    "botania:yellow_double_flower"
  };
  public static String[] BOTANIA_MYSTICAL_FLOWERS = {
    "botania:black_mystical_flower",
    "botania:blue_mystical_flower",
    "botania:brown_mystical_flower",
    "botania:cyan_mystical_flower",
    "botania:gray_mystical_flower",
    "botania:green_mystical_flower",
    "botania:light_blue_mystical_flower",
    "botania:light_gray_mystical_flower",
    "botania:lime_mystical_flower",
    "botania:magenta_mystical_flower",
    "botania:orange_mystical_flower",
    "botania:pink_mystical_flower",
    "botania:purple_mystical_flower",
    "botania:red_mystical_flower",
    "botania:white_mystical_flower",
    "botania:yellow_mystical_flower"
  };

  public static ItemLike itemLike(String namespace, String path) { return ForgeRegistries.ITEMS.getValue(new ResourceLocation(namespace, path)); }

  public static Ingredient livingrock() {
    return Ingredient.of(itemLike("botania", "livingrock"));
  }
  public static Ingredient terrasteelingot() {
    return Ingredient.of(itemLike("botania", "terrasteel_ingot"));
  }
  public static Ingredient manasteelingot() {
    return Ingredient.of(itemLike("botania", "manasteel_ingot"));
  }
  public static Ingredient elementiumingot() {
    return Ingredient.of(itemLike("botania", "elementium_ingot"));
  }
  public static Ingredient gaiaingot() {
    return Ingredient.of(itemLike("botania", "gaia_ingot"));
  }
  public static class I {

  public static TagKey<Item> redstone() {
      return Tags.Items.DUSTS_REDSTONE;
    }

  public static TagKey<Item> planks() {
      return ItemTags.PLANKS;
    }

  public static TagKey<Item> woodSlab() {
      return ItemTags.WOODEN_SLABS;
    }

  public static TagKey<Item> gold() {
      return AllTags.forgeItemTag("ingots/gold");
    }

  public static TagKey<Item> goldSheet() {
      return AllTags.forgeItemTag("plates/gold");
    }

  public static TagKey<Item> stone() {
      return Tags.Items.STONE;
    }

  public static ItemLike andesite() {
      return AllItems.ANDESITE_ALLOY.get();
    }

  public static ItemLike shaft() {
      return AllBlocks.SHAFT.get();
    }

  public static ItemLike cog() {
      return AllBlocks.COGWHEEL.get();
    }

  public static ItemLike largeCog() {
      return AllBlocks.LARGE_COGWHEEL.get();
    }

  public static ItemLike andesiteCasing() {
      return AllBlocks.ANDESITE_CASING.get();
    }

  public static TagKey<Item> brass() {
      return AllTags.forgeItemTag("ingots/brass");
    }

  public static TagKey<Item> brassSheet() {
      return AllTags.forgeItemTag("plates/brass");
    }

  public static TagKey<Item> iron() {
      return Tags.Items.INGOTS_IRON;
    }

  public static TagKey<Item> ironNugget() {
      return AllTags.forgeItemTag("nuggets/iron");
    }

  public static TagKey<Item> zinc() {
      return AllTags.forgeItemTag("ingots/zinc");
    }

  public static TagKey<Item> ironSheet() {
      return AllTags.forgeItemTag("plates/iron");
    }

  public static TagKey<Item> sturdySheet() {
      return AllTags.forgeItemTag("plates/obsidian");
    }

  public static ItemLike brassCasing() {
      return AllBlocks.BRASS_CASING.get();
    }

  public static ItemLike railwayCasing() {
      return AllBlocks.RAILWAY_CASING.get();
    }

  public static ItemLike electronTube() {
      return AllItems.ELECTRON_TUBE.get();
    }

  public static ItemLike precisionMechanism() {
      return AllItems.PRECISION_MECHANISM.get();
    }

  public static ItemLike copperBlock() {
      return Items.COPPER_BLOCK;
    }

  public static TagKey<Item> brassBlock() {
      return AllTags.forgeItemTag("storage_blocks/brass");
    }

  public static TagKey<Item> zincBlock() {
      return AllTags.forgeItemTag("storage_blocks/zinc");
    }

  public static TagKey<Item> wheatFlour() {
      return AllTags.forgeItemTag("flour/wheat");
    }

  public static ItemLike copper() {
      return Items.COPPER_INGOT;
    }

  public static TagKey<Item> copperSheet() {
      return AllTags.forgeItemTag("plates/copper");
    }

  public static TagKey<Item> copperNugget() {
      return AllTags.forgeItemTag("nuggets/copper");
    }

  public static TagKey<Item> brassNugget() {
      return AllTags.forgeItemTag("nuggets/brass");
    }

  public static TagKey<Item> zincNugget() {
      return AllTags.forgeItemTag("nuggets/zinc");
    }

  public static ItemLike copperCasing() {
      return AllBlocks.COPPER_CASING.get();
    }

  public static ItemLike refinedRadiance() {
      return AllItems.REFINED_RADIANCE.get();
    }

  public static ItemLike shadowSteel() {
      return AllItems.SHADOW_STEEL.get();
    }

  }
}
