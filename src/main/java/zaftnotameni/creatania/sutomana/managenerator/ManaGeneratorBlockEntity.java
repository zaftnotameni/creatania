package zaftnotameni.creatania.sutomana.managenerator;

import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.api.mana.IManaPool;
import zaftnotameni.creatania.config.CommonConfig;

public class ManaGeneratorBlockEntity extends KineticTileEntity {
  public boolean isFirstTick = true;
  public ManaGeneratorBlockEntity(BlockEntityType<? extends ManaGeneratorBlockEntity> type, BlockPos pos, BlockState state) {
    super(type, pos, state);
    this.setLazyTickRate(CommonConfig.MANA_GENERATOR_LAZY_TICK_RATE.get());
  }

  public int getNormalizedRPM() {
    var min = CommonConfig.MANA_GENERATOR_MINIMUM_RPM.get();
    if (this.getSpeed() < min) return 0;
    return Math.max(CommonConfig.MANA_GENERATOR_MINIMUM_RPM.get(),
      Math.min(CommonConfig.MANA_GENERATOR_MAXIMUM_RPM.get(), (int) Math.abs(this.getSpeed())));
  }
  public void serverTick() {
    if (this.isOverStressed() || this.getNormalizedRPM() == 0 || this.worldPosition == null) return;
    if (Math.abs(getSpeed()) > 0 && isSpeedRequirementFulfilled()) {
      var blockAbove = level.getBlockEntity(this.worldPosition.above());
      if (blockAbove != null && blockAbove instanceof IManaPool) {
        var pool = (IManaPool) blockAbove;
        if (!pool.isFull()) pool.receiveMana(this.getManaProductionRate());
      }
    }
  }
  @Override
  public void tick() {
    super.tick();
    isFirstTick = false;
    if (this.level == null || this.level.isClientSide()) return;
    serverTick();
  }

  public int getManaProductionRate() {
    return getNormalizedRPM() * CommonConfig.MANA_GENERATOR_MANA_PER_RPM_PER_TICK.get();
  }

  @Override
  public float calculateStressApplied() {
    float impact = CommonConfig.MANA_GENERATOR_SU_PER_RPM.get() * Math.abs(this.getSpeed());
    this.lastStressApplied = impact;
    return impact;
  }
  @Override
  public float calculateAddedStressCapacity() {
    return 0f;
  }
}


