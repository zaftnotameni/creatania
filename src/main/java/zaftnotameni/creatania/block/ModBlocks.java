package zaftnotameni.creatania.block;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.block.custom.ManaMotorBlock;
import zaftnotameni.creatania.item.ModItems;

import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MODID);

  public static final RegistryObject<Block> MANA_MOTOR = registerBlock(Constants.MANA_MOTOR,
    () -> new ManaMotorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()),
    CreativeModeTab.TAB_MISC);

  public static void register(IEventBus bus) { BLOCKS.register(bus); }

  private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
    return BLOCKS.register(name, block);
  }

  private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block,
                                                                   CreativeModeTab tab, String tooltipKey) {
    RegistryObject<T> toReturn = BLOCKS.register(name, block);
    registerBlockItem(name, toReturn, tab, tooltipKey);
    return toReturn;
  }

  private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                          CreativeModeTab tab, String tooltipKey) {
    return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
      new Item.Properties().tab(tab)) {
      @Override
      public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(new TextComponent(tooltipKey));
      }
    });
  }

  private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
    RegistryObject<T> toReturn = BLOCKS.register(name, block);
    registerBlockItem(name, toReturn, tab);
    return toReturn;
  }

  private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                          CreativeModeTab tab) {
    return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
      new Item.Properties().tab(tab)));
  }


}
