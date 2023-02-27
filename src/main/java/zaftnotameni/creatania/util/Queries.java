package zaftnotameni.creatania.util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.StringUtils;

import static zaftnotameni.creatania.registry.CreataniaFluids.ALL;
public class Queries {
  public static boolean isOnTopOrInsideManaFluid(Level level, LivingEntity entity, BlockPos pos) {
    var posAbove = entity.getOnPos().above();
    var posBelow = entity.getOnPos().below();
    var fluidState = level.getFluidState(pos);
    if (fluidState.is(ALL.PURE_MANA.get())) return true;
    var fluidStateAbove = level.getFluidState(posAbove);
    if (fluidStateAbove.is(ALL.PURE_MANA.get())) return true;
    var fluidStateBelow = level.getFluidState(posBelow);
    if (fluidStateBelow.is(ALL.PURE_MANA.get())) return true;
    return false;
  }
  public static boolean isSlimeEntity(Level level, LivingEntity entity) {
    if (StringUtils.containsIgnoreCase(entity.getType().getDescriptionId(), "slime")) return true;
    if (StringUtils.containsIgnoreCase(entity.getType().getDescriptionId(), "magma")) return true;
    return false;
  }
}
