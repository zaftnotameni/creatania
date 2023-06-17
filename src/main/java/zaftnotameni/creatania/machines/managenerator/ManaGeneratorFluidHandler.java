package zaftnotameni.creatania.machines.managenerator;

import com.simibubi.create.infrastructure.config.AllConfigs;
import com.simibubi.create.foundation.fluid.CombinedTankWrapper;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.registry.Fluids;

public class ManaGeneratorFluidHandler {

  public boolean contentsChanged;
  public SmartFluidTankBehaviour inputTankBehavior;
  public LazyOptional<IFluidHandler> fluidCapability;
  private ManaGeneratorBlockEntity manaGenerator;

  public ManaGeneratorFluidHandler(ManaGeneratorBlockEntity manaGenerator) {
    this.manaGenerator = manaGenerator;
    contentsChanged = true;
  }

  public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    this.inputTankBehavior = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.INPUT, this.manaGenerator, 1, (int) this.getManaTankCapacity(), false).whenFluidUpdates(() ->
      this.contentsChanged =
        true);

    behaviours.add(inputTankBehavior);

    this.fluidCapability = LazyOptional.of(() -> {
      LazyOptional<? extends IFluidHandler> inputCap = inputTankBehavior.getCapability();
      return new CombinedTankWrapper(inputCap.orElse(null));
    });
  }

  public SmartFluidTankBehaviour.TankSegment getPrimaryTank() {
    if (this.inputTankBehavior == null || this.inputTankBehavior.getPrimaryTank() == null) return null;
    return this.inputTankBehavior.getPrimaryTank();
  }

  public int drainManaFluidFromTank(int amount) {
    var fluidCapability = this.inputTankBehavior.getCapability().orElse(null);
    var sourceMana = new FluidStack(Fluids.PURE_MANA.get().getSource(), amount);
    var flowingMana = new FluidStack(Fluids.PURE_MANA.get().getFlowing(), amount);
    var sourceDrained = fluidCapability.drain(sourceMana, IFluidHandler.FluidAction.EXECUTE);
    var flowingDrained = fluidCapability.drain(flowingMana, IFluidHandler.FluidAction.EXECUTE);
    this.getPrimaryTank().onFluidStackChanged();
    return sourceDrained.getAmount() + flowingDrained.getAmount();
  }

  public float getManaFluidAvailable() {
    var primaryTank = this.getPrimaryTank();
    if (primaryTank == null) return 0f;
    return primaryTank.getFluidLevel().getValue() * 1000;
  }

  public float getManaTankCapacity() {
    return Math.min(6f, CommonConfig.MANA_GENERATOR_MAX_MANA_FLUID_STORAGE.get()) * AllConfigs.server().kinetics.maxRotationSpeed.get();
  }

  public void read(CompoundTag compound, boolean clientPacket) {

  }

  public void write(CompoundTag compound, boolean clientPacket) {

  }

  public void invalidate() {
    this.fluidCapability.invalidate();
  }

  public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
    if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return fluidCapability.cast();
    return null;
  }

  public boolean serverTick() {
    if (!this.contentsChanged) return false;
    this.contentsChanged = false;
    return true;
  }

}