package zaftnotameni.creatania.mana.manaduct;
import zaftnotameni.creatania.config.CommonConfig;
public class ManasteelManaductBlock extends BaseManaductBlock {
  public ManasteelManaductBlock(Properties pProperties) {
    super(pProperties);
    this.manaMultiplier = CommonConfig.MANA_DUCT_TIER_1_MULTIPLIER.get();
  }
}
