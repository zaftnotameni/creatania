package zaftnotameni.creatania.registry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import zaftnotameni.creatania.Constants;
public class CreativeModeTabs {
  public static final CreativeModeTab CREATANIA_ITEMS = new CreativeModeTab(Constants.MODID) {
    @Override
    public ItemStack makeIcon() { return new ItemStack(CreataniaBlocks.REAL_MANA_BLOCK.get()); }
    @Override
    public Component getDisplayName() { return Component.literal("Creatania"); }
  };
  public static NonNullSupplier<CreativeModeTab> creataniaCreativeTab = () -> CreativeModeTabs.CREATANIA_ITEMS;
}
