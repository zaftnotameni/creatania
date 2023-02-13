package zaftnotameni.creatania.registry.datagen;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import zaftnotameni.creatania.Constants;
public class ForgeBlockstatesProvider extends BlockStateProvider {
  public ForgeBlockstatesProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
    super(gen, Constants.MODID, exFileHelper);
  }
  @Override
  protected void registerStatesAndModels() {
    
  }
}
