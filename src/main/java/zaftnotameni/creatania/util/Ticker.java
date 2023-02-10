package zaftnotameni.creatania.util;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
public class Ticker {
  public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(
      BlockEntityType<A> pServerType, BlockEntityType<E> pClientType, BlockEntityTicker<? super E> pTicker) {
    return pClientType == pServerType ? (BlockEntityTicker<A>)pTicker : null;
  }
}
