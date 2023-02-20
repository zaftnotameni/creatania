package zaftnotameni.creatania.mana.manablock;
import com.simibubi.create.foundation.data.AssetLookup;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import zaftnotameni.creatania.registry.Index;
import zaftnotameni.creatania.registry.Tags;
import zaftnotameni.creatania.util.Voxel;

import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static zaftnotameni.creatania.registry.CreataniaRegistrate.sameAsBlockItemModel;
public abstract class BaseManaBlock  extends Block {
  public BaseManaBlock(Properties pProperties) {
    super(pProperties);
  }

  public static BlockBehaviour.Properties makeManablockProperties() {
    return BlockBehaviour.Properties.copy(Blocks.GLASS)
      .destroyTime(1f)
      .lightLevel(x -> 15)
      .hasPostProcess((x, y, z) -> true)
      .emissiveRendering((x, y, z) -> true)
      .noOcclusion()
      .isViewBlocking((x, y, z) -> false);
  }

  @Override
  public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
    return Voxel.FULL_BLOCK_VOXEL;
  }

  public static <T extends BaseManaBlock> BlockEntry<T> registerManablock(String name, String friendlyName, TagKey<Block> typeTag, TagKey<Block> specificTag, NonNullFunction<Properties, T> factory) {
    return Index.all().block(name, factory)
      .transform(pickaxeOnly())
      .tag(typeTag, specificTag, Tags.Blocks.ALL_MANA)
      .blockstate((c, p) -> p.simpleBlock(c.get(), AssetLookup.partialBaseModel(c, p)))
      .lang(friendlyName)
      .item()
      .model(sameAsBlockItemModel(name, "block"))
      .build()
      .register();
  }
}
