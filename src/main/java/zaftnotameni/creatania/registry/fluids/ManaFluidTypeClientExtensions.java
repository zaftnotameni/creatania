package zaftnotameni.creatania.registry.fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
public class ManaFluidTypeClientExtensions implements IClientFluidTypeExtensions {
  @Override
  public int getTintColor() {
    return IClientFluidTypeExtensions.super.getTintColor();
  }
  public static class PureMana implements  IClientFluidTypeExtensions {
    @Override
    public int getTintColor() {
      return 0x9900ff44;
    }
  }
  public static class CorruptMana implements  IClientFluidTypeExtensions {
    @Override
    public int getTintColor() {
      return 0x99aaaa00;
    }
  }
  public static class RealMana implements  IClientFluidTypeExtensions {
    @Override
    public int getTintColor() {
      return 0x9966ffff;
    }
  }
}
