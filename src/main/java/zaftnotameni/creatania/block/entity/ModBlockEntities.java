package zaftnotameni.creatania.block.entity;

import com.simibubi.create.content.contraptions.base.HalfShaftInstance;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.CreataniaMain;
import zaftnotameni.creatania.block.ModBlocks;
import zaftnotameni.creatania.block.entity.custom.ManaGeneratorBlockEntity;
import zaftnotameni.creatania.block.entity.custom.ManaMotorBlockEntity;
import zaftnotameni.creatania.block.entity.custom.ManaMotorRenderer;
import zaftnotameni.creatania.util.Log;

public class ModBlockEntities {
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
    DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Constants.MODID);

//    public static final RegistryObject<BlockEntityType<ManaMotorBlockEntity>> MANA_MOTOR_BLOCK_ENTITY =
//      BLOCK_ENTITIES.register(Constants.MANA_MOTOR_BLOCK_ENTITY, () ->
//        BlockEntityType.Builder.of(ManaMotorBlockEntity::new, ModBlocks.MANA_MOTOR.get()).build(null));

    public static final BlockEntityEntry<ManaMotorBlockEntity> MANA_MOTOR_BLOCK_ENTITY = CreataniaMain.getRegistrate()
      .tileEntity(Constants.MANA_MOTOR_BLOCK_ENTITY, ManaMotorBlockEntity::new)
      .instance(() -> HalfShaftInstance::new)
      .validBlocks(ModBlocks.MANA_MOTOR)
      .renderer(() -> ManaMotorRenderer::new)
      .register();

  public static final RegistryObject<BlockEntityType<ManaGeneratorBlockEntity>> MANA_GENERATOR_BLOCK_ENTITY =
    BLOCK_ENTITIES.register(Constants.MANA_GENERATOR_BLOCK_ENTITY, () ->
      BlockEntityType.Builder.of(ManaGeneratorBlockEntity::new, ModBlocks.MANA_GENERATOR.get()).build(null));

  public static void register(IEventBus eventBus) {
      Log.LOGGER.debug("register block entities");
      BLOCK_ENTITIES.register(eventBus);
    }
}
