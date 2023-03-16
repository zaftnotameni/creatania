package zaftnotameni.creatania.util

import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import dev.architectury.platform.Platform
import dev.latvian.mods.kubejs.KubeJSPaths
import org.apache.commons.io.IOUtils
import zaftnotameni.creatania.CreataniaMain
import java.nio.file.Files
import java.nio.file.Path

fun gameDirectory() : Path = Platform.getGameFolder().normalize().toAbsolutePath()

fun readMetadata() : JsonArray {
  val metadataStream = CreataniaMain::class.java.getResourceAsStream("/data/creatania/kubejs_metadata.json")
  val reader = JsonReader(metadataStream?.bufferedReader())
  val metadataJson = JsonParser.parseReader(reader).asJsonObject
  return metadataJson.getAsJsonArray("targetResources")
}

fun createRequiredFolders() {
  val assetsFolder = KubeJSPaths.ASSETS.resolve("creatania/ponder")
  val clientScriptsFolder = KubeJSPaths.CLIENT_SCRIPTS.resolve("creatania/ponder")
  info("Creating folder for kubejs assets for ponder for creatania: $assetsFolder")
  Files.createDirectories(assetsFolder)
  info("Creating folder for kubejs client scripts for ponder for creatania: $clientScriptsFolder")
  Files.createDirectories(clientScriptsFolder)
}

fun writeResourceFile(resource: String) {
  val pathInJar = "/data/kubejs/$resource"
  debug("Copying $resource from $pathInJar to its destination for kubejs")
  val inputStream = CreataniaMain::class.java.getResourceAsStream(pathInJar)
  val targetPath = if (resource.contains("client_scripts")) KubeJSPaths.CLIENT_SCRIPTS else KubeJSPaths.ASSETS
  val file = targetPath.resolve(resource.replace("client_scripts/", "").replace("assets/", ""))
  Files.createDirectories(file.parent)

  if (Files.notExists(file, *arrayOfNulls(0))) {
    val outputStream = Files.newOutputStream(file)
    outputStream.write(IOUtils.toByteArray(inputStream))
    outputStream.close()
    debug("Copied $resource to ${file.toAbsolutePath()}")
  } else {
    info("Did NOT copy $resource to ${file.toAbsolutePath()} because a file already exists, delete the file to get a new version")
  }
}

fun onlyPonderResources(resource : String) = resource.contains("creatania/ponder")
fun writeToFile(resource : String) = writeResourceFile(resource)

fun handleKubejsScripts() {
  createRequiredFolders()
  val targetResources = readMetadata()
  targetResources.map { it.asString }
    .filter(::onlyPonderResources)
    .forEach(::writeToFile)
}

fun safeHandleKubeJsScripts() {
  try {
    handleKubejsScripts()
    info("ponderjs scripts for creatania copied to their appropriated folders, happy pondering!")
  } catch (tw : Throwable) {
    warn("Could not setup ponderjs scripts, some ponder details might be missing! Reason: $tw, ${tw.stackTraceToString()}")
  }
}