package zaftnotameni.creatania.ponder;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;
public class CreataniaPonderUtils {
  public Vec3 gentleFall() { return util.vector.of(0.0, -0.1, 0.0); }

  public Vec3 offsetTopOf(BlockPos target) {
    return util.vector.topOf(target).add(0.0, 0.5, 0.0);
  }
  public void dropBlocksOnto(BlockPos target, ResourceLocation blockId, int howMany, Consumer<Integer> afterEach) {
    while(howMany-- > 0) { dropBlockOnto(target, blockId); afterEach.accept(howMany); }
  }
  public void dropBlockOnto(BlockPos target,  ResourceLocation blockId) {
    var stack = new ItemStack(ForgeRegistries.BLOCKS.getValue(blockId).asItem(), 1);
    scene.world.createItemEntity(offsetTopOf(target), gentleFall(), stack);
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
    var fluid = ForgeRegistries.FLUIDS.getValue(fluidId).getRegistryName().toString();
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
      emptyTankContentFn().accept(tank1);
      emptyTankContentFn().accept(tank2);
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
      emptyTankContentFn().accept(tank2);
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
  public SceneBuilder scene;
  public SceneBuildingUtil util;
  public CreataniaPonderUtils(SceneBuilder scene, SceneBuildingUtil util) {
    this.scene = scene;
    this.util = util;
  }
  public static CreataniaPonderUtils of(SceneBuilder scene, SceneBuildingUtil util) { return new CreataniaPonderUtils(scene, util); }
}

