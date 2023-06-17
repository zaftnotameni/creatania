package zaftnotameni.creatania.registry;

import com.simibubi.create.content.kinetics.base.HalfShaftInstance;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.minecraftforge.eventbus.api.IEventBus;
import zaftnotameni.creatania.machines.manacondenser.ManaCondenserBlockEntity;
import zaftnotameni.creatania.machines.manacondenser.ManaCondenserRenderer;
import zaftnotameni.creatania.machines.managenerator.ManaGeneratorBlockEntity;
import zaftnotameni.creatania.machines.managenerator.ManaGeneratorRenderer;
import zaftnotameni.creatania.machines.manamotor.ManaMotorBlockEntity;
import zaftnotameni.creatania.machines.manamotor.ManaMotorRenderer;
import zaftnotameni.creatania.mana.flowers.blazunia.BlazuniaFunctionalFlowerBlockEntity;
import zaftnotameni.creatania.mana.flowers.blazunia.BlazuniaRenderer;
import zaftnotameni.creatania.stress.omnibox.OmniboxBlockEntity;
import zaftnotameni.creatania.stress.omnibox.OmniboxInstance;
import zaftnotameni.creatania.stress.omnibox.OmniboxRenderer;
import zaftnotameni.creatania.stress.xorlever.XorLeverBlockEntity;
import zaftnotameni.creatania.stress.xorlever.XorLeverInstance;
import zaftnotameni.creatania.stress.xorlever.XorLeverRenderer;

import static zaftnotameni.creatania.util.LogKt.log;

public class BlockEntities {

  public static final BlockEntityEntry<ManaMotorBlockEntity> MANA_MOTOR_BLOCK_ENTITY = Index.all()
    .blockEntity("mana_motor_block_entity", ManaMotorBlockEntity::new)
    .instance(() -> HalfShaftInstance::new)
    .validBlocks(Blocks.MANA_MOTOR)
    .renderer(() -> ManaMotorRenderer::new)
    .register();

  public static final BlockEntityEntry<ManaGeneratorBlockEntity> MANA_GENERATOR_BLOCK_ENTITY = Index.all()
    .blockEntity("mana_generator_block_entity", ManaGeneratorBlockEntity::new)
    .instance(() -> HalfShaftInstance::new)
    .validBlocks(Blocks.MANA_GENERATOR)
    .renderer(() -> ManaGeneratorRenderer::new)
    .register();

  public static final BlockEntityEntry<ManaCondenserBlockEntity> MANA_CONDENSER_BLOCK_ENTITY = Index.all()
    .blockEntity("mana_condenser_block_entity", ManaCondenserBlockEntity::new)
    .instance(() -> HalfShaftInstance::new)
    .validBlocks(Blocks.MANA_CONDENSER)
    .renderer(() -> ManaCondenserRenderer::new)
    .register();

  public static final BlockEntityEntry<OmniboxBlockEntity> OMNIBOX_BLOCK_ENTITY = Index.all()
    .blockEntity("omnibox_block_entity", OmniboxBlockEntity::new)
    .instance(() -> OmniboxInstance::new)
    .validBlocks(Blocks.OMNIBOX)
    .renderer(() -> OmniboxRenderer::new)
    .register();

  public static final BlockEntityEntry<XorLeverBlockEntity> XOR_LEVER_BLOCK_ENTITY = Index.all()
    .blockEntity("xor_lever_block_entity", XorLeverBlockEntity::new)
    .instance(() -> XorLeverInstance::new, false)
    .validBlocks(Blocks.XOR_LEVER)
    .renderer(() -> XorLeverRenderer::new)
    .register();

  public static final BlockEntityEntry<BlazuniaFunctionalFlowerBlockEntity> BLAZUNIA_BLOCK_ENTITY = Index.all()
    .blockEntity("blazunia_block_entity", BlazuniaFunctionalFlowerBlockEntity::new)
    .validBlocks(Blocks.BLAZUNIA_BLOCK)
    .renderer(() -> BlazuniaRenderer::new)
    .register();

  public static void register(IEventBus eventBus) {
      log(l -> l.debug("register block entities"));
    }
}