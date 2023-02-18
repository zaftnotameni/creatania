package zaftnotameni.creatania.sutomana.manaduct;
import zaftnotameni.creatania.config.CommonConfig;
public class ElementiumManaductBlock extends BaseManaductBlock {
  public ElementiumManaductBlock(Properties pProperties) {
    super(pProperties);
    this.manaMultiplier = CommonConfig.MANA_DUCT_TIER_3_MULTIPLIER.get();
  }
}
