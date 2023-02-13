package zaftnotameni.creatania.fx.particle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ManaParticles extends TextureSheetParticle {

  protected ManaParticles(ClientLevel pLevel, SpriteSet spriteSet, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
    super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
    this.friction = 0.8F;
    this.xd = xd;
    this.yd = yd;
    this.zd = zd;
    this.quadSize *= 0.5F;
    this.lifetime = 20;
    this.setSpriteFromAge(spriteSet);
    this.rCol = 1f;
    this.gCol = 1f;
    this.bCol = 1f;
  }

  @Override
  public void tick() {
    super.tick();
    fadeOut();
  }

  private void fadeOut() {
    this.alpha = (-(1/(float)lifetime) * age + 1);
  }

  @OnlyIn(Dist.CLIENT)
  public static class Provider implements ParticleProvider<SimpleParticleType> {
    private final SpriteSet sprites;

    public Provider(SpriteSet spriteSet) {
      this.sprites = spriteSet;
    }

    public Particle createParticle(SimpleParticleType particleType, ClientLevel level,
                                   double x, double y, double z,
                                   double dx, double dy, double dz) {
      return new ManaParticles(level, this.sprites, x, y, z, dx, dy, dz);
    }
  }

  @Override
  public ParticleRenderType getRenderType() {
    return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
  }

}
