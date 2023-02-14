package zaftnotameni.creatania.event;

import com.google.common.collect.Lists;
import com.simibubi.create.foundation.block.ITE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.particle.GlowParticle;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.fx.particle.ManaParticles;
import zaftnotameni.creatania.registry.Fluids;
import zaftnotameni.creatania.registry.Particles;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBus {

  @SubscribeEvent
  public static void registerParticleFactories(final ParticleFactoryRegisterEvent evt) {
    Minecraft.getInstance().particleEngine.register(Particles.MANA_PARTICLES.get(), GlowParticle.ElectricSparkProvider::new);
  }

  public static final List<Pair<ItemColor, Supplier<? extends ItemLike>[]>> ITEM_COLORS = Lists.newArrayList();
  @SubscribeEvent
  public static void onItemColorEvent(ColorHandlerEvent.Item event) {
    initItemColors();
    for (Pair<ItemColor, Supplier<? extends ItemLike>[]> pair : ITEM_COLORS) {
      event.getItemColors().register(pair.getLeft(), unpackItems(pair.getRight()));
    }
  }

  private static ItemLike[] unpackItems(Supplier<? extends ItemLike>[] items) {
    ItemLike[] array = new ItemLike[items.length];
    for (int i = 0; i < items.length; i++) {
      array[i] = Objects.requireNonNull(items[i].get());
    }
    return array;
  }

  public static void registerItemColors(ItemColor itemColor, Supplier<? extends ItemLike>... items) {
    Objects.requireNonNull(itemColor, "color is null!");
    if (Minecraft.getInstance().getItemColors() == null) {
      ITEM_COLORS.add(Pair.of(itemColor, items));
    } else {
      Minecraft.getInstance().getItemColors().register(itemColor, unpackItems(items));
    }
  }

  public static void initItemColors() {
    if (!ITEM_COLORS.isEmpty()) return;
    var purple = new ItemColor() {
      @Override
      public int getColor(ItemStack pStack, int pTintIndex) {
        return MaterialColor.COLOR_PURPLE.calculateRGBColor(MaterialColor.Brightness.LOW);
      }
    };
    var cyan = new ItemColor() {
      @Override
      public int getColor(ItemStack pStack, int pTintIndex) {
        return MaterialColor.COLOR_CYAN.calculateRGBColor(MaterialColor.Brightness.HIGH);
      }
    };
    registerItemColors(purple, () -> Fluids.CORRUPT_MANA_FLUID_BUCKET.get().asItem());
    registerItemColors(cyan, () -> Fluids.MANA_FLUID_BUCKET.get().asItem());
  }
}
