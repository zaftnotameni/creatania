package zaftnotameni.creatania.mana.manaduct;
import zaftnotameni.creatania.config.CommonConfig;

public class ElementiumManaductBlock extends BaseManaductBlock {
  public static final String NAME = "manaducts/elementium";
  public ElementiumManaductBlock(Properties pProperties) {
    super(pProperties);
    this.manaMultiplier = CommonConfig.MANA_DUCT_TIER_3_MULTIPLIER.get();
  }
}
