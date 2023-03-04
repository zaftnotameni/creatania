package zaftnotameni.creatania.registry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tterrag.registrate.util.entry.FluidEntry;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import zaftnotameni.creatania.util.Humanity;
import zaftnotameni.creatania.util.Log;

import static net.minecraft.sounds.SoundEvents.HONEY_BLOCK_PLACE;
import static net.minecraft.sounds.SoundEvents.HONEY_DRINK;
import static zaftnotameni.creatania.util.Fluids.specialCobblegenCanSpread;
import static zaftnotameni.creatania.util.Fluids.specialCobblegenSpread;
import static zaftnotameni.creatania.util.Humanity.keyResource;
import static zaftnotameni.creatania.util.Humanity.lang;

public class Fluids {

  public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
  public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
  public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");
  public static final ResourceLocation LAVA_STILL_RL = new ResourceLocation("block/lava_still");
  public static final ResourceLocation LAVA_FLOWING_RL = new ResourceLocation("block/lava_flow");
  public static final ResourceLocation LAVA_OVERLAY_RL = new ResourceLocation("block/water_overlay");

  public static final ResourceLocation MOLTEN_STILL_RL = Index.resource("fluid/molten_still");
  public static final ResourceLocation MOLTEN_FLOWING_RL = Index.resource("fluid/molten_flow");

  public static final ResourceLocation MANA_STILL_RL = Index.resource("fluid/mana_still");
  public static final ResourceLocation MANA_FLOWING_RL = Index.resource("fluid/mana_flow");

  // molten vanilla
  public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_GOLD = registerMoltenFluid("gold", 0xffffff00);
  public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_IRON = registerMoltenFluid("iron", 0xffdd0000);
  public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_COPPER = registerMoltenFluid("copper", 0xff666600);
  // molten create
  public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_ZINC = registerMoltenFluid("zinc", 0xff999999);
  public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_BRASS = registerMoltenFluid("brass", 0xffdddd33);
  public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_ANDESITE = registerMoltenFluid("andesite", 0xff666666);
  // molten botania
  public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_MANASTEEL = registerMoltenFluid("manasteel", 0xff000088);
  public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_TERRASTEEL = registerMoltenFluid("terrasteel", 0xff008822);
  public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_ELEMENTIUM = registerMoltenFluid("elementium", 0xffffaaaa);
  public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_GAIA = registerMoltenFluid("gaia", 0xffffffff);
  // mana
  public static final FluidEntry<ForgeFlowingFluid.Flowing> PURE_MANA = registerManaFluid("pure_mana", 0xff11aaff, Tags.Fluids.PURE_MANA);
  public static final FluidEntry<ForgeFlowingFluid.Flowing> CORRUPT_MANA = registerManaFluid("corrupt_mana", 0xff440044, Tags.Fluids.CORRUPT_MANA);
  public static final FluidEntry<ForgeFlowingFluid.Flowing> REAL_MANA = registerManaFluid("real_mana", 0xff44ffff, Tags.Fluids.REAL_MANA);

  public static JsonElement provideLangEntries() {
    var json = new JsonObject();
    Index.all().getAll(Fluid.class).forEach(entry -> json.addProperty("fluid." + keyResource(entry.getId()), Humanity.slashes(lang.get().getAutomaticName(entry))));
    return json;
  }

