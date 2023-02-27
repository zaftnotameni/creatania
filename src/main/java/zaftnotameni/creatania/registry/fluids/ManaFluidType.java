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
import static zaftnotameni.creatania.registry.fluids.VanillaFluidTextures.waterFlowing;
import static zaftnotameni.creatania.registry.fluids.VanillaFluidTextures.waterStill;
public class ManaFluidType extends FluidType {
  public ManaFluidType(Properties p) { super(p); }
  public static ManaFluidType create() {
    var p = Properties.create();
    p.viscosity(10);
    p.rarity(Rarity.RARE);
    p.canHydrate(true);
    p.density(1);
    p.supportsBoating(true);
    p.lightLevel(15);
    p.temperature(1);
    return new ManaFluidType(p);
  }
  public static FluidEntry<Flowing> named(String name) {
    return CreataniaIndex.all()
      .fluid(name, waterStill(), waterFlowing(), ManaFluidType.Flowing::new)
      .fluidProperties(fp -> fp
        .levelDecreasePerBlock(3)
        .tickRate(1 * 20))
      .source(ManaFluidType.Source::new)
      .renderType(RenderType::translucent)
      .bucket(ManaFluidType.Bucket::new)
      .build()
      .block(ManaFluidType.Block::new).build()
      .register();
  }
  @Override
  public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
    consumer.accept(new ManaFluidTypeClientExtensions());
  }
  public static ForgeFlowingFluid.Properties makeProperties() {
    var p = new ForgeFlowingFluid.Properties(
      ManaFluidType::create,
      ManaFluidType.Source::create,
      ManaFluidType.Flowing::create
    );
    return p;
  }
  public static class Source extends ForgeFlowingFluid.Source {
    public Source(ForgeFlowingFluid.Properties p) {
      super(p);
    }
    public static ForgeFlowingFluid.Source create() {
      return new Source(ManaFluidType.makeProperties());
    }
    @Override
    protected void spreadTo(LevelAccessor pLevel, BlockPos pPos, BlockState pBlockState, Direction pDirection, FluidState pFluidState) {
      if (!specialCobblegenSpread(pLevel, pPos, pBlockState, pDirection, pFluidState, this))
        super.spreadTo(pLevel, pPos, pBlockState, pDirection, pFluidState);
    }
  }
  public static class Flowing extends ForgeFlowingFluid.Flowing {
    public Flowing(ForgeFlowingFluid.Properties p) {
      super(p);
    }
    public static Flowing create() {
      return new Flowing(ManaFluidType.makeProperties());
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
