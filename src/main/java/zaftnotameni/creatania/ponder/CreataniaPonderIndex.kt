package zaftnotameni.creatania.ponder

import zaftnotameni.creatania.ponder.CreataniaTagMap.associate

object CreataniaPonderIndex {
  @JvmStatic
  fun register() {
    registerScenes()
    associateTags()
    registerTags()
  }

  fun associateTags() {
    associate()
  }

  fun registerTags() {
    CreataniaPonderTag.registerPonderTags()
  }

  fun registerScenes() {
    CreataniaPonderScene.register()
  }
}