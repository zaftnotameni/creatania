package zaftnotameni.creatania.manatosu.manamotor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.KineticTileEntityRenderer;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import zaftnotameni.creatania.registry.Fluids;
import zaftnotameni.creatania.registry.Particles;
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
    renderFluids(te, partialTicks, ms, buffer, light, overlay);
  }

  private void renderFluids(KineticTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
    var renderedFluid = new FluidStack(Fluids.MANA_FLUID.get(), 1000);
    var motor = (ManaMotorBlockEntity) te;
    float ymax = (motor.mana == 0) ? 0.55f : (0.8f + (0.19f * motor.mana / (float) motor.manaMachine.manaCap));
    FluidRenderer.renderFluidBox(renderedFluid, 0.1f, 0.5f, 0.1f, 0.9f, ymax, 0.9f, buffer, ms, light,
      true);
  }
}
