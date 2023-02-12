package zaftnotameni.creatania.registry.datagen;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.registry.Blocks;
import zaftnotameni.creatania.registry.Tags;
public class ForgeBlockTagProvider extends BlockTagsProvider {
  public ForgeBlockTagProvider(DataGenerator generator, ExistingFileHelper helper) {
    super(generator, Constants.MODID, helper);
  }

  @Override
  protected void addTags() {
    tag(Tags.Blocks.MANA_MACHINE).add(
      Blocks.MANA_CONDENSER.get(),
      Blocks.MANA_GENERATOR.get(),
      Blocks.MANA_MOTOR.get());

    tag(Tags.Blocks.PURE_INERT_MANA).add(
      Blocks.PURIFIED_INERT_MANA_BLOCK.get());

    tag(Tags.Blocks.CORRUPT_INERT_MANA).add(
      Blocks.CORRUPTED_INERT_MANA_BLOCK.get());
  }
}
