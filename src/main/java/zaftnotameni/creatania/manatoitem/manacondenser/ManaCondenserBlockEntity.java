package zaftnotameni.creatania.manatoitem.manacondenser;

import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import vazkii.botania.api.BotaniaForgeCapabilities;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.api.mana.IManaReceiver;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.registry.Items;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.MathContext;

public class ManaCondenserBlockEntity extends KineticTileEntity implements IManaReceiver {
  public LazyOptional<IManaReceiver> lazyManaReceiver = LazyOptional.empty();
  public boolean isFirstTick = true;
  public int mana = 0;
  public ManaCondenserBlockEntity(BlockEntityType<? extends ManaCondenserBlockEntity> type, BlockPos pos, BlockState state) {
    super(type, pos, state);
    this.setLazyTickRate(CommonConfig.MANA_GENERATOR_LAZY_TICK_RATE.get());
  }
  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    if (cap == BotaniaForgeCapabilities.MANA_RECEIVER) return lazyManaReceiver.cast();
    return super.getCapability(cap, side);
  }
  public int getNormalizedRPM() {
    var min = CommonConfig.MANA_CONDENSER_SU_PER_RPM.get();
    if (Math.abs(this.getSpeed()) < min) return 0;
    return Math.max(0, (int) Math.abs(this.getSpeed()));
  }
  public void serverTick() {
    var rpm = this.getNormalizedRPM();
    var requiredMana = CommonConfig.MANA_CONDENSER_MANA_PER_TICK_PER_RPM.get() * rpm;
    if (this.doesNotMeetRequirementsToCondenseMana(rpm, requiredMana)) return;
    this.receiveMana(-requiredMana);
    this.insertManaGelBelow();
  }
  public boolean doesNotMeetRequirementsToCondenseMana(int rpm, int requiredMana) {
    return this.isOverStressed() || (rpm <= 0) || (this.worldPosition == null) || !this.isSpeedRequirementFulfilled() || (this.mana < requiredMana);
  }
  public void insertManaGelBelow() {
    BlockEntity entityBelow = this.level.getBlockEntity(this.worldPosition.below());
    if (entityBelow == null) return;
    entityBelow.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).ifPresent((inventoryBelow) -> {
      ItemStack stack = new ItemStack(Items.MANA_GEL.get().asItem());
      for (int i = 0; i < inventoryBelow.getSlots(); i++) {
        if (!inventoryBelow.isItemValid(i, stack)) continue;
        if (!inventoryBelow.insertItem(i, stack, true).isEmpty()) continue;
        inventoryBelow.insertItem(i, stack, false);
        break;
      }
    });
  }
  @Override
  public void tick() {
    super.tick();
    isFirstTick = false;
    if (this.level == null || this.level.isClientSide()) return;
    serverTick();
  }
  public int getManaConsumptionRate() { return getNormalizedRPM() * CommonConfig.MANA_CONDENSER_MANA_PER_TICK_PER_RPM.get(); }

  @Override
  public float calculateStressApplied() {
    float impact = CommonConfig.MANA_CONDENSER_SU_PER_RPM.get() * Math.abs(this.getSpeed());
    this.lastStressApplied = impact;
    return impact;
  }
  @Override
  public float calculateAddedStressCapacity() { return 0f; }
  @Override
  public Level getManaReceiverLevel() { return this.getLevel(); }
  @Override
  public BlockPos getManaReceiverPos() { return this.getBlockPos(); }
  @Override
  public int getCurrentMana() { return this.mana; }
  @Override
  public boolean isFull() { return this.mana > CommonConfig.MANA_CONDENSER_MAX_MANA_STORAGE.get(); }
  @Override
  public void receiveMana(int mana) { this.mana = Math.max(0, this.mana + mana); this.setChanged(); }
  @Override
  public boolean canReceiveManaFromBursts() { return !this.isFull(); }
  @Override
  public void invalidateCaps() {
    super.invalidateCaps();
    this.lazyManaReceiver.invalidate();
  }
  @Override
  public void onLoad() {
    super.onLoad();
    lazyManaReceiver = LazyOptional.of(() -> this);
  }
}


