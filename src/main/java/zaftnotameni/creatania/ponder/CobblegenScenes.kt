package zaftnotameni.creatania.ponder

import com.simibubi.create.foundation.ponder.SceneBuilder
import com.simibubi.create.foundation.ponder.SceneBuildingUtil
import net.minecraft.core.Direction
import net.minecraftforge.registries.ForgeRegistries
import zaftnotameni.creatania.recipes.cobblegen.CobblegenRecipe

class CobblegenScenes(var recipe : CobblegenRecipe) {
  fun header(s : SceneBuilder?, u : SceneBuildingUtil?) : CreataniaPonderUtils {
    val x = CreataniaPonderUtils(s, u)
    val outputItem = recipe.resultItem.item
    x.scene.title("cobblegen_" + outputItem.registryName!!.path, "Magic Cobblegen")
    x.scene.showBasePlate()
    return x
  }

  fun makeCobblegenFlowingSourceScene(s : SceneBuilder?, u : SceneBuildingUtil?) {
    val c = header(s, u)
    checkerboard(c, c.at(0, 1, 1), c.at(2, 1, 1))
    val fs = recipe.asFlowingSource()
    val flowing = fs.a
    val source = fs.b
    fluidWithLevelAt(c, 9, flowing, 2, 1, 0)
    val ff = fluidWithLevelAt(c, 5, flowing, 1, 1, 0)
    c.keyFrameText("Flowing " + nameOf(flowing) + " will not be consumed", ff!!.center(), 60).run()
    c.scene.idle(60)
    val ss = fluidWithLevelAt(c, 9, source, 0, 1, 0)
    c.keyFrameText(nameOf(source) + " Source will be consumed", ss!!.center(), 60).run()
    c.scene.idle(60)
    val output = c.at(0, 1, 0)
    val block = ForgeRegistries.BLOCKS.getValue(recipe.resultItem.item.registryName)
    c.scene.world.setBlock(output.asBlockPos(), block!!.defaultBlockState(), true)
    c.keyFrameText(block.name.string + " will be generated", output.center(), 60).run()
  }

  fun makeCobblegenFlowingFlowingScene(s : SceneBuilder?, u : SceneBuildingUtil?) {
    val c = header(s, u)
    checkerboard(c, c.at(1, 1, 1), c.at(1, 1, 1))
    val ff = recipe.asFlowingFlowing()
    val a = ff.a
    val b = ff.b
    fluidWithLevelAt(c, 9, a, 2, 1, 0)
    val aa = fluidWithLevelAt(c, 5, a, 1, 1, 0)
    c.keyFrameText("Flowing " + nameOf(a) + " will not be consumed", aa!!.center(), 60).run()
    c.scene.idle(60)
    fluidWithLevelAt(c, 9, b, 0, 1, 2)
    val bb = fluidWithLevelAt(c, 5, b, 0, 1, 1)
    c.keyFrameText("Flowing " + nameOf(b) + " will not be consumed", bb!!.center(), 60).run()
    c.scene.idle(60)
    val output = c.at(0, 1, 0)
    val block = ForgeRegistries.BLOCKS.getValue(recipe.resultItem.item.registryName)
    c.scene.world.setBlock(output.asBlockPos(), block!!.defaultBlockState(), true)
    c.scene.world.showSection(output.asSelection(), Direction.UP)
    c.keyFrameText(nameOf(block) + " will be generated", output.center(), 60).run()
  }

  fun makeCobblegenScene(s : SceneBuilder?, u : SceneBuildingUtil?) {
    if (recipe.hasSource()) {
      makeCobblegenFlowingSourceScene(s, u)
    } else makeCobblegenFlowingFlowingScene(s, u)
  }
}