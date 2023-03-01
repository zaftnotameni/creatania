package zaftnotameni.creatania.ponder;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.components.mixer.MechanicalMixerTileEntity;
import com.simibubi.create.content.contraptions.fluids.actors.ItemDrainTileEntity;
import com.simibubi.create.content.contraptions.processing.BasinBlock;
import com.simibubi.create.content.contraptions.processing.BasinTileEntity;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.utility.Pointing;
import net.minecraft.resources.ResourceLocation;
import zaftnotameni.creatania.ponder.CreataniaPonderUtils.XYZ;
import zaftnotameni.creatania.registry.Blocks;
import zaftnotameni.creatania.registry.Fluids;

import static net.minecraft.core.Direction.*;

public class ManaManipulationScenes {

  public static void makeLiquidPureMana(SceneBuilder scene, SceneBuildingUtil util) {
    makeManaFluidFromBlocksScene(scene, util, Blocks.PURE_MANA_BLOCK.getId(), Fluids.PURE_MANA.getId());
  }

  public static void makeLiquidCorruptMana(SceneBuilder scene, SceneBuildingUtil util) {
    makeManaFluidFromBlocksScene(scene, util, Blocks.CORRUPT_MANA_BLOCK.getId(), Fluids.CORRUPT_MANA.getId());
  }

  public static void makeManaFluidFromBlocksScene(SceneBuilder scene, SceneBuildingUtil util, ResourceLocation blockId, ResourceLocation fluidId) {
    var x = new CreataniaPonderUtils(scene, util);
    x.scene.title("mana_" + blockId.getPath() + "_to_" + fluidId.getPath(), "Making Mana Fluid");
    x.scene.scaleSceneView(1.5f);
    var basin = x.at(1, 2, 1);
    var mixer = x.at(1, 4, 1);
    var blaze = x.at(1, 1, 1);
    var drain = x.at(0, 1, 1);
    setupInitialMixerScene(x, blaze, basin, mixer);
    addManaBlocksAsIngredients(x, blockId, blaze);
    superheatBlazeBurner(x, blaze);
    processManaBlocks(x, fluidId, basin, mixer);
    drainFluidMana(x, fluidId, basin, drain);
    outputExplanationText(x, drain);
  }

  private static void outputExplanationText(CreataniaPonderUtils x, XYZ drain) {
    x
      .keyFrameText("Generates Fluid Mana", drain.top(), 40)
      .run();
  }

  private static void setupInitialMixerScene(CreataniaPonderUtils x, XYZ blaze, XYZ basin, XYZ mixer) {
    x.scene.world.modifyBlock(blaze.asBlockPos(), x.unlit(), false);
    x
      .idleAfter(20, x.scene::showBasePlate)
      .get();
    x
      .idleAfter(20, blaze.appearTo(EAST))
      .get();
    x
      .idleAfter(20, basin.appearTo(WEST))
      .get();
    x
      .idleAfter(20, mixer.appearTo(UP))
      .get();
  }

  private static void addManaBlocksAsIngredients(CreataniaPonderUtils x, ResourceLocation blockId, XYZ basin) {
    x
      .keyFrameText("Throw some mana blocks into the mix", basin.top(), 60)
      .run();
    x
      .showInputItem(blockId, Pointing.DOWN, basin.offsetTop(), 60)
      .run();
    x.scene.idle(60);
    x
      .idleAfter(20, x.dropBlocksOnto(basin, blockId, 4, i -> x.scene.idle(20)))
      .get();
  }

  private static void drainFluidMana(CreataniaPonderUtils x, ResourceLocation fluidId, XYZ basin, XYZ drain) {
    drain
      .setBlock(AllBlocks.ITEM_DRAIN.getDefaultState())
      .run();
    x
      .idleAfter(1, drain.appearTo(EAST))
      .get();
    x
      .idleAfter(1, basin.modifyBlock(x.facing(BasinBlock.FACING, WEST)))
      .get();
    x
      .idleAfter(1, basin.modifyNbt(BasinTileEntity.class, x.emptyTanksFn("OutputTanks")))
      .get();
    x
      .idleAfter(1, drain.modifyNbt(ItemDrainTileEntity.class, x.fillTanksFn("Tanks", fluidId, 1000)))
      .get();
  }

  private static void processManaBlocks(CreataniaPonderUtils x, ResourceLocation fluidId, XYZ basin, XYZ mixer) {
    mixer
      .modifyNbt(MechanicalMixerTileEntity.class, x.startProcessingFn())
      .run();
    basin
      .modifyNbt(BasinTileEntity.class, x.fillTanksFn("OutputTanks", fluidId, 1000))
      .run();
    x.scene.idle(10);
    basin
      .modifyNbt(BasinTileEntity.class, x.emptyInputItemsFn())
      .run();
    x.scene.idle(100);
  }

  private static void superheatBlazeBurner(CreataniaPonderUtils x, XYZ blaze) {
    x
      .idleAfter(20, blaze.modifyBlock(x.heated()))
      .get();
    x
      .keyFrameText("Superheat the blaze burner", blaze.center(), 30)
      .run();
    x
      .showInputItem(AllItems.BLAZE_CAKE.get(), Pointing.UP, blaze.center(), 30, InputWindowElement::rightClick)
      .run();
    x.scene.idle(30);
    x
      .idleAfter(40, blaze.modifyBlock(x.superHeated()))
      .get();
  }

}