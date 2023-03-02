package zaftnotameni.creatania.ponder;

import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.foundation.ponder.PonderScene;
import com.simibubi.create.foundation.ponder.PonderTag;
import com.simibubi.create.foundation.ponder.PonderWorld;
import com.tterrag.registrate.util.entry.BlockEntry;
import java.util.Collection;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.registry.Blocks;
import zaftnotameni.creatania.registry.Index;

import static zaftnotameni.creatania.recipes.cobblegen.AllCobblegenRecipes.getCobblegenRecipes;

public class CreataniaPonderScene extends PonderScene {
  public static final PonderRegistrationHelper INDEX = new PonderRegistrationHelper(Constants.MODID);

  public CreataniaPonderScene(PonderWorld world, String namespace, ResourceLocation component, Collection<PonderTag> tags) {
    super(world, namespace, component, tags);
  }
  public static void register() {
    INDEX
      .forComponents(Blocks.PURE_MANA_BLOCK)
      .addStoryBoard("mana_superheated_mixer", ManaManipulationScenes::makeLiquidPureMana, CreataniaPonderTag.MANA_MANIPULATION);

    INDEX
      .forComponents(Blocks.CORRUPT_MANA_BLOCK)
      .addStoryBoard("mana_superheated_mixer", ManaManipulationScenes::makeLiquidCorruptMana, CreataniaPonderTag.MANA_MANIPULATION);
  }

  public static void deferredRegister() {
    for (var recipe : getCobblegenRecipes(Minecraft.getInstance().level)) {
      var id = recipe.getResultItem().getItem().getRegistryName();
      var path = id.getPath().toString();
      var component = Index.all().get(path, ForgeRegistries.BLOCKS.getRegistryKey());
      var scene = new CobblegenScenes(recipe);
      INDEX.forComponents(BlockEntry.cast(component))
        .addStoryBoard("mana_superhexated_mixer_" + path.toString(), scene::makeCobblegenScene, CreataniaPonderTag.COBBLEGEN);
    }
  }
}