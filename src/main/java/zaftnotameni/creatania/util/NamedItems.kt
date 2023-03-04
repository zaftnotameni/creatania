package zaftnotameni.creatania.util

import com.simibubi.create.AllBlocks
import com.simibubi.create.AllItems
import com.simibubi.create.AllTags
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.minecraftforge.common.Tags
import net.minecraftforge.registries.ForgeRegistries
import org.apache.commons.lang3.StringUtils

@SuppressWarnings("unused")
object NamedItems {
  fun namespaceOf(id : String) : String {
    return id.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
  }

  @JvmStatic
  fun pathOf(id : String) : String {
    return id.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
  }

  fun resourceOf(id : String) : ResourceLocation {
    return ResourceLocation(namespaceOf(id), pathOf(id))
  }

  @JvmStatic
  fun itemLikeOf(id : String) : ItemLike? {
    return itemLike(namespaceOf(id), pathOf(id))
  }

  fun idToTallFlower(id : String) : String {
    return id.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
  }

  fun tallToMysticFlower(tallPath : String?) : String {
    return StringUtils.replace(tallPath, "double", "mystical")
  }

  fun tallToFlowerPetal(tallPath : String?) : String {
    return StringUtils.replace(tallPath, "double_flower", "petal")
  }

  @JvmField
  val MINECRAFT_SHORT_FLOWERS = arrayOf(
    "minecraft:poppy",
    "minecraft:dandelion"
  )
  @JvmField
  val MINECRAFT_TALL_FLOWERS = arrayOf(
    "minecraft:rose_bush"
  )
  @JvmField
  val BOTANIA_TALL_FLOWERS = arrayOf(
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
  )
  @JvmField
  var BOTANIA_MYSTICAL_FLOWERS = arrayOf(
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
  )

  @JvmStatic
  fun itemLike(namespace : String?, path : String?) : ItemLike? {
    return ForgeRegistries.ITEMS.getValue(ResourceLocation(namespace, path))
  }

  @JvmStatic
  fun livingrock() : Ingredient {
    return Ingredient.of(itemLike("botania", "livingrock"))
  }

  @JvmStatic
  fun terrasteelingot() : Ingredient {
    return Ingredient.of(itemLike("botania", "terrasteel_ingot"))
  }

  @JvmStatic
  fun manasteelingot() : Ingredient {
    return Ingredient.of(itemLike("botania", "manasteel_ingot"))
  }

  @JvmStatic
  fun elementiumingot() : Ingredient {
    return Ingredient.of(itemLike("botania", "elementium_ingot"))
  }

  @JvmStatic
  fun gaiaingot() : Ingredient {
    return Ingredient.of(itemLike("botania", "gaia_ingot"))
  }

  object I {
    fun redstone() : TagKey<Item> {
      return Tags.Items.DUSTS_REDSTONE
    }

    fun planks() : TagKey<Item> {
      return ItemTags.PLANKS
    }

    fun woodSlab() : TagKey<Item> {
      return ItemTags.WOODEN_SLABS
    }

    fun gold() : TagKey<Item> {
      return AllTags.forgeItemTag("ingots/gold")
    }

    fun goldSheet() : TagKey<Item> {
      return AllTags.forgeItemTag("plates/gold")
    }

    fun stone() : TagKey<Item> {
      return Tags.Items.STONE
    }

    fun andesite() : ItemLike {
      return AllItems.ANDESITE_ALLOY.get()
    }

    fun shaft() : ItemLike {
      return AllBlocks.SHAFT.get()
    }

    fun cog() : ItemLike {
      return AllBlocks.COGWHEEL.get()
    }

    fun largeCog() : ItemLike {
      return AllBlocks.LARGE_COGWHEEL.get()
    }

    fun andesiteCasing() : ItemLike {
      return AllBlocks.ANDESITE_CASING.get()
    }

    fun brass() : TagKey<Item> {
      return AllTags.forgeItemTag("ingots/brass")
    }

    fun brassSheet() : TagKey<Item> {
      return AllTags.forgeItemTag("plates/brass")
    }

    fun iron() : TagKey<Item> {
      return Tags.Items.INGOTS_IRON
    }

    fun ironNugget() : TagKey<Item> {
      return AllTags.forgeItemTag("nuggets/iron")
    }

    fun zinc() : TagKey<Item> {
      return AllTags.forgeItemTag("ingots/zinc")
    }

    fun ironSheet() : TagKey<Item> {
      return AllTags.forgeItemTag("plates/iron")
    }

    fun sturdySheet() : TagKey<Item> {
      return AllTags.forgeItemTag("plates/obsidian")
    }

    fun brassCasing() : ItemLike {
      return AllBlocks.BRASS_CASING.get()
    }

    fun railwayCasing() : ItemLike {
      return AllBlocks.RAILWAY_CASING.get()
    }

    fun electronTube() : ItemLike {
      return AllItems.ELECTRON_TUBE.get()
    }

    fun precisionMechanism() : ItemLike {
      return AllItems.PRECISION_MECHANISM.get()
    }

    fun copperBlock() : ItemLike {
      return Items.COPPER_BLOCK
    }

    fun brassBlock() : TagKey<Item> {
      return AllTags.forgeItemTag("storage_blocks/brass")
    }

    fun zincBlock() : TagKey<Item> {
      return AllTags.forgeItemTag("storage_blocks/zinc")
    }

    fun wheatFlour() : TagKey<Item> {
      return AllTags.forgeItemTag("flour/wheat")
    }

    fun copper() : ItemLike {
      return Items.COPPER_INGOT
    }

    fun copperSheet() : TagKey<Item> {
      return AllTags.forgeItemTag("plates/copper")
    }

    fun copperNugget() : TagKey<Item> {
      return AllTags.forgeItemTag("nuggets/copper")
    }

    fun brassNugget() : TagKey<Item> {
      return AllTags.forgeItemTag("nuggets/brass")
    }

    fun zincNugget() : TagKey<Item> {
      return AllTags.forgeItemTag("nuggets/zinc")
    }

    fun copperCasing() : ItemLike {
      return AllBlocks.COPPER_CASING.get()
    }

    fun refinedRadiance() : ItemLike {
      return AllItems.REFINED_RADIANCE.get()
    }

    fun shadowSteel() : ItemLike {
      return AllItems.SHADOW_STEEL.get()
    }
  }
}