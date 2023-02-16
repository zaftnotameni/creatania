package zaftnotameni.creatania.registry;
import zaftnotameni.creatania.advancements.CreataniaAdvancement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import static zaftnotameni.creatania.advancements.CreataniaAdvancement.TaskType.*;
public class Advancements {
  public static final List<CreataniaAdvancement> ENTRIES = new ArrayList<>();
  public static final CreataniaAdvancement START = null,
    ROOT = create("root", b -> b.icon(zaftnotameni.creatania.registry.Items.MANA_GEL.get())
      .title("Welcome to Creatania")
      .description("Creatania is a Contraption Magical Tech Mod")
      .awardedForFree()
      .special(SILENT)),
    MANA_MACHINE_COMPONENT = create("mana_machine_component", b -> b.icon(zaftnotameni.creatania.registry.Blocks.MANA_MACHINE_COMPONENT.get())
      .title("Magical... Stress")
      .description("Obtain a mana machine component, the core of all mana machines")
      .after(ROOT)
      .special(NOISY)
      .whenIconCollected()),
    MANA_GENERATOR = create("mana_generator", b -> b.icon(zaftnotameni.creatania.registry.Blocks.MANA_GENERATOR.get())
      .title("Poolished Mechanics")
      .description("Create your first Mana Generator, now you can convert inert mana fluid to real mana")
      .after(MANA_MACHINE_COMPONENT)
      .special(NOISY)
      .whenIconCollected()),
    MANA_CONDENSER = create("mana_condenser", b -> b.icon(zaftnotameni.creatania.registry.Blocks.MANA_CONDENSER.get())
      .title("Mana ex nihilo")
      .description("You're now ready to extract mana out of thin air, though it might be corrupted in the process")
      .after(MANA_MACHINE_COMPONENT)
      .special(NOISY)
      .whenIconCollected()),
    MANA_MOTOR = create("mana_motor", b -> b.icon(zaftnotameni.creatania.registry.Blocks.MANA_MOTOR.get())
      .title("Don't Stress over it... you'll MANAge just fine")
      .description("You're now ready to generate rotation force with mana")
      .after(MANA_MACHINE_COMPONENT)
      .whenIconCollected()),
    DOING_THE_LORDS_WORK = create("lords_work", b -> b.icon(net.minecraft.world.item.Items.WANDERING_TRADER_SPAWN_EGG)
      .title("More like wandering TRAITOR...")
      .description("The llama guy got what he deserves, thanks for your service")
      .after(ROOT)
      .special(SECRET)),
    BUFF_FROM_INERT_MANA_BLOCKS = create("buff_from_purified_blocks", b -> b.icon(Blocks.PURIFIED_INERT_MANA_BLOCK.get())
      .title("Power Overwhelming!")
      .description("Purified mana might be inert, but still contains a lot of power within")
      .after(ROOT)
      .special(SECRET)),
    BUFF_FROM_REAL_MANA_BLOCKS = create("buff_from_real_mana_blocks", b -> b.icon(Blocks.BOTANIA_MANA_BLOCK.get())
      .title("Flying High")
      .description("Botania mana blocks are the real deal, pure condensed magical power")
      .after(ROOT)
      .special(SECRET)),
    DEBUFF_FROM_INERT_MANA_BLOCKS = create("debuff_from_corrupt_blocks", b -> b.icon(Blocks.CORRUPTED_INERT_MANA_BLOCK.get())
      .title("Watch your step!")
      .description("Corrupt mana might be inert, but still extremely dangerous")
      .after(ROOT)
      .special(SECRET)),
    END = null;

  public static CreataniaAdvancement create(String id, UnaryOperator<CreataniaAdvancement.Builder> b) {
    return new CreataniaAdvancement(id, b);
  }

  public static void register() {}
}
