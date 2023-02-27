//package zaftnotameni.creatania.util;
//import com.tterrag.registrate.providers.ProviderType;
//import com.tterrag.registrate.providers.RegistrateLangProvider;
//import com.tterrag.registrate.util.entry.RegistryEntry;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.material.Fluid;
//import net.minecraftforge.common.util.Lazy;
//import net.minecraftforge.registries.ForgeRegistries;
//import net.minecraftforge.registries.RegistryObject;
//import org.apache.commons.lang3.StringUtils;
//import zaftnotameni.creatania.registry.Index;
//
//import java.util.Arrays;
//
//import static com.tterrag.registrate.providers.RegistrateLangProvider.toEnglishName;
//import static org.apache.commons.lang3.StringUtils.*;
//public class Humanity {
//  public static Lazy<RegistrateLangProvider> lang = Lazy.of(() -> Index.all().getDataProvider(ProviderType.LANG).get());
//
//  public static String digestString(String in) {
//    var firstPass = capitalize(join(splitByCharacterTypeCamelCase(in), SPACE));
//    var secondPass = split(firstPass, "_");
//    var thirdPass = Arrays.stream(secondPass).map(StringUtils::trimToEmpty).filter(s -> !s.isBlank()).toArray();
//    var fourthPass = join(thirdPass, SPACE);
//    var fifthPass = replace(fourthPass, "/", ".");
//    var sixthPass = replace(fifthPass, ":", ".");
//    return sixthPass;
//  }
//  public static String digestResource(ResourceLocation in) {
//    return digestString(in.getPath());
//  }
//  public static String digestBlock(Block in) { return digestString(in.getDescriptionId().toString()); }
//  public static String digestItem(Item in) { return digestString(in.getDescriptionId().toString()); }
//  public static String digestItem(RegistryObject<Item> entry) { return digestString(entry.getId().getPath()); }
//  public static String digestBlock(RegistryObject<Block> entry) { return digestString(entry.getId().getPath()); }
//  public static String digestFluid(RegistryObject<Fluid> entry) { return digestString(entry.getId().getPath()); }
//
//  public static String keyString(String in) { return replace(replace(in, ":", "."), "/", "."); }
//  public static String keyResource(ResourceLocation in) { return keyString(in.toString()); }
//  public static String keyBlock(Block in) { return "item." + keyString(in.getDescriptionId().toString()); }
//  public static String keyItem(Item in) { return "block." + keyString(in.getDescriptionId().toString()); }
//  public static String keyItem(RegistryObject<Item> entry) { return "item." + keyString(entry.getId().toString()); }
//  public static String keyBlock(RegistryObject<Block> entry) { return "block." + keyString(entry.getId().toString()); }
//  public static String keyFluid(RegistryObject<Fluid> entry) { return "fluid." + keyString(entry.getId().toString()); }
//
//  public static String keyBlock(RegistryEntry<Block> entry) { return "block." + keyString(entry.getId().toString()); }
//  public static String keyItem(RegistryEntry<Item> entry) { return "block." + keyString(entry.getId().toString()); }
//  public static String keyFluid(RegistryEntry<Fluid> entry) { return "block." + keyString(entry.getId().toString()); }
//  public static String digestBlock(RegistryEntry<Block> entry) { return digestString(entry.get().getName().getString()); }
//  public static String digestItem(RegistryEntry<Item> entry) { return digestString(entry.get().getDescription().getString()); }
//  public static String digestFluid(RegistryEntry<Fluid> entry) { return digestString(ForgeRegistries.FLUIDS.getKey(entry.get()).getPath()); }
//  public static String slashes(String slashyname) {
//    if (!containsIgnoreCase(slashyname, "/")) return slashyname;
//    var slashies = split(slashyname, "/");
//    if (slashies.length <= 1) return slashyname;
//    var last = slashies[slashies.length - 1];
//    var ss = last;
//    for (var s : slashies) if (!equalsIgnoreCase(last, s)) ss += " " + s;
//    return toEnglishName(removeEnd(ss, "s"));
//  }
//}
