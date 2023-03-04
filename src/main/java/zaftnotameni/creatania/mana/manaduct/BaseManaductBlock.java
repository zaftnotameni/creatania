package zaftnotameni.creatania.mana.manaduct;

import com.google.common.collect.ImmutableMap;
import com.simibubi.create.foundation.data.AssetLookup;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zaftnotameni.creatania.registry.Index;
import zaftnotameni.creatania.registry.Tags;
import zaftnotameni.creatania.util.Voxel;

import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.ATTACH_FACE;
import static zaftnotameni.creatania.registry.CreataniaRegistrate.sameAsBlockItemModel;
public class BaseManaductBlock extends FaceAttachedHorizontalDirectionalBlock {
  public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

  public int manaMultiplier = 1;

  public BaseManaductBlock(Properties pProperties) {
    super(pProperties);
  }

  public static Direction getMouthDirection(BlockState bs) {
    return bs.getValue(FACING).getOpposite();
  }
  public static BlockState getMouthPointedAtBlockState(Level level, BlockState bs, BlockPos pos) {
    var mouth = getMouthDirection(bs);
    return level.getBlockState(pos.relative(mouth, 1));
  }
  public static BlockEntity getMouthPointedAtBlockEntity(Level level, BlockState bs, BlockPos pos) {
    var mouth = getMouthDirection(bs);
    return level.getBlockEntity(pos.relative(mouth, 1));
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) { pBuilder.add(FACING).add(ATTACH_FACE); }

  @Nullable
  @Override
  public BlockState getStateForPlacement(@NotNull BlockPlaceContext pContext) {
    var horizontalDirection = pContext.getHorizontalDirection();
    Direction facing;
    if (pContext.getPlayer() != null && pContext.getPlayer().isShiftKeyDown()) facing = horizontalDirection;
    else facing = horizontalDirection.getOpposite();
    return super.getStateForPlacement(pContext).setValue(FACING, facing);
  }
  @Override
  public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
    return Voxel.TINY_BLOCK_VOXEL;
  }
  @Override
  public @NotNull VoxelShape getOcclusionShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos) {
    return Voxel.ALMOST_NONE_VOXEL;
  }
  @Override
  public int getLightBlock(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos) {
    return 1;
  }
  @Override
  protected @NotNull ImmutableMap<BlockState, VoxelShape> getShapeForEachState(@NotNull Function<BlockState, VoxelShape> shapeFactory) {
    return super.getShapeForEachState(x -> Voxel.TINY_BLOCK_VOXEL);
  }

  public static BlockBehaviour.Properties makeManaductProperties() {
    return BlockBehaviour.Properties.of(Material.STONE)
      .destroyTime(1f)
      .lightLevel(x -> 15)
      .hasPostProcess((x, y, z) -> true)
      .emissiveRendering((x, y, z) -> true);
  }
  public static <T extends BaseManaductBlock> BlockEntry<T> registerManaduct(String name, String friendlyName, TagKey<Block> tier, NonNullFunction<Properties, T> factory) {
    return Index.all().block(name, factory)
      .transform(pickaxeOnly())
      .tag(tier, Tags.Blocks.MANADUCT)
      .blockstate((c, p) -> p.horizontalFaceBlock(c.get(), AssetLookup.partialBaseModel(c, p)))
      .lang(friendlyName)
      .item()
      .model(sameAsBlockItemModel(name, "block"))
      .build()
      .register();
  }
}