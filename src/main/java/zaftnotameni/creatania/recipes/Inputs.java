package zaftnotameni.creatania.recipes;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.Ingredient;
public class Inputs {
  public NonNullList<FluidIngredient> fluids = NonNullList.create(); // of(FluidIngredient.fromFluid(Fluids.WATER, 1000));
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
  public Inputs withIngredientList(NonNullList<Ingredient> inputs) {
    var result = this;
    result.items.addAll(inputs);
    return result;
  }
  public Inputs withIngredient(Ingredient input) {
    var result = this;
    result.items.add(input);
    return result;
  }
}
