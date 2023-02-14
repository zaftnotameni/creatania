package zaftnotameni.creatania.registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
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
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.util.Log;

public class Fluids {
  public static final DeferredRegister<Fluid> INDEX = DeferredRegister.create(ForgeRegistries.FLUIDS, Constants.MODID);

  public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
  public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
  public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");

  public static final RegistryObject<FlowingFluid> MANA_FLUID
    = INDEX.register(Constants.MANA_FLUID, () -> new ForgeFlowingFluid.Source(Fluids.MANA_FLUID_PROPERTIES));

  public static final RegistryObject<FlowingFluid> MANA_FLOWING
    = INDEX.register(Constants.MANA_FLOWING, () -> new ForgeFlowingFluid.Flowing(Fluids.MANA_FLUID_PROPERTIES));

  public static final RegistryObject<FlowingFluid> CORRUPT_MANA_FLUID
    = INDEX.register(Constants.CORRUPT_MANA_FLUID, () -> new ForgeFlowingFluid.Source(Fluids.CORRUPT_MANA_FLUID_PROPERTIES));

  public static final RegistryObject<FlowingFluid> CORRUPT_MANA_FLOWING
    = INDEX.register(Constants.CORRUPT_MANA_FLOWING, () -> new ForgeFlowingFluid.Flowing(Fluids.CORRUPT_MANA_FLUID_PROPERTIES));

  public static final ForgeFlowingFluid.Properties MANA_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
    () -> MANA_FLUID.get(),
    () -> MANA_FLOWING.get(),
    FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
      .density(15)
      .luminosity(2)
      .viscosity(5)
      .sound(SoundEvents.HONEY_DRINK)
      .overlay(WATER_OVERLAY_RL)
      .color(0xdd11aaff))
      .slopeFindDistance(2)
      .levelDecreasePerBlock(2)
      .block(() -> Fluids.MANA_FLUID_BLOCK.get())
      .bucket(() -> Fluids.MANA_FLUID_BUCKET.get());

  public static final ForgeFlowingFluid.Properties CORRUPT_MANA_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
    () -> CORRUPT_MANA_FLUID.get(),
    () -> CORRUPT_MANA_FLOWING.get(),
    FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
      .density(15)
      .luminosity(2)
      .viscosity(5)
      .sound(SoundEvents.HONEY_DRINK)
      .overlay(WATER_OVERLAY_RL)
      .color(0xaa440044))
    .slopeFindDistance(2)
    .levelDecreasePerBlock(2)
    .block(() -> Fluids.CORRUPT_MANA_FLUID_BLOCK.get())
    .bucket(() -> Fluids.CORRUPT_MANA_FLUID_BUCKET.get());

  public static BucketItem createCorruptManaFluidBucket() {
    return new BucketItem(Fluids.CORRUPT_MANA_FLUID, new Item.Properties().tab(CreativeModeTabs.CREATANIA_ITEMS).stacksTo(1));
  }
  public static BucketItem createManaFluidBucket() {
    return new BucketItem(Fluids.MANA_FLUID, new Item.Properties().tab(CreativeModeTabs.CREATANIA_ITEMS).stacksTo(1));
  }
  public static final RegistryObject<Item> MANA_FLUID_BUCKET = Items.INDEX.register(
    Constants.MANA_FLUID_BUCKET_NAME, () -> createManaFluidBucket());
  public static final RegistryObject<Item> CORRUPT_MANA_FLUID_BUCKET = Items.INDEX.register(
    Constants.CORRUPT_MANA_FLUID_BUCKET_NAME, () -> createCorruptManaFluidBucket());

  public static LiquidBlock createCorruptManaFluidBlock() {
    return new LiquidBlock(() -> CORRUPT_MANA_FLUID.get(), BlockBehaviour.Properties
      .of(Material.LAVA)
      .color(MaterialColor.COLOR_PURPLE)
      .noCollission()
      .strength(100f));
  }
  public static LiquidBlock createManaFluidBlock() {
    return new LiquidBlock(() -> MANA_FLUID.get(), BlockBehaviour.Properties
      .of(Material.WATER)
      .color(MaterialColor.COLOR_CYAN)
      .noCollission()
      .strength(100f));
  }
  public static final RegistryObject<LiquidBlock> MANA_FLUID_BLOCK = Blocks.INDEX.register(
    Constants.MANA_FLUID_BLOCK_NAME, () -> createManaFluidBlock());
  public static final RegistryObject<LiquidBlock> CORRUPT_MANA_FLUID_BLOCK = Blocks.INDEX.register(
    Constants.CORRUPT_MANA_FLUID_BLOCK_NAME, () -> createCorruptManaFluidBlock());

  public static void register(IEventBus bus) {
    Log.LOGGER.debug("register fluids");
    INDEX.register(bus);
  }
}
