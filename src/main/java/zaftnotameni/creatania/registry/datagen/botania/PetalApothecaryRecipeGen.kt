package zaftnotameni.creatania.registry.datagen.botania

import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.simibubi.create.AllItems
import net.minecraft.data.DataGenerator
import net.minecraft.data.DataProvider
import net.minecraft.data.HashCache
import java.io.IOException

const val example = """
{
  "type": "botania:gog_alternation",
  "gog": {
    "type": "botania:petal_apothecary",
    "output": {"item": "#outputitem"}
  },
  "base": {
    "type": "botania:petal_apothecary",
    "output": {"item": "#outputitem"}
  }
}
"""

val blazecake = JsonParser.parseString("{item: \"${AllItems.BLAZE_CAKE.id}\"}").asJsonObject
val red = JsonParser.parseString("{tag: \"botania:petals/red\"}").asJsonObject
val yellow = JsonParser.parseString("{tag: \"botania:petals/yellow\"}").asJsonObject
val orange = JsonParser.parseString("{tag: \"botania:petals/orange\"}").asJsonObject

class PetalApothecaryRecipeGen(gen : DataGenerator?) : BotaniaBaseRecipeGen(gen, "botania:petal_apothecary"), DataProvider {
  override fun run(pCache : HashCache) {
    val recipe = example.replace("#outputitem", "creatania:blazunia")
    val root = JsonParser.parseString(recipe).asJsonObject
    val gog = root.getAsJsonObject("gog").asJsonObject
    val base = root.getAsJsonObject("base").asJsonObject
    val ingredients = JsonArray()
    with(ingredients) {
      add(red)
      add(yellow)
      add(orange)
      add(blazecake)
    }
    gog.add("ingredients", ingredients.deepCopy())
    base.add("ingredients", ingredients.deepCopy())
    with(root) {
      remove("gog")
      remove("base")
      add("gog", gog)
      add("base", base)
    }
    try {
      DataProvider.save(GSON, pCache, root, getPath(generator.outputFolder, super.typeNoNs(), "blazunia"))
    } catch (e : IOException) {
      throw RuntimeException(e)
    }
  }

  override fun getName() : String = "Creatania's Petal Apothecary recipes"
}