package zaftnotameni.creatania.registry;
import com.simibubi.create.AllItems;
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
    MANADUCT_TIER_1 = create("manaduct_tier_1", b -> b.icon(Blocks.MANASTEEL_MANADUCT_BLOCK.get())
      .title("Pipe Dream")
      .description("Ducts transfer mana without the massive efficiency loss of transmitting it through air")
      .after(MANA_GENERATOR)
      .special(NOISY)
      .whenIconCollected()),
    MANADUCT_TIER_2 = create("manaduct_tier_2", b -> b.icon(Blocks.TERRASTEEL_MANADUCT_BLOCK.get())
      .title("Duct Tales")
      .description("A more refined duct, will increase your mana gains even more")
      .after(MANA_GENERATOR)
      .special(NOISY)
      .whenIconCollected()),
    MANADUCT_TIER_3 = create("manaduct_tier_3", b -> b.icon(Blocks.GAIA_MANADUCT_BLOCK.get())
      .title("Duct taping mana")
      .description("An even larger pipe")
      .after(MANA_GENERATOR)
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
    PICK_A_BOTANIA_BLOCK_WITH_CREATE_WRENCH = create("botania_block_with_create_wrench", b -> b.icon(AllItems.WRENCH.get())
      .title("Joyful Mana Gut Wrenching")
      .description("Pick up blocks from botania using the create wrench. Be very careful with it")
      .after(ROOT)
      .special(SECRET)),
    PREVENT_ENDER_ENTITY_FROM_TELEPORTING = create("prevent_ender_entity_from_teleporting", b -> b.icon(net.minecraft.world.item.Items.ENDER_PEARL)
      .title("Denied!")
      .description("Corrupt mana blocks, among its many properties, can prevent ender entities from teleporting")
      .after(ROOT)
      .special(SECRET)),
    PRODUCE_MANA_GEL_FROM_SLIME = create("produce_mana_gel_from_slime", b -> b.icon(Items.MANA_GEL.get())
      .title("YUMMY!")
      .description("Produce mana gel by having a slime touch purified inert mana fluid")
      .after(ROOT)
      .special(SECRET)),
    END = null;

  public static CreataniaAdvancement create(String id, UnaryOperator<CreataniaAdvancement.Builder> b) {
    return new CreataniaAdvancement(id, b);
  }

  public static void register() {}
}
