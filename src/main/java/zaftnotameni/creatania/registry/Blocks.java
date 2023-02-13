package zaftnotameni.creatania.registry;

import com.mojang.datafixers.kinds.Const;
import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.block.BlockStressDefaults;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.manatoitem.CorruptedManaBlock;
import zaftnotameni.creatania.manatoitem.PurifiedManaBlock;
import zaftnotameni.creatania.manatoitem.manacondenser.ManaCondenserBlock;
import zaftnotameni.creatania.manatosu.manamotor.ManaMotorBlock;
import zaftnotameni.creatania.sutomana.managenerator.ManaGeneratorBlock;
import zaftnotameni.creatania.util.Log;

import java.util.function.Function;
import java.util.function.Supplier;

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

  public static final RegistryObject<CorruptedManaBlock> CORRUPTED_INERT_MANA_BLOCK = registerBlockWithItem(
    Constants.CORRUPTED_INERT_MANA_BLOCK,
    () -> new CorruptedManaBlock(BlockBehaviour.Properties.of(Material.STONE).destroyTime(5f)));

  public static final RegistryObject<PurifiedManaBlock> PURIFIED_INERT_MANA_BLOCK = registerBlockWithItem(
    Constants.PURIFIED_INERT_MANA_BLOCK,
    () -> new PurifiedManaBlock(BlockBehaviour.Properties.of(Material.STONE).destroyTime(1f)));

  public static final RegistryObject<PurifiedManaBlock> MANA_MACHINE_COMPONENT = registerBlockWithItem(
    Constants.MANA_MACHINE_COMPONENT,
    () -> new PurifiedManaBlock(BlockBehaviour.Properties.of(Material.STONE).destroyTime(1f)));

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
}
