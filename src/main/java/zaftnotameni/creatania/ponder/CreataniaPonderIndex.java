package zaftnotameni.creatania.ponder;
public class CreataniaPonderIndex {
  public static void register() {
    registerScenes();
    registerTags();
  }

  public static void registerScenes() {
    CreataniaPonderScene.register();
  }

  public static void registerTags() {
    CreataniaPonderTag.register();
  }
}
