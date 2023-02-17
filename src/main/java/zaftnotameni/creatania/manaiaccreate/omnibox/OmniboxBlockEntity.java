package zaftnotameni.creatania.manaiaccreate.omnibox;
import com.simibubi.create.content.contraptions.relays.encased.DirectionalShaftHalvesTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
public class OmniboxBlockEntity extends DirectionalShaftHalvesTileEntity {
  public OmniboxBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
    super(type, pos, state);
  }
}
