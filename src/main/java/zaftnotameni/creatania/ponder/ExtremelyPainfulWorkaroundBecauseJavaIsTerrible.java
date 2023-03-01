package zaftnotameni.creatania.ponder;

import com.simibubi.create.foundation.ponder.PonderScene;
import com.simibubi.create.foundation.ponder.PonderWorld;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import java.lang.reflect.Field;
import java.util.function.Supplier;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.Property;

public class ExtremelyPainfulWorkaroundBecauseJavaIsTerrible {
  public static Supplier<PonderWorld> zawarudo(CreataniaPonderUtils.XYZ self) {
    return () -> {
      try {
        Field fScene = SceneBuilder.class.getDeclaredField("scene");
        fScene.setAccessible(true);
        var vScene = fScene.get(self.u.scene);
        if (vScene instanceof PonderScene ponderScene) self.ponderScene = ponderScene;
        Field fWorld = PonderScene.class.getDeclaredField("world");
        fWorld.setAccessible(true);
        var vWorld = fWorld.get(vScene);
        if (vWorld instanceof PonderWorld ponderWorld) return self.ponderWorld = ponderWorld;
        return null;
      } catch (Exception e) { return null; }
    };
  }

  public static Supplier<Property<Direction>> getBlockStateFacingType(CreataniaPonderUtils.XYZ self) {
    return () -> {
      self.getBlockState();
      if (self.resolvedBlockState == null) return null;
      return (Property<Direction>) self.resolvedBlockState
        .getValues()
        .keySet()
        .stream()
        .filter(k -> k
          .getName()
          .equalsIgnoreCase("facing"))
        .findFirst()
        .get();
    };
  }

}