  public static FluidAttributes.Builder defaultMolten(FluidAttributes.Builder in, int color) {
    return in.density(15).luminosity(15).viscosity(10).temperature(9000).sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA).overlay(LAVA_OVERLAY_RL).color(color);
  }

  public static void register(IEventBus bus) {
    Log.LOGGER.debug("register fluids");
  }

  public static FluidEntry<ForgeFlowingFluid.Flowing> registerManaFluid(String name, int color, TagKey<Fluid> tag) {
    return Index.all()
      .waterLikeFluid(name, Colored.from(color))
      .lang("Liquid " + StringUtils.capitalize(name))
      .attributes(b -> defaultMolten(b, color).sound(HONEY_DRINK, HONEY_BLOCK_PLACE))
      .properties(p -> p.tickRate(1 * 20).levelDecreasePerBlock(2).slopeFindDistance(2).explosionResistance(100f))
      .tag(Tags.Fluids.ALL_MANA, tag, Tags.Fluids.minecraftTag("water"))
      .source(CreataniaFlowingFluidSource::new)
      .tag(Tags.Fluids.minecraftTag("water"))
      .bucket()
      .model((ctx, prov) -> prov.generated(ctx::getEntry, Index.resource("fluid/mana_bucket")))
      .tag(Tags.Items.tag("buckets/mana/" + name), Tags.Items.minecraftTag("water"))
      .build()
      .block()
      .tag(Tags.Blocks.minecraftTag("water"))
      .build()
      .register();
  }

  public static FluidEntry<ForgeFlowingFluid.Flowing> registerMoltenFluid(String name, int color) {
    return Index.all()
      .lavaLikeFluid("molten_" + name, Colored.from(color))
      .lang("Molten " + StringUtils.capitalize(name))
      .attributes(b -> defaultMolten(b, color))
      .properties(p -> p.tickRate(5 * 20)
        .levelDecreasePerBlock(3)
        .slopeFindDistance(2)
        .explosionResistance(100f))
      .tag(Tags.Fluids.MOLTEN, Tags.Fluids.minecraftTag("water"))
      .source(CreataniaFlowingFluidSource::new)
      .tag(Tags.Fluids.minecraftTag("water"))
      .bucket()
      .model((ctx, prov) -> prov.generated(ctx::getEntry, Index.resource("fluid/molten_bucket")))
      .tag(Tags.Items.tag("buckets/molten/" + name), Tags.Items.minecraftTag("water"))
      .build()
      .block()
      .tag(Tags.Blocks.minecraftTag("water"))
      .build()
      .register();
  }

  public static class Colored extends FluidAttributes {

    public Colored(Builder builder, Fluid fluid) { super(builder, fluid); }

    public int color = 0x00ffffff;

    public static NonNullBiFunction<Builder, Fluid, FluidAttributes> from(int color) {
      return (b, f) -> {
        var self = new Colored(b, f);
        self.color = color;
        return self;
      };
    }

    @Override public int getColor(BlockAndTintGetter world, BlockPos pos) {
      return color;
    }

  }

  public static class CreataniaFlowingFluidFlowing extends ForgeFlowingFluid.Flowing {

    public CreataniaFlowingFluidFlowing(Properties properties) { super(properties); }

    @Override protected boolean canSpreadTo(
      @NotNull BlockGetter pLevel,
      @NotNull BlockPos pFromPos,
      @NotNull BlockState pFromBlockState,
      @NotNull Direction pDirection,
      @NotNull BlockPos pToPos,
      @NotNull BlockState pToBlockState,
      @NotNull FluidState pToFluidState,
      @NotNull Fluid pFluid
    ) {
      return specialCobblegenCanSpread(pLevel, pFromPos, pFromBlockState, pDirection, pToPos, pToBlockState, pToFluidState, pFluid, this) && super.canSpreadTo(
        pLevel,
        pFromPos,
        pFromBlockState,
        pDirection,
        pToPos,
        pToBlockState,
        pToFluidState,
        pFluid
      );
    }

    @Override protected void spreadTo(@NotNull LevelAccessor pLevel, @NotNull BlockPos pPos, @NotNull BlockState pBlockState, @NotNull Direction pDirection, @NotNull FluidState pFluidState) {
      if (!specialCobblegenSpread(pLevel, pPos, pBlockState, pDirection, pFluidState, this)) { super.spreadTo(pLevel, pPos, pBlockState, pDirection, pFluidState); }
    }

  }

  public static class CreataniaFlowingFluidSource extends ForgeFlowingFluid.Source {

    @Override protected boolean canSpreadTo(
      @NotNull BlockGetter pLevel,
      @NotNull BlockPos pFromPos,
      @NotNull BlockState pFromBlockState,
      @NotNull Direction pDirection,
      @NotNull BlockPos pToPos,
      @NotNull BlockState pToBlockState,
      @NotNull FluidState pToFluidState,
      @NotNull Fluid pFluid
    ) {
      return specialCobblegenCanSpread(pLevel, pFromPos, pFromBlockState, pDirection, pToPos, pToBlockState, pToFluidState, pFluid, this) && super.canSpreadTo(
        pLevel,
        pFromPos,
        pFromBlockState,
        pDirection,
        pToPos,
        pToBlockState,
        pToFluidState,
        pFluid
      );
    }

    @Override protected void spreadTo(@NotNull LevelAccessor pLevel, @NotNull BlockPos pPos, @NotNull BlockState pBlockState, @NotNull Direction pDirection, @NotNull FluidState pFluidState) {
      if (!specialCobblegenSpread(pLevel, pPos, pBlockState, pDirection, pFluidState, this)) { super.spreadTo(pLevel, pPos, pBlockState, pDirection, pFluidState); }
    }

    public CreataniaFlowingFluidSource(Properties properties) {
      super(properties);
    }

  }

}