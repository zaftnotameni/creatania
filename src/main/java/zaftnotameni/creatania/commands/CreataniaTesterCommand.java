package zaftnotameni.creatania.commands;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
public class CreataniaTesterCommand {
  public CreataniaTesterCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
    CommandBuildContext commandbuildcontext = new CommandBuildContext(RegistryAccess.BUILTIN.get());
    dispatcher.register(
      Commands.literal("creatania").then(
        Commands.literal("checkerboardfloor").then(
          Commands.argument("range", IntegerArgumentType.integer(1, 300)).then(
            Commands.argument("block1", BlockStateArgument.block(commandbuildcontext)).then(
              Commands.argument("block2", BlockStateArgument.block(commandbuildcontext))
                .executes(CreataniaTesterCommand::checkerboardfloor))))));
  }

  public static int checkerboardfloor(CommandContext<CommandSourceStack> command) {
    var source = command.getSource();
    var range = IntegerArgumentType.getInteger(command, "range");
    var b1 = BlockStateArgument.getBlock(command, "block1").getState();
    var b2 = BlockStateArgument.getBlock(command, "block2").getState();
    var player = source.getEntity();
    var level = source.getLevel();
    if (level == null || level.isClientSide || player == null || !(player instanceof ServerPlayer p)) return 0;
    var pos = player.getOnPos();
    player.sendSystemMessage (Component.literal("checkerboard started") );
    for (var x = -range; x <= range; x++) for (var z = -range; z <= range; z++) {
      var targetPos = pos.offset(x, 0, z);
      var b = ((x + z) % 2) == 0 ? b1 : b2;
      player.sendSystemMessage(Component.literal(
        "Setting block at " + targetPos.toShortString() + " to " + b.getBlock().getDescriptionId()));
      level.setBlockAndUpdate(targetPos, b);
    }
    player.sendSystemMessage(Component.literal("checkerboard finished"));
    return 0;
  }
}
