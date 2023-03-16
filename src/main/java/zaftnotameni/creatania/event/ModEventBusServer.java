package zaftnotameni.creatania.event;

import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.recipes.condenser.ManaCondenserRecipe;
import zaftnotameni.creatania.recipes.generator.ManaGeneratorRecipe;

import static net.minecraft.core.Registry.register;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.DEDICATED_SERVER)
public class ModEventBusServer {
  @SubscribeEvent
  public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> evt) {
    register(
      Registry.RECIPE_TYPE, ManaGeneratorRecipe.Type.ID, ManaGeneratorRecipe.Type.INSTANCE
    );
    register(
      Registry.RECIPE_TYPE, ManaCondenserRecipe.Type.ID, ManaCondenserRecipe.Type.INSTANCE
    );
  }
}