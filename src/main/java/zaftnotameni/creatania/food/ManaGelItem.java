package zaftnotameni.creatania.food;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.effects.VanillaEffectConfiguration;
import zaftnotameni.creatania.registry.CreativeModeTabs;
import zaftnotameni.creatania.registry.Potions;
public class ManaGelItem extends Item  {
  public ManaGelItem(Properties p) {
    super(p);
  }
  public ManaGelItem() {
    this(propertiesOfManaGel(new Item.Properties().tab(CreativeModeTabs.CREATANIA_ITEMS)));
  }
  public static Properties propertiesOfManaGel(Properties p) {
    return p.stacksTo(CommonConfig.MANAGEL_STACKS_TO.get())
      .fireResistant()
      .rarity(Rarity.UNCOMMON)
      .food(makeFoodProperties());
  }
  public static FoodProperties makeFoodProperties() {
    var f = new FoodProperties.Builder();
    if (CommonConfig.MANAGEL_CAN_ALWAYS_EAT.get()) f.alwaysEat();
    if (CommonConfig.MANAGEL_FAST_EATING.get()) f.fast();
    if (CommonConfig.MANAGEL_FLIGHT_PROBABILITY.get() > 0) f.effect(ManaGelItem::flightEffect, CommonConfig.MANAGEL_FLIGHT_PROBABILITY.get());
    f.saturationMod(CommonConfig.MANAGEL_SATURATION_MODIFIER.get());
    f.nutrition(CommonConfig.MANAGEL_NUTRITION.get());
    return f.build();
  }
  public static MobEffectInstance flightEffect() {
    var effectConfig = new VanillaEffectConfiguration(
      Potions.FLIGHT_EFFECT.get(),
      CommonConfig.MANAGEL_FLIGHT_DURATION.get(),
      CommonConfig.MANAGEL_FLIGHT_MODIFIER.get()
    );
    return effectConfig.createInstance();
  }
}