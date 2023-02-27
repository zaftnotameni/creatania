package zaftnotameni.creatania.util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import zaftnotameni.creatania.registry.CreataniaAdvancements;
import zaftnotameni.creatania.registry.CreataniaItems;
public class Actions {
  public static void killSlimeProduceManagelGrantAchievement(Level level, LivingEntity entity, BlockPos pos) {
    if (level.isClientSide()) return;
    if (!Queries.isSlimeEntity(level, entity)) return;
    ScanArea.forEachPlayerInTheArea(level, pos, 128, p -> true, CreataniaAdvancements.PRODUCE_MANA_GEL_FROM_SLIME::awardTo);
    var stack = new ItemStack(CreataniaItems.MANA_GEL.get(),1);
    entity.kill();
    var itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack);
    itemEntity.setDeltaMovement(0f, 0.5f, 0f);
    level.addFreshEntity(itemEntity);
  }
}
