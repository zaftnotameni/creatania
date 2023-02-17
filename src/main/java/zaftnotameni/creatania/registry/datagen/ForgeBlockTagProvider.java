package zaftnotameni.creatania.registry.datagen;
import com.simibubi.create.AllBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.registry.Blocks;
import zaftnotameni.creatania.registry.Tags;
public class ForgeBlockTagProvider extends BlockTagsProvider {
  public ForgeBlockTagProvider(DataGenerator generator, ExistingFileHelper helper) {
    super(generator, Constants.MODID, helper);
  }

  @Override
  protected void addTags() {
    tag(Tags.Blocks.MANADUCT).add(
      Blocks.MANASTEEL_MANADUCT_BLOCK.get(),
      Blocks.TERRASTEEL_MANADUCT_BLOCK.get(),
      Blocks.GAIA_MANADUCT_BLOCK.get());

    tag(Tags.Blocks.TIER_1).add(
      Blocks.MANASTEEL_MANADUCT_BLOCK.get(),
      Blocks.MANA_GENERATOR.get(),
      Blocks.MANA_MOTOR.get(),
      Blocks.MANA_CONDENSER.get());

    tag(Tags.Blocks.TIER_2).add(
      Blocks.TERRASTEEL_MANADUCT_BLOCK.get());

    tag(Tags.Blocks.TIER_3).add(
      Blocks.GAIA_MANADUCT_BLOCK.get());

    tag(Tags.Blocks.BOTANIA_TERRA_PLATE_BASE).add(
      Blocks.MANA_GENERATOR.get(),
      AllBlocks.FLUID_PIPE.get());

    tag(Tags.Blocks.FORGE_LAPIS).add(
      Blocks.MANA_GENERATOR.get());

    tag(Tags.Blocks.PREVENTS_ENDER_TELEPORTATION).add(
      Blocks.CORRUPTED_INERT_MANA_BLOCK.get());

    tag(Tags.Blocks.MANA_MACHINE).add(
      Blocks.MANA_CONDENSER.get(),
      Blocks.MANA_GENERATOR.get(),
      Blocks.MANA_MOTOR.get());

    tag(Tags.Blocks.PURE_INERT_MANA).add(
      Blocks.PURIFIED_INERT_MANA_BLOCK.get());

    tag(Tags.Blocks.CORRUPT_INERT_MANA).add(
      Blocks.CORRUPTED_INERT_MANA_BLOCK.get());

    tag(Tags.Blocks.BOTANIA_MANA).add(
      Blocks.BOTANIA_MANA_BLOCK.get());
  }
}
