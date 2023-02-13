package zaftnotameni.sharedbehaviors;
import com.google.common.base.Predicates;
import com.simibubi.create.content.contraptions.base.DirectionalKineticBlock;
import com.simibubi.create.content.contraptions.components.motor.CreativeMotorTileEntity;
import com.simibubi.create.foundation.config.AllConfigs;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.behaviour.CenteredSideValueBoxTransform;
import com.simibubi.create.foundation.tileEntity.behaviour.scrollvalue.ScrollValueBehaviour;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.AABB;
import vazkii.botania.api.mana.spark.IManaSpark;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.simibubi.create.content.contraptions.base.KineticTileEntity.convertToDirection;
public class KineticManaMachine<T extends SmartTileEntity & IAmManaMachine> {
  public int manaPerRpmPerTick = 1;
  public int stressUnitsPerRpm = 1;
  public int manaCap = 1;
  public int baseRpm = 1;
  public ScrollValueBehaviour scrollValueBehaviour;
  public T te;
  public KineticManaMachine(T pte) { te = pte; }
  public KineticManaMachine withManaPerRpmPerTick(int i) { this.manaPerRpmPerTick = i; return this; }
  public KineticManaMachine withStressUnitsPerRpm(int i) { this.stressUnitsPerRpm = i; return this; }
  public KineticManaMachine withManaCap(int i) { this.manaCap = i; return this; }
  public KineticManaMachine withBaseRpm(int i) { this.baseRpm = i; return this; }
  public float calculateStress() { return calculateStress(() -> true, (x) -> {}); }
  public float calculateStress(Supplier<Boolean> cond) { return calculateStress(cond, (x) -> {}); }
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
  public int getMinimumManaPerTick() { return this.manaPerRpmPerTick * Math.abs(te.getManaMachineGeneratedSpeed()); }

  public CenteredSideValueBoxTransform createShaftSlot(DirectionProperty dir) { return new CenteredSideValueBoxTransform((motor, side) -> motor.getValue(dir) == side.getOpposite()); }
  public ScrollValueBehaviour createScrollBehavior(DirectionProperty dir) {
    var maxRPM = AllConfigs.SERVER.kinetics.maxMotorSpeed.get();
    var shaftSlot = createShaftSlot(dir);
    var behavior = new ScrollValueBehaviour(Lang.translateDirect("generic.speed"), te, shaftSlot)
      .between(-maxRPM, maxRPM)
      .withUnit(i -> Lang.translateDirect("generic.unit.rpm"))
      .withCallback(te::updateGeneratedRotation)
      .withStepFunction(CreativeMotorTileEntity::step);
    behavior.value = this.baseRpm;
    behavior.scrollableValue = behavior.value;
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

  public static BlockState rotate(BlockState state, Rotation rotation) {
    return state.setValue(DirectionalKineticBlock.FACING, state.getValue(DirectionalKineticBlock.FACING).getClockWise(Direction.Axis.Y));
  }
  public static BlockState rotate(BlockState state, Direction direction) {
    return state.setValue(DirectionalKineticBlock.FACING, state.getValue(DirectionalKineticBlock.FACING).getClockWise(Direction.Axis.Y));
  }
}
