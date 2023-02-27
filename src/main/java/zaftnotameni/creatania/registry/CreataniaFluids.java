package zaftnotameni.creatania.registry;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import zaftnotameni.creatania.registry.fluids.ManaFluidType;
import zaftnotameni.creatania.registry.fluids.MoltenFluidType;

import java.util.ArrayList;
import java.util.Collection;

import static zaftnotameni.creatania.Constants.MODID;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.tterrag.registrate.util.entry.FluidEntry;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.tags.TagKey;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.material.Fluid;
//import net.minecraft.world.level.material.FluidState;
//import net.minecraftforge.eventbus.api.IEventBus;
//import net.minecraftforge.fluids.ForgeFlowingFluid;
//import net.minecraftforge.registries.ForgeRegistries;
//import org.apache.commons.lang3.StringUtils;
//import zaftnotameni.creatania.util.Humanity;
//import zaftnotameni.creatania.util.Log;
//
//import static zaftnotameni.creatania.util.Fluids.specialCobblegenSpread;
//import static zaftnotameni.creatania.util.Humanity.keyResource;
//import static zaftnotameni.creatania.util.Humanity.lang;
//
//import net.minecraftforge.fluids.ForgeFlowingFluid.Properties;
//
public class CreataniaFluids extends CreataniaForgeRegistryHolder {
  public static CreataniaFluids ALL = new CreataniaFluids();
  public CreataniaFluids() {}
  public FluidEntry<ManaFluidType.Flowing> PURE_MANA = ManaFluidType.named("pure_mana");
  public FluidEntry<ManaFluidType.Flowing> CORRUPT_MANA = ManaFluidType.named("corrupt_mana");
  public FluidEntry<ManaFluidType.Flowing> REAL_MANA = ManaFluidType.named("real_mana");
  public FluidEntry<MoltenFluidType.Flowing> MOLTEN_IRON = MoltenFluidType.named("molten_iron");
  public FluidEntry<MoltenFluidType.Flowing> MOLTEN_COPPER = MoltenFluidType.named("molten_copper");
  public FluidEntry<MoltenFluidType.Flowing> MOLTEN_GOLD = MoltenFluidType.named("molten_gold");
  public FluidEntry<MoltenFluidType.Flowing> MOLTEN_ZINC = MoltenFluidType.named("molten_zinc");
  public FluidEntry<MoltenFluidType.Flowing> MOLTEN_BRASS = MoltenFluidType.named("molten_brass");
  public FluidEntry<MoltenFluidType.Flowing> MOLTEN_ANDESITE = MoltenFluidType.named("molten_andesite");
  public FluidEntry<MoltenFluidType.Flowing> MOLTEN_TERRASTEEL = MoltenFluidType.named("molten_terrasteel");
  public FluidEntry<MoltenFluidType.Flowing> MOLTEN_MANASTEEL = MoltenFluidType.named("molten_manasteel");
  public FluidEntry<MoltenFluidType.Flowing> MOLTEN_ELEMENTIUM = MoltenFluidType.named("molten_elementium");
  public FluidEntry<MoltenFluidType.Flowing> MOLTEN_GAIA = MoltenFluidType.named("molten_gaia");
  @Override
  public Collection<DeferredRegister> getDeferredRegistries() {
    var fluidTypes = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, MODID);
    fluidTypes.register("mana", ManaFluidType::create);
    fluidTypes.register("molten", MoltenFluidType::create);
    var registries = new ArrayList<DeferredRegister>();
    registries.add(fluidTypes);
    return registries;
  }

