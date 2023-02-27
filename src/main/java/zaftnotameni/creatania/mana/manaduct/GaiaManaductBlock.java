package zaftnotameni.creatania.mana.manaduct;
import zaftnotameni.creatania.config.CommonConfig;

public class GaiaManaductBlock extends BaseManaductBlock {
  public static final String NAME = "manaducts/gaia";
  public GaiaManaductBlock(Properties pProperties) {
    super(pProperties);
    this.manaMultiplier = CommonConfig.MANA_DUCT_TIER_4_MULTIPLIER.get();
  }
}
