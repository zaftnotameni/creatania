package zaftnotameni.creatania.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MODID);

  public static final RegistryObject<Block> MANA_MOTOR_BLOCK = registerBlock(Constants.MANA_MOTOR_BLOCK,
    () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(1f)), CreativeModeTab.TAB_MISC);

  public static void register(IEventBus bus) { BLOCKS.register(bus); }

  private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
    return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
  }

  private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
    RegistryObject<T> toReturn = BLOCKS.register(name, block);
    registerBlockItem(name, toReturn, tab);
    return toReturn;
  }
}
