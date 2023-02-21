package zaftnotameni.creatania.registry.datagen;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import zaftnotameni.creatania.Constants;
public class ForgeItemTagProvider extends ItemTagsProvider {
  public ForgeItemTagProvider(DataGenerator generator, BlockTagsProvider blockTags, ExistingFileHelper helper) {
    super(generator, blockTags, Constants.MODID, helper);
  }
}

