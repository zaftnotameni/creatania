package zaftnotameni.creatania.ponder;

import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import zaftnotameni.creatania.recipes.cobblegen.CobblegenRecipe;

import static net.minecraft.world.level.block.LiquidBlock.LEVEL;

public class CobblegenScenes {

  public CobblegenRecipe recipe;

  public CobblegenScenes(CobblegenRecipe r) { recipe = r; }

  public void makeCobblegenScene(SceneBuilder s, SceneBuildingUtil u) {
    var x = new CreataniaPonderUtils(s, u);
    var outputItem = recipe
      .getResultItem()
      .getItem();
    x.scene.title("cobblegen_" + outputItem
      .getRegistryName()
      .getPath(), "Magic Cobblegen");
    x.scene.showBasePlate();
    x.scene.scaleSceneView(1.5f);

    for (var i = 0; i < 2; i++) for (var j = 0; j < 2; j++) {
      var even = (i + j) % 2 == 0;
      var block = even ? Blocks.BLACK_CONCRETE : Blocks.GRAY_CONCRETE;
      var y = x.at(2 - i, 1, 2 - j);
      x.scene.world.setBlock(y.asBlockPos(), block.defaultBlockState(), false);
      x.scene.world.showSection(y.asSelection(), Direction.DOWN);
      x.scene.idle(10);
    }

    var fluidA = x.at(0, 1, 2);
    var flowA = x.at(0, 1, 1);

    var fluidB = x.at(2, 1, 0);
    var flowB = x.at(1, 1, 0);
    var result = x.at(0, 1, 0);

    var outputBlock = ForgeRegistries.BLOCKS.getValue(outputItem.getRegistryName());

    showFlowingFluid(x,0, fluidA, flowA);
    showFlowingFluid(x,1, fluidB, flowB);

    x.scene.world.setBlock(result.asBlockPos(), outputBlock.defaultBlockState(), true);
    x.scene.world.showSection(result.asSelection(), Direction.UP);
    x.keyFrameText("Blocks generated with mana!", result.top(), 60).run();
  }

  private void showFloorPiece(CreataniaPonderUtils x, int i) {
    var block = i % 2 == 0 ? Blocks.SNOW.defaultBlockState() : Blocks.WHITE_CONCRETE.defaultBlockState();
    x.scene.world.setBlock(x.at(i, 0, 0).asBlockPos(), block, false);
    x.scene.idle(5);
    x.scene.world.showSection(x.at(i, 0, 0).asSelection(), Direction.UP);
    x.scene.idle(5);
  }

  private void showFlowingFluid(CreataniaPonderUtils x, int pIndex, CreataniaPonderUtils.XYZ fluid, CreataniaPonderUtils.XYZ flow) {
    x.scene.world.setBlock(fluid.asBlockPos(), blockOf(pIndex), false);
    x.scene.world.setBlock(flow.asBlockPos(), blockOf(pIndex), false);

    x.scene.world.showSection(fluid.asSelection(), Direction.DOWN);
    x.scene.idle(40);
    x.scene.world.showSection(flow.asSelection(), Direction.DOWN);

    var requiresFullBlock = recipe.getInputFluidStacks().get(pIndex).getRequiredAmount() > 999;
    if (!requiresFullBlock) {
      x.scene.world.modifyBlock(flow.asBlockPos(), bs -> bs.setValue(LEVEL, 5), false);
      x.keyFrameText("Flowing fluids will NOT be consumed in the process.", flow.top(), 50).run();
    } else {
      x.keyFrameText("Source fluids WILL be consumed in the process.", flow.top(), 50).run();
    }

    x.scene.idle(60);
    if (requiresFullBlock) {
      x.scene.world.hideSection(flow.asSelection(), Direction.DOWN);
    }
  }

  @NotNull private BlockState blockOf(int pIndex) {
    return recipe
      .getInputFluidStacks()
      .get(pIndex)
      .getMatchingFluidStacks()
      .get(0)
      .getFluid()
      .defaultFluidState()
      .createLegacyBlock();
  }

}