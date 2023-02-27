//package zaftnotameni.creatania.registry;
//import net.minecraft.core.Registry;
//import net.minecraft.world.item.crafting.RecipeSerializer;
//import net.minecraft.world.item.crafting.RecipeType;
//import net.minecraftforge.eventbus.api.IEventBus;
//import net.minecraftforge.registries.DeferredRegister;
//import net.minecraftforge.registries.ForgeRegistries;
//import net.minecraftforge.registries.RegistryObject;
//import zaftnotameni.creatania.Constants;
//import zaftnotameni.creatania.recipes.cobblegen.CobblegenRecipe;
//import zaftnotameni.creatania.recipes.condenser.ManaCondenserRecipe;
//import zaftnotameni.creatania.recipes.generator.ManaGeneratorRecipe;
//public class Recipes {
//  public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Constants.MODID);
//  public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, Constants.MODID);
//  static {
//    CobblegenRecipe.init();
//  }
//  public static final RegistryObject<RecipeSerializer<ManaGeneratorRecipe>> MANA_GENERATOR_SERIALIZER = SERIALIZERS.register(ManaGeneratorRecipe.Type.ID, () -> ManaGeneratorRecipe.Serializer.INSTANCE);
//  public static final RegistryObject<RecipeSerializer<ManaCondenserRecipe>> MANA_CONDENSER_SERIALIZER = SERIALIZERS.register(ManaCondenserRecipe.Type.ID, () -> ManaCondenserRecipe.Serializer.INSTANCE);
//
//  public static void register(IEventBus bus) {
//    SERIALIZERS.register(bus);
//    TYPES.register(bus);
//  }
//}
