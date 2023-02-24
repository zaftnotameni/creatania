package zaftnotameni.creatania.recipes.base;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
public class Outputs {
  public NonNullList<FluidStack> fluids = NonNullList.create();
  public NonNullList<ItemStack> items = NonNullList.create();
  public static Outputs fromItemStack(ItemStack output) {
    var result = new Outputs();
    result.items.add(output);
    return result;
  }
  public static Outputs fromFluidStack(FluidStack output) {
    var result = new Outputs();
    result.fluids.add(output);
    return result;
  }
  public static Outputs fromFluidStackList(NonNullList<FluidStack> outputs) {
    var result = new Outputs();
    result.fluids.addAll(outputs);
    return result;
  }
  public static Outputs fromItemStackList(NonNullList<ItemStack> outputs) {
    var result = new Outputs();
    result.items.addAll(outputs);
    return result;
  }
  public Outputs withItemStack(ItemStack output) {
    var result = this;
    result.items.add(output);
    return result;
  }
  public Outputs withFluidStack(FluidStack output) {
    var result = this;
    result.fluids.add(output);
    return result;
  }
  public Outputs withItemStackList(NonNullList<ItemStack> outputs) {
    var result = this;
    result.items.addAll(outputs);
    return result;
  }
  public Outputs withFluidStackList(NonNullList<FluidStack> outputs) {
    var result = this;
    result.fluids.addAll(outputs);
    return result;
  }
}
