package zaftnotameni.creatania.registry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.relays.encased.EncasedCTBehaviour;
import com.simibubi.create.content.contraptions.relays.gearbox.GearboxBlock;
import com.simibubi.create.foundation.block.BlockStressDefaults;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.StringUtils;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.manaiaccreate.omnibox.OmniboxBlock;
import zaftnotameni.creatania.manatoitem.BotaniaManaBlock;
import zaftnotameni.creatania.manatoitem.CorruptedManaBlock;
import zaftnotameni.creatania.manatoitem.PurifiedManaBlock;
import zaftnotameni.creatania.manatoitem.manacondenser.ManaCondenserBlock;
import zaftnotameni.creatania.manatosu.manamotor.ManaMotorBlock;
import zaftnotameni.creatania.sutomana.manaduct.TerrasteelManaductBlock;
import zaftnotameni.creatania.sutomana.managenerator.ManaGeneratorBlock;
import zaftnotameni.creatania.util.Humanity;
import zaftnotameni.creatania.util.Log;
import zaftnotameni.sharedbehaviors.ManaMachineComponentBlock;

import java.util.function.Supplier;

import static com.simibubi.create.Create.REGISTRATE;
import static com.simibubi.create.foundation.data.BlockStateGen.axisBlock;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;

public class Blocks {
  public static final DeferredRegister<Block> INDEX = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MODID);

  public static final CreateRegistrate CREATE_REGISTRATE = Index.getCreateRegistrate()
    .creativeModeTab(() -> CreativeModeTabs.CREATANIA_ITEMS);

  public static final BlockEntry<ManaMotorBlock> MANA_MOTOR = CREATE_REGISTRATE
    .block(Constants.MANA_MOTOR, ManaMotorBlock::new)
    .initialProperties(SharedProperties::stone)
    .blockstate(BlockStateGen.directionalAxisBlockProvider())
    .addLayer(() -> RenderType::cutoutMipped)
    .transform(axeOrPickaxe())
    .transform(BlockStressDefaults.setCapacity(CommonConfig.MANA_MOTOR_SU_PER_RPM.get()))
    .item()
    .transform(customItemModel())
    .register();

  public static final BlockEntry<ManaGeneratorBlock> MANA_GENERATOR = CREATE_REGISTRATE
    .block(Constants.MANA_GENERATOR, ManaGeneratorBlock::new)
    .initialProperties(SharedProperties::softMetal)
    .blockstate(BlockStateGen.directionalAxisBlockProvider())
    .addLayer(() -> RenderType::cutoutMipped)
    .transform(axeOrPickaxe())
    .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
    .transform(BlockStressDefaults.setImpact(CommonConfig.MANA_GENERATOR_SU_PER_RPM.get()))
    .item()
    .transform(customItemModel())
    .register();

  public static final BlockEntry<ManaCondenserBlock> MANA_CONDENSER = CREATE_REGISTRATE
    .block(Constants.MANA_CONDENSER, ManaCondenserBlock::new)
    .initialProperties(SharedProperties::softMetal)
    .blockstate(BlockStateGen.directionalAxisBlockProvider())
    .addLayer(() -> RenderType::cutoutMipped)
    .transform(axeOrPickaxe())
    .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
    .transform(BlockStressDefaults.setImpact(CommonConfig.MANA_CONDENSER_SU_PER_RPM.get()))
    .item()
    .transform(customItemModel())
    .register();

  public static final BlockEntry<OmniboxBlock> OMNIBOX = CREATE_REGISTRATE
    .block(Constants.OMNIBOX, OmniboxBlock::new)
    .initialProperties(SharedProperties::stone)
    // .blockstate(BlockStateGen.simpleCubeAll(Constants.OMNIBOX))
    .properties(BlockBehaviour.Properties::noOcclusion)
    .properties(p -> p.color(MaterialColor.PODZOL))
    .transform(BlockStressDefaults.setNoImpact())
    .transform(axeOrPickaxe())
    .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
    .item()
    .transform(customItemModel())
    .register();

  public static final RegistryObject<TerrasteelManaductBlock> TERRASTEEL_MANADUCT_BLOCK = registerBlockWithItem(
    Constants.TERRASTEEL_MANA_DUCT_BLOCK,
    () -> new TerrasteelManaductBlock(BlockBehaviour.Properties.of(Material.STONE).destroyTime(1f)));

  public static final RegistryObject<CorruptedManaBlock> CORRUPTED_INERT_MANA_BLOCK = registerBlockWithItem(
    Constants.CORRUPTED_INERT_MANA_BLOCK,
    () -> new CorruptedManaBlock(BlockBehaviour.Properties.of(Material.STONE).destroyTime(10f)));

  public static final RegistryObject<PurifiedManaBlock> PURIFIED_INERT_MANA_BLOCK = registerBlockWithItem(
    Constants.PURIFIED_INERT_MANA_BLOCK,
    () -> new PurifiedManaBlock(BlockBehaviour.Properties.of(Material.STONE).destroyTime(1f)));

  public static final RegistryObject<BotaniaManaBlock> BOTANIA_MANA_BLOCK = registerBlockWithItem(
    Constants.BOTANIA_MANA_BLOCK,
    () -> new BotaniaManaBlock(BlockBehaviour.Properties.of(Material.GLASS).destroyTime(1f)));

  public static final RegistryObject<ManaMachineComponentBlock> MANA_MACHINE_COMPONENT = registerBlockWithItem(
    Constants.MANA_MACHINE_COMPONENT,
    () -> new ManaMachineComponentBlock(BlockBehaviour.Properties.of(Material.STONE).destroyTime(1f)));

  public static <T extends Block> RegistryObject<T> registerBlockWithItem(String name, Supplier<T> createBlock) {
    RegistryObject<T> block = INDEX.register(name, createBlock);
    registerBlockItem(name, block, CreativeModeTabs.CREATANIA_ITEMS);
    return block;
  }
  private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
    return Items.INDEX.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
  }

  public static void register(IEventBus bus) {
    Log.LOGGER.debug("register blocks");
    INDEX.register(bus);
  }

  public static JsonElement provideLangEntries() {
    var json = new JsonObject();
    INDEX.getEntries().forEach(entry -> json.addProperty(Humanity.keyBlock(entry), Humanity.digestBlock(entry)));
    return json;
  }

  public static class Partials {
    public static final PartialModel MANA_MOTOR_FAN = block("mana_motor/mana_motor_fan");
    private static PartialModel block(String path) {
      return new PartialModel(new ResourceLocation(Constants.MODID + ":block/" + path));
    }

    public static void init() {}
  }
}
