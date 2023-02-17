package zaftnotameni.creatania.manaiaccreate.omnibox;
import com.simibubi.create.content.contraptions.relays.gearbox.GearboxTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
public class OmniboxBlockEntity extends GearboxTileEntity {
  public OmniboxBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
    super(type, pos, state);
  }
}
