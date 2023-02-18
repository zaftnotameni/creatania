package zaftnotameni.creatania.registry;

import com.simibubi.create.content.contraptions.base.HalfShaftInstance;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.minecraftforge.eventbus.api.IEventBus;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.manaiaccreate.omnibox.OmniboxBlockEntity;
import zaftnotameni.creatania.manaiaccreate.omnibox.OmniboxRenderer;
import zaftnotameni.creatania.manatoitem.manacondenser.ManaCondenserBlockEntity;
import zaftnotameni.creatania.manatoitem.manacondenser.ManaCondenserRenderer;
import zaftnotameni.creatania.manatosu.manamotor.ManaMotorBlockEntity;
import zaftnotameni.creatania.manatosu.manamotor.ManaMotorRenderer;
import zaftnotameni.creatania.redstone.xorlever.XorLeverBlockEntity;
import zaftnotameni.creatania.redstone.xorlever.XorLeverInstance;
import zaftnotameni.creatania.redstone.xorlever.XorLeverRenderer;
import zaftnotameni.creatania.sutomana.managenerator.ManaGeneratorBlockEntity;
import zaftnotameni.creatania.sutomana.managenerator.ManaGeneratorRenderer;
import zaftnotameni.creatania.util.Log;

public class BlockEntities {
  public static final CreateRegistrate CREATE_REGISTRATE = Index.getCreateRegistrate()
    .creativeModeTab(() -> CreativeModeTabs.CREATANIA_ITEMS);

  public static final BlockEntityEntry<ManaMotorBlockEntity> MANA_MOTOR_BLOCK_ENTITY = CREATE_REGISTRATE
    .tileEntity(Constants.MANA_MOTOR_BLOCK_ENTITY, ManaMotorBlockEntity::new)
    .instance(() -> HalfShaftInstance::new)
    .validBlocks(Blocks.MANA_MOTOR)
    .renderer(() -> ManaMotorRenderer::new)
    .register();

  public static final BlockEntityEntry<ManaGeneratorBlockEntity> MANA_GENERATOR_BLOCK_ENTITY = CREATE_REGISTRATE
    .tileEntity(Constants.MANA_GENERATOR_BLOCK_ENTITY, ManaGeneratorBlockEntity::new)
    .instance(() -> HalfShaftInstance::new)
    .validBlocks(Blocks.MANA_GENERATOR)
    .renderer(() -> ManaGeneratorRenderer::new)
    .register();

  public static final BlockEntityEntry<ManaCondenserBlockEntity> MANA_CONDENSER_BLOCK_ENTITY = CREATE_REGISTRATE
    .tileEntity(Constants.MANA_CONDENSER_BLOCK_ENTITY, ManaCondenserBlockEntity::new)
    .instance(() -> HalfShaftInstance::new)
    .validBlocks(Blocks.MANA_CONDENSER)
    .renderer(() -> ManaCondenserRenderer::new)
    .register();

  public static final BlockEntityEntry<OmniboxBlockEntity> OMNIBOX_BLOCK_ENTITY = CREATE_REGISTRATE
    .tileEntity(Constants.OMNIBOX_BLOCK_ENTITY, OmniboxBlockEntity::new)
    .instance(() -> HalfShaftInstance::new)
    .validBlocks(Blocks.OMNIBOX)
    .renderer(() -> OmniboxRenderer::new)
    .register();

  public static final BlockEntityEntry<XorLeverBlockEntity> XOR_LEVER_BLOCK_ENTITY = CREATE_REGISTRATE
    .tileEntity(Constants.XOR_LEVER_BLOCK_ENTITY, XorLeverBlockEntity::new)
    .instance(() -> XorLeverInstance::new, false)
    .validBlocks(Blocks.XOR_LEVER)
    .renderer(() -> XorLeverRenderer::new)
    .register();

  public static void register(IEventBus eventBus) {
      Log.LOGGER.debug("register block entities");
    }
}
