package zaftnotameni.creatania.registry;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.StringUtils;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.mana.fluids.BaseFlowingManaFluid;
import zaftnotameni.creatania.util.Humanity;
import zaftnotameni.creatania.util.Log;

import java.util.function.UnaryOperator;

import static net.minecraft.sounds.SoundEvents.HONEY_DRINK;

public class Fluids {
  public static final DeferredRegister<Fluid> INDEX = DeferredRegister.create(ForgeRegistries.FLUIDS, Constants.MODID);
  public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
  public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
  public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");
  public static final ResourceLocation LAVA_STILL_RL = new ResourceLocation("block/lava_still");
  public static final ResourceLocation LAVA_FLOWING_RL = new ResourceLocation("block/lava_flow");
  public static final ResourceLocation LAVA_OVERLAY_RL = new ResourceLocation("block/water_overlay");


  public static final com.tterrag.registrate.util.entry.FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_GOLD = registerMoltenFluid("gold", 0xffffff00);
  public static final com.tterrag.registrate.util.entry.FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_IRON = registerMoltenFluid("iron", 0xffdd0000);
  public static final com.tterrag.registrate.util.entry.FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_COPPER = registerMoltenFluid("copper", 0xff666600);
  public static final com.tterrag.registrate.util.entry.FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_ZINC = registerMoltenFluid("zinc", 0xff999999);
  public static final com.tterrag.registrate.util.entry.FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_BRASS = registerMoltenFluid("brass", 0xffdddd33);
  public static final com.tterrag.registrate.util.entry.FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_ANDESITE = registerMoltenFluid("andesite", 0xff666666);
  public static final com.tterrag.registrate.util.entry.FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_MANASTEEL = registerMoltenFluid("manasteel", 0xff000088);
  public static final com.tterrag.registrate.util.entry.FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_TERRASTEEL = registerMoltenFluid("terrasteel", 0xff008822);
  public static final com.tterrag.registrate.util.entry.FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_ELEMENTIUM = registerMoltenFluid("elementium", 0xffffaaaa);
  public static final com.tterrag.registrate.util.entry.FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_GAIA = registerMoltenFluid("gaia", 0xffffffff);

  public static final FluidEntry PURIFIED_MANA_FLUID = FluidEntry.named(Constants.MANA_FLUID_BLOCK_NAME)
    .withFluidAttributesFn(a -> a
      .density(15)
      .luminosity(2)
      .viscosity(5)
      .sound(HONEY_DRINK)
      .overlay(WATER_OVERLAY_RL)
      .color(0xff11aaff))
    .withFluidPropertiesFn(p -> p.slopeFindDistance(2).levelDecreasePerBlock(2))
    .auto();
  public static final FluidEntry BOTANIA_MANA_FLUID = FluidEntry.named(Constants.BOTANIA_MANA_FLUID_BLOCK_NAME)
    .withFluidAttributesFn(a -> a
      .density(15)
      .luminosity(2)
      .viscosity(5)
      .sound(HONEY_DRINK)
      .overlay(WATER_OVERLAY_RL)
      .color(0xff44ffff))
    .withFluidPropertiesFn(p -> p.slopeFindDistance(2).levelDecreasePerBlock(2))
    .auto();

