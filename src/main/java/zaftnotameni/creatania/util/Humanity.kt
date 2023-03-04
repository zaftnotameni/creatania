package zaftnotameni.creatania.util

import com.tterrag.registrate.providers.ProviderType
import com.tterrag.registrate.providers.RegistrateLangProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraftforge.common.util.Lazy
import net.minecraftforge.registries.RegistryObject
import org.apache.commons.lang3.StringUtils
import zaftnotameni.creatania.registry.Index
import java.util.*

object Humanity {
  @JvmField
  var lang : Lazy<RegistrateLangProvider> = Lazy.of { Index.all().getDataProvider(ProviderType.LANG).get() }
  fun digestString(`in` : String?) : String {
    val firstPass = StringUtils.capitalize(
      StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(`in`), StringUtils.SPACE)
    )
    val secondPass = StringUtils.split(firstPass, "_")
    val thirdPass =
      Arrays.stream(secondPass).map { str : String? -> StringUtils.trimToEmpty(str) }.filter { s : String -> s.isNotBlank() }
        .toArray()
    val fourthPass = StringUtils.join(thirdPass, StringUtils.SPACE)
    val fifthPass = StringUtils.replace(fourthPass, "/", ".")
    return StringUtils.replace(fifthPass, ":", ".")
  }

  @JvmStatic
  fun digestResource(`in` : ResourceLocation) : String {
    return digestString(`in`.path)
  }

  @JvmStatic
  fun digestItem(`in` : Item) : String {
    return digestString(`in`.descriptionId.toString())
  }

  @JvmStatic
  fun digestItem(entry : RegistryObject<Item?>) : String {
    return digestString(entry.id.path)
  }

  fun keyString(`in` : String?) : String {
    return StringUtils.replace(StringUtils.replace(`in`, ":", "."), "/", ".")
  }

  @JvmStatic
  fun keyResource(`in` : ResourceLocation) : String {
    return keyString(`in`.toString())
  }

  @JvmStatic
  fun keyItem(entry : RegistryObject<Item?>) : String {
    return "item." + keyString(entry.id.toString())
  }

  @JvmStatic
  fun slashes(slashyname : String) : String {
    if (!StringUtils.containsIgnoreCase(slashyname, "/")) return slashyname
    val slashies = StringUtils.split(slashyname, "/")
    if (slashies.size <= 1) return slashyname
    val last = slashies[slashies.size - 1]
    var ss = last
    for (s in slashies) if (!StringUtils.equalsIgnoreCase(last, s)) ss += " $s"
    return RegistrateLangProvider.toEnglishName(StringUtils.removeEnd(ss, "s"))
  }
}