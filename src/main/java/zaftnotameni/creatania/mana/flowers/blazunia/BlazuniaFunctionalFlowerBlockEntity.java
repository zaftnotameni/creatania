package zaftnotameni.creatania.mana.flowers.blazunia;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerTileEntity;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.api.block.IWandBindable;
import vazkii.botania.api.block.IWandHUD;
import vazkii.botania.api.block.IWandable;

import java.util.List;
import java.util.UUID;

public class BlazuniaFunctionalFlowerBlockEntity extends SmartTileEntity implements IWandBindable, IWandable, IWandHUD {
  public BlazuniaFunctionalFlowerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
    super(type, pos, state);
  }
  @Override
  public void addBehaviours(List<TileEntityBehaviour> behaviours) {

  }
  public int ticks = 0;
  @Override
  public void tick() {
    super.tick();
    if (ticks++ < 100) return;
    if (level == null || level.isClientSide()) return;
    if (!(level instanceof ServerLevel serverLevel)) return;
    ticks = 0;
    var fakePlayer = new FakePlayer(serverLevel, new GameProfile(UUID.randomUUID(), "fake"));
    fakePlayer.getInventory().add(new ItemStack(Items.COAL_BLOCK, 64));
    var bs = getBlockState();
    var pos = getBlockPos();
    for (var i = -50; i < 50; i++) for (var j = -50; j < 50; j++) {
      if (!(level.getBlockEntity(pos.offset(i , 0, j)) instanceof BlazeBurnerTileEntity blaze)) continue;
      var bbs = blaze.getBlockState();
      var bpos = blaze.getBlockPos();
      var block = bbs.getBlock();
      var hit = new BlockHitResult(new Vec3(bpos.getX(), bpos.getY(), bpos.getZ()), Direction.NORTH, bpos, false);
      block.use(bbs, level, bpos, fakePlayer, InteractionHand.MAIN_HAND, hit);
    }
  }
  @Override
  public CompoundTag getUpdateTag() {
    return super.getUpdateTag();
  }
  @Override
  public ClientboundBlockEntityDataPacket getUpdatePacket() {
    return super.getUpdatePacket();
  }
  @Override
  public void handleUpdateTag(CompoundTag tag) {
    super.handleUpdateTag(tag);
  }
  @Override
  public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet) {
    super.onDataPacket(connection, packet);
  }
  @Override
  public void sendData() {
    super.sendData();
  }
  @Override
  public void causeBlockUpdate() {
    super.causeBlockUpdate();
  }
  @Override
  public void notifyUpdate() {
    super.notifyUpdate();
  }
  @Override
  public PacketDistributor.PacketTarget packetTarget() {
    return super.packetTarget();
  }
  @Override
  public boolean canSelect(Player player, ItemStack wand, BlockPos pos, Direction side) {
    return false;
  }
  @Override
  public boolean bindTo(Player player, ItemStack wand, BlockPos pos, Direction side) {
    return false;
  }
  @Nullable
  @Override
  public BlockPos getBinding() {
    return null;
  }
  @Override
  public void renderHUD(PoseStack ms, Minecraft mc) {

  }
  @Override
  public boolean onUsedByWand(@Nullable Player player, ItemStack stack, Direction side) {
    return false;
  }
}