  public static final FluidEntry CORRUPT_MANA_FLUID = FluidEntry.named(Constants.CORRUPT_MANA_FLUID_BLOCK_NAME)
    .withFluidAttributesFn(a -> a
      .density(15)
      .luminosity(2)
      .viscosity(5)
      .sound(HONEY_DRINK)
      .overlay(WATER_OVERLAY_RL)
      .color(0xff440044))
    .withFluidPropertiesFn(f -> f.slopeFindDistance(2).levelDecreasePerBlock(2))
    .withBlockPropertiesFn(b -> b.color(MaterialColor.COLOR_PURPLE).noCollission().strength(100f))
    .auto();
  public static JsonElement provideLangEntries() {
    var json = new JsonObject();
    INDEX.getEntries().forEach(entry -> json.addProperty(Humanity.keyFluid(entry), Humanity.digestFluid(entry)));
    return json;
  }
  public static class FluidEntry {
    public RegistryObject<FlowingFluid> fluid;
    public ForgeFlowingFluid.Properties fluidProperties;
    public UnaryOperator<ForgeFlowingFluid.Properties> fluidPropertiesFn;
    public FluidAttributes.Builder fluidAttributesBuilder;
    public UnaryOperator<FluidAttributes.Builder> fluidAttributesFn;
    public UnaryOperator<BlockBehaviour.Properties> blockPropertiesFn;
    public BlockBehaviour.Properties blockProperties;
    public RegistryObject<FlowingFluid> flowing;
    public RegistryObject<BucketItem> bucket;
    public RegistryObject<LiquidBlock> block;
    public String name;
    public String fluidName;
    public String flowingName;
    public String bucketName;
    public String blockName;
    public FluidEntry(String name) { this.name = name; }
    public static FluidEntry named(String name) { return new FluidEntry(name); }
    public FluidEntry withFluid(RegistryObject<FlowingFluid> f) { fluid = f; return this; }
    public FluidEntry withFluidPropertiesFn(UnaryOperator<ForgeFlowingFluid.Properties> f) { fluidPropertiesFn = f; return this; }
    public FluidEntry withFluidAttributesBuilder(FluidAttributes.Builder f) { fluidAttributesBuilder = f; return this; }
    public FluidEntry withFluidAttributesFn(UnaryOperator<FluidAttributes.Builder> f) { fluidAttributesFn = f; return this; }
    public FluidEntry withBlockPropertiesFn(UnaryOperator<BlockBehaviour.Properties> f) { blockPropertiesFn = f; return this; }
    public FluidEntry withFlowing(RegistryObject<FlowingFluid> f) { flowing = f; return this; }
    public FluidEntry withBucket(RegistryObject<BucketItem> f) { bucket = f; return this; }
    public FluidEntry withBlock(RegistryObject<LiquidBlock> f) { block = f; return this; }
    public FluidEntry auto() {
      if (fluid == null) {
        fluidName = name + "_fluid";
        fluid = INDEX.register(fluidName, () -> new ForgeFlowingFluid.Source(fluidProperties));
      }
      if (flowing == null) {
        flowingName = name + "_flowing";
        flowing = INDEX.register(flowingName, () -> new BaseFlowingManaFluid(fluidProperties));
      }
      if (fluidAttributesBuilder == null) {
        fluidAttributesBuilder = FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL);
      }
      if (fluidAttributesFn != null) {
        fluidAttributesFn.apply(fluidAttributesBuilder);
      }
      if (fluidProperties == null) {
        fluidProperties = new ForgeFlowingFluid.Properties(() -> fluid.get(), () -> flowing.get(), fluidAttributesBuilder).block(() -> block.get()).bucket(() -> bucket.get());
      }
      if (fluidPropertiesFn != null) {
        fluidPropertiesFn.apply(fluidProperties);
      }
      if (bucket == null) {
        bucketName = name + "_fluid_bucket";
        bucket = Items.INDEX.register(bucketName, () -> new BucketItem(() -> fluid.get(), new Item.Properties()
          .tab(CreativeModeTabs.CREATANIA_ITEMS).stacksTo(1)));
      }
      if (blockProperties == null) {
        blockProperties = BlockBehaviour.Properties.of(Material.LAVA);
      }
      if (blockPropertiesFn == null) {
        blockPropertiesFn = x -> x;
      }
      blockProperties = blockPropertiesFn.apply(blockProperties);
      if (block == null) {
        blockName = name + "_fluid_block";
        block = Blocks.INDEX.register(blockName, () -> new LiquidBlock(() -> fluid.get(), blockProperties));
      }
      return this;
    }
  }
  public static FluidAttributes.Builder defaultMolten(FluidAttributes.Builder in, int color) {
    return in.density(15)
      .luminosity(2)
      .viscosity(8)
      .sound(SoundEvents.BUCKET_FILL_LAVA)
      .overlay(LAVA_OVERLAY_RL)
      .color(color);
  }
  public static void register(IEventBus bus) {
    Log.LOGGER.debug("register fluids");
    INDEX.register(bus);
  }

  public static com.tterrag.registrate.util.entry.FluidEntry<ForgeFlowingFluid.Flowing> registerManaFluid(String name, int color) {
    return Index.all().waterLikeFluid("molten_" + name, Colored::new)
      .lang("Molten " + StringUtils.capitalize(name))
      .attributes(b -> defaultMolten(b, color).sound(HONEY_DRINK))
      .properties(p -> p.levelDecreasePerBlock(2)
        .tickRate(5 * 20)
        .slopeFindDistance(3)
        .explosionResistance(100f))
      .tag(Tags.Fluids.MOLTEN)
      .source(ForgeFlowingFluid.Source::new)
      .bucket()
      .model((ctx, prov) -> prov.generated(ctx::getEntry, new ResourceLocation("minecraft", "item/lava_bucket")))
      .tag(Tags.Items.tag("buckets/molten/" + name))
      .build()
      .block()
      .build()
      .register();
  }

  public static com.tterrag.registrate.util.entry.FluidEntry<ForgeFlowingFluid.Flowing> registerMoltenFluid(String name, int color) {
    return Index.all().waterLikeFluid("molten_" + name, Colored::new)
      .lang("Molten " + StringUtils.capitalize(name))
      .attributes(b -> defaultMolten(b, color))
      .properties(p -> p.levelDecreasePerBlock(2)
        .tickRate(5 * 20)
        .slopeFindDistance(3)
        .explosionResistance(100f))
      .tag(Tags.Fluids.MOLTEN)
      .source(ForgeFlowingFluid.Source::new)
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
}
