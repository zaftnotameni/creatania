package zaftnotameni.creatania.registry;

import net.minecraftforge.eventbus.api.IEventBus;
import zaftnotameni.creatania.util.Log;

public class BlockEntities {
  
//  public static final BlockEntityEntry<ManaMotorBlockEntity> MANA_MOTOR_BLOCK_ENTITY = Index.all()
//    .tileEntity("mana_motor_block_entity", ManaMotorBlockEntity::new)
//    .instance(() -> HalfShaftInstance::new)
//    .validBlocks(Blocks.MANA_MOTOR)
//    .renderer(() -> ManaMotorRenderer::new)
//    .register();
//
//  public static final BlockEntityEntry<ManaGeneratorBlockEntity> MANA_GENERATOR_BLOCK_ENTITY = Index.all()
//    .tileEntity("mana_generator_block_entity", ManaGeneratorBlockEntity::new)
//    .instance(() -> HalfShaftInstance::new)
//    .validBlocks(Blocks.MANA_GENERATOR)
//    .renderer(() -> ManaGeneratorRenderer::new)
//    .register();
//
//  public static final BlockEntityEntry<ManaCondenserBlockEntity> MANA_CONDENSER_BLOCK_ENTITY = Index.all()
//    .tileEntity("mana_condenser_block_entity", ManaCondenserBlockEntity::new)
//    .instance(() -> HalfShaftInstance::new)
//    .validBlocks(Blocks.MANA_CONDENSER)
//    .renderer(() -> ManaCondenserRenderer::new)
//    .register();
//
//  public static final BlockEntityEntry<OmniboxBlockEntity> OMNIBOX_BLOCK_ENTITY = Index.all()
//    .tileEntity("omnibox_block_entity", OmniboxBlockEntity::new)
//    .instance(() -> HalfShaftInstance::new)
//    .validBlocks(Blocks.OMNIBOX)
//    .renderer(() -> OmniboxRenderer::new)
//    .register();
//
//  public static final BlockEntityEntry<XorLeverBlockEntity> XOR_LEVER_BLOCK_ENTITY = Index.all()
//    .tileEntity("xor_lever_block_entity", XorLeverBlockEntity::new)
//    .instance(() -> XorLeverInstance::new, false)
//    .validBlocks(Blocks.XOR_LEVER)
//    .renderer(() -> XorLeverRenderer::new)
//    .register();
//
//  public static final BlockEntityEntry<BlazuniaFunctionalFlowerBlockEntity> BLAZUNIA_BLOCK_ENTITY = Index.all()
//    .tileEntity("blazunia_block_entity", BlazuniaFunctionalFlowerBlockEntity::new)
//    .validBlocks(Blocks.BLAZUNIA_BLOCK)
//    .register();

  public static void register(IEventBus eventBus) {
      Log.LOGGER.debug("register block entities");
    }
}
