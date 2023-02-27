package zaftnotameni.creatania.food;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.effects.VanillaEffectConfiguration;
import zaftnotameni.creatania.registry.CreataniaPotions;
public class ManaGelItem extends Item  {
  public ManaGelItem(Item.Properties p) {
    super(p);
  }
  public static ManaGelItem create(Item.Properties p) {
    return new ManaGelItem(p
      .stacksTo(CommonConfig.MANAGEL_STACKS_TO.get())
      .fireResistant()
      .rarity(Rarity.UNCOMMON)
      .food(makeFoodProperties()));
  }
  public static ManaGelItem create() { return create(new Item.Properties()); }
  public static FoodProperties makeFoodProperties() {
    var f = new FoodProperties.Builder();
    if (CommonConfig.MANAGEL_CAN_ALWAYS_EAT.get()) f.alwaysEat();
    if (CommonConfig.MANAGEL_FAST_EATING.get()) f.fast();
    if (CommonConfig.MANAGEL_FLIGHT_PROBABILITY.get() > 0) f.effect(ManaGelItem::flightEffect,
      CommonConfig.MANAGEL_FLIGHT_PROBABILITY.get().floatValue());
    f.saturationMod(CommonConfig.MANAGEL_SATURATION_MODIFIER.get().floatValue());
    f.nutrition(CommonConfig.MANAGEL_NUTRITION.get());
    return f.build();
  }
  public static MobEffectInstance flightEffect() {
    var effectConfig = new VanillaEffectConfiguration(
      CreataniaPotions.FLIGHT_EFFECT.get(),
      CommonConfig.MANAGEL_FLIGHT_DURATION.get(),
      CommonConfig.MANAGEL_FLIGHT_MODIFIER.get()
    );
    return effectConfig.createInstance();
  }
}
