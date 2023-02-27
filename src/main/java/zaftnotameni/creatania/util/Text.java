package zaftnotameni.creatania.util;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.LangBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
public class Text {
  public static LangBuilder colored(String text, ChatFormatting color) {
    return Lang.builder().add(Component.literal(text)).style(color);
  }
  public static LangBuilder muted(String text) { return colored(text, ChatFormatting.DARK_GRAY); }
  public static LangBuilder gray(String text) { return colored(text, ChatFormatting.GRAY); }
  public static LangBuilder green(String text) { return colored(text, ChatFormatting.DARK_GREEN); }
  public static LangBuilder aqua(String text) {
    return colored(text, ChatFormatting.AQUA);
  }
  public static LangBuilder red(String text) {
    return colored(text, ChatFormatting.RED);
  }
  public static LangBuilder purple(String text) { return colored(text, ChatFormatting.LIGHT_PURPLE); }
}
