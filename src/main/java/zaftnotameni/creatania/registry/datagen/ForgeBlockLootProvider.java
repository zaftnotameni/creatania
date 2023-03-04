package zaftnotameni.creatania.registry.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;
import zaftnotameni.creatania.Constants;
public class ForgeBlockLootProvider implements DataProvider {
  private final DataGenerator generator;
  private static final Function<Block, LootTable.Builder> SKIP = b -> { throw new RuntimeException("shouldn't be executed"); };
  private final Map<Block, Function<Block, LootTable.Builder>> functionTable = new HashMap<>();
  public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
  public ForgeBlockLootProvider(DataGenerator generator) { this.generator = generator;  }
  @Override
  public void run(@NotNull HashCache pCache) throws IOException {
    var tables = new HashMap<ResourceLocation, LootTable.Builder>();
    for (var b : Registry.BLOCK) {
      ResourceLocation id = Registry.BLOCK.getKey(b);
      if (!Constants.MODID.equals(id.getNamespace())) continue;

      Function<Block, LootTable.Builder> func = functionTable.getOrDefault(b, ForgeBlockLootProvider::genRegular);
      if (func != SKIP) {
        tables.put(id, func.apply(b));
      }
    }
    for (Map.Entry<ResourceLocation, LootTable.Builder> e : tables.entrySet()) {
      Path path = getPath(generator.getOutputFolder(), e.getKey());
      DataProvider.save(GSON, pCache, LootTables.serialize(e.getValue().setParamSet(LootContextParamSets.BLOCK).build()), path);
    }
  }
  protected static LootTable.Builder genRegular(Block b) {
    LootPoolEntryContainer.Builder<?> entry = LootItem.lootTableItem(b);
    LootPool.Builder pool = LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(entry)
      .when(ExplosionCondition.survivesExplosion());
    return LootTable.lootTable().withPool(pool);
  }
  public static Path getPath(Path root, ResourceLocation id) {
    return root.resolve("data/" + id.getNamespace() + "/loot_tables/blocks/" + id.getPath() + ".json");
  }
  @Override
  public @NotNull String getName() {
    return "creatania block loot tables";
  }
}