package zaftnotameni.creatania.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.block.ModBlocks;
import zaftnotameni.creatania.block.entity.custom.ManaMotorBlockEntity;

public class ModBlockEntities {
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
    DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Constants.MODID);

    public static final RegistryObject<BlockEntityType<ManaMotorBlockEntity>> MANA_MOTOR_BLOCK_ENTITY =
      BLOCK_ENTITIES.register("mana_motor_block_entity", () ->
        BlockEntityType.Builder.of(ManaMotorBlockEntity::new, ModBlocks.MANA_MOTOR.get()).build(null));


    public static void register(IEventBus eventBus) {
      BLOCK_ENTITIES.register(eventBus);
    }
}
