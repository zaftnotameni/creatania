package zaftnotameni.creatania.registry;

import com.simibubi.create.content.contraptions.base.HalfShaftInstance;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.minecraftforge.eventbus.api.IEventBus;
import zaftnotameni.creatania.machines.manacondenser.ManaCondenserBlockEntity;
import zaftnotameni.creatania.machines.manacondenser.ManaCondenserRenderer;
import zaftnotameni.creatania.machines.managenerator.ManaGeneratorBlockEntity;
import zaftnotameni.creatania.machines.managenerator.ManaGeneratorRenderer;
import zaftnotameni.creatania.machines.manamotor.ManaMotorBlockEntity;
import zaftnotameni.creatania.machines.manamotor.ManaMotorRenderer;
import zaftnotameni.creatania.mana.flowers.blazunia.BlazuniaFunctionalFlowerBlockEntity;
import zaftnotameni.creatania.stress.omnibox.OmniboxBlockEntity;
import zaftnotameni.creatania.stress.omnibox.OmniboxRenderer;
import zaftnotameni.creatania.stress.xorlever.XorLeverBlockEntity;
import zaftnotameni.creatania.stress.xorlever.XorLeverInstance;
import zaftnotameni.creatania.stress.xorlever.XorLeverRenderer;

public class BlockEntities {
  public static final CreateRegistrate CREATE_REGISTRATE = Index.getCreateRegistrate()
    .creativeModeTab(() -> CreativeModeTabs.CREATANIA_ITEMS);

  public static final BlockEntityEntry<ManaMotorBlockEntity> MANA_MOTOR_BLOCK_ENTITY = CREATE_REGISTRATE
    .tileEntity("mana_motor_block_entity", ManaMotorBlockEntity::new)
    .instance(() -> HalfShaftInstance::new)
    .validBlocks(Blocks.MANA_MOTOR)
    .renderer(() -> ManaMotorRenderer::new)
    .register();

  public static final BlockEntityEntry<ManaGeneratorBlockEntity> MANA_GENERATOR_BLOCK_ENTITY = CREATE_REGISTRATE
    .tileEntity("mana_generator_block_entity", ManaGeneratorBlockEntity::new)
    .instance(() -> HalfShaftInstance::new)
    .validBlocks(Blocks.MANA_GENERATOR)
    .renderer(() -> ManaGeneratorRenderer::new)
    .register();

  public static final BlockEntityEntry<ManaCondenserBlockEntity> MANA_CONDENSER_BLOCK_ENTITY = CREATE_REGISTRATE
    .tileEntity("mana_condenser_block_entity", ManaCondenserBlockEntity::new)
    .instance(() -> HalfShaftInstance::new)
    .validBlocks(Blocks.MANA_CONDENSER)
    .renderer(() -> ManaCondenserRenderer::new)
    .register();

  public static final BlockEntityEntry<OmniboxBlockEntity> OMNIBOX_BLOCK_ENTITY = CREATE_REGISTRATE
    .tileEntity("omnibox_block_entity", OmniboxBlockEntity::new)
    .instance(() -> HalfShaftInstance::new)
    .validBlocks(Blocks.OMNIBOX)
    .renderer(() -> OmniboxRenderer::new)
    .register();

  public static final BlockEntityEntry<XorLeverBlockEntity> XOR_LEVER_BLOCK_ENTITY = CREATE_REGISTRATE
    .tileEntity("xor_lever_block_entity", XorLeverBlockEntity::new)
    .instance(() -> XorLeverInstance::new, false)
    .validBlocks(Blocks.XOR_LEVER)
    .renderer(() -> XorLeverRenderer::new)
    .register();

  public static final BlockEntityEntry<BlazuniaFunctionalFlowerBlockEntity> BLAZUNIA_BLOCK_ENTITY = CREATE_REGISTRATE
    .tileEntity("blazunia_block_entity", BlazuniaFunctionalFlowerBlockEntity::new)
    .validBlocks(Blocks.BLAZUNIA_BLOCK)
    .register();

  public static void register(IEventBus eventBus) {
      log(l -> l.debug("register block entities");
    }
}