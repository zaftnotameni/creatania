package zaftnotameni.creatania.block.entity.custom;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import zaftnotameni.creatania.block.entity.ModBlockEntities;

public class ManaGeneratorBlockEntity extends KineticTileEntity {
  public static final int LAZY_TICK_RATE = 20;
  public boolean isFirstTick = true;

  public ManaGeneratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
    super(ModBlockEntities.MANA_GENERATOR_BLOCK_ENTITY.get(), pPos, pBlockState);
    this.setLazyTickRate(LAZY_TICK_RATE);
  }

  @Override
  public float calculateStressApplied() {
    float impact = 64/256f;
    this.lastStressApplied = impact;
    return impact;
  }

  @Override
  public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
    return super.getCapability(cap, side);
  }

  @Override
  public void read(CompoundTag compound, boolean clientPacket) {
    super.read(compound, clientPacket);
  }

  @Override
  public void write(CompoundTag compound, boolean clientPacket) {
    super.write(compound, clientPacket);
  }

  @Override
  public void tick() {
    super.tick();
    if(level.isClientSide())
      return;
    if(isFirstTick)
      firstTick();
    isFirstTick = false;

    if(Math.abs(getSpeed()) > 0 && isSpeedRequirementFulfilled()) {
      // TODO: produce mana
    }
  }

  public static int getManaProductionRate(int rpm) {
    return Math.abs(rpm);
  }

  @Override
  protected Block getStressConfigKey() {
    return AllBlocks.MECHANICAL_MIXER.get();
  }

  @Override
  public void remove() {
    super.remove();
  }

  public void firstTick() {
    updateCache();
  };

  public void updateCache() {
    if(level.isClientSide())
      return;
  }
}


