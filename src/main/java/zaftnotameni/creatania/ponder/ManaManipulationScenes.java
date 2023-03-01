package zaftnotameni.creatania.ponder;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.components.mixer.MechanicalMixerTileEntity;
import com.simibubi.create.content.contraptions.fluids.actors.ItemDrainTileEntity;
import com.simibubi.create.content.contraptions.processing.BasinBlock;
import com.simibubi.create.content.contraptions.processing.BasinTileEntity;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.Selection;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.utility.Pointing;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
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
    scene.title("mana_" + blockId.getPath() + "_to_" + fluidId.getPath(), "Making Mana Fluid");
    scene.scaleSceneView(1.5f);
    var x = new CreataniaPonderUtils(scene, util);
    var basinPos = util.grid.at(1, 2, 1);
    var mixerPos = util.grid.at(1, 4, 1);
    var blazePos = util.grid.at(1, 1, 1);
    var drainPos = util.grid.at(0, 1, 1);
    var basin = util.select.position(basinPos);
    var mixer = util.select.position(mixerPos);
    var blaze = util.select.position(blazePos);
    var drain = util.select.position(drainPos);
    setupInitialMixerScene(scene, x, blazePos, basin, mixer, blaze);
    addManaBlocksAsIngredients(scene, util, blockId, x, basinPos);
    superheatBlazeBurner(scene, util, x, blazePos);
    processManaBlocks(scene, fluidId, x, basin, mixer);
    drainFluidMana(scene, fluidId, x, basinPos, drainPos, basin, drain);
    outputExplanationText(scene, util, drainPos);
  }
  private static void outputExplanationText(SceneBuilder scene, SceneBuildingUtil util, BlockPos drainPos) {
    scene.overlay
      .showText(40)
      .attachKeyFrame()
      .text("Generates fluid mana")
      .pointAt(util.vector.topOf(drainPos));
  }
  private static void setupInitialMixerScene(SceneBuilder scene, CreataniaPonderUtils x, BlockPos blazePos, Selection basin, Selection mixer, Selection blaze) {
    scene.world.modifyBlock(blazePos, x.unlit(), false);
    scene.showBasePlate();
    scene.idle(20);
    scene.world.showSection(blaze, EAST);
    scene.idle(20);
    scene.world.showSection(basin, WEST);
    scene.idle(20);
    scene.world.showSection(mixer, UP);
    scene.idle(20);
  }
  private static void addManaBlocksAsIngredients(SceneBuilder scene, SceneBuildingUtil util, ResourceLocation blockId, CreataniaPonderUtils x, BlockPos basinPos) {
    var stack = new ItemStack(ForgeRegistries.BLOCKS.getValue(blockId).asItem(), 1);
    scene.overlay
      .showText(60)
      .attachKeyFrame()
      .text("Throw some mana blocks into the mix")
      .pointAt(util.vector.centerOf(basinPos));
    scene.overlay
      .showControls(
      new InputWindowElement(util.vector.topOf(basinPos), Pointing.DOWN).withItem(stack),
      60);
    scene.idle(60);

    x.dropBlocksOnto(basinPos, blockId, 4, i -> scene.idle(20));
    scene.idle(20);
  }
  private static void drainFluidMana(SceneBuilder scene, ResourceLocation fluidId, CreataniaPonderUtils x, BlockPos basinPos, BlockPos drainPos, Selection basin, Selection drain) {
    scene.world.setBlock(drainPos, AllBlocks.ITEM_DRAIN.getDefaultState(), false);
    scene.world.showSection(drain, EAST);
    scene.idle(1);

    scene.world.modifyBlock(basinPos, x.facing(BasinBlock.FACING, WEST), false);
    scene.idle(1);

    scene.world.modifyTileNBT(basin, BasinTileEntity.class, x.emptyTanksFn("OutputTanks"));
    scene.idle(1);

    scene.world.modifyTileNBT(drain, ItemDrainTileEntity.class, x.fillTanksFn("Tanks", fluidId, 1000));
    scene.idle(1);
  }
  private static void processManaBlocks(SceneBuilder scene, ResourceLocation fluidId, CreataniaPonderUtils x, Selection basin, Selection mixer) {
    scene.world.modifyTileNBT(mixer, MechanicalMixerTileEntity.class, x.startProcessingFn());
    scene.world.modifyTileNBT(basin, BasinTileEntity.class, x.emptyInputItemsFn());
    scene.world.modifyTileNBT(basin, BasinTileEntity.class, x.fillTanksFn("OutputTanks", fluidId, 1000));
    scene.idle(20);
  }
  private static void superheatBlazeBurner(SceneBuilder scene, SceneBuildingUtil util, CreataniaPonderUtils x, BlockPos blazePos) {
    scene.world.modifyBlock(blazePos, x.heated(), false);
    scene.idle(20);
    scene.overlay
      .showText(30)
      .attachKeyFrame()
      .text("Superheat your blaze burner")
      .pointAt(util.vector.centerOf(blazePos));
    scene.overlay
      .showControls(
        new InputWindowElement(util.vector.centerOf(blazePos), Pointing.UP)
          .withItem(new ItemStack(AllItems.BLAZE_CAKE.get(), 1))
          .rightClick(),
        30);
    scene.world.modifyBlock(blazePos, x.superHeated(), false);
    scene.idle(40);
  }
}
