package zaftnotameni.creatania.registry;

import com.jozufozu.flywheel.core.PartialModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import zaftnotameni.creatania.Constants;

@OnlyIn(Dist.CLIENT)
public class BlockPartials {
  public static final PartialModel MANA_MOTOR_FAN = block("mana_motor/mana_motor_fan");
  public static final PartialModel MANA_GENERATOR_TURBINE = block("mana_generator/copper_turbine");
  private static PartialModel block(String path) {
    return new PartialModel(new ResourceLocation(Constants.MODID + ":block/" + path));
  }

  public static void init() {}

}