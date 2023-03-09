package zaftnotameni.creatania.machines.managenerator;

import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ManaGeneratorBlockStates {
  public static final BooleanProperty IS_GENERATING = BooleanProperty.create("is_generating");
  public static final BooleanProperty HAS_ENOUGH_MANA = BooleanProperty.create("has_enough_mana");
  public static final BooleanProperty HAS_DUCT = BooleanProperty.create("has_duct");
}