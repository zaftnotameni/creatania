package zaftnotameni.creatania.ponder;

public class CreataniaPonderIndex {

  public static void register() {
    registerScenes();
    associateTags();
    registerTags();
  }

  public static void associateTags() {
    CreataniaTagMap.associate();
  }
  public static void registerTags() {
    CreataniaPonderTag.register();
  }

  public static void registerScenes() {
    CreataniaPonderScene.register();
  }

}