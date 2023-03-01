package zaftnotameni.creatania.ponder;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.foundation.ponder.PonderScene;
import com.simibubi.create.foundation.ponder.PonderTag;
import com.simibubi.create.foundation.ponder.PonderWorld;
import net.minecraft.resources.ResourceLocation;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.registry.Blocks;

import java.util.Collection;
public class CreataniaPonderScene extends PonderScene {
  public static final PonderRegistrationHelper INDEX = new PonderRegistrationHelper(Constants.MODID);

  public CreataniaPonderScene(PonderWorld world, String namespace, ResourceLocation component, Collection<PonderTag> tags) {
    super(world, namespace, component, tags);
  }
  public static void register() {
    INDEX.forComponents(Blocks.PURE_MANA_BLOCK)
      .addStoryBoard("mana_superheated_mixer", ManaManipulationScenes::makeLiquidPureMana, CreataniaPonderTag.MANA_MANIPULATION);

    INDEX.forComponents(Blocks.CORRUPT_MANA_BLOCK)
      .addStoryBoard("mana_superheated_mixer", ManaManipulationScenes::makeLiquidCorruptMana, CreataniaPonderTag.MANA_MANIPULATION);
  }
}
