package zaftnotameni.creatania.mana.manaduct;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import zaftnotameni.creatania.config.CommonConfig;

public class ManasteelManaductBlock extends BaseManaductBlock {
  public static final String NAME = "manaducts/manasteel";
  public ManasteelManaductBlock(Properties pProperties) {
    super(pProperties);
    this.manaMultiplier = CommonConfig.MANA_DUCT_TIER_1_MULTIPLIER.get();
  }
}
