package zaftnotameni.creatania.ponder

import com.simibubi.create.AllBlocks
import com.simibubi.create.AllItems
import com.simibubi.create.foundation.ponder.SceneBuilder
import com.simibubi.create.foundation.ponder.SceneBuildingUtil
import com.simibubi.create.foundation.ponder.element.InputWindowElement
import com.simibubi.create.foundation.utility.Pointing
import net.minecraft.core.Direction
import net.minecraft.resources.ResourceLocation
import zaftnotameni.creatania.ponder.CreataniaPonderUtils.XYZ
import zaftnotameni.creatania.registry.Blocks
import zaftnotameni.creatania.registry.Fluids

object ManaManipulationScenes {
  fun makeLiquidPureMana(scene : SceneBuilder?, util : SceneBuildingUtil?) {
    makeManaFluidFromBlocksScene(scene, util, Blocks.PURE_MANA_BLOCK.id, Fluids.PURE_MANA.id)
  }

  fun makeLiquidCorruptMana(scene : SceneBuilder?, util : SceneBuildingUtil?) {
    makeManaFluidFromBlocksScene(scene, util, Blocks.CORRUPT_MANA_BLOCK.id, Fluids.CORRUPT_MANA.id)
  }

  fun makeManaFluidFromBlocksScene(scene : SceneBuilder?, util : SceneBuildingUtil?, blockId : ResourceLocation, fluidId : ResourceLocation) {
    val x = CreataniaPonderUtils(scene, util)
    x.scene.title("mana_" + blockId.path + "_to_" + fluidId.path, "Making Mana Fluid")
    x.scene.scaleSceneView(1.5f)
    val basin = x.at(1, 2, 1)
    val mixer = x.at(1, 4, 1)
    val blaze = x.at(1, 1, 1)
    val drain = x.at(0, 1, 1)
    setupInitialMixerScene(x, blaze, basin, mixer)
    addManaBlocksAsIngredients(x, blockId, blaze)
    superheatBlazeBurner(x, blaze)
    processManaBlocks(x, fluidId, basin, mixer)
    drainFluidMana(x, fluidId, basin, drain)
    outputExplanationText(x, drain)
  }

  private fun outputExplanationText(x : CreataniaPonderUtils, drain : XYZ) {
    x.keyFrameText("Generates Fluid Mana", drain.top(), 40).run()
  }

  private fun setupInitialMixerScene(x : CreataniaPonderUtils, blaze : XYZ, basin : XYZ, mixer : XYZ) {
    x.scene.world.modifyBlock(blaze.asBlockPos(), x.unlit(), false)
    x.idleAfter(20, Runnable { x.scene.showBasePlate() }).get()
    x.idleAfter(20, blaze.appearTo(Direction.EAST)).get()
    x.idleAfter(20, basin.appearTo(Direction.WEST)).get()
    x.idleAfter(20, mixer.appearTo(Direction.UP)).get()
  }

  private fun addManaBlocksAsIngredients(x : CreataniaPonderUtils, blockId : ResourceLocation, basin : XYZ) {
    x.keyFrameText("Throw some mana blocks into the mix", basin.top(), 60).run()
    x.showInputItem(blockId, Pointing.DOWN, basin.offsetTop(), 60).run()
    x.scene.idle(60)
    x.idleAfter(20, x.dropBlocksOnto(basin, blockId, 4) { i : Int? -> x.scene.idle(20) }).get()
  }

  private fun drainFluidMana(x : CreataniaPonderUtils, fluidId : ResourceLocation, basin : XYZ, drain : XYZ) {
    drain.setBlock(AllBlocks.ITEM_DRAIN.defaultState).run()
    x.idleAfter(1, drain.appearTo(Direction.EAST)).get()
    x.idleAfter(1, basin.setFacing(Direction.WEST)).get()
    x.idleAfter(1, basin.modifyNbt(x.emptyTanksFn("OutputTanks"))).get()
    x.idleAfter(1, drain.modifyNbt(x.fillTanksFn("Tanks", fluidId, 1000))).get()
  }

  private fun processManaBlocks(x : CreataniaPonderUtils, fluidId : ResourceLocation, basin : XYZ, mixer : XYZ) {
    mixer.modifyNbt(x.startProcessingFn()).run()
    basin.modifyNbt(x.fillTanksFn("OutputTanks", fluidId, 1000)).run()
    x.scene.idle(10)
    basin.modifyNbt(x.emptyInputItemsFn()).run()
    x.scene.idle(100)
  }

  private fun superheatBlazeBurner(x : CreataniaPonderUtils, blaze : XYZ) {
    x.idleAfter(20, blaze.modifyBlock(x.heated())).get()
    x.keyFrameText("Superheat the blaze burner", blaze.center(), 30).run()
    x.showInputItem(AllItems.BLAZE_CAKE.get(), Pointing.UP, blaze.center(), 30) { obj : InputWindowElement -> obj.rightClick() }.run()
    x.scene.idle(30)
    x.idleAfter(40, blaze.modifyBlock(x.superHeated())).get()
  }
}