package zaftnotameni.creatania.registry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import zaftnotameni.creatania.Constants;
public class CreativeModeTabs {
  public static final CreativeModeTab CREATANIA_ITEMS = new CreativeModeTab(Constants.MODID) {
    @Override
    public @NotNull ItemStack makeIcon() { return new ItemStack(Blocks.MANA_MOTOR.get()); }
    @Override
    public @NotNull Component getDisplayName() { return new TextComponent("Creatania"); }
  };
  public static NonNullSupplier<CreativeModeTab> creataniaCreativeTab = () -> CreativeModeTabs.CREATANIA_ITEMS;
}