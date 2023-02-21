package zaftnotameni.creatania.registry;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.tterrag.registrate.util.entry.FluidEntry;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zaftnotameni.creatania.util.Log;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static net.minecraft.sounds.SoundEvents.HONEY_BLOCK_PLACE;
import static net.minecraft.sounds.SoundEvents.HONEY_DRINK;
import static zaftnotameni.creatania.util.Humanity.digestResource;
import static zaftnotameni.creatania.util.Humanity.keyResource;

public class Fluids {
  public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
  public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
  public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");
  public static final ResourceLocation LAVA_STILL_RL = new ResourceLocation("block/lava_still");
  public static final ResourceLocation LAVA_FLOWING_RL = new ResourceLocation("block/lava_flow");
  public static final ResourceLocation LAVA_OVERLAY_RL = new ResourceLocation("block/water_overlay");


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
  public static final FluidEntry<ForgeFlowingFluid.Flowing> CORRUPT_MANA = registerManaFluid("corrupt_mana", 0xff440044,Tags.Fluids.CORRUPT_MANA);
  public static final FluidEntry<ForgeFlowingFluid.Flowing> REAL_MANA = registerManaFluid("real_mana", 0xff44ffff,Tags.Fluids.REAL_MANA);

  public static JsonElement provideLangEntries() {
    var json = new JsonObject();
    Index.all().getAll(Fluid.class).forEach(entry -> json.addProperty("fluid." + keyResource(entry.getId()), digestResource(entry.getId())));
    return json;
  }
  public static FluidAttributes.Builder defaultMolten(FluidAttributes.Builder in, int color) {
    return in.density(15)
      .luminosity(4)
      .viscosity(8)
      .temperature(9000)
      .sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA)
      .overlay(LAVA_OVERLAY_RL)
      .color(color);
  }
  public static void register(IEventBus bus) {
    Log.LOGGER.debug("register fluids");
  }

  public static FluidEntry<ForgeFlowingFluid.Flowing> registerManaFluid(String name, int color, TagKey<Fluid> tag) {
    return Index.all().waterLikeFluid(name, Colored.from(color))
      .lang("Molten " + StringUtils.capitalize(name))
      .attributes(b -> defaultMolten(b, color).sound(HONEY_DRINK, HONEY_BLOCK_PLACE))
      .properties(p -> p.tickRate(5 * 20)
        .levelDecreasePerBlock(2)
        .slopeFindDistance(3)
        .explosionResistance(100f))
      .tag(Tags.Fluids.ALL_MANA, tag)
      .source(CreataniaFlowingFluidSource::new)
      .bucket()
      .model((ctx, prov) -> prov.generated(ctx::getEntry, new ResourceLocation("minecraft", "item/lava_bucket")))
      .tag(Tags.Items.tag("buckets/molten/" + name))
      .build()
      .block()
      .build()
      .register();
  }

  public static FluidEntry<ForgeFlowingFluid.Flowing> registerMoltenFluid(String name, int color) {
    return Index.all().waterLikeFluid("molten_" + name, Colored.from(color))
      .lang("Molten " + StringUtils.capitalize(name))
      .attributes(b -> defaultMolten(b, color))
      .properties(p -> p.tickRate(5 * 20)
        .levelDecreasePerBlock(2)
        .slopeFindDistance(3)
        .explosionResistance(100f))
      .tag(Tags.Fluids.MOLTEN)
      .source(CreataniaFlowingFluidSource::new)
      .bucket()
      .model((ctx, prov) -> prov.generated(ctx::getEntry, new ResourceLocation("minecraft", "item/lava_bucket")))
      .tag(Tags.Items.tag("buckets/molten/" + name))
      .build()
      .block()
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
    @Override
    public int getColor(BlockAndTintGetter world, BlockPos pos) {
      return color;
    }
  }

  public static class CreataniaFlowingFluidSource extends ForgeFlowingFluid.Source {
    public CreataniaFlowingFluidSource(Properties properties) {
      super(properties);
    }
    @Override
    public int getAmount(FluidState state) {
      return super.getAmount(state);
    }
    @Override
    public boolean isSource(FluidState state) {
      return super.isSource(state);
    }
    @Override
    public Fluid getFlowing() {
      return super.getFlowing();
    }
    @Override
    public Fluid getSource() {
      return super.getSource();
    }
    @Override
    protected boolean canConvertToSource() {
      return super.canConvertToSource();
    }
    @Override
    protected void beforeDestroyingBlock(LevelAccessor worldIn, BlockPos pos, BlockState state) {
      super.beforeDestroyingBlock(worldIn, pos, state);
    }
    @Override
    protected int getSlopeFindDistance(LevelReader worldIn) {
      return super.getSlopeFindDistance(worldIn);
    }
    @Override
    protected int getDropOff(LevelReader worldIn) {
      return super.getDropOff(worldIn);
    }
    @Override
    public Item getBucket() {
      return super.getBucket();
    }
    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockGetter level, BlockPos pos, Fluid fluidIn, Direction direction) {
      return super.canBeReplacedWith(state, level, pos, fluidIn, direction);
    }
    @Override
    public int getTickDelay(LevelReader level) {
      return super.getTickDelay(level);
    }
    @Override
    protected float getExplosionResistance() {
      return super.getExplosionResistance();
    }
    @Override
    protected BlockState createLegacyBlock(FluidState state) {
      return super.createLegacyBlock(state);
    }
    @Override
    public boolean isSame(Fluid fluidIn) {
      return super.isSame(fluidIn);
    }
    @NotNull
    @Override
    public Optional<SoundEvent> getPickupSound() {
      return super.getPickupSound();
    }
    @Override
    protected FluidAttributes createAttributes() {
      return super.createAttributes();
    }
    @Override
    protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> pBuilder) {
      super.createFluidStateDefinition(pBuilder);
    }
    @Override
    public Vec3 getFlow(BlockGetter pBlockReader, BlockPos pPos, FluidState pFluidState) {
      return super.getFlow(pBlockReader, pPos, pFluidState);
    }
    @Override
    protected boolean isSolidFace(BlockGetter pLevel, BlockPos pNeighborPos, Direction pSide) {
      return super.isSolidFace(pLevel, pNeighborPos, pSide);
    }
    @Override
    protected void spread(LevelAccessor pLevel, BlockPos pPos, FluidState pState) {
      super.spread(pLevel, pPos, pState);
    }
    @Override
    protected FluidState getNewLiquid(LevelReader pLevel, BlockPos pPos, BlockState pBlockState) {
      return super.getNewLiquid(pLevel, pPos, pBlockState);
    }
    @Override
    public FluidState getFlowing(int pLevel, boolean pFalling) {
      return super.getFlowing(pLevel, pFalling);
    }
    @Override
    public FluidState getSource(boolean pFalling) {
      return super.getSource(pFalling);
    }
    @Override
    protected void spreadTo(LevelAccessor pLevel, BlockPos pPos, BlockState pBlockState, Direction pDirection, FluidState pFluidState) {
      super.spreadTo(pLevel, pPos, pBlockState, pDirection, pFluidState);
    }
    @Override
    protected int getSlopeDistance(LevelReader pLevel, BlockPos p_76028_, int p_76029_, Direction pDirection, BlockState p_76031_, BlockPos p_76032_, Short2ObjectMap<Pair<BlockState, FluidState>> p_76033_, Short2BooleanMap p_76034_) {
      return super.getSlopeDistance(pLevel, p_76028_, p_76029_, pDirection, p_76031_, p_76032_, p_76033_, p_76034_);
    }
    @Override
    protected Map<Direction, FluidState> getSpread(LevelReader pLevel, BlockPos pPos, BlockState pState) {
      return super.getSpread(pLevel, pPos, pState);
    }
    @Override
    protected boolean canSpreadTo(BlockGetter pLevel, BlockPos pFromPos, BlockState pFromBlockState, Direction pDirection, BlockPos pToPos, BlockState pToBlockState, FluidState pToFluidState, Fluid pFluid) {
      return super.canSpreadTo(pLevel, pFromPos, pFromBlockState, pDirection, pToPos, pToBlockState, pToFluidState, pFluid);
    }
    @Override
    protected int getSpreadDelay(Level pLevel, BlockPos pPos, FluidState p_76000_, FluidState p_76001_) {
      return super.getSpreadDelay(pLevel, pPos, p_76000_, p_76001_);
    }
    @Override
    public void tick(Level pLevel, BlockPos pPos, FluidState pState) {
      super.tick(pLevel, pPos, pState);
    }
    @Override
    public float getHeight(FluidState pState, BlockGetter pLevel, BlockPos pPos) {
      return super.getHeight(pState, pLevel, pPos);
    }
    @Override
    public float getOwnHeight(FluidState pState) {
      return super.getOwnHeight(pState);
    }
    @Override
    public VoxelShape getShape(FluidState pState, BlockGetter pLevel, BlockPos pPos) {
      return super.getShape(pState, pLevel, pPos);
    }
    @Override
    public StateDefinition<Fluid, FluidState> getStateDefinition() {
      return super.getStateDefinition();
    }
    @Override
    protected void animateTick(Level pLevel, BlockPos pPos, FluidState pState, Random pRandom) {
      super.animateTick(pLevel, pPos, pState, pRandom);
    }
    @Override
    protected void randomTick(Level pLevel, BlockPos pPos, FluidState pState, Random pRandom) {
      super.randomTick(pLevel, pPos, pState, pRandom);
    }
    @Nullable
    @Override
    protected ParticleOptions getDripParticle() {
      return super.getDripParticle();
    }
    @Override
    protected boolean isRandomlyTicking() {
      return super.isRandomlyTicking();
    }
    @Override
    protected boolean isEmpty() {
      return super.isEmpty();
    }
  }
}
