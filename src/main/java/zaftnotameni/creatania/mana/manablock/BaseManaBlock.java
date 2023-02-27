package zaftnotameni.creatania.mana.manablock;
import com.simibubi.create.foundation.data.AssetLookup;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlassBlock;
import zaftnotameni.creatania.registry.CreataniaIndex;
import zaftnotameni.creatania.registry.Tags;

import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static zaftnotameni.creatania.registry.CreataniaRegistrate.sameAsBlockItemModel;

@SuppressWarnings("removal")
public abstract class BaseManaBlock  extends GlassBlock {
  public BaseManaBlock(Properties pProperties) {
    super(pProperties.copy(Blocks.GLASS).lightLevel(x -> 15).noOcclusion());
  }

  public static <T extends BaseManaBlock> BlockEntry<T> registerManablock(String name, String friendlyName, TagKey<Block> typeTag, TagKey<Block> specificTag, NonNullFunction<Properties, T> factory) {
    return CreataniaIndex.all().block(name, factory)
      .transform(pickaxeOnly())
      .addLayer(() -> RenderType::translucent)
      .tag(typeTag, specificTag, Tags.Blocks.ALL_MANA)
      .blockstate((c, p) -> p.simpleBlock(c.get(), AssetLookup.partialBaseModel(c, p)))
      .lang(friendlyName)
      .item()
      .model(sameAsBlockItemModel(name,  "block"))
      .build()
      .register();
  }
}
