package zaftnotameni.creatania.ponder;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.ponder.*;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.utility.Pointing;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import static java.util.function.Function.identity;
import static zaftnotameni.creatania.ponder.ExtremelyPainfulWorkaroundBecauseJavaIsTerrible.getBlockStateFacingType;

public class CreataniaPonderUtils {

  public Vec3 gentleFall() { return util.vector.of(0.0, -0.1, 0.0); }

  public Runnable dropBlocksOnto(XYZ target, ResourceLocation blockId, final int howMany, Consumer<Integer> afterEach) {
    return () -> {
      if (howMany <= 0) return;
      dropBlockOnto(target, blockId).run();
      afterEach.accept(howMany);
      dropBlocksOnto(target, blockId, howMany - 1, afterEach).run();
    };
  }

  public Runnable dropBlockOnto(XYZ target, ResourceLocation blockId) {
    return () -> {
      var stack = stackOfBlock(blockId);
      scene.world.createItemEntity(target.offsetTop(), gentleFall(), stack);
    };
  }

  @NotNull public static ItemStack stackOfBlock(ResourceLocation blockId) {
    return new ItemStack(ForgeRegistries.BLOCKS
                           .getValue(blockId)
                           .asItem(), 1);
  }

  public Consumer<CompoundTag> emptyLevelFn() {
    return (CompoundTag nbt) -> {
      nbt.putFloat("Speed", 0.25f);
      nbt.putFloat("Target", 0.0f);
      nbt.putFloat("Value", 1.0f);
    };
  }

  public Consumer<CompoundTag> fullLevelFn() {
    return (CompoundTag nbt) -> {
      nbt.putFloat("Speed", 0.25f);
      nbt.putFloat("Target", 1.0f);
      nbt.putFloat("Value", 0.0f);
    };
  }

  public Consumer<CompoundTag> emptyTankContentFn() {
    return (CompoundTag nbt) -> {
      nbt.putInt("Amount", 0);
      nbt.putString("FluidName", "minecraft:empty");
    };
  }

  public Consumer<CompoundTag> emptyTankFn() {
    return (CompoundTag nbt) -> {
      var level = new CompoundTag();
      var tank = new CompoundTag();
      emptyLevelFn().accept(level);
      emptyTankContentFn().accept(tank);
      nbt.put("Level", level);
      nbt.put("TankContent", tank);
    };
  }

  public Consumer<CompoundTag> tankWithFluidContentFn(ResourceLocation fluidId, int amount) {
    var fluid = ForgeRegistries.FLUIDS
      .getValue(fluidId)
      .getRegistryName()
      .toString();
    return (CompoundTag nbt) -> {
      nbt.putInt("Amount", amount);
      nbt.putString("FluidName", fluid);
    };
  }

  public Consumer<CompoundTag> tankWithAmountFn(ResourceLocation fluidId, int amount) {
    return (CompoundTag nbt) -> {
      var level = new CompoundTag();
      var tank = new CompoundTag();
      fullLevelFn().accept(level);
      tankWithFluidContentFn(fluidId, amount).accept(tank);
      nbt.put("Level", level);
      nbt.put("TankContent", tank);
    };
  }

  public Consumer<CompoundTag> startProcessingFn() {
    return (CompoundTag nbt) -> {
      nbt.putBoolean("Running", true);
    };
  }

  public Consumer<CompoundTag> emptyInputItemsFn() {
    return (CompoundTag nbt) -> {
      var items = new CompoundTag();
      items.put("Items", new ListTag());
      items.putInt("Size", 9);
      nbt.put("InputItems", items);
    };
  }

  public Consumer<CompoundTag> emptyTanksFn(String tankPropertyName) {
    return (CompoundTag nbt) -> {
      var tanks = new ListTag();
      var tank1 = new CompoundTag();
      var tank2 = new CompoundTag();
      emptyTankFn().accept(tank1);
      emptyTankFn().accept(tank2);
      tanks.add(tank1);
      tanks.add(tank2);
      nbt.put(tankPropertyName, tanks);
    };
  }

  public Consumer<CompoundTag> fillTanksFn(String tankPropertyName, ResourceLocation fluidId, int amount) {
    return (CompoundTag nbt) -> {
      var tanks = new ListTag();
      var tank1 = new CompoundTag();
      var tank2 = new CompoundTag();
      tankWithAmountFn(fluidId, amount).accept(tank1);
      emptyTankFn().accept(tank2);
      tanks.add(tank1);
      tanks.add(tank2);
      nbt.put(tankPropertyName, tanks);
    };
  }

  public UnaryOperator<BlockState> unlit() {
    return (BlockState blockstate) -> blockstate.setValue(BlazeBurnerBlock.HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.SMOULDERING);
  }

  public UnaryOperator<BlockState> heated() {
    return (BlockState blockstate) -> blockstate.setValue(BlazeBurnerBlock.HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.KINDLED);
  }

  public UnaryOperator<BlockState> superHeated() {
    return (BlockState blockstate) -> blockstate.setValue(BlazeBurnerBlock.HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.SEETHING);
  }

  public UnaryOperator<BlockState> facing(Direction direction) {
    return facing(BlockStateProperties.FACING, direction);
  }

  public UnaryOperator<BlockState> facing(DirectionProperty property, Direction direction) {
    return (BlockState blockstate) -> blockstate.setValue(property, direction);
  }

