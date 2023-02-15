package zaftnotameni.creatania.recipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.crafting.Recipe;
public abstract class CreataniaRecipe implements Recipe<SimpleContainer> {
  public Inputs inputs;
  public Outputs outputs;
  public ResourceLocation id;

  public CreataniaRecipe(ResourceLocation id, Inputs in, Outputs out) {
    this.inputs = in;
    this.outputs = out;
  }
}
