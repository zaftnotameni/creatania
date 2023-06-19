package zaftnotameni.creatania.mana.flowers.blazunia;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.VecHelper;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.api.block.IWandHUD;
import zaftnotameni.creatania.mana.flowers.BotaniaFlowerInterfaces;
import zaftnotameni.creatania.mana.flowers.FunctionalFlowerHandler;

import static com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel.SEETHING;
import static com.simibubi.create.content.processing.burner.BlazeBurnerBlock.tryInsert;
import static zaftnotameni.creatania.mana.flowers.blazunia.BlazeBurnerInteraction.scanRangeForBlazeBurners;
import static zaftnotameni.creatania.mana.flowers.blazunia.BlazeBurnerInteraction.stackOfBlazeCake;
import static zaftnotameni.creatania.mana.flowers.blazunia.BlazeBurnerInteraction.stackOfOakPlanks;
import static zaftnotameni.creatania.mana.flowers.blazunia.BlazuniaBlockStates.HAS_MANA_SOURCE;
import static zaftnotameni.creatania.mana.flowers.blazunia.BlazuniaBlockStates.IS_SUPERHOT;

@OnlyIn(value = Dist.CLIENT, _interface = IWandHUD.class) public class BlazuniaFunctionalFlowerBlockEntity extends SmartBlockEntity implements BotaniaFlowerInterfaces {

  public LazyOptional<FunctionalFlowerHandler> lazyFlowerHandler = LazyOptional.of(() -> FunctionalFlowerHandler.of(this).withColor(0xffffff00)
    .withMaxMana(10000)
    .withMaxTransfer(100)
    .withTickRate(40)
    .withRequiredManaPerOperation(10));

  public BlazuniaFunctionalFlowerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
    super(type, pos, state);
  }

  public <T> @NotNull LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
    return evalFlowerHandler(f -> f.getCapability(cap, side), () -> super.getCapability(cap, side));
  }

  @Override public void onLoad() {
    super.onLoad();
    this.lazyFlowerHandler.resolve();
  }

  @Override public @NotNull CompoundTag getUpdateTag() {
    var tag = super.getUpdateTag();
    return evalFlowerHandler(f -> f.getUpdateTag(tag), () -> tag);
  }

  @Override public void handleUpdateTag(@NotNull CompoundTag tag) {
    this.lazyFlowerHandler.resolve().ifPresent(f -> f.handleUpdateTag(tag));
    super.handleUpdateTag(tag);
  }

  @Override protected void write(CompoundTag tag, boolean clientPacket) {
    super.write(tag, clientPacket);
    this.lazyFlowerHandler.resolve().ifPresent(f -> f.saveAdditional(tag));
  }

  @Override protected void read(CompoundTag tag, boolean clientPacket) {
    super.read(tag, clientPacket);
    this.lazyFlowerHandler.resolve().ifPresent(f -> f.load(tag));
  }

  public void setIsSuperhot(Boolean willBeSuperhot) {
    var wasSuperhot = isSuperHot();
    if (wasSuperhot == willBeSuperhot) return;
    level.setBlockAndUpdate(worldPosition, getBlockState().setValue(IS_SUPERHOT, willBeSuperhot));
    notifyUpdate();
  }

  public void setHasManaSource(Boolean willHaveManaSource) {
    var hadManaSource = hasManaSource();
    if (hadManaSource == willHaveManaSource) return;
    level.setBlockAndUpdate(worldPosition, getBlockState().setValue(HAS_MANA_SOURCE, willHaveManaSource));
    notifyUpdate();
  }

  @Override public void doAnimationTick() {
    if (level == null) return;
    if (!level.isClientSide()) return;
    doClientSideAnimation();
  }

  @Override public int doTick() {
    if (level == null) return 0;
    if (!(level instanceof ServerLevel serverLevel)) return 0;
    var stack = isSuperHot() ? stackOfBlazeCake() : stackOfOakPlanks();
    var pos = getBlockPos();
    NonNullList<Integer> consumedMana = NonNullList.create();
    scanRangeForBlazeBurners(1, 3, 1, serverLevel, pos, (bb) -> {
      if (!evalFlowerHandler(FunctionalFlowerHandler::hasEnoughManaForOneOperation, () -> false)) return;
      var bbs = bb.getBlockState();
      var bpos = bb.getBlockPos();
      var isUpgrade = bb.getHeatLevelFromBlock() != SEETHING && isSuperHot();
      var littleFuelRemaining = bb.getRemainingBurnTime() < 100;
      if (isUpgrade || littleFuelRemaining) {
        tryInsert(bbs, level, bpos, stack, true, false, false);
        consumedMana.add(evalFlowerHandler(FunctionalFlowerHandler::getManaPerOperation, () -> 0));
      }
    });
    if (!hasManaSource()) return evalFlowerHandler(fh -> fh.mana, () -> 999999);
    return consumedMana.stream().mapToInt(Integer::intValue).sum();
  }

  public boolean isSuperHot() {
    return getBlockState().getOptionalValue(IS_SUPERHOT).orElse(false);
  }

  public int doClientSideAnimation() {
    spawnParticles(hasManaSource(), isSuperHot(), 1);
    return 0;
  }

  @NotNull private Boolean hasManaSource() {
    return getBlockState().getOptionalValue(HAS_MANA_SOURCE).orElse(false);
  }

  public void spawnParticles(Boolean hasManaSource, Boolean superhot, double burstMult) {
    if (level == null) return;

    if (!hasManaSource) return;

    Random r = level.getRandom();

    if (r.nextInt(3) != 0) return;

    Vec3 c = VecHelper.getCenterOf(worldPosition);
    Vec3 v = c.add(VecHelper.offsetRandomly(Vec3.ZERO, r, .125f).multiply(1, 0, 1));

    boolean empty = level.getBlockState(worldPosition.above()).getCollisionShape(level, worldPosition.above()).isEmpty();

    double yMotion = empty ? .0625f : r.nextDouble() * .0125f;
    Vec3 v2 = c.add(VecHelper.offsetRandomly(Vec3.ZERO, r, .5f).multiply(1, .25f, 1).normalize().scale((empty ? .25f : .5) + r.nextDouble() * .125f)).add(0, .5, 0);

    if (superhot) {
      level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, v2.x, v2.y, v2.z, 0, yMotion, 0);
    } else {
      level.addParticle(ParticleTypes.FLAME, v2.x, v2.y, v2.z, 0, yMotion, 0);
    }
  }

  @Override public void addBehaviours(List<BlockEntityBehaviour> behaviours) { }

  @Override public void tick() {
    super.tick();
    this.lazyFlowerHandler.resolve().ifPresent(FunctionalFlowerHandler::doTick);
  }

  @Override public boolean canSelect(Player player, ItemStack wand, BlockPos pos, Direction side) {
    return evalFlowerHandler(x -> x.canSelect(player, wand, pos, side), () -> false);
  }

  @Override public boolean bindTo(Player player, ItemStack wand, BlockPos pos, Direction side) {
    return evalFlowerHandler(x -> x.bindTo(player, wand, pos, side), () -> false);
  }

  @Nullable @Override public BlockPos getBinding() { return evalFlowerHandler(FunctionalFlowerHandler::getBinding, () -> null); }

  @Override public void renderHUD(PoseStack ms, Minecraft mc) {
    lazyFlowerHandler.resolve().ifPresent(x -> x.renderHUD(ms, mc));
  }

  @Override public boolean onUsedByWand(@Nullable Player player, ItemStack stack, Direction side) {
    return evalFlowerHandler(x -> x.onUsedByWand(player, stack, side), () -> false);
  }

  public <R> R evalFlowerHandler(Function<FunctionalFlowerHandler, R> yesFn, Supplier<R> noFn) {
    var resolved = lazyFlowerHandler.resolve();
    return resolved.isPresent() ? yesFn.apply(resolved.get()) : noFn.get();
  }

  @Override public void remove() {
    this.lazyFlowerHandler.invalidate();
    super.remove();
  }

}