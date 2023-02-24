package zaftnotameni.creatania.recipes.base;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.crafting.Recipe;
import org.apache.commons.lang3.function.TriFunction;
import zaftnotameni.creatania.util.Functional;
public abstract class CreataniaRecipe implements Recipe<SimpleContainer> {
  public Inputs inputs;
  public Outputs outputs;
  public ResourceLocation id;


  public static <T extends CreataniaRecipe> T create(ResourceLocation id, Inputs in, Outputs out, TriFunction<ResourceLocation, Inputs, Outputs, T> factory) {
    return factory.apply(id, in, out);
  }
  public CreataniaRecipe(CreataniaRecipe r) {
    this.inputs = r.inputs;
    this.outputs = r.outputs;
    this.id = r.id;
  }
  public CreataniaRecipe(ResourceLocation id, Inputs in, Outputs out) {
    this.inputs = in;
    this.outputs = out;
    this.id = id;
  }

  public interface Factory<T extends CreataniaRecipe> extends Functional.TriFunction<ResourceLocation, Inputs, Outputs, T> {
  }
}
