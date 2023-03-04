@file:Suppress("unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
  "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused"
)

package zaftnotameni.creatania.util

import com.simibubi.create.foundation.utility.Lang
import com.simibubi.create.foundation.utility.LangBuilder
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.TextComponent

object Text {
  fun colored(text : String?, color : ChatFormatting?) : LangBuilder {
    return Lang.builder().add(TextComponent(text)).style(color)
  }

  fun muted(text : String?) : LangBuilder {
    return colored(text, ChatFormatting.DARK_GRAY)
  }

  fun gray(text : String?) : LangBuilder {
    return colored(text, ChatFormatting.GRAY)
  }

  @Suppress("unused") fun green(text : String?) : LangBuilder {
    return colored(text, ChatFormatting.DARK_GREEN)
  }

  fun aqua(text : String?) : LangBuilder {
    return colored(text, ChatFormatting.AQUA)
  }

  fun red(text : String?) : LangBuilder {
    return colored(text, ChatFormatting.RED)
  }

  fun purple(text : String?) : LangBuilder {
    return colored(text, ChatFormatting.LIGHT_PURPLE)
  }
}