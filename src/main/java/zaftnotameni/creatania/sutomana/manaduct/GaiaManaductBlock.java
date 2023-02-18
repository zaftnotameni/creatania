package zaftnotameni.creatania.sutomana.manaduct;
import zaftnotameni.creatania.config.CommonConfig;
public class GaiaManaductBlock extends BaseManaductBlock {
  public GaiaManaductBlock(Properties pProperties) {
    super(pProperties);
    this.manaMultiplier = CommonConfig.MANA_DUCT_TIER_4_MULTIPLIER.get();
  }
}
