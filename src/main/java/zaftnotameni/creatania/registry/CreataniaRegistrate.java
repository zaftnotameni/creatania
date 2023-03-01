package zaftnotameni.creatania.registry;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class CreataniaRegistrate extends CreateRegistrate {

  public CreataniaRegistrate(String modid) {
    super(modid);
  }

  public static CreataniaRegistrate forMod(String id) { return new CreataniaRegistrate(id); }

  @Override
  public CreateRegistrate registerEventListeners(IEventBus bus) { return super.registerEventListeners(bus); }

  public static <I extends BlockItem> NonNullBiConsumer<DataGenContext<Item, I>, RegistrateItemModelProvider> sameAsBlockItemModel(
    String... folders
  ) {
    return (c, p) -> {
      String path = "block";
      for (String string : folders)
        path += "/" + ("_".equals(string) ? c.getName() : string);
      p.withExistingParent("item/" + c.getName(), p.modLoc(path));
    };
  }

  public FluidBuilder<ForgeFlowingFluid.Flowing, CreateRegistrate> standardFluid(String name) {
    return fluid(name, Index.resource("fluid/" + name + "_still"), Index.resource("fluid/" + name + "_flow"));
  }

  public FluidBuilder<ForgeFlowingFluid.Flowing, CreateRegistrate> waterLikeFluid(
    String name,
    NonNullBiFunction<FluidAttributes.Builder, Fluid, FluidAttributes> attributesFactory
  ) {
    return fluid(
      name,
      Fluids.MANA_STILL_RL,
      Fluids.MANA_FLOWING_RL,
      attributesFactory,
      Fluids.CreataniaFlowingFluidFlowing::new
    );
  }

  public FluidBuilder<ForgeFlowingFluid.Flowing, CreateRegistrate> lavaLikeFluid(
    String name,
    NonNullBiFunction<FluidAttributes.Builder, Fluid, FluidAttributes> attributesFactory
  ) {
    return fluid(
      name,
      Fluids.MOLTEN_STILL_RL,
      Fluids.MOLTEN_FLOWING_RL,
      attributesFactory,
      Fluids.CreataniaFlowingFluidFlowing::new
    );

  }

  public CreataniaRegistrate creativeModeTabFor(NonNullSupplier<CreativeModeTab> creataniaCreativeTab) {
    super.creativeModeTab(creataniaCreativeTab);
    return this;
  }

}