//  public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");
//  public static final ResourceLocation LAVA_STILL_RL = new ResourceLocation("block/lava_still");
//  public static final ResourceLocation LAVA_FLOWING_RL = new ResourceLocation("block/lava_flow");
//  public static final ResourceLocation LAVA_OVERLAY_RL = new ResourceLocation("block/water_overlay");
//
//
//  // molten vanilla
//  public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_GOLD = registerMoltenFluid("gold", 0xffffff00);
//  public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_IRON = registerMoltenFluid("iron", 0xffdd0000);
//  public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_COPPER = registerMoltenFluid("copper", 0xff666600);
//  // molten create
//  public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_ZINC = registerMoltenFluid("zinc", 0xff999999);
//  public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_BRASS = registerMoltenFluid("brass", 0xffdddd33);
//  public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_ANDESITE = registerMoltenFluid("andesite", 0xff666666);
//  // molten botania
//  public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_MANASTEEL = registerMoltenFluid("manasteel", 0xff000088);
//  public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_TERRASTEEL = registerMoltenFluid("terrasteel", 0xff008822);
//  public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_ELEMENTIUM = registerMoltenFluid("elementium", 0xffffaaaa);
//  public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_GAIA = registerMoltenFluid("gaia", 0xffffffff);
//  // mana
//  public static final FluidEntry<ForgeFlowingFluid.Flowing> PURE_MANA = registerManaFluid("pure_mana", 0xff11aaff, Tags.Fluids.PURE_MANA);
//  public static final FluidEntry<ForgeFlowingFluid.Flowing> CORRUPT_MANA = registerManaFluid("corrupt_mana", 0xff440044,Tags.Fluids.CORRUPT_MANA);
//  public static final FluidEntry<ForgeFlowingFluid.Flowing> REAL_MANA = registerManaFluid("real_mana", 0xff44ffff,Tags.Fluids.REAL_MANA);
//
//  public static JsonElement provideLangEntries() {
//    var json = new JsonObject();
//    Index.all().getAll(ForgeRegistries.FLUIDS.getRegistryKey()).forEach(
//      entry -> json.addProperty("fluid." + keyResource(entry.getId()),
//        Humanity.slashes(lang.get().getAutomaticName (entry, ForgeRegistries.FLUIDS.getRegistryKey()))));
//    return json;
//  }
////  public static FluidAttributes.Builder defaultMolten(FluidAttributes.Builder in, int color) {
////    return in.density(15)
////      .luminosity(15)
////      .viscosity(10)
////      .temperature(9000)
////      .sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA)
////      .overlay(LAVA_OVERLAY_RL)
////      .color(color);
////  }
//  public static void register(IEventBus bus) {
//    Log.LOGGER.debug("register fluids");
//  }
//
//  public static FluidEntry<ForgeFlowingFluid.Flowing> registerManaFluid(String name, int color, TagKey<Fluid> tag) {
//    return Index.all().waterLikeFluid(name)
//      .lang("Liquid " + StringUtils.capitalize(name))
//      .tag(Tags.Fluids.ALL_MANA, tag)
//      .source(CreataniaFlowingFluidSource::new)
//      .bucket()
//      .model((ctx, prov) -> prov.generated(ctx::getEntry, new ResourceLocation("minecraft", "item/lava_bucket")))
//      .tag(Tags.Items.tag("buckets/molten/" + name))
//      .build()
//      .block()
//      .build()
//      .register();
//  }
//
//  public static FluidEntry<ForgeFlowingFluid.Flowing> registerMoltenFluid(String name, int color) {
//    return Index.all().lavaLikeFluid("molten_" + name)
//      .lang("Molten " + StringUtils.capitalize(name))
//      .tag(Tags.Fluids.MOLTEN)
//      .source(CreataniaFlowingFluidSource::new)
//      .bucket()
//      .model((ctx, prov) -> prov.generated(ctx::getEntry, new ResourceLocation("minecraft", "item/lava_bucket")))
//      .tag(Tags.Items.tag("buckets/molten/" + name))
//      .build()
//      .block()
//      .build()
//      .register();
//  }
//  public static class CreataniaFlowingFluidFlowing extends ForgeFlowingFluid.Flowing {
//    public CreataniaFlowingFluidFlowing(Properties properties) { super(properties); }
//    @Override
//    protected void spreadTo(LevelAccessor pLevel, BlockPos pPos, BlockState pBlockState, Direction pDirection, FluidState pFluidState) {
//      if (!specialCobblegenSpread(pLevel, pPos, pBlockState, pDirection, pFluidState, this))
//        super.spreadTo(pLevel, pPos, pBlockState, pDirection, pFluidState);
//    }
//  }
//  public static class CreataniaFlowingFluidSource extends ForgeFlowingFluid.Source {
//    @Override
//    protected void spreadTo(LevelAccessor pLevel, BlockPos pPos, BlockState pBlockState, Direction pDirection, FluidState pFluidState) {
//      if (!specialCobblegenSpread(pLevel, pPos, pBlockState, pDirection, pFluidState, this))
//        super.spreadTo(pLevel, pPos, pBlockState, pDirection, pFluidState);
//    }
//    public CreataniaFlowingFluidSource(Properties properties) {
//      super(properties);
//    }
//  }
}
