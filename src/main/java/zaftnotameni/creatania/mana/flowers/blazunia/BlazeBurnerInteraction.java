package zaftnotameni.creatania.mana.flowers.blazunia;

import com.mojang.authlib.GameProfile;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.util.FakePlayer;
import org.apache.logging.log4j.util.TriConsumer;

public class BlazeBurnerInteraction {

  public static FakePlayer fakePlayer;

  public static FakePlayer makeFakePlayer(ServerLevel level) {
    return new FakePlayer(level, new GameProfile(UUID.randomUUID(), "fake"));
  }

  public static ItemStack stackOfOakPlanks() { return new ItemStack(Items.OAK_PLANKS, 64); }

  public static ItemStack stackOfBlazeCake() { return new ItemStack(AllItems.BLAZE_CAKE.get(), 64); }

  public static void useFuelOnBlazeBurner(FakePlayer fakePlayer, ServerLevel level, BlockState bbs, BlockPos bpos, Block block, BlockHitResult hit) {
    block.use(bbs, level, bpos, fakePlayer, InteractionHand.MAIN_HAND, hit);
  }

  public static void scanRangeXYZ(int xr, int yr, int zr, BlockPos pos, TriConsumer<Integer, Integer, Integer> fn) {
    for (var x = -xr; x <= xr; x++) for (var z = -zr; z <= zr; z++) for (var y = -yr; y <= yr; y++) fn.accept(x, y, z);
  }

  /**
   * range = radius
   */
  public static void scanRangeForBlazeBurners(int xrange, int yrange, int zrange, ServerLevel level, BlockPos pos, Consumer<BlazeBurnerBlockEntity> fn) {
    scanRangeXYZ(xrange, yrange, zrange, pos, (x, y, z) -> {
      if (level.getBlockEntity(pos.offset(x, y, z)) instanceof BlazeBurnerBlockEntity te) fn.accept(te);
    });
  }

}