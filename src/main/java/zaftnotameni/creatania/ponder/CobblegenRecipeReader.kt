package zaftnotameni.creatania.ponder

import kotlinx.serialization.json.Json
import net.minecraft.server.packs.resources.ResourceManager
import zaftnotameni.creatania.registry.Index
import zaftnotameni.creatania.util.Log
import java.io.File

class CobblegenRecipeReader {
  fun readFileDirectlyAsText(fileName: String): String
    = File(fileName).readText(Charsets.UTF_8)

  fun loadRecipe() {
    var f = ResourceManager.Empty.INSTANCE.getResource(Index.resource("recipes/cobblegen/flowing_flowing_none_calcite.json"))
    var j = f.inputStream.bufferedReader(Charsets.UTF_8).readText()
    var o = Json.parseToJsonElement(j)
    Log.LOGGER.info("hello" + o.toString())
  }
}