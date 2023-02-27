package zaftnotameni.creatania.mana.manaduct;
import zaftnotameni.creatania.config.CommonConfig;

public class TerrasteelManaductBlock extends BaseManaductBlock {
  public static final String NAME = "manaducts/terrasteel";
  public TerrasteelManaductBlock(Properties pProperties) {
    super(pProperties);
    this.manaMultiplier = CommonConfig.MANA_DUCT_TIER_2_MULTIPLIER.get();
  }
}
