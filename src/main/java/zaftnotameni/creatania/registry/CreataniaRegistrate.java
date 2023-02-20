package zaftnotameni.creatania.registry;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
public class CreataniaRegistrate extends AbstractRegistrate<CreataniaRegistrate> {
  public CreataniaRegistrate(String modid) {
    super(modid);
  }
  public static CreataniaRegistrate create(String id) { return new CreataniaRegistrate(id); }
  @Override
  public CreataniaRegistrate registerEventListeners(IEventBus bus) { return super.registerEventListeners(bus); }


  public static <I extends BlockItem> NonNullBiConsumer<DataGenContext<Item, I>, RegistrateItemModelProvider> sameAsBlockItemModel(
    String... folders) {
    return (c, p) -> {
      String path = "block";
      for (String string : folders)
        path += "/" + ("_".equals(string) ? c.getName() : string);
      p.withExistingParent("item/" + c.getName(), p.modLoc(path));
    };
  }
  public FluidBuilder<ForgeFlowingFluid.Flowing, CreataniaRegistrate> standardFluid(String name) {
    return fluid(name, Index.resource("fluid/" + name + "_still"), Index.resource("fluid/" + name + "_flow"));
  }

  public FluidBuilder<ForgeFlowingFluid.Flowing, CreataniaRegistrate> waterLikeFluid(String name,
                                                                                     NonNullBiFunction<FluidAttributes.Builder, Fluid, FluidAttributes> attributesFactory) {
    return fluid(name,
      Fluids.WATER_STILL_RL,
      Fluids.WATER_FLOWING_RL,
      attributesFactory);
  }
  public FluidBuilder<ForgeFlowingFluid.Flowing, CreataniaRegistrate> lavaLikeFluid(String name,
                                                                                     NonNullBiFunction<FluidAttributes.Builder, Fluid, FluidAttributes> attributesFactory) {
    return fluid(name,
      Fluids.LAVA_STILL_RL,
      Fluids.LAVA_FLOWING_RL,
      attributesFactory);
  }
  public FluidBuilder<ForgeFlowingFluid.Flowing, CreataniaRegistrate> texturedFluid(String name,
                                                                                 NonNullBiFunction<FluidAttributes.Builder, Fluid, FluidAttributes> attributesFactory) {
    return fluid(name,
      Index.resource("fluid/" + name + "_still"),
      Index.resource("fluid/" + name + "_flow"),
      attributesFactory);
  }

}
