package zaftnotameni.creatania.mana.flowers.blazunia;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
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
import net.minecraft.world.phys.BlockHitResult;
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

import static zaftnotameni.creatania.mana.flowers.blazunia.BlazeBurnerInteraction.makeFakePlayer;
import static zaftnotameni.creatania.mana.flowers.blazunia.BlazeBurnerInteraction.scanRangeForBlazeBurners;
import static zaftnotameni.creatania.mana.flowers.blazunia.BlazeBurnerInteraction.stackOfOakPlanks;
import static zaftnotameni.creatania.mana.flowers.blazunia.BlazeBurnerInteraction.useFuelOnBlazeBurner;
import static zaftnotameni.creatania.mana.flowers.blazunia.BlazuniaBlockStates.HAS_MANA_SOURCE;
import static zaftnotameni.creatania.mana.flowers.blazunia.BlazuniaBlockStates.IS_SUPERHOT;

@OnlyIn(value = Dist.CLIENT, _interface = IWandHUD.class) public class BlazuniaFunctionalFlowerBlockEntity extends SmartTileEntity implements BotaniaFlowerInterfaces {

  public LazyOptional<FunctionalFlowerHandler> lazyFlowerHandler = LazyOptional.of(() -> new FunctionalFlowerHandler(this).withColor(0xffffff00)
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

  public void setHasManaSource(Boolean hasManaSource) {
    var hadManaSource = getBlockState().getOptionalValue(HAS_MANA_SOURCE).orElse(false);
    if (hadManaSource == hasManaSource) return;
    level.setBlockAndUpdate(worldPosition, getBlockState().setValue(HAS_MANA_SOURCE, hasManaSource));
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
    var fakePlayer = makeFakePlayer(serverLevel);
    fakePlayer.getInventory().add(stackOfOakPlanks());
    var pos = getBlockPos();
    NonNullList<Integer> consumedMana = NonNullList.create();
    scanRangeForBlazeBurners(1, 3, 1, serverLevel, pos, (bb) -> {
      if (!evalFlowerHandler(FunctionalFlowerHandler::hasEnoughManaForOneOperation, () -> false)) return;
      var bbs = bb.getBlockState();
      var bpos = bb.getBlockPos();
      var block = bbs.getBlock();
      var hit = makeBlockHitResult(bpos);
      useFuelOnBlazeBurner(fakePlayer, serverLevel, bbs, bpos, block, hit);
      consumedMana.add(evalFlowerHandler(FunctionalFlowerHandler::getManaPerOperation, () -> 0));
    });
    return consumedMana.stream().mapToInt(Integer::intValue).sum();
  }

  public int doClientSideAnimation() {
    spawnParticles(getBlockState().getOptionalValue(HAS_MANA_SOURCE).orElse(false),
      getBlockState().getOptionalValue(IS_SUPERHOT).orElse(false),
      1);
    return 0;
  }

  public void spawnParticles(Boolean hasManaSource, Boolean superhot, double burstMult) {
    if (level == null)
      return;

    if (!hasManaSource) return;

    Random r = level.getRandom();

    if (r.nextInt(3) != 0)
      return;

    Vec3 c = VecHelper.getCenterOf(worldPosition);
    Vec3 v = c.add(VecHelper.offsetRandomly(Vec3.ZERO, r, .125f)
      .multiply(1, 0, 1));

    boolean empty = level.getBlockState(worldPosition.above())
      .getCollisionShape(level, worldPosition.above())
      .isEmpty();

//    if (empty || r.nextInt(8) == 0)
//      level.addParticle(ParticleTypes.LARGE_SMOKE, v.x, v.y, v.z, 0, 0, 0);

    double yMotion = empty ? .0625f : r.nextDouble() * .0125f;
    Vec3 v2 = c.add(VecHelper.offsetRandomly(Vec3.ZERO, r, .5f)
        .multiply(1, .25f, 1)
        .normalize()
        .scale((empty ? .25f : .5) + r.nextDouble() * .125f))
      .add(0, .5, 0);

    if (superhot) {
      level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, v2.x, v2.y, v2.z, 0, yMotion, 0);
    } else {
      level.addParticle(ParticleTypes.FLAME, v2.x, v2.y, v2.z, 0, yMotion, 0);
    }
  }

  @NotNull private static BlockHitResult makeBlockHitResult(BlockPos bpos) {
    var hit = new BlockHitResult(new Vec3(bpos.getX(), bpos.getY(), bpos.getZ()), Direction.NORTH, bpos, false);
    return hit;
  }

  @Override public void addBehaviours(List<TileEntityBehaviour> behaviours) { }

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