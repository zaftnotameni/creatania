package zaftnotameni.creatania.stress.xorlever;

import com.mojang.math.Vector3f;
import com.simibubi.create.foundation.block.IBE;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.registry.BlockEntities;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.CONDITIONAL;

public class XorLeverBlock extends FaceAttachedHorizontalDirectionalBlock implements IBE<XorLeverBlockEntity> {
  public XorLeverBlock(Properties pProperties) {
    super(pProperties);
  }
  @Override
  public @NotNull InteractionResult use(@NotNull BlockState state, Level worldIn, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand handIn, @NotNull BlockHitResult hit) {
    if (worldIn.isClientSide) return useOnClient(state, worldIn, pos);
    return useOnServer(worldIn, pos, player);
  }
  public InteractionResult useOnClient(BlockState state, Level worldIn, BlockPos pos) {
    addParticles(state, worldIn, pos, 1.0F);
    return InteractionResult.SUCCESS;
  }
  public InteractionResult useOnServer(Level worldIn, BlockPos pos, Player player) {
    return onBlockEntityUse(worldIn, pos, te -> {
      te.toggleState();
      float f = .25f + ((te.state + 5) / 15f) * .5f;
      worldIn.playSound(null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.4F, f);
      return InteractionResult.SUCCESS;
    });
  }
  @Override
  public int getSignal(@NotNull BlockState blockState, @NotNull BlockGetter blockAccess, @NotNull BlockPos pos, @NotNull Direction side) {
    return getBlockEntityOptional(blockAccess, pos).map(computeStateSignal(blockState, side)).orElse(0);
  }

  public static final int SIGNAL_STRENGTH = CommonConfig.XOR_LEVER_SIGNAL_STRENGTH.get();
  public static Function<XorLeverBlockEntity, Integer> computeStateSignal(BlockState bs, Direction side) {
    return te -> (side == getSignalDirection(te, bs)) ? SIGNAL_STRENGTH : 0;
  }

  @Override
  public boolean isSignalSource(@NotNull BlockState state) { return true; }
  @Override
  public int getDirectSignal(@NotNull BlockState blockState, @NotNull BlockGetter blockAccess, @NotNull BlockPos pos, @NotNull Direction side) { return getConnectedDirection(blockState) == side ? getSignal(blockState, blockAccess, pos, side) : 0; }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void animateTick(@NotNull BlockState stateIn, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull Random rand) {
    withBlockEntityDo(worldIn, pos, te -> {
      if (rand.nextFloat() < 0.5F) {
        var delta = (getSignalDirection(te, stateIn) == getTrueFacing(stateIn)) ? -0.5f : 0.5f;
        addParticles(stateIn, worldIn, pos, 0.5F, delta);
      }
    });
  }

  @Override
  public void onRemove(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
    if (isMoving || state.getBlock() == newState.getBlock())
      return;
    withBlockEntityDo(worldIn, pos, te -> {
      if (te.state != 0) updateNeighbors(state, worldIn, pos);
      worldIn.removeBlockEntity(pos);
    });
  }

  private static void addParticles(BlockState state, LevelAccessor worldIn, BlockPos pos, float alpha, float delta) {
    Direction direction = state.getValue(FACING).getOpposite();
    Direction direction1 = getConnectedDirection(state).getOpposite();
    Direction trueDirection = getTrueFacing(state);
    double d0 = (double) pos.getX() + 0.5D + 0.1D * (double) direction.getStepX() + 0.2D * (double) direction1.getStepX() + (delta * trueDirection.getStepX());
    double d1 = (double) pos.getY() + 0.5D + 0.1D * (double) direction.getStepY() + 0.2D * (double) direction1.getStepY() + (delta * trueDirection.getStepY());
    double d2 = (double) pos.getZ() + 0.5D + 0.1D * (double) direction.getStepZ() + 0.2D * (double) direction1.getStepZ() + (delta * trueDirection.getStepZ());
    double xspeed = 0d;
    double yspeed = 0d;
    double zspeed = 0d;
    worldIn.addParticle(new DustParticleOptions(new Vector3f(1.0F, 0.0F, 0.0F), alpha), d0, d1, d2, xspeed, yspeed, zspeed);
  }
  private static void addParticles(BlockState state, LevelAccessor worldIn, BlockPos pos, float alpha) {
    addParticles(state, worldIn, pos, alpha, 0f);
  }

  static void updateNeighbors(BlockState state, Level world, BlockPos pos) {
    world.updateNeighborsAt(pos, state.getBlock());
  }

  @SuppressWarnings("deprecation")
  @Override
  public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
    return Blocks.DIAMOND_BLOCK.getShape(state, worldIn, pos, context);
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    super.createBlockStateDefinition(builder.add(FACING, FACE, CONDITIONAL));
  }

  @Nullable @Override public BlockState getStateForPlacement(BlockPlaceContext pContext) {
    return super.getStateForPlacement(pContext).setValue(CONDITIONAL, false);
  }

  @Override
  public Class<XorLeverBlockEntity> getBlockEntityClass() {
    return XorLeverBlockEntity.class;
  }

  @Override
  public BlockEntityType<? extends XorLeverBlockEntity> getBlockEntityType() {
    return BlockEntities.XOR_LEVER_BLOCK_ENTITY.get();
  }

  @Override
  public boolean isPathfindable(@NotNull BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos, @NotNull PathComputationType type) {
    return false;
  }
  public static Direction.Axis getTrueFaceAxis(BlockState bs) {
    var face = bs.getValue(FACE);
    var faceAxis = getTrueFacing(bs).getAxis();
    if (face == AttachFace.WALL) faceAxis = Direction.Axis.Y;
    return faceAxis;
  }
  public static Direction getSignalDirection(XorLeverBlockEntity te, BlockState bs) {
    var teIsOn = te.state > 0;
    var facing = getTrueFacing(bs);
    if (teIsOn) return facing;
    return facing.getOpposite();
  }
  public static Direction getTrueFacing(BlockState bs) {
    var facing = bs.getValue(FACING);
    var face = bs.getValue(FACE);
    if (face == AttachFace.FLOOR) facing = facing.getOpposite();
    if (face == AttachFace.CEILING) facing = facing;
    if (face == AttachFace.WALL) facing = Direction.UP;
    return facing;
  }
}