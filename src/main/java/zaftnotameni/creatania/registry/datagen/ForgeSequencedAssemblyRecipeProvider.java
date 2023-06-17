package zaftnotameni.creatania.registry.datagen;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.foundation.data.recipe.SequencedAssemblyRecipeGen;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.registry.Blocks;
import zaftnotameni.creatania.registry.Items;

import java.util.function.Consumer;
public class ForgeSequencedAssemblyRecipeProvider extends SequencedAssemblyRecipeGen {
  public ForgeSequencedAssemblyRecipeProvider(DataGenerator p_i48262_1_) {
    super(p_i48262_1_);
  }
  GeneratedRecipe MANA_MACHINE_COMPONENT = create(Constants.MANA_MACHINE_COMPONENT, r -> r.require(livingwood())
    .transitionTo(Items.INCOMPLETE_MANA_MACHINE_COMPONENT.get())
    .addOutput(new ItemStack(Blocks.MANA_CASING.get().asItem(), 2), 1f)
    .loops(1)
    .addStep(CuttingRecipe::new, rr -> rr)
    .addStep(DeployerApplicationRecipe::new, rr -> rr.require(manasteel())));

  GeneratedRecipe XOR_LEVER = create(Constants.XOR_LEVER, r -> r.require(lever())
    .transitionTo(Items.INCOMPLETE_XOR_LEVER.get())
    .addOutput(new ItemStack(Blocks.XOR_LEVER.get().asItem(), 2), 1f)
    .loops(1)
    .addStep(CuttingRecipe::new, rr -> rr)
    .addStep(DeployerApplicationRecipe::new, rr -> rr.require(manasteel()))
    .addStep(DeployerApplicationRecipe::new, rr -> rr.require(redstone())));
  public Item andesitecasing() {
    return AllBlocks.ANDESITE_CASING.get().asItem();
  }
  public Item shaft() {
    return AllBlocks.SHAFT.get().asItem();
  }
  public Item redstone() {
    return ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft:redstone")).asItem();
  }
  public Item lever() {
    return ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft:lever")).asItem();
  }
  public Item manasteel() {
    return ForgeRegistries.ITEMS.getValue(new ResourceLocation("botania:manasteel_ingot")).asItem();
  }
  public Item livingrock() {
    return ForgeRegistries.ITEMS.getValue(new ResourceLocation("botania:livingrock")).asItem();
  }
  public Item livingwood() {
    return ForgeRegistries.ITEMS.getValue(new ResourceLocation("botania:livingwood")).asItem();
  }
  @Override
  protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
    MANA_MACHINE_COMPONENT.register(consumer);
    XOR_LEVER.register(consumer);
  }
  @Override
  public String getName() {
    return "Creatania's Sequenced Assembly Recipes";
  }
}
