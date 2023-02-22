package zaftnotameni.creatania.machines.managenerator;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.KineticTileEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import zaftnotameni.creatania.config.ClientConfig;
import zaftnotameni.creatania.registry.Particles;
import zaftnotameni.creatania.sharedbehaviors.IAmParticleEmittingMachine;
public class ManaGeneratorRenderer extends KineticTileEntityRenderer {
  public ManaGeneratorRenderer(BlockEntityRendererProvider.Context context) {
    super(context);
  }
  @Override
  protected SuperByteBuffer getRotatedModel(KineticTileEntity te, BlockState state) {
    return CachedBufferer.partialFacing(AllBlockPartials.SHAFT_HALF, state);
  }

  @Override
  protected void renderSafe(KineticTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
    super.renderSafe(te, partialTicks, ms, buffer, light, overlay);
    if (!(te instanceof ManaGeneratorBlockEntity generator)) return;
    if (generator.activeStateSynchronizerBehavior.active && !generator.activeStateSynchronizerBehavior.duct) this.spawnManaParticles(te, partialTicks);
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
