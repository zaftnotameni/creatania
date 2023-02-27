//package zaftnotameni.creatania.event;
//
//import com.google.common.collect.Lists;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.color.block.BlockColor;
//import net.minecraft.client.color.item.ItemColor;
//import net.minecraft.client.particle.GlowParticle;
//import net.minecraft.core.Registry;
//import net.minecraft.world.level.ItemLike;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.material.MaterialColor;
//import net.minecraftforge.client.event.RegisterColorHandlersEvent;
//import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.registries.RegisterEvent;
//import org.apache.commons.lang3.tuple.Pair;
//import zaftnotameni.creatania.Constants;
//import zaftnotameni.creatania.recipes.generator.ManaGeneratorRecipe;
//import zaftnotameni.creatania.registry.Fluids;
//import zaftnotameni.creatania.registry.Particles;
//
//import java.util.List;
//import java.util.Objects;
//import java.util.function.Supplier;
//
//@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
//public class ModEventBus {
//
//
//  @SubscribeEvent
//  public static void registerRecipeTypes(final RegisterEvent evt) {
//    Registry.register(
//      Registry.RECIPE_TYPE, ManaGeneratorRecipe.Type.ID, ManaGeneratorRecipe.Type.INSTANCE
//    );
//  }
//  @SubscribeEvent
//  public static void registerParticleFactories(final RegisterParticleProvidersEvent evt) {
//    Minecraft.getInstance().particleEngine.register(Particles.MANA_PARTICLES.get(), GlowParticle.ElectricSparkProvider::new);
//  }
//
//  public static final List<Pair<ItemColor, Supplier<? extends ItemLike>[]>> ITEM_COLORS = Lists.newArrayList();
//  public static final List<Pair<BlockColor, Supplier<? extends Block>[]>> BLOCK_COLORS = Lists.newArrayList();
//  @SubscribeEvent
//  public static void onItemColorEvent(RegisterColorHandlersEvent.Item event) {
//    initItemColors();
//    for (Pair<ItemColor, Supplier<? extends ItemLike>[]> pair : ITEM_COLORS) {
//      event.getItemColors().register(pair.getLeft(), unpackItems(pair.getRight()));
//    }
//  }
//
//  @SubscribeEvent
//  public static void onBlockColorEvent(RegisterColorHandlersEvent.Block event) {
//    initBlockColors();
//    for (Pair<BlockColor, Supplier<? extends Block>[]> pair : BLOCK_COLORS) {
//      event.getBlockColors().register(pair.getLeft(), unpackBlocks(pair.getRight()));
//    }
//  }
//
//  private static Block[] unpackBlocks(Supplier<? extends Block>[] blocks) {
//    var array = new Block[blocks.length];
//    for (int i = 0; i < blocks.length; i++) {
//      array[i] = Objects.requireNonNull(blocks[i].get());
//    }
//    return array;
//  }
//  private static ItemLike[] unpackItems(Supplier<? extends ItemLike>[] items) {
//    ItemLike[] array = new ItemLike[items.length];
//    for (int i = 0; i < items.length; i++) {
//      array[i] = Objects.requireNonNull(items[i].get());
//    }
//    return array;
//  }
//  public static void registerBlockColors(BlockColor blockColor, Supplier<? extends Block>... blocks) {
//    Objects.requireNonNull(blockColor, "color is null!");
//    if (Minecraft.getInstance().getBlockColors() == null) {
//      BLOCK_COLORS.add(Pair.of(blockColor, blocks));
//    } else {
//      Minecraft.getInstance().getBlockColors().register(blockColor, unpackBlocks(blocks));
//    }
//  }
//  public static void registerItemColors(ItemColor itemColor, Supplier<? extends ItemLike>... items) {
//    Objects.requireNonNull(itemColor, "color is null!");
//    if (Minecraft.getInstance().getItemColors() == null) {
//      ITEM_COLORS.add(Pair.of(itemColor, items));
//    } else {
//      Minecraft.getInstance().getItemColors().register(itemColor, unpackItems(items));
//    }
//  }
//  public static void initBlockColors() {
//    if (!BLOCK_COLORS.isEmpty()) return;
//    var purple = colorOfBlock(MaterialColor.COLOR_PURPLE);
//    var cyan = colorOfBlock(MaterialColor.COLOR_CYAN);
//    var wart = colorOfBlock(MaterialColor.WARPED_WART_BLOCK);
//    var iron = colorOfBlock(MaterialColor.TERRACOTTA_RED);
//    var copper = colorOfBlock(MaterialColor.COLOR_ORANGE);
//    var gold = colorOfBlock(MaterialColor.GOLD);
//    var zinc = colorOfBlock(MaterialColor.COLOR_GRAY);
//    var andesite = colorOfBlock(MaterialColor.COLOR_GRAY);
//    var brass = colorOfBlock(MaterialColor.COLOR_YELLOW);
//    var mana = colorOfBlock(MaterialColor.COLOR_BLUE);
//    var terra = colorOfBlock(MaterialColor.COLOR_GREEN);
//    var elem = colorOfBlock(MaterialColor.COLOR_PINK);
//    var gaia = colorOfBlock(MaterialColor.COLOR_LIGHT_GRAY);
////    registerBlockColors(purple, () -> Blocks.CORRUPT_MANA_BLOCK.get());
////    registerBlockColors(cyan, () -> Blocks.PURE_MANA_BLOCK.get());
////    registerBlockColors(wart, () -> Blocks.REAL_MANA_BLOCK.get());
//    // TODO
////    registerBlockColors(gold, () -> blockFrom(Fluids.MOLTEN_GOLD));
////    registerBlockColors(iron, () -> blockFrom(Fluids.MOLTEN_IRON));
////    registerBlockColors(copper, () -> blockFrom(Fluids.MOLTEN_COPPER));
////    registerBlockColors(zinc, () -> blockFrom(Fluids.MOLTEN_ZINC));
////    registerBlockColors(andesite, () -> blockFrom(Fluids.MOLTEN_ANDESITE));
////    registerBlockColors(brass, () -> blockFrom(Fluids.MOLTEN_BRASS));
////    registerBlockColors(mana, () -> blockFrom(Fluids.MOLTEN_MANASTEEL));
////    registerBlockColors(terra, () -> blockFrom(Fluids.MOLTEN_TERRASTEEL));
////    registerBlockColors(elem, () -> blockFrom(Fluids.MOLTEN_ELEMENTIUM));
////    registerBlockColors(gaia, () -> blockFrom(Fluids.MOLTEN_GAIA));
//  }
//// TODO
////  public static Block blockFrom(FluidEntry<ForgeFlowingFluid.Flowing> f) {
////    return ForgeRegistries.BLOCKS.getValue(f.get().getSource());
////  }
//
//  public static ItemColor colorOf(MaterialColor c) {
//    return (pStack, pTintIndex) -> c.calculateRGBColor(MaterialColor.Brightness.HIGH);
//  }
//  public static BlockColor colorOfBlock(MaterialColor c) { return (pState, pLevel, pPos, pTintIndex) -> c.calculateRGBColor(MaterialColor.Brightness.HIGH); }
//  public static void initItemColors() {
//    if (!ITEM_COLORS.isEmpty()) return;
//    var purple = colorOf(MaterialColor.COLOR_PURPLE);
//    var cyan = colorOf(MaterialColor.COLOR_CYAN);
//    var wart = colorOf(MaterialColor.WARPED_WART_BLOCK);
//    var iron = colorOf(MaterialColor.TERRACOTTA_RED);
//    var copper = colorOf(MaterialColor.COLOR_ORANGE);
//    var gold = colorOf(MaterialColor.GOLD);
//    var zinc = colorOf(MaterialColor.COLOR_GRAY);
//    var andesite = colorOf(MaterialColor.COLOR_GRAY);
//    var brass = colorOf(MaterialColor.COLOR_YELLOW);
//    var mana = colorOf(MaterialColor.COLOR_BLUE);
//    var terra = colorOf(MaterialColor.COLOR_GREEN);
//    var elem = colorOf(MaterialColor.COLOR_PINK);
//    var gaia = colorOf(MaterialColor.COLOR_LIGHT_GRAY);
//    registerItemColors(purple, () -> Fluids.CORRUPT_MANA.get().getBucket());
//    registerItemColors(cyan, () -> Fluids.PURE_MANA.get().getBucket());
//    registerItemColors(wart, () -> Fluids.REAL_MANA.get().getBucket());
//    registerItemColors(gold, () -> Fluids.MOLTEN_GOLD.get().getBucket());
//    registerItemColors(iron, () -> Fluids.MOLTEN_IRON.get().getBucket());
//    registerItemColors(copper, () -> Fluids.MOLTEN_COPPER.get().getBucket());
//    registerItemColors(zinc, () -> Fluids.MOLTEN_ZINC.get().getBucket());
//    registerItemColors(andesite, () -> Fluids.MOLTEN_ANDESITE.get().getBucket());
//    registerItemColors(brass, () -> Fluids.MOLTEN_BRASS.get().getBucket());
//    registerItemColors(mana, () -> Fluids.MOLTEN_MANASTEEL.get().getBucket());
//    registerItemColors(terra, () -> Fluids.MOLTEN_TERRASTEEL.get().getBucket());
//    registerItemColors(elem, () -> Fluids.MOLTEN_ELEMENTIUM.get().getBucket());
//    registerItemColors(gaia, () -> Fluids.MOLTEN_GAIA.get().getBucket());
//  }
//}
