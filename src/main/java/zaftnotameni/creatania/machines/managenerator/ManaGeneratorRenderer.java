package zaftnotameni.creatania.machines.managenerator;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.IRotate;
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
import zaftnotameni.creatania.registry.BlockPartials;
import zaftnotameni.creatania.registry.Fluids;
import zaftnotameni.creatania.registry.Particles;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

public class ManaGeneratorRenderer extends KineticBlockEntityRenderer {
  public ManaGeneratorRenderer(BlockEntityRendererProvider.Context context) {
    super(context);
  }
  @Override
  protected SuperByteBuffer getRotatedModel(KineticBlockEntity te, BlockState state) {
    return CachedBufferer.partialFacing(AllPartialModels.SHAFT_HALF, state);
  }

  @Override
  protected void renderSafe(KineticBlockEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
    super.renderSafe(te, partialTicks, ms, buffer, light, overlay);
    if (!(te instanceof ManaGeneratorBlockEntity generator)) return;
    if (generator.activeStateSynchronizerBehavior.active && !generator.activeStateSynchronizerBehavior.duct) this.spawnManaParticles(te, partialTicks);
    renderTurbine(te, ms, buffer);
    renderFluids(te, partialTicks, ms, buffer, light, overlay);
  }

  public static void renderTurbine(KineticBlockEntity te, PoseStack ms, MultiBufferSource buffer) {
    Direction direction = te.getBlockState().getValue(FACING);
    VertexConsumer vb = buffer.getBuffer(RenderType.cutoutMipped());

    int lightInFront = LevelRenderer.getLightColor(te.getLevel(), te.getBlockPos().relative(direction));

    SuperByteBuffer fanInner1 = CachedBufferer.partialFacing(BlockPartials.MANA_GENERATOR_TURBINE, te.getBlockState(), direction.getOpposite());

    Direction.Axis axis = ((IRotate) te.getBlockState().getBlock()).getRotationAxis(te.getBlockState());
    var angle = getAngleForTe(te, te.getBlockPos(), axis);

    kineticRotationTransform(fanInner1, te, direction.getAxis(), angle, lightInFront).renderInto(ms, vb);
  }

  public static float getYMaxForFluidLevel(KineticBlockEntity te){
    if (!(te instanceof  ManaGeneratorBlockEntity generator)) return 0f;
    var topPadding = ClientConfig.MANA_MOTOR_MANA_FILL_TOP_PADDING.get();
    var bottomPadding = ClientConfig.MANA_MOTOR_MANA_FILL_BOTTOM_PADDING.get();
    var topWithPadding = 1.0f - topPadding;
    var toFill = 1.0f - bottomPadding;
    var percentageFull = generator.manaGeneratorFluidHandler.getManaFluidAvailable() /
      generator.manaGeneratorFluidHandler.getManaTankCapacity();
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
  public static void renderFluids(KineticBlockEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
    var renderedFluid = new FluidStack(Fluids.PURE_MANA.get(), 1000);
    float ymin = getYMinForFluidLevel();
    float ymax = getYMaxForFluidLevel(te);
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
    var cy = te.getBlockPos().above().getY();
    var cz = te.getBlockPos().getZ() + .5f;
    var xs = 0.1f - (Math.random() * 0.2f);
    var ys = Math.random() * this.speedModifier;
    var zs = 0.1f - (Math.random() * 0.2f);
    var px = cx;
    var py = cy;
    var pz = cz;
    level.addParticle(Particles.MANA_PARTICLES.get(), px, py, pz, xs, ys, zs);
  }
}