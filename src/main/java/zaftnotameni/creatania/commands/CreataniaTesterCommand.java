package zaftnotameni.creatania.commands;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;
public class CreataniaTesterCommand {
  public CreataniaTesterCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
    dispatcher.register(
      Commands.literal("creatania").then(
        Commands.literal("checkerboardfloor").then(
          Commands.argument("range", IntegerArgumentType.integer(1, 300)).then(
            Commands.argument("block1", BlockStateArgument.block()).then(
              Commands.argument("block2", BlockStateArgument.block())
                .executes(CreataniaTesterCommand::creataniaTest))))));
  }

  public static int creataniaTest(CommandContext<CommandSourceStack> command) {
    var source = command.getSource();
    var range = IntegerArgumentType.getInteger(command, "range");
    var b1 = BlockStateArgument.getBlock(command, "block1").getState();
    var b2 = BlockStateArgument.getBlock(command, "block2").getState();
    var player = source.getEntity();
    var level = source.getLevel();
    if (level == null || level.isClientSide || player == null || !(player instanceof ServerPlayer p)) return 0;
    var pos = player.getOnPos();
    player.sendMessage(new TextComponent("checkerboard started"), UUID.randomUUID());
    for (var x = -range; x <= range; x++) for (var z = -range; z <= range; z++) {
      var targetPos = pos.offset(x, 0, z);
      var b = ((x + z) % 2) == 0 ? b1 : b2;
      player.sendMessage(new TextComponent(
        "Setting block at " + targetPos.toShortString() + " to " + b.getBlock().getDescriptionId()), UUID.randomUUID());
      level.setBlockAndUpdate(targetPos, b);
    }
    player.sendMessage(new TextComponent("checkerboard finished"), UUID.randomUUID());
    return 0;
  }
}
