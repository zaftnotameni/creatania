package zaftnotameni.creatania.config;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.simibubi.create.infrastructure.config.CStress;
import com.simibubi.create.foundation.utility.Couple;
import java.lang.reflect.Field;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeConfigSpec;
public class StressProvider {
  public static class CreataniaStressProvider extends CStress {
    Field c;
    Field i;
    ForgeConfigSpec spec;
    public CreataniaStressProvider() {
      stealFields();
    }
    public void stealFields() {
      try {
        c = AllConfigs.server().kinetics.stressValues.getClass().getField("capacities");
        i = AllConfigs.server().kinetics.stressValues.getClass().getField("impacts");
        c.setAccessible(true);
        i.setAccessible(true);
      } catch (Exception e) {}
    }
    @Override
    public Couple<Integer> getGeneratedRPM(Block block) {
      return super.getGeneratedRPM(block);
    }
    @Override
    public boolean hasImpact(Block block) {
      return super.hasImpact(block);
    }
    @Override
    public boolean hasCapacity(Block block) {
      return super.hasCapacity(block);
    }
    @Override
    public String getName() {
      return super.getName();
    }
    @Override
    public Map<ResourceLocation, ForgeConfigSpec.ConfigValue<Double>> getImpacts() {
      return AllConfigs.server().kinetics.stressValues.getImpacts();
    }
    @Override
    public Map<ResourceLocation, ForgeConfigSpec.ConfigValue<Double>> getCapacities() {
      return AllConfigs.server().kinetics.stressValues.getCapacities();
    }
    @Override
    public double getImpact(Block block) {
      return AllConfigs.server().kinetics.stressValues.getImpact(block);
    }
    @Override
    public double getCapacity(Block block) {
      return AllConfigs.server().kinetics.stressValues.getCapacity(block);
    }
    @Override
    public void registerAll(ForgeConfigSpec.Builder builder) {
      builder.comment(".", Comments.su, Comments.impact)
        .push("impact");
      BlockStressDefaults.DEFAULT_IMPACTS.forEach((r, i) -> {
        getImpacts().put(r, builder.define(r.getPath(), i));
      });
      builder.pop();
      builder.comment(".", Comments.su, Comments.capacity).push("capacity");
      BlockStressDefaults.DEFAULT_CAPACITIES.forEach((r, i) -> {
        getCapacities().put(r, builder.define(r.getPath(), i));
      });
      builder.pop();
    }
    public void registerAll() {
      var builder = new ForgeConfigSpec.Builder();
      builder.comment(".", Comments.su, Comments.impact).push("impact");
      BlockStressDefaults.DEFAULT_IMPACTS.forEach((r, i) -> {
        getImpacts().put(r, builder.define(r.getPath(), i));
      });
      builder.pop();
      builder.comment(".", Comments.su, Comments.capacity).push("capacity");
      BlockStressDefaults.DEFAULT_CAPACITIES.forEach((r, i) -> {
        getCapacities().put(r, builder.define(r.getPath(), i));
      });
      builder.pop();
      spec = builder.build();
    }
  }
  private static class Comments {
    static String su = "[in Stress Units]";
    static String impact =
      "Configure the individual stress impact of mechanical blocks. Note that this cost is doubled for every speed increase it receives.";
    static String capacity = "Configure how much stress a source can accommodate for.";
  }
}