  public Supplier<Integer> idleAfter(int ticks, Consumer<Integer> action) {
    return () -> {
      action.accept(ticks);
      scene.idle(ticks);
      return ticks;
    };
  }

  public Supplier<Integer> idleBefore(int ticks, Consumer<Integer> action) {
    return () -> {
      scene.idle(ticks);
      action.accept(ticks);
      return ticks;
    };
  }

  public Supplier<Integer> idleAfter(int ticks, Runnable action) { return idleAfter(ticks, i -> action.run()); }

  public Supplier<Integer> idleBefore(int ticks, Runnable action) { return idleBefore(ticks, i -> action.run()); }

  public SceneBuilder scene;
  public SceneBuildingUtil util;

  public CreataniaPonderUtils(SceneBuilder scene, SceneBuildingUtil util) {
    this.scene = scene;
    this.util = util;
  }

  public static CreataniaPonderUtils of(SceneBuilder scene, SceneBuildingUtil util) { return new CreataniaPonderUtils(scene, util); }

  public Runnable keyFrameText(String text, Vec3 target, int duration) {
    return () -> scene.overlay
      .showText(duration)
      .attachKeyFrame()
      .text(text)
      .pointAt(target);
  }

  public XYZ at(int x, int y, int z) { return new XYZ(this, x, y, z); }

  public Runnable showInputItem(ResourceLocation blockId, Pointing pointing, Vec3 target, int duration) {
    var item = ForgeRegistries.BLOCKS
      .getValue(blockId)
      .asItem();
    return showInputItem(item, pointing, target, duration, identity());
  }

  public Runnable showInputItem(ItemLike item, Pointing pointing, Vec3 target, int duration) {
    return showInputItem(item, pointing, target, duration, identity());
  }

  public Runnable showInputItem(ItemLike item, Pointing pointing, Vec3 target, int duration, Function<InputWindowElement, InputWindowElement> fn) {
    return () -> {
      var popover = new InputWindowElement(target, pointing).withItem(new ItemStack(item, 1));
      fn.apply(popover);
      scene.overlay.showControls(popover, duration);
    };
  }

  public static class XYZ {

    public Vec3 asVec3() { return new Vec3(x, y, z); }

    public BlockPos asBlockPos() { return u.util.grid.at(x, y, z); }

    public Vec3 center() { return u.util.vector.centerOf(asBlockPos()); }

    public Vec3 top() { return u.util.vector.topOf(asBlockPos()); }

    public Vec3 offsetTop(double offset) { return top().add(0.0, offset, 0.0); }

    public Vec3 offsetTop() { return offsetTop(0.5); }

    public Runnable appearTo(Direction direction) { return () -> u.scene.world.showSection(asSelection(), direction); }

    public Runnable appearFrom(Direction direction) { return appearTo(direction.getOpposite()); }

    public Runnable setBlock(BlockState blockState) { return () -> u.scene.world.setBlock(asBlockPos(), blockState, false); }

    public Runnable modifyBlock(UnaryOperator<BlockState> blockstateFunc) { return () -> u.scene.world.modifyBlock(asBlockPos(), blockstateFunc, false); }

    public Runnable modifyNbt(Class<? extends BlockEntity> type, Consumer<CompoundTag> nbtFn) { return () -> u.scene.world.modifyBlockEntityNBT(asSelection(), type, nbtFn, true); }

    public Runnable modifyNbt(Consumer<CompoundTag> nbtFn) {
      return modifyNbt(getSmartTileEntityType().get(), nbtFn);
    }

    public Supplier<PonderWorld> zawarudo() {
      return () -> {
        if (ponderWorld != null) return ponderWorld;
        return ExtremelyPainfulWorkaroundBecauseJavaIsTerrible
          .zawarudo(this)
          .get();
      };
    }

    public Runnable resolve() {
      return () -> {
        if (zawarudo().get() == null) return;
        this.resolvedBlockState = zawarudo()
          .get()
          .getBlockState(asBlockPos());
        this.resolvedBlockEntity = zawarudo()
          .get()
          .getBlockEntity(asBlockPos());
      };
    }

    public Supplier<BlockEntity> getBlockEntity() {
      return () -> {
        if (this.resolvedBlockEntity == null) resolve().run();
        return this.resolvedBlockEntity;
      };
    }

    public Supplier<BlockState> getBlockState() {
      return () -> {
        if (this.resolvedBlockState == null) resolve().run();
        return this.resolvedBlockState;
      };
    }

    public Runnable setFacing(Direction direction) {
      return () -> {
        var type = getBlockStateFacingType(this).get();
        if (type == null) return;
        u.scene.world.modifyBlock(asBlockPos(), bs -> bs.setValue(type, direction), false);
      };
    }

    public Supplier<Class<? extends BlockEntity>> getSmartTileEntityType() {
      return () -> {
        if (!(getBlockState().get() instanceof IBE ite)) return BlockEntity.class;
        return ite.getBlockEntityClass();
      };
    }

    public Selection asSelection() { return u.util.select.position(x, y, z); }

    public CreataniaPonderUtils u;
    public int x;
    public int y;
    public int z;
    public BlockState resolvedBlockState;
    public BlockEntity resolvedBlockEntity;
    public PonderWorld ponderWorld;
    public PonderScene ponderScene;

    public XYZ(CreataniaPonderUtils u, int x, int y, int z) {
      this.u = u;
      this.x = x;
      this.y = y;
      this.z = z;
    }

  }

}