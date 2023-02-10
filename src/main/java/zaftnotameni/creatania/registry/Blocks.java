package zaftnotameni.creatania.registry;

import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.block.BlockStressDefaults;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.manatosu.manamotor.ManaMotorBlock;
import zaftnotameni.creatania.sutomana.managenerator.ManaGeneratorBlock;
import zaftnotameni.creatania.util.Log;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;

public class Blocks {
  public static final DeferredRegister<Block> INDEX = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MODID);

  public static final CreateRegistrate CREATE_REGISTRATE = Index.getCreateRegistrate()
    .creativeModeTab(() -> CreativeModeTab.TAB_MISC);

  public static final BlockEntry<ManaMotorBlock> MANA_MOTOR = CREATE_REGISTRATE
    .block(Constants.MANA_MOTOR, ManaMotorBlock::new)
    .initialProperties(SharedProperties::softMetal)
    .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
    .transform(BlockStressDefaults.setCapacity(CommonConfig.MANA_MOTOR_SU_PER_RPM.get()))
    .item()
    .transform(customItemModel())
    .register();

  public static final BlockEntry<ManaGeneratorBlock> MANA_GENERATOR = CREATE_REGISTRATE
    .block(Constants.MANA_GENERATOR, ManaGeneratorBlock::new)
    .initialProperties(SharedProperties::softMetal)
    .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
    .transform(BlockStressDefaults.setImpact(CommonConfig.MANA_GENERATOR_SU_PER_RPM.get()))
    .item()
    .transform(customItemModel())
    .register();

  public static void register(IEventBus bus) {
    Log.LOGGER.debug("register blocks");
    INDEX.register(bus);
  }
}
