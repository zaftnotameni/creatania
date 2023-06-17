package zaftnotameni.creatania.stress.omnibox;
import com.simibubi.create.content.kinetics.base.DirectionalShaftHalvesBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
public class OmniboxBlockEntity extends DirectionalShaftHalvesBlockEntity {
  public OmniboxBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
    super(type, pos, state);
  }
}
