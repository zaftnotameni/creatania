package zaftnotameni.creatania.recipes.base;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.Ingredient;
public class Inputs {
  public NonNullList<FluidIngredient> fluids = NonNullList.create();
  public NonNullList<Ingredient> items = NonNullList.create();
  public static Inputs fromIngredientList(NonNullList<Ingredient> inputs) {
    var result = new Inputs();
    result.items.addAll(inputs);
    return result;
  }
  public static Inputs fromIngredient(Ingredient input) {
    var result = new Inputs();
    result.items.add(input);
    return result;
  }
  public static Inputs fromFluidIngredient(FluidIngredient input) {
    var result = new Inputs();
    result.fluids.add(input);
    return result;
  }
  public static Inputs empty() {
    return new Inputs();
  }
  public Inputs withIngredientList(NonNullList<Ingredient> inputs) {
    var result = this;
    result.items.addAll(inputs);
    return result;
  }
  public Inputs withFluidIngredientList(NonNullList<FluidIngredient> inputs) {
    var result = this;
    result.fluids.addAll(inputs);
    return result;
  }
  public Inputs withIngredient(Ingredient input) {
    var result = this;
    result.items.add(input);
    return result;
  }
}
