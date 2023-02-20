package zaftnotameni.creatania.registry.datagen;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import zaftnotameni.creatania.Constants;
public class ForgeFluidTagProvider extends FluidTagsProvider {
  public ForgeFluidTagProvider(DataGenerator generator, ExistingFileHelper helper) {
    super(generator, Constants.MODID, helper);
  }

  @Override
  protected void addTags() {
  }
}

