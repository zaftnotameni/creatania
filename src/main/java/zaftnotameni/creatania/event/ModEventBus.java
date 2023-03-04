package zaftnotameni.creatania.event;

import com.tterrag.registrate.util.entry.FluidEntry;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.particle.GlowParticle;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.recipes.condenser.ManaCondenserRecipe;
import zaftnotameni.creatania.recipes.generator.ManaGeneratorRecipe;
import zaftnotameni.creatania.registry.Blocks;
import zaftnotameni.creatania.registry.Fluids;
import zaftnotameni.creatania.registry.Particles;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Objects.requireNonNull;
import static net.minecraft.client.Minecraft.getInstance;
import static net.minecraft.core.Registry.register;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBus {


  @SubscribeEvent
  public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> evt) {
    register(
      Registry.RECIPE_TYPE, ManaGeneratorRecipe.Type.ID, ManaGeneratorRecipe.Type.INSTANCE
    );
    register(
      Registry.RECIPE_TYPE, ManaCondenserRecipe.Type.ID, ManaCondenserRecipe.Type.INSTANCE
    );
  }
  @SubscribeEvent
  public static void registerParticleFactories(final ParticleFactoryRegisterEvent evt) {
    getInstance().particleEngine.register(Particles.MANA_PARTICLES.get(), GlowParticle.ElectricSparkProvider::new);
  }

  public static final List<Pair<ItemColor, Supplier<? extends ItemLike>[]>> ITEM_COLORS = newArrayList();
  public static final List<Pair<BlockColor, Supplier<? extends Block>[]>> BLOCK_COLORS = newArrayList();
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
    for (Pair<BlockColor, Supplier<? extends Block>[]> pair : BLOCK_COLORS) {
      event.getBlockColors().register(pair.getLeft(), unpackBlocks(pair.getRight()));
    }
  }

  private static Block[] unpackBlocks(Supplier<? extends Block>[] blocks) {
    var array = new Block[blocks.length];
    for (int i = 0; i < blocks.length; i++) {
      array[i] = requireNonNull(blocks[i].get());
    }
    return array;
  }
  private static ItemLike[] unpackItems(Supplier<? extends ItemLike>[] items) {
    ItemLike[] array = new ItemLike[items.length];
    for (int i = 0; i < items.length; i++) {
      array[i] = requireNonNull(items[i].get());
    }
    return array;
  }
  public static void registerBlockColors(BlockColor blockColor, Supplier<? extends Block>... blocks) {
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
    var purple = colorOfBlock(MaterialColor.COLOR_PURPLE);
    var cyan = colorOfBlock(MaterialColor.COLOR_CYAN);
    var wart = colorOfBlock(MaterialColor.WARPED_WART_BLOCK);
    var iron = colorOfBlock(MaterialColor.TERRACOTTA_RED);
    var copper = colorOfBlock(MaterialColor.COLOR_ORANGE);
    var gold = colorOfBlock(MaterialColor.GOLD);
    var zinc = colorOfBlock(MaterialColor.COLOR_GRAY);
    var andesite = colorOfBlock(MaterialColor.COLOR_GRAY);
    var brass = colorOfBlock(MaterialColor.COLOR_YELLOW);
    var mana = colorOfBlock(MaterialColor.COLOR_BLUE);
    var terra = colorOfBlock(MaterialColor.COLOR_GREEN);
    var elem = colorOfBlock(MaterialColor.COLOR_PINK);
    var gaia = colorOfBlock(MaterialColor.COLOR_LIGHT_GRAY);
    registerBlockColors(purple, Blocks.CORRUPT_MANA_BLOCK::get);
    registerBlockColors(cyan, Blocks.PURE_MANA_BLOCK::get);
    registerBlockColors(wart, Blocks.REAL_MANA_BLOCK::get);
    registerBlockColors(gold, () -> blockFrom(Fluids.MOLTEN_GOLD));
    registerBlockColors(iron, () -> blockFrom(Fluids.MOLTEN_IRON));
    registerBlockColors(copper, () -> blockFrom(Fluids.MOLTEN_COPPER));
    registerBlockColors(zinc, () -> blockFrom(Fluids.MOLTEN_ZINC));
    registerBlockColors(andesite, () -> blockFrom(Fluids.MOLTEN_ANDESITE));
    registerBlockColors(brass, () -> blockFrom(Fluids.MOLTEN_BRASS));
    registerBlockColors(mana, () -> blockFrom(Fluids.MOLTEN_MANASTEEL));
    registerBlockColors(terra, () -> blockFrom(Fluids.MOLTEN_TERRASTEEL));
    registerBlockColors(elem, () -> blockFrom(Fluids.MOLTEN_ELEMENTIUM));
    registerBlockColors(gaia, () -> blockFrom(Fluids.MOLTEN_GAIA));
  }

  public static Block blockFrom(FluidEntry<ForgeFlowingFluid.Flowing> f) {
    return ForgeRegistries.BLOCKS.getValue(f.get().getSource().getRegistryName());
  }

  public static ItemColor colorOf(int rgbNotA) {
    return (pStack, pTintIndex) -> rgbNotA;
  }
  public static BlockColor colorOfBlock(MaterialColor c) { return (pState, pLevel, pPos, pTintIndex) -> c.calculateRGBColor(MaterialColor.Brightness.HIGH); }
  public static void initItemColors() {
    if (!ITEM_COLORS.isEmpty()) return;
    var purple = colorOf(0x440044);
    var cyan = colorOf(0x11aaff);
    var wart = colorOf(0x44ffff);
    var iron = colorOf(0xdd0000);
    var copper = colorOf(0x666600);
    var gold = colorOf(0xffff00);
    var zinc = colorOf(0x999999);
    var andesite = colorOf(0x666666);
    var brass = colorOf(0xdddd33);
    var mana = colorOf(0x000088);
    var terra = colorOf(0x008822);
    var elem = colorOf(0xffaaaa);
    var gaia = colorOf(0xffffff);
    registerItemColors(purple, () -> Fluids.CORRUPT_MANA.get().getBucket());
    registerItemColors(cyan, () -> Fluids.PURE_MANA.get().getBucket());
    registerItemColors(wart, () -> Fluids.REAL_MANA.get().getBucket());
    registerItemColors(gold, () -> Fluids.MOLTEN_GOLD.get().getBucket());
    registerItemColors(iron, () -> Fluids.MOLTEN_IRON.get().getBucket());
    registerItemColors(copper, () -> Fluids.MOLTEN_COPPER.get().getBucket());
    registerItemColors(zinc, () -> Fluids.MOLTEN_ZINC.get().getBucket());
    registerItemColors(andesite, () -> Fluids.MOLTEN_ANDESITE.get().getBucket());
    registerItemColors(brass, () -> Fluids.MOLTEN_BRASS.get().getBucket());
    registerItemColors(mana, () -> Fluids.MOLTEN_MANASTEEL.get().getBucket());
    registerItemColors(terra, () -> Fluids.MOLTEN_TERRASTEEL.get().getBucket());
    registerItemColors(elem, () -> Fluids.MOLTEN_ELEMENTIUM.get().getBucket());
    registerItemColors(gaia, () -> Fluids.MOLTEN_GAIA.get().getBucket());
  }
}