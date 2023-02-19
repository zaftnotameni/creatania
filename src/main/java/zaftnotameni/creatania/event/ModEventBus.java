package zaftnotameni.creatania.event;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.particle.GlowParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.recipes.ManaGeneratorRecipe;
import zaftnotameni.creatania.registry.Blocks;
import zaftnotameni.creatania.registry.Fluids;
import zaftnotameni.creatania.registry.Particles;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBus {
  @SubscribeEvent
  public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> evt) {
    Registry.register(
      Registry.RECIPE_TYPE, ManaGeneratorRecipe.Type.ID, ManaGeneratorRecipe.Type.INSTANCE
    );
  }
  @SubscribeEvent
  public static void registerParticleFactories(final ParticleFactoryRegisterEvent evt) {
    Minecraft.getInstance().particleEngine.register(Particles.MANA_PARTICLES.get(), GlowParticle.ElectricSparkProvider::new);
  }

  public static final List<Pair<ItemColor, Supplier<? extends ItemLike>[]>> ITEM_COLORS = Lists.newArrayList();
  public static final List<Pair<BlockColor, Supplier<? extends net.minecraft.world.level.block.Block>[]>> BLOCK_COLORS = Lists.newArrayList();
  @SubscribeEvent
  public static void onItemColorEvent(ColorHandlerEvent.Item event) {
    initItemColors();
    for (Pair<ItemColor, Supplier<? extends ItemLike>[]> pair : ITEM_COLORS) {
      event.getItemColors().register(pair.getLeft(), unpackItems(pair.getRight()));
    }
  }

  @SubscribeEvent
  public static void onBlockColorEvent(ColorHandlerEvent.Block event) {
    initBlockColors();
    for (Pair<BlockColor, Supplier<? extends net.minecraft.world.level.block.Block>[]> pair : BLOCK_COLORS) {
      event.getBlockColors().register(pair.getLeft(), unpackBlocks(pair.getRight()));
    }
  }

  private static net.minecraft.world.level.block.Block[] unpackBlocks(Supplier<? extends net.minecraft.world.level.block.Block>[] blocks) {
    var array = new net.minecraft.world.level.block.Block[blocks.length];
    for (int i = 0; i < blocks.length; i++) {
      array[i] = Objects.requireNonNull(blocks[i].get());
    }
    return array;
  }
  private static ItemLike[] unpackItems(Supplier<? extends ItemLike>[] items) {
    ItemLike[] array = new ItemLike[items.length];
    for (int i = 0; i < items.length; i++) {
      array[i] = Objects.requireNonNull(items[i].get());
    }
    return array;
  }
  public static void registerBlockColors(BlockColor blockColor, Supplier<? extends net.minecraft.world.level.block.Block>... blocks) {
    Objects.requireNonNull(blockColor, "color is null!");
    if (Minecraft.getInstance().getBlockColors() == null) {
      BLOCK_COLORS.add(Pair.of(blockColor, blocks));
    } else {
      Minecraft.getInstance().getBlockColors().register(blockColor, unpackBlocks(blocks));
    }
  }
  public static void registerItemColors(ItemColor itemColor, Supplier<? extends ItemLike>... items) {
    Objects.requireNonNull(itemColor, "color is null!");
    if (Minecraft.getInstance().getItemColors() == null) {
      ITEM_COLORS.add(Pair.of(itemColor, items));
    } else {
      Minecraft.getInstance().getItemColors().register(itemColor, unpackItems(items));
    }
  }
  public static void initBlockColors() {
    if (!BLOCK_COLORS.isEmpty()) return;
    var purple = new BlockColor() {
      @Override
      public int getColor(BlockState pState, @Nullable BlockAndTintGetter pLevel, @Nullable BlockPos pPos, int pTintIndex) {
        return MaterialColor.COLOR_PURPLE.calculateRGBColor(MaterialColor.Brightness.LOW);
      }
    };
    var cyan = new BlockColor() {
      @Override
      public int getColor(BlockState pState, @Nullable BlockAndTintGetter pLevel, @Nullable BlockPos pPos, int pTintIndex) {
        return MaterialColor.COLOR_CYAN.calculateRGBColor(MaterialColor.Brightness.HIGH);
      }
    };
    var wart = new BlockColor() {
      @Override
      public int getColor(BlockState pState, @Nullable BlockAndTintGetter pLevel, @Nullable BlockPos pPos, int pTintIndex) {
        return MaterialColor.WARPED_WART_BLOCK.calculateRGBColor(MaterialColor.Brightness.HIGH);
      }
    };
    registerBlockColors(purple, () -> Blocks.CORRUPTED_INERT_MANA_BLOCK.get());
    registerBlockColors(purple, () -> Fluids.CORRUPT_MANA_FLUID.block.get());
    registerBlockColors(cyan, () -> Blocks.PURIFIED_INERT_MANA_BLOCK.get());
    registerBlockColors(cyan, () -> Fluids.PURIFIED_MANA_FLUID.block.get());
    registerBlockColors(wart, () -> Blocks.BOTANIA_MANA_BLOCK.get());
    registerBlockColors(wart, () -> Fluids.BOTANIA_MANA_FLUID.block.get());
  }

  public static ItemColor colorOf(MaterialColor c) {
    return (pStack, pTintIndex) -> c.calculateRGBColor(MaterialColor.Brightness.HIGH);
  }
  public static void initItemColors() {
    if (!ITEM_COLORS.isEmpty()) return;
    var purple = colorOf(MaterialColor.COLOR_PURPLE);
    var cyan = colorOf(MaterialColor.COLOR_CYAN);
    var wart = colorOf(MaterialColor.WARPED_WART_BLOCK);
    var iron = colorOf(MaterialColor.TERRACOTTA_RED);
    var copper = colorOf(MaterialColor.COLOR_ORANGE);
    var gold = colorOf(MaterialColor.GOLD);
    var zinc = colorOf(MaterialColor.COLOR_GRAY);
    var andesite = colorOf(MaterialColor.COLOR_GRAY);
    var brass = colorOf(MaterialColor.COLOR_YELLOW);
    var mana = colorOf(MaterialColor.COLOR_BLUE);
    var terra = colorOf(MaterialColor.COLOR_GREEN);
    var elem = colorOf(MaterialColor.COLOR_PINK);
    var gaia = colorOf(MaterialColor.COLOR_LIGHT_GRAY);
    registerItemColors(purple, () -> Fluids.CORRUPT_MANA_FLUID.bucket.get().asItem());
    registerItemColors(cyan, () -> Fluids.PURIFIED_MANA_FLUID.bucket.get().asItem());
    registerItemColors(wart, () -> Fluids.BOTANIA_MANA_FLUID.bucket.get().asItem());
    registerItemColors(gold, () -> Fluids.MOLTEN_GOLD_FLUID.bucket.get().asItem());
    registerItemColors(iron, () -> Fluids.MOLTEN_IRON_FLUID.bucket.get().asItem());
    registerItemColors(copper, () -> Fluids.MOLTEN_COPPER_FLUID.bucket.get().asItem());
    registerItemColors(zinc, () -> Fluids.MOLTEN_ZINC_FLUID.bucket.get().asItem());
    registerItemColors(andesite, () -> Fluids.MOLTEN_ANDESITE_ALLOY_FLUID.bucket.get().asItem());
    registerItemColors(brass, () -> Fluids.MOLTEN_BRASS_FLUID.bucket.get().asItem());
    registerItemColors(mana, () -> Fluids.MOLTEN_MANASTEEL.bucket.get().asItem());
    registerItemColors(terra, () -> Fluids.MOLTEN_TERRASTEEL.bucket.get().asItem());
    registerItemColors(elem, () -> Fluids.MOLTEN_ELEMENTIUM.bucket.get().asItem());
    registerItemColors(gaia, () -> Fluids.MOLTEN_GAIA.bucket.get().asItem());
  }
}
