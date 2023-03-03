package zaftnotameni.creatania.ponder;

import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import net.minecraft.core.Direction;
import net.minecraftforge.registries.ForgeRegistries;
import zaftnotameni.creatania.recipes.cobblegen.CobblegenRecipe;

import static zaftnotameni.creatania.ponder.FillerFunctionsKt.checkerboard;
import static zaftnotameni.creatania.ponder.FluidFunctionsKt.fluidWithLevelAt;
import static zaftnotameni.creatania.ponder.StringFunctionsKt.nameOf;

public class CobblegenScenes {

  public CobblegenRecipe recipe;

  public CobblegenScenes(CobblegenRecipe r) { recipe = r; }

  public CreataniaPonderUtils header(SceneBuilder s, SceneBuildingUtil u) {
    var x = new CreataniaPonderUtils(s, u);
    var outputItem = recipe.getResultItem().getItem();
    x.scene.title("cobblegen_" + outputItem.getRegistryName().getPath(), "Magic Cobblegen");
    x.scene.showBasePlate();
    return x;
  }

  public void makeCobblegenFlowingSourceScene(SceneBuilder s, SceneBuildingUtil u) {
    var c = header(s, u);
    checkerboard(c, c.at(0, 1, 1), c.at(2, 1, 1));
    var fs = recipe.asFlowingSource();
    var flowing = fs.getA();
    var source = fs.getB();
    fluidWithLevelAt(c, 9, flowing, 2, 1, 0);
    var ff = fluidWithLevelAt(c, 5, flowing, 1, 1, 0);
    c.keyFrameText("Flowing " + nameOf(flowing) + " will not be consumed", ff.center(), 60).run();
    c.scene.idle(60);

    var ss = fluidWithLevelAt(c, 9, source, 0, 1, 0);
    c.keyFrameText(nameOf(source) + " Source will be consumed", ss.center(), 60).run();
    c.scene.idle(60);

    var output = c.at(0, 1, 0);
    var block = ForgeRegistries.BLOCKS.getValue(recipe.getResultItem().getItem().getRegistryName());
    c.scene.world.setBlock(output.asBlockPos(), block.defaultBlockState(), true);
    c.keyFrameText(block.getName().getString() + " will be generated", output.center(), 60).run();
  }

  public void makeCobblegenFlowingFlowingScene(SceneBuilder s, SceneBuildingUtil u) {
    var c = header(s, u);
    checkerboard(c, c.at(1, 1, 1), c.at(1, 1, 1));
    var ff = recipe.asFlowingFlowing();
    var a = ff.getA();
    var b = ff.getB();
    fluidWithLevelAt(c, 9, a, 2, 1, 0);
    var aa = fluidWithLevelAt(c, 5, a, 1, 1, 0);
    c.keyFrameText("Flowing " + nameOf(a) + " will not be consumed", aa.center(), 60).run();
    c.scene.idle(60);

    fluidWithLevelAt(c, 9, b, 0, 1, 2);
    var bb = fluidWithLevelAt(c, 5, b, 0, 1, 1);
    c.keyFrameText("Flowing " + nameOf(b) + " will not be consumed", bb.center(), 60).run();
    c.scene.idle(60);

    var output = c.at(0, 1, 0);
    var block = ForgeRegistries.BLOCKS.getValue(recipe.getResultItem().getItem().getRegistryName());
    c.scene.world.setBlock(output.asBlockPos(), block.defaultBlockState(), true);
    c.scene.world.showSection(output.asSelection(), Direction.UP);
    c.keyFrameText(nameOf(block) + " will be generated", output.center(), 60).run();
  }

  public void makeCobblegenScene(SceneBuilder s, SceneBuildingUtil u) {
    if (recipe.hasSource()) { makeCobblegenFlowingSourceScene(s, u); } else makeCobblegenFlowingFlowingScene(s, u);
  }

}