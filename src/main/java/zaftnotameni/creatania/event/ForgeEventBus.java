package zaftnotameni.creatania.event;

import com.simibubi.create.AllItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.registry.*;
import zaftnotameni.creatania.util.ScanArea;

import java.util.Collection;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventBus {
  public static void slimy(Level level, LivingEntity entity) {
    if (level.isClientSide()) return;
    entity.is(EntityType.SLIME.create(level));
    var pos = entity.getOnPos();
    var posAbove = entity.getOnPos().above();
    var blockState = level.getBlockState(pos);
    var blockStateAbove = level.getBlockState(posAbove);
    var fluidState = level.getFluidState(pos);
    var fluidStateAbove = level.getFluidState(posAbove);
    if (!fluidState.is(Fluids.MANA_FLUID.get())
      && !fluidStateAbove.is(Fluids.MANA_FLUID.get())
      && !blockState.is(Fluids.MANA_FLUID_BLOCK.get())
      && !blockStateAbove.is(Fluids.MANA_FLUID_BLOCK.get())) return;
    ScanArea.forEachPlayerInTheArea(level, pos, 128, p -> true, Advancements.PRODUCE_MANA_GEL_FROM_SLIME::awardTo);
    var stack = new ItemStack(Items.MANA_GEL.get(),1);
    entity.kill();
    var itemEntity = new ItemEntity(level, posAbove.getX(), posAbove.getY(), posAbove.getZ(), stack);
    itemEntity.setDeltaMovement(0f, 1f, 0f);
    level.addFreshEntity(itemEntity);
  }
  @SubscribeEvent
  public static void onLivingFallEvent(LivingFallEvent evt) {
    var entity = evt.getEntityLiving();
    var level = entity.getLevel();
    slimy(level, entity);
  }
  @SubscribeEvent
  public static void onLivingJumpEvent(LivingEvent.LivingJumpEvent evt) {
    var entity = evt.getEntityLiving();
    var level = entity.getLevel();
    slimy(level, entity);
  }
  @SubscribeEvent
  public static void onEnderEntityTeleportAttempt(EntityTeleportEvent.EnderEntity evt) {
    try {
      var level = evt.getEntityLiving().getLevel();
      if (level.isClientSide()) return;
      var pos = evt.getEntityLiving().getOnPos();
      var blockStateBelow = level.getBlockState(pos);
      var preventsTeleportation = blockStateBelow.is(Tags.Blocks.PREVENTS_ENDER_TELEPORTATION);
      if (!preventsTeleportation) return;
      var previous = evt.getPrev();
      evt.setCanceled(true);
      evt.setResult(Event.Result.DENY);
      evt.setTargetX(previous.x);
      evt.setTargetY(previous.y);
      evt.setTargetZ(previous.z);
      ScanArea.forEachPlayerInTheArea(level, pos, 128, p -> true, Advancements.PREVENT_ENDER_ENTITY_FROM_TELEPORTING::awardTo);
    } catch (RuntimeException e) {} catch (Exception e) {}
  }
  @SubscribeEvent
  public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock evt) {
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
      var blockBlacklisted = blockState.is(Tags.Blocks.BLACKLISTED_FOR_WRENCH_PICKUP);

      if (blockBlacklisted || item == null) {
        evt.setResult(Event.Result.ALLOW);
        return;
      }
      Advancements.PICK_A_BOTANIA_BLOCK_WITH_CREATE_WRENCH.awardTo(player);
      var stack = new ItemStack(item,1);
      var pos = hit.getBlockPos();
      var be = level.getBlockEntity(pos);
      if (be != null) {
        var nbt = be.saveWithFullMetadata();
        stack.setTag(nbt);
        be.saveToItem(stack);
      }
      addItemToPlayer(player, stack);
      level.destroyBlock(hit.getBlockPos(), false);
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
    var path = resource.getPath();
    var blacklisted = StringUtils.containsIgnoreCase(CommonConfig.BLACKLISTED_WRENCH_BLOCKS.get(), path);
    var notQuite = blacklisted || item == null || resource == null || namespace == null || !namespace.equalsIgnoreCase("botania");
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
