package zaftnotameni.creatania.registry.fluids;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import zaftnotameni.creatania.registry.CreataniaIndex;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static zaftnotameni.creatania.registry.fluids.CobblegenMechanics.specialCobblegenSpread;
import static zaftnotameni.creatania.registry.fluids.VanillaFluidTextures.lavaFlowing;
import static zaftnotameni.creatania.registry.fluids.VanillaFluidTextures.lavaStill;
public class MoltenFluidType extends FluidType {
  public MoltenFluidType(Properties p) { super(p); }
  public static MoltenFluidType create() {
    var p = Properties.create();
    p.viscosity(20);
    p.rarity(Rarity.UNCOMMON);
    p.canHydrate(false);
    p.density(16);
    p.supportsBoating(false);
    p.lightLevel(15);
    p.temperature(9000);
    return new MoltenFluidType(p);
  }
  public static FluidEntry<Flowing> named(String name) {
    return CreataniaIndex.all()
      .fluid(name, lavaStill(), lavaFlowing(), MoltenFluidType.Flowing::new)
      .fluidProperties(fp -> fp
        .levelDecreasePerBlock(5)
        .tickRate(5 * 20))
      .source(MoltenFluidType.Source::new)
      .renderType(RenderType::translucent)
      .bucket(MoltenFluidType.Bucket::new)
      .build()
      .block(MoltenFluidType.Block::new).build()
      .register();
  }
  @Override
  public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
    consumer.accept(new MoltenFluidTypeClientExtensions());
  }
  public static ForgeFlowingFluid.Properties makeProperties() {
    var p = new ForgeFlowingFluid.Properties(
      MoltenFluidType::create,
      MoltenFluidType.Source::create,
      MoltenFluidType.Flowing::create
    );
    return p;
  }
  public static class Source extends ForgeFlowingFluid.Source {
    public Source(Properties p) {
      super(p);
    }
    public static Source create() {
      return new Source(MoltenFluidType.makeProperties());
    }
    @Override
    protected void spreadTo(LevelAccessor pLevel, BlockPos pPos, BlockState pBlockState, Direction pDirection, FluidState pFluidState) {
      if (!specialCobblegenSpread(pLevel, pPos, pBlockState, pDirection, pFluidState, this))
        super.spreadTo(pLevel, pPos, pBlockState, pDirection, pFluidState);
    }
  }
  public static class Flowing extends ForgeFlowingFluid.Flowing {
    public Flowing(Properties p) {
      super(p);
    }
    public static Flowing create() {
      return new Flowing(MoltenFluidType.makeProperties());
    }
    @Override
    protected void spreadTo(LevelAccessor pLevel, BlockPos pPos, BlockState pBlockState, Direction pDirection, FluidState pFluidState) {
      if (!specialCobblegenSpread(pLevel, pPos, pBlockState, pDirection, pFluidState, this))
        super.spreadTo(pLevel, pPos, pBlockState, pDirection, pFluidState);
    }
  }
  public static class Bucket extends BucketItem {
    public Bucket(Supplier<? extends Fluid> supplier, Properties builder) {
      super(supplier, builder);
    }
  }
  public static class Block extends LiquidBlock {
    public Block(Supplier<? extends FlowingFluid> pFluid, Properties pProperties) {
      super(pFluid, pProperties);
    }
  }
}
