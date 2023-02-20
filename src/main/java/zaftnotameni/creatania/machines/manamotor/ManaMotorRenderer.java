package zaftnotameni.creatania.machines.manamotor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.content.contraptions.base.IRotate;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.KineticTileEntityRenderer;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import zaftnotameni.creatania.config.ClientConfig;
import zaftnotameni.creatania.registry.Blocks;
import zaftnotameni.creatania.registry.Fluids;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;
public class ManaMotorRenderer extends KineticTileEntityRenderer {
  public ManaMotorRenderer(BlockEntityRendererProvider.Context context) {
    super(context);
  }
  @Override
  protected SuperByteBuffer getRotatedModel(KineticTileEntity te, BlockState state) {
    return CachedBufferer.partialFacing(AllBlockPartials.SHAFT_HALF, state);
  }
  @Override
  protected void renderSafe(KineticTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
    super.renderSafe(te, partialTicks, ms, buffer, light, overlay);
    renderFans(te, ms, buffer);
    renderFluids(te, partialTicks, ms, buffer, light, overlay);
  }
  public static void renderFans(KineticTileEntity te, PoseStack ms, MultiBufferSource buffer) {
    Direction direction = te.getBlockState().getValue(FACING);
    VertexConsumer vb = buffer.getBuffer(RenderType.cutoutMipped());

    int lightInFront = LevelRenderer.getLightColor(te.getLevel(), te.getBlockPos().relative(direction));

    SuperByteBuffer fanInner1 = CachedBufferer.partialFacing(Blocks.Partials.MANA_MOTOR_FAN, te.getBlockState(), direction.getOpposite());

    Direction.Axis axis = ((IRotate) te.getBlockState().getBlock()).getRotationAxis(te.getBlockState());
    var angle = getAngleForTe(te, te.getBlockPos(), axis);

    kineticRotationTransform(fanInner1, te, direction.getAxis(), angle, lightInFront).renderInto(ms, vb);
  }
  public static float getYMaxForFluidLevel(ManaMotorBlockEntity motor){
    var topPadding = ClientConfig.MANA_MOTOR_MANA_FILL_TOP_PADDING.get();
    var bottomPadding = ClientConfig.MANA_MOTOR_MANA_FILL_BOTTOM_PADDING.get();
    var topWithPadding = 1.0f - topPadding;
    var percentageFull = (float) motor.mana / (float) motor.manaMachine.manaCap;
    var toFill = 1.0f - bottomPadding;
    return Math.min(topWithPadding, bottomPadding + (toFill * percentageFull));

  }
  public static float getYMinForFluidLevel() {
    var bottomPadding = ClientConfig.MANA_MOTOR_MANA_FILL_BOTTOM_PADDING.get();
    return 0.0f + bottomPadding;
  }
  public static float getHorizontalMinForFluidLevel() {
    var horizontalPadding = ClientConfig.MANA_MOTOR_MANA_FILL_HORIZONTAL_PADDING.get();
    return 0.0f + horizontalPadding;
  }
  public static float getHorizontalMaxForFluidLevel() {
    var horizontalPadding = ClientConfig.MANA_MOTOR_MANA_FILL_HORIZONTAL_PADDING.get();
    return 1.0f - horizontalPadding;
  }
  public static void renderFluids(KineticTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
    if (!(te instanceof ManaMotorBlockEntity motor)) return;
    if (motor.mana < 1) return;
    var renderedFluid = new FluidStack(Fluids.PURE_MANA.get(), 1000);
    float ymin = getYMinForFluidLevel();
    float ymax = getYMaxForFluidLevel(motor);
    float xmin = getHorizontalMinForFluidLevel();
    float xmax = getHorizontalMaxForFluidLevel();
    float zmin = getHorizontalMinForFluidLevel();
    float zmax = getHorizontalMaxForFluidLevel();
    FluidRenderer.renderFluidBox(renderedFluid, xmin, ymin, zmin, xmax, ymax, zmax, buffer, ms, light, true);
  }
}
