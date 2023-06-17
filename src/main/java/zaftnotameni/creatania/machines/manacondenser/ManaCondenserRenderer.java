package zaftnotameni.creatania.machines.manacondenser;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import zaftnotameni.creatania.config.ClientConfig;
import zaftnotameni.creatania.machines.manamachine.IAmParticleEmittingMachine;
import zaftnotameni.creatania.registry.Fluids;
import zaftnotameni.creatania.registry.Particles;
public class ManaCondenserRenderer extends KineticBlockEntityRenderer {
  public ManaCondenserRenderer(BlockEntityRendererProvider.Context context) {
    super(context);
  }
  @Override
  protected SuperByteBuffer getRotatedModel(KineticBlockEntity te, BlockState state) {
    return CachedBufferer.partialFacing(AllPartialModels.SHAFT_HALF, state);
  }
  @Override
  protected void renderSafe(KineticBlockEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
    super.renderSafe(te, partialTicks, ms, buffer, light, overlay);
    var active = (te instanceof ManaCondenserBlockEntity generator) && generator.activeStateSynchronizerBehavior.active;
    renderFans(te, ms, buffer, active);
    if (active) this.spawnManaParticles(te, partialTicks);
    if (active) renderFluids(te, partialTicks, ms, buffer, light, overlay);
  }
  public void renderFans(KineticBlockEntity te, PoseStack ms, MultiBufferSource buffer, boolean active) {
    Direction direction = Direction.UP;
    Direction.Axis axis = Direction.Axis.Y;

    VertexConsumer vb = buffer.getBuffer(RenderType.cutoutMipped());

    int lightInFront = LevelRenderer.getLightColor(te.getLevel(), te.getBlockPos().relative(direction));
    var angle = getAngleForTe(te, te.getBlockPos(), axis);

    SuperByteBuffer fanInner1 = CachedBufferer.partialFacing(AllPartialModels.ENCASED_FAN_INNER, te.getBlockState(), Direction.DOWN);
    if (active) kineticRotationTransform(fanInner1, te, axis, angle, lightInFront).renderInto(ms, vb);
    else fanInner1.renderInto(ms, vb);
  }

  public static float getYMaxForFluidLevel(){
    var topPadding = ClientConfig.MANA_MOTOR_MANA_FILL_TOP_PADDING.get();
    var bottomPadding = ClientConfig.MANA_MOTOR_MANA_FILL_BOTTOM_PADDING.get();
    var topWithPadding = 1.0f - topPadding;
    var toFill = 1.0f - bottomPadding;
    return Math.min(topWithPadding, bottomPadding + toFill);

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
  public static void renderFluids(KineticBlockEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
    var renderedFluid = new FluidStack(Fluids.CORRUPT_MANA.get(), 1000);
    float ymin = getYMinForFluidLevel();
    float ymax = getYMaxForFluidLevel();
    float xmin = getHorizontalMinForFluidLevel();
    float xmax = getHorizontalMaxForFluidLevel();
    float zmin = getHorizontalMinForFluidLevel();
    float zmax = getHorizontalMaxForFluidLevel();
    FluidRenderer.renderFluidBox(renderedFluid, xmin, ymin, zmin, xmax, ymax, zmax, buffer, ms, light, true);
  }


  public float tickCounter = 0f;
  public float signal = 1f;
  public float speedModifier = 1.1f;
  public float particlesEveryFTicks = ClientConfig.TICKS_PER_PARTICLE.get();
  public boolean enableManaParticles = ClientConfig.ENABLE_MANA_PARTICLES.get();

  public void spawnManaParticles(KineticBlockEntity te, float partialTicks) {
    if (!this.enableManaParticles) return;
    if (Minecraft.getInstance().isPaused()) return;
    if (!(te instanceof IAmParticleEmittingMachine particleEmittingMachine)) return;
    if (!particleEmittingMachine.shouldEmitParticles()) return;
    var level = te.getLevel();
    if (level == null) return;
    this.tickCounter += partialTicks;
    if (this.tickCounter < this.particlesEveryFTicks) return;
    this.tickCounter = 0f;
    this.signal *= -1f;
    var cx = te.getBlockPos().getX() + .5f;
    var cy = te.getBlockPos().above(2).getY();
    var cz = te.getBlockPos().getZ() + .5f;
    var xs = 0.1f - (Math.random() * 0.2f);
    var ys = Math.random() * this.speedModifier;
    var zs = 0.1f - (Math.random() * 0.2f);
    var px = cx;
    var py = cy;
    var pz = cz;
    level.addParticle(Particles.MANA_PARTICLES.get(), px, py, pz, xs, -ys, zs);
  }
}