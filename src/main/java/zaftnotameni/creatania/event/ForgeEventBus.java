package zaftnotameni.creatania.event;

import com.simibubi.create.AllItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.registry.Advancements;

import java.util.Collection;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventBus {

  @SubscribeEvent
  public static void rickClickBlock(PlayerInteractEvent.RightClickBlock evt) {
    try {
      var player = evt.getPlayer();
      var level = evt.getWorld();
      var hit = evt.getHitVec();

      if (isNotServerOrNotMainHandOrHitAirOrHandIsEmpty(evt, player, level, hit)) {
        evt.setResult(Event.Result.ALLOW);
        return;
      }
      var blockState = level.getBlockState(hit.getBlockPos());
      var item = getTargetItem(level, blockState, hit);
      if (item == null) {
        evt.setResult(Event.Result.ALLOW);
        return;
      }
      var stack = new ItemStack(item,1);

      if (!(level instanceof ServerLevel serverLevel)) {
        evt.setResult(Event.Result.ALLOW);
        return;
      }
      // addItemToPlayer(player, stack);
      Advancements.PICK_A_BOTANIA_BLOCK_WITH_CREATE_WRENCH.awardTo(player);
      // level.destroyBlock(hit.getBlockPos(), false);
      var drops = level.getBlockState(hit.getBlockPos()).getDrops(new LootContext.Builder(serverLevel));

      evt.setCanceled(true);
    } catch (RuntimeException e) {} catch (Exception e) {}
  }
  private static void addItemToPlayer(Player player, ItemStack stack) {
    if (!player.getInventory().add(stack)) { player.drop(stack, false); }
  }
  private static void addItemsToPlayer(Player player, Collection<ItemStack> stacks) {
    for (var stack : stacks) addItemToPlayer(player, stack);
  }
  private static Item getTargetItem(Level level, BlockState blockState, BlockHitResult hit) {
    var item = blockState.getBlock().asItem();
    var resource = ForgeRegistries.ITEMS.getKey(item);
    var namespace = resource.getNamespace();
    var notQuite = item == null || resource == null || namespace == null || !namespace.equalsIgnoreCase("botania");
    if (notQuite) { return null; }
    else { return item; }
  }
  private static boolean isNotServerOrNotMainHandOrHitAirOrHandIsEmpty(PlayerInteractEvent.RightClickBlock evt, Player player, Level level, BlockHitResult hit) {
    var itemStack = evt.getItemStack();
    var hand = evt.getHand();
    var isWrench = itemStack != null && !itemStack.isEmpty() && itemStack.is(AllItems.WRENCH.get());
    var isCrouching = player != null && player.isCrouching();
    var isServer = !level.isClientSide();
    var isMainHand = InteractionHand.MAIN_HAND.equals(hand);
    var isWrong = !isWrench || !isCrouching || hit == null || !isServer || !isMainHand;
    return isWrong;
  }
}
