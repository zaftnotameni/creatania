package zaftnotameni.creatania.util;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
public class ScanArea {
  public static List<Player> getNearbyPlayers(Level level, AABB pArea, Function<Player, Boolean> fn) {
    List<Player> list = Lists.newArrayList();
    for(Player player : level.players()) {
      if (pArea.contains(player.getX(), player.getY(), player.getZ()) && fn.apply(player)) list.add(player);
    }
    return list;
  }
  public static AABB areaAround(BlockPos position, int radius) {
    return AABB.ofSize(new Vec3(position.getX(), position.getY(), position.getZ()), radius, radius, radius);
  }
  public static void forEachPlayerInTheArea(Level level, BlockPos position, int radius, Function<Player, Boolean> fn, Consumer<Player> action) {
    var players = getNearbyPlayers(level, areaAround(position, 128), fn);
    for (var player : players) action.accept(player);
  }
}
