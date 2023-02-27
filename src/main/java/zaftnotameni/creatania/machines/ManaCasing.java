package zaftnotameni.creatania.machines;
import com.simibubi.create.foundation.data.AssetLookup;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Block;
import zaftnotameni.creatania.registry.CreataniaIndex;

import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static zaftnotameni.creatania.registry.CreataniaRegistrate.sameAsBlockItemModel;

public class ManaCasing extends Block {
  public static final String NAME = "mana/machines/casing";
  public ManaCasing(Properties pProperties) {
    super(pProperties);
  }

  public static BlockEntry<ManaCasing> registerSelf() {
    return CreataniaIndex.all().block(ManaCasing.NAME, ManaCasing::new)
      .transform(axeOrPickaxe())
      .blockstate((c, p) -> p.simpleBlock(c.get(), AssetLookup.partialBaseModel(c, p)))
      .lang("Mana Casing")
      .item()
      .model(sameAsBlockItemModel(ManaCasing.NAME, "block"))
      .build()
      .register();
  }
}
