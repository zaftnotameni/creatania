package zaftnotameni.creatania.mana.flowers.blazunia;
import com.mojang.authlib.GameProfile;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.util.FakePlayer;

import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
public class BlazeBurnerInteraction {
  public static FakePlayer makeFakePlayer(ServerLevel level) { return new FakePlayer(level, new GameProfile(UUID.randomUUID(), "fake"));}
  public static ItemStack stackOfOakPlanks() { return new ItemStack(Items.OAK_PLANKS, 64); }
  public static void useFuelOnBlazeBurner(FakePlayer fakePlayer, ServerLevel level, BlockState bbs, BlockPos bpos, Block block, BlockHitResult hit) {
    block.use(bbs, level, bpos, fakePlayer, InteractionHand.MAIN_HAND, hit);
  }
  public static void scanRange(int range, BlockPos pos, BiConsumer<Integer, Integer> fn) {
    for (var x = -range / 2; x <= range / 2; x++) for (var z = -range / 2; z <= range / 2; z++) fn.accept(x, z);


  }
  public static void scanRangeForBlazeBurners(int range, ServerLevel level, BlockPos pos, Consumer<BlazeBurnerTileEntity> fn) {
    scanRange(range, pos, (x, z) -> {
      if (level.getBlockEntity(pos.offset(x , 0, z)) instanceof BlazeBurnerTileEntity te) fn.accept(te);
    });
  }
}
