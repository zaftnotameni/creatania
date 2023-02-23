package zaftnotameni.creatania.util;
import com.simibubi.create.content.palettes.AllPaletteStoneTypes;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.extensions.IForgeBlockState;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.util.Arrays;
import java.util.Optional;

import static com.simibubi.create.content.palettes.AllPaletteStoneTypes.*;
import static zaftnotameni.creatania.registry.Fluids.*;
import static zaftnotameni.creatania.registry.Tags.Fluids.ALL_MANA;
import static zaftnotameni.creatania.registry.Tags.Fluids.MOLTEN;
public class Fluids {
  public static boolean isMatch(Fluid ta, Fluid tb, FluidState...fs) {
    var match = matching(ta, tb, fs);
    return match.getA().isPresent() && match.getB().isPresent();
  }
  public static boolean isSourceMatchA(Fluid ta, Fluid tb, FluidState ...fs) {
    var match = matching(ta, tb, fs);
    return match.getA().isPresent() && match.getB().isPresent() && match.getA().get().isSource();
  }
  public static boolean isSourceMatchB(Fluid ta, Fluid tb, FluidState ...fs) {
    var match = matching(ta, tb, fs);
    return match.getA().isPresent() && match.getB().isPresent() && match.getB().get().isSource();
  }
  public static Tuple<Optional<FluidState>, Optional<FluidState>> matching(Fluid target, FluidState ...fs) {
    var first = Arrays.stream(fs).filter(x -> x.is(target)).findFirst();
    var second = Arrays.stream(fs).filter(x -> !x.is(target)).findFirst();
    return new Tuple<>(first, second);
  }
  public static Tuple<Optional<FluidState>, Optional<FluidState>> matching(Fluid ta, Fluid tb, FluidState ...fs) {
    var first = Arrays.stream(fs).filter(x -> x.is(ta)).findFirst();
    var second = Arrays.stream(fs).filter(x -> !x.is(tb)).findFirst();
    return new Tuple<>(first, second);
  }

  public static String nameOf(FluidEntry<ForgeFlowingFluid.Flowing> f) {
    return f.get().getSource().getRegistryName().toString();
  }
  public static String abc(FluidEntry<ForgeFlowingFluid.Flowing> a, FluidEntry<ForgeFlowingFluid.Flowing> b, AllPaletteStoneTypes c) {
    return nameOf(a) + "+" + nameOf(b) + "=" + c.getBaseBlock().get().getDescriptionId();
  }
  public static String defaultFlowingGen() {
    String newLine = System.getProperty("line.separator");
    return String.join(newLine,
      abc(PURE_MANA, MOLTEN_COPPER, GRANITE),
       abc(PURE_MANA, MOLTEN_ZINC, DIORITE),
       abc(PURE_MANA, MOLTEN_ANDESITE, ANDESITE),
       abc(PURE_MANA, MOLTEN_GOLD, CALCITE),
       abc(PURE_MANA, MOLTEN_IRON, DRIPSTONE),
       abc(PURE_MANA, MOLTEN_BRASS, DEEPSLATE),
       abc(PURE_MANA, MOLTEN_MANASTEEL, TUFF));
  }
  public static IForgeBlockState fluidInteractionOrNull(FluidState fa, FluidState fb) {
    if (isSourceMatchB(PURE_MANA.get(), MOLTEN_COPPER.get(), fa, fb)) return VERIDIUM.getBaseBlock().get().defaultBlockState();
    if (isSourceMatchB(PURE_MANA.get(), MOLTEN_ZINC.get(), fa, fb)) return LIMESTONE.getBaseBlock().get().defaultBlockState();
    if (isSourceMatchB(PURE_MANA.get(), MOLTEN_ANDESITE.get(), fa, fb)) return SCORCHIA.getBaseBlock().get().defaultBlockState();
    if (isSourceMatchB(PURE_MANA.get(), MOLTEN_GOLD.get(), fa, fb)) return OCHRUM.getBaseBlock().get().defaultBlockState();
    if (isSourceMatchB(PURE_MANA.get(), MOLTEN_IRON.get(), fa, fb)) return CRIMSITE.getBaseBlock().get().defaultBlockState();
    if (isSourceMatchB(PURE_MANA.get(), MOLTEN_BRASS.get(), fa, fb)) return SCORIA.getBaseBlock().get().defaultBlockState();
    if (isSourceMatchB(PURE_MANA.get(), MOLTEN_MANASTEEL.get(), fa, fb)) return ASURINE.getBaseBlock().get().defaultBlockState();

    if (isMatch(PURE_MANA.get(), MOLTEN_COPPER.get(), fa, fb)) return GRANITE.getBaseBlock().get().defaultBlockState();
    if (isMatch(PURE_MANA.get(), MOLTEN_ZINC.get(), fa, fb)) return DIORITE.getBaseBlock().get().defaultBlockState();
    if (isMatch(PURE_MANA.get(), MOLTEN_ANDESITE.get(), fa, fb)) return ANDESITE.getBaseBlock().get().defaultBlockState();
    if (isMatch(PURE_MANA.get(), MOLTEN_GOLD.get(), fa, fb)) return CALCITE.getBaseBlock().get().defaultBlockState();
    if (isMatch(PURE_MANA.get(), MOLTEN_IRON.get(), fa, fb)) return DRIPSTONE.getBaseBlock().get().defaultBlockState();
    if (isMatch(PURE_MANA.get(), MOLTEN_BRASS.get(), fa, fb)) return DEEPSLATE.getBaseBlock().get().defaultBlockState();
    if (isMatch(PURE_MANA.get(), MOLTEN_MANASTEEL.get(), fa, fb)) return TUFF.getBaseBlock().get().defaultBlockState();

    return null;
  }
  public static boolean specialFluidInteraction(BlockGetter pLevel, BlockState pFromBlockState, BlockPos pToPos, FluidState pToFluidState) {
    FluidState fluidA = pFromBlockState.getFluidState();
    FluidState fluidB = pToFluidState;
    if (!(pLevel instanceof ServerLevel serverLevel) || !(fluidA.is(MOLTEN) || fluidA.is(ALL_MANA))) return false;
    if (!(fluidInteractionOrNull(fluidA, fluidB) instanceof BlockState resultingBlockState)) return false;
    serverLevel.setBlockAndUpdate(pToPos, resultingBlockState);
    return true;
  }
}
