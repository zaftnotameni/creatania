package zaftnotameni.creatania.manatoitem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
public abstract class BaseManaBlock  extends Block {
  public BaseManaBlock(Properties pProperties) {
    super(pProperties);
  }

  public static BlockBehaviour.Properties makeManablockProperties() {
    return BlockBehaviour.Properties.of(Material.STONE)
      .destroyTime(1f)
      .lightLevel(x -> 15)
      .hasPostProcess((x, y, z) -> true)
      .emissiveRendering((x, y, z) -> true);
  }
}
