package zaftnotameni.creatania.registry;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import zaftnotameni.creatania.registry.fluids.ManaFluidType;
import zaftnotameni.creatania.registry.fluids.MoltenFluidType;

import java.util.ArrayList;
import java.util.Collection;

import static zaftnotameni.creatania.Constants.MODID;
public class CreataniaFluids extends CreataniaForgeRegistryHolder {
  public static CreataniaFluids ALL = new CreataniaFluids();
  public CreataniaFluids() {}
  public FluidEntry<ManaFluidType.Flowing> PURE_MANA = ManaFluidType.named("pure_mana");
  public FluidEntry<ManaFluidType.Flowing> CORRUPT_MANA = ManaFluidType.named("corrupt_mana");
  public FluidEntry<ManaFluidType.Flowing> REAL_MANA = ManaFluidType.named("real_mana");
  public FluidEntry<MoltenFluidType.Flowing> MOLTEN_IRON = MoltenFluidType.named("molten_iron");
  public FluidEntry<MoltenFluidType.Flowing> MOLTEN_COPPER = MoltenFluidType.named("molten_copper");
  public FluidEntry<MoltenFluidType.Flowing> MOLTEN_GOLD = MoltenFluidType.named("molten_gold");
  public FluidEntry<MoltenFluidType.Flowing> MOLTEN_ZINC = MoltenFluidType.named("molten_zinc");
  public FluidEntry<MoltenFluidType.Flowing> MOLTEN_BRASS = MoltenFluidType.named("molten_brass");
  public FluidEntry<MoltenFluidType.Flowing> MOLTEN_ANDESITE = MoltenFluidType.named("molten_andesite");
  public FluidEntry<MoltenFluidType.Flowing> MOLTEN_TERRASTEEL = MoltenFluidType.named("molten_terrasteel");
  public FluidEntry<MoltenFluidType.Flowing> MOLTEN_MANASTEEL = MoltenFluidType.named("molten_manasteel");
  public FluidEntry<MoltenFluidType.Flowing> MOLTEN_ELEMENTIUM = MoltenFluidType.named("molten_elementium");
  public FluidEntry<MoltenFluidType.Flowing> MOLTEN_GAIA = MoltenFluidType.named("molten_gaia");
  @Override
  public Collection<DeferredRegister> getDeferredRegistries() {
    var fluidTypes = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, MODID);
    fluidTypes.register("mana", ManaFluidType::create);
    fluidTypes.register("molten", MoltenFluidType::create);
    var registries = new ArrayList<DeferredRegister>();
    registries.add(fluidTypes);
    return registries;
  }
}
