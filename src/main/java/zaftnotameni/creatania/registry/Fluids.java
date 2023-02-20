package zaftnotameni.creatania.registry;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tterrag.registrate.util.entry.FluidEntry;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.apache.commons.lang3.StringUtils;
import zaftnotameni.creatania.util.Log;

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
  public static final FluidEntry<ForgeFlowingFluid.Flowing> PURE_MANA = registerManaFluid("pure_mana", 0xff11aaff);
  public static final FluidEntry<ForgeFlowingFluid.Flowing> CORRUPT_MANA = registerManaFluid("corrupt_mana", 0xff440044);
  public static final FluidEntry<ForgeFlowingFluid.Flowing> REAL_MANA = registerManaFluid("real_mana", 0xff44ffff);

  public static JsonElement provideLangEntries() {
    var json = new JsonObject();
    Index.all().getAll(Fluid.class).forEach(entry -> json.addProperty("fluid." + keyResource(entry.getId()), digestResource(entry.getId())));
    return json;
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
  }

  public static FluidEntry<ForgeFlowingFluid.Flowing> registerManaFluid(String name, int color) {
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

  public static FluidEntry<ForgeFlowingFluid.Flowing> registerMoltenFluid(String name, int color) {
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
