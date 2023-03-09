package zaftnotameni.creatania.event;

import com.simibubi.create.AllItems;
import java.util.Collection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.registry.Advancements;
import zaftnotameni.creatania.registry.Blocks;
import zaftnotameni.creatania.registry.Tags;
import zaftnotameni.creatania.util.ScanArea;

import static com.simibubi.create.AllItems.BLAZE_CAKE;
import static net.minecraft.sounds.SoundEvents.BLAZE_AMBIENT;
import static net.minecraft.sounds.SoundEvents.BLAZE_BURN;
import static net.minecraft.sounds.SoundEvents.BLAZE_SHOOT;
import static net.minecraft.sounds.SoundSource.BLOCKS;
import static zaftnotameni.creatania.util.Actions.killSlimeProduceManagelGrantAchievement;
import static zaftnotameni.creatania.util.Queries.isOnTopOrInsideManaFluid;
import static zaftnotameni.creatania.util.Queries.isSlimeEntity;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventBus {
//  @SubscribeEvent
//  public static void onCommandRegister(RegisterCommandsEvent event) {
//    new CreataniaTesterCommand(event.getDispatcher());
//  }

  public static void slimy(Level level, LivingEntity entity) {
    if (level.isClientSide()) return;
    if (!isSlimeEntity(level, entity)) return;
    var pos = entity.getOnPos();
    if (!isOnTopOrInsideManaFluid(level, entity, pos)) return;
    killSlimeProduceManagelGrantAchievement(level, entity, pos);
  }
  @SubscribeEvent
  public static void onLivingHurtEvent(LivingHurtEvent evt) {
    if (evt.getSource() == DamageSource.OUT_OF_WORLD) return;
    var entity = evt.getEntityLiving();
    var level = entity.getLevel();
    slimy(level, entity);
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
      evt.setTargetX(previous.x);
      evt.setTargetY(previous.y);
      evt.setTargetZ(previous.z);
      ScanArea.forEachPlayerInTheArea(level, pos, 128, p -> true, Advancements.PREVENT_ENDER_ENTITY_FROM_TELEPORTING::awardTo);
    } catch (Exception e) {}
  }
  @SubscribeEvent
  public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock evt) {
    try {
      var player = evt.getPlayer();
      var level = evt.getWorld();
      var hit = evt.getHitVec();
      if (handledAsBlazuniaEvent(evt, player, level, hit)) return;
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
    } catch (Exception e) {}
  }

  public static boolean handledAsBlazuniaEvent(PlayerInteractEvent.RightClickBlock evt, Player player, Level level, BlockHitResult hit) {
    var itemStack = evt.getItemStack();
    var hand = evt.getHand();
    var isCake = !itemStack.isEmpty() && itemStack.is(BLAZE_CAKE.get());
    var isServer = !level.isClientSide();
    var isMainHand = InteractionHand.MAIN_HAND.equals(hand);
    var isWrong = !isCake || hit == null || !isServer || !isMainHand;
    if (isWrong) return false;
    evt.setCanceled(true);
    var blockState = level.getBlockState(hit.getBlockPos());
    var isEndoFlame = blockState.getBlock().getRegistryName().compareTo(new ResourceLocation("botania", "endoflame")) == 0;
    if (!isEndoFlame) return false;
    level.setBlockAndUpdate(hit.getBlockPos(), Blocks.BLAZUNIA_BLOCK.getDefaultState());
    remoteItemFromPlayer(player, itemStack);
    level.playSound(null, hit.getBlockPos(), BLAZE_BURN, BLOCKS, 1f, 1f);
    level.playSound(null, hit.getBlockPos(), BLAZE_AMBIENT, BLOCKS, 1f, 1f);
    level.playSound(null, hit.getBlockPos(), BLAZE_SHOOT, BLOCKS, 1f, 1f);
    evt.setResult(Event.Result.ALLOW);
    return true;
  }

  public static void remoteItemFromPlayer(Player player, ItemStack stack) {
    stack.shrink(1);
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
    var notQuite = blacklisted || !namespace.equalsIgnoreCase("botania");
    if (notQuite) { return null; }
    else { return item; }
  }
  private static boolean isNotServerOrNotMainHandOrHitAirOrHandIsEmpty(PlayerInteractEvent.RightClickBlock evt, Player player, Level level, BlockHitResult hit) {
    var itemStack = evt.getItemStack();
    var hand = evt.getHand();
    var isWrench = !itemStack.isEmpty() && itemStack.is(AllItems.WRENCH.get());
    var isCrouching = player != null && player.isCrouching();
    var isServer = !level.isClientSide();
    var isMainHand = InteractionHand.MAIN_HAND.equals(hand);
    var isWrong = !isWrench || !isCrouching || hit == null || !isServer || !isMainHand;
    return isWrong;
  }
}