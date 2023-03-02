package zaftnotameni.creatania.ponder;

import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import net.minecraft.core.Direction;
import zaftnotameni.creatania.recipes.cobblegen.CobblegenRecipe;
import zaftnotameni.creatania.registry.Fluids;

public class CobblegenScenes {
  public CobblegenRecipe recipe;
  public CobblegenScenes(CobblegenRecipe r) { recipe = r; }
  public void makeCobblegenScene(SceneBuilder s, SceneBuildingUtil u) {
    var x = new CreataniaPonderUtils(s, u);
    x.scene.title("cobblegen_" + recipe.getResultItem().getItem().getRegistryName().getPath(), "Magic Cobblegen");
    x.scene.scaleSceneView(2f);

    x.scene.world.setBlock(x.at(1, 1, 1).asBlockPos(), Fluids.PURE_MANA.get().getSource().defaultFluidState()
                                                                       .createLegacyBlock(), false);
    x.scene.world.showSection(x.at(1, 1, 1).asSelection(), Direction.DOWN);
  }
}