package zaftnotameni.creatania.commands;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayer;

import java.util.UUID;
public class CreataniaTesterCommand {
  public CreataniaTesterCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
    dispatcher.register(Commands.literal("creatania").then(Commands.literal("test").executes(command -> {
      return creataniaTest(command);
    })));
  }

  public static int creataniaTest(CommandContext<CommandSourceStack> command) {
    var source = command.getSource();
    var player = source.getEntity();
    var level = source.getLevel();
    if (level == null || level.isClientSide || player == null || !(player instanceof ServerPlayer p)) return 0;
    var fakePlayer = new FakePlayer(level, new GameProfile(UUID.randomUUID(), "fake"));
    fakePlayer.getInventory().add(new ItemStack(Items.COAL_BLOCK, 64));
    var pos = p.getOnPos().west().above();
    var state = level.getBlockState(pos);
    var block = state.getBlock();
    var hit = new BlockHitResult(new Vec3(pos.getX(), pos.getY(), pos.getZ()), Direction.NORTH, pos, false);
    block.use(state, level, pos, fakePlayer, InteractionHand.MAIN_HAND, hit);
    return 0;
  }
}
