package zaftnotameni.creatania.machines.manacondenser;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.KineticTileEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import zaftnotameni.creatania.config.ClientConfig;
import zaftnotameni.creatania.machines.manamachine.IAmParticleEmittingMachine;
import zaftnotameni.creatania.registry.Particles;
public class ManaCondenserRenderer extends KineticTileEntityRenderer {
  public ManaCondenserRenderer(BlockEntityRendererProvider.Context context) {
    super(context);
  }
  @Override
  protected SuperByteBuffer getRotatedModel(KineticTileEntity te, BlockState state) {
    return CachedBufferer.partialFacing(AllBlockPartials.SHAFT_HALF, state);
  }
  @Override
  protected void renderSafe(KineticTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
    super.renderSafe(te, partialTicks, ms, buffer, light, overlay);
    var active = (te instanceof ManaCondenserBlockEntity generator) && generator.activeStateSynchronizerBehavior.active;
    renderFans(te, ms, buffer, active);
    if (active) this.spawnManaParticles(te, partialTicks);
  }
  public void renderFans(KineticTileEntity te, PoseStack ms, MultiBufferSource buffer, boolean active) {
    Direction direction = Direction.UP;
    Direction.Axis axis = Direction.Axis.Y;

    VertexConsumer vb = buffer.getBuffer(RenderType.cutoutMipped());

    int lightInFront = LevelRenderer.getLightColor(te.getLevel(), te.getBlockPos().relative(direction));
    var angle = getAngleForTe(te, te.getBlockPos(), axis);

    SuperByteBuffer fanInner1 = CachedBufferer.partialFacing(AllBlockPartials.ENCASED_FAN_INNER, te.getBlockState(), Direction.DOWN);
    if (active) kineticRotationTransform(fanInner1, te, axis, angle, lightInFront).renderInto(ms, vb);
    else fanInner1.renderInto(ms, vb);
  }


  public float tickCounter = 0f;
  public float signal = 1f;
  public float speedModifier = 1.1f;
  public float particlesEveryFTicks = ClientConfig.TICKS_PER_PARTICLE.get();
  public boolean enableManaParticles = ClientConfig.ENABLE_MANA_PARTICLES.get();

  public void spawnManaParticles(KineticTileEntity te, float partialTicks) {
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
