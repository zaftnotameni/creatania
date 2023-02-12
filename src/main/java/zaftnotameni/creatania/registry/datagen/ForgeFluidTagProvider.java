package zaftnotameni.creatania.registry.datagen;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.registry.Fluids;
import zaftnotameni.creatania.registry.Tags;
public class ForgeFluidTagProvider extends FluidTagsProvider {
  public ForgeFluidTagProvider(DataGenerator generator, ExistingFileHelper helper) {
    super(generator, Constants.MODID, helper);
  }

  @Override
  protected void addTags() {
    tag(Tags.Fluids.PURE_INERT_MANA).add(Fluids.MANA_FLUID.get());
  }
}

