package zaftnotameni.creatania.machines.manamachine;

import com.google.common.base.Predicates;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
// import com.simibubi.create.content.kinetics.motor.CreativeMotorBlockEntity;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.CenteredSideValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
// import com.simibubi.create.foundation.gui.widget.ScrollInput;
import com.simibubi.create.foundation.utility.Lang;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.DistExecutor;
import vazkii.botania.api.BotaniaAPIClient;
import vazkii.botania.api.BotaniaForgeClientCapabilities;
import vazkii.botania.api.block.IWandHUD;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.api.mana.spark.IManaSpark;

import static com.simibubi.create.content.kinetics.base.KineticBlockEntity.convertToDirection;
public class KineticManaMachine<T extends SmartBlockEntity & IAmManaMachine> {
  public int manaPerRpmPerTick = 1;
  public int rpmPerManaPerTick = 1;
  public int stressUnitsPerRpm = 1;
  public int manaCap = 1;
  public int baseRpm = 1;
  public ScrollValueBehaviour scrollValueBehaviour;
  public T te;
  public KineticManaMachine(T pte) { te = pte; }
  public KineticManaMachine<T> withManaPerRpmPerTick(int i) { this.manaPerRpmPerTick = i; return this; }
  public KineticManaMachine<T> withRpmPerManaPerTick(int i) { this.rpmPerManaPerTick = i; return this; }
  public KineticManaMachine<T> withStressUnitsPerRpm(int i) { this.stressUnitsPerRpm = i; return this; }
  public KineticManaMachine<T> withManaCap(int i) { this.manaCap = i; return this; }
  public KineticManaMachine<T> withBaseRpm(int i) { this.baseRpm = i; return this; }
  public float calculateStress(Supplier<Boolean> cond, Consumer<Float> cons) {
    float value = cond.get() ? this.stressUnitsPerRpm : 0f;
    cons.accept(value);
    return value;
  }
  public float calculateStressRegardless() { return calculateStress(() -> true, te::setManaMachineLastStressImpact); }
  public float calculateStressActiveOnly() { return calculateStress(te::isManaMachineActive, te::setManaMachineLastCapacityProvided); }
  public IManaSpark getAttachedSpark() {
    var level = te.getLevel();
    if (level == null) return null;
    var sparks = level.getEntitiesOfClass(Entity.class,
      new AABB(te.getBlockPos().above(), te.getBlockPos().above().offset(1, 1, 1)), Predicates.instanceOf(IManaSpark.class));
    if (sparks.isEmpty()) return null;
    return (IManaSpark) sparks.get(0);
  }
  public int getAvailableSpaceForMana() { return this.manaCap - te.getManaMachineMana(); }
  public void receiveMana(int pMana) { this.updateMana(te.getManaMachineMana() + pMana); }
  public void updateMana(int newManaValue) {
    te.setManaMachineMana(Math.max(0, newManaValue));
    te.setChanged();
  }
  public boolean isFull() { return te.getManaMachineMana() > this.manaCap; }
  public int getMinimumManaPerTick() {
    if (this.manaPerRpmPerTick > 1) { return Math.max(1, this.manaPerRpmPerTick * Math.abs(te.getManaMachineGeneratedSpeed())); }
    if (this.rpmPerManaPerTick > 1) { return (int) Math.max(1, Math.abs(te.getManaMachineGeneratedSpeed()) / (float) this.rpmPerManaPerTick); }
    return 1;
  }

  public CenteredSideValueBoxTransform createTransformOppositeTo(DirectionProperty dir) { return new CenteredSideValueBoxTransform((te, side) -> te.getValue(dir) == side.getOpposite()); }
  public ScrollValueBehaviour createScrollBehavior(DirectionProperty dir) {
    var maxRPM = AllConfigs.server().kinetics.maxRotationSpeed.get();
    var shaftSlot = createTransformOppositeTo(dir);
    var behavior = new ScrollValueBehaviour(Lang.translateDirect("generic.speed"), te, shaftSlot)
      .between(-maxRPM, maxRPM)
      // .withUnit(i -> Lang.translateDirect("generic.unit.rpm"))
      .withCallback(te::updateGeneratedRotation);
      // .withStepFunction(CreativeMotorBlockEntity::step);
    behavior.value = this.baseRpm;
    // behavior.scrollableValue = behavior.value;
    this.scrollValueBehaviour = behavior;
    return behavior;
  }
  public int getSpeedSafe() { return (this.scrollValueBehaviour != null) ? this.scrollValueBehaviour.getValue() : 0; }
  public float getGeneratedSpeed(DirectionProperty dir) { return convertToDirection(this.getSpeedSafe(), te.getBlockState().getValue(dir)); }

  public static Direction getFacingForPlacement(BlockPlaceContext context) {
    Direction facing = context.getHorizontalDirection();
    if (context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) return facing;
    return facing.getOpposite();
  }
  public static boolean hasShaftTowards(BlockState state, Direction face) {
    return face == state.getValue(DirectionalKineticBlock.FACING);
  }

  public static BlockState rotate(BlockState state) {
    return state.setValue(DirectionalKineticBlock.FACING, state.getValue(DirectionalKineticBlock.FACING).getClockWise(Direction.Axis.Y));
  }
  public static void renderSimpleBotaniaHud(Level level, BlockState bs, PoseStack ps, int color, int current, int max, IManaPool pool) {
    if (level == null || !level.isClientSide()) return;
    String name = I18n.get(bs.getBlock().getDescriptionId());
    if (pool == null) {
      BotaniaAPIClient.instance().drawSimpleManaHUD(ps, color, current, max, name);
    } else {
      BotaniaAPIClient.instance().drawSimpleManaHUD(ps, color, pool.getCurrentMana(), max, name);
    }
  }
  public static void renderSimpleBotaniaHud(Level level, BlockState bs, PoseStack ps, int color, int current, int max) {
    renderSimpleBotaniaHud(level,bs,ps,color,current,max,null);
  }
  public static <T> LazyOptional<T>  handleBotaniaManaHudCapability(@Nonnull Capability<T> cap, IWandHUD self) {
    return DistExecutor.unsafeRunForDist(
      () -> () -> (cap == BotaniaForgeClientCapabilities.WAND_HUD) ? LazyOptional.of(() -> self).cast() : LazyOptional.empty(),
      () -> LazyOptional::empty
    );
  }
  public int getMaximumSUPossible() {
    return stressUnitsPerRpm * AllConfigs.server().kinetics.maxRotationSpeed.get();
  }
}