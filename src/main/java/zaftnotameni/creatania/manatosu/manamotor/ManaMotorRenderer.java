package zaftnotameni.creatania.manatosu.manamotor;
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

    SuperByteBuffer fanInner1 = CachedBufferer.partialFacing(AllBlockPartials.ENCASED_FAN_INNER, te.getBlockState(), direction.getOpposite());
    SuperByteBuffer fanInner2 = CachedBufferer.partialFacing(AllBlockPartials.ENCASED_FAN_INNER, te.getBlockState(), direction);

    Direction.Axis axis = ((IRotate) te.getBlockState().getBlock()).getRotationAxis(te.getBlockState());
    var angle = getAngleForTe(te, te.getBlockPos(), axis);

    kineticRotationTransform(fanInner1, te, direction.getAxis(), angle, lightInFront).renderInto(ms, vb);
    kineticRotationTransform(fanInner2, te, direction.getAxis(), angle, lightInFront).renderInto(ms, vb);
  }
  public static void renderFluids(KineticTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
    var renderedFluid = new FluidStack(Fluids.MANA_FLUID.get(), 1000);
    var motor = (ManaMotorBlockEntity) te;
    float ymax = Math.min(1f, (motor.mana == 0) ? 0.0f : (0.01f + (0.99f * motor.mana / (float) motor.manaMachine.manaCap)));
    FluidRenderer.renderFluidBox(renderedFluid, 0.1f, 0.5f, 0.1f, 0.9f, ymax, 0.9f, buffer, ms, light,
      true);
  }
}
