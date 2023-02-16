package zaftnotameni.creatania.effects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
public class VanillaEffectConfiguration {
  public int duration;
  public int modifier;
  public MobEffect effect;
  public VanillaEffectConfiguration(MobEffect effect, int duration, int modifier) {
    this.effect = effect;
    this.duration = duration;
    this.modifier = modifier;
  }
  public boolean applyTo(Level level, Entity entity) {
    var applicable = this.canApplyTo(level, entity);
    if (!applicable) return false;
    ((LivingEntity) entity).addEffect(new MobEffectInstance(effect, duration, modifier));
    return true;
  }
  public boolean canApplyTo(Level level, Entity entity) {
    if (level.isClientSide()) return false;
    if (!(entity instanceof LivingEntity livingEntity)) return false;
    if (modifier < 1) return false;
    return true;
  }
  public MobEffectInstance createInstance() {
    return new MobEffectInstance(this.effect, this.duration, this.modifier);
  }
}
