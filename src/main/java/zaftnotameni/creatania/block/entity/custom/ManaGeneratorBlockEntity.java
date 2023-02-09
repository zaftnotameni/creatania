package zaftnotameni.creatania.block.entity.custom;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.foundation.block.BlockStressValues;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.api.mana.IManaPool;
import zaftnotameni.creatania.block.entity.ModBlockEntities;
import zaftnotameni.creatania.config.ModCommonConfigs;

public class ManaGeneratorBlockEntity extends KineticTileEntity {
  public boolean isFirstTick = true;

  public ManaGeneratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
    super(ModBlockEntities.MANA_GENERATOR_BLOCK_ENTITY.get(), pPos, pBlockState);
    this.setLazyTickRate(ModCommonConfigs.MANA_GENERATOR_LAZY_TICK_RATE.get());
  }

  public int getNormalizedRPM() {
    var min = ModCommonConfigs.MANA_GENERATOR_MINIMUM_RPM.get();
    if (this.getSpeed() < min) return 0;
    return Math.max(ModCommonConfigs.MANA_GENERATOR_MINIMUM_RPM.get(),
      Math.min(ModCommonConfigs.MANA_GENERATOR_MAXIMUM_RPM.get(), (int) Math.abs(this.getSpeed())));
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
    return getNormalizedRPM() * ModCommonConfigs.MANA_GENERATOR_MANA_PER_RPM_PER_TICK.get();
  }

  @Override
  public float calculateStressApplied() {
    float impact = ModCommonConfigs.MANA_GENERATOR_SU_PER_RPM.get() * Math.abs(this.getSpeed());
    this.lastStressApplied = impact;
    return impact;
  }
  @Override
  public float calculateAddedStressCapacity() {
    return 0f;
  }
}


