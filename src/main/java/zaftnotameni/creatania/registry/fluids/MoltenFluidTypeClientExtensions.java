package zaftnotameni.creatania.registry.fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
public class MoltenFluidTypeClientExtensions implements IClientFluidTypeExtensions {
  @Override
  public int getTintColor() {
    return IClientFluidTypeExtensions.super.getTintColor();
  }
  public static class Copper implements  IClientFluidTypeExtensions {
    @Override
    public int getTintColor() {
      return 0x9900ff44;
    }
  }
  public static class Iron implements  IClientFluidTypeExtensions {
    @Override
    public int getTintColor() {
      return 0x99aaaa00;
    }
  }
  public static class Gold implements  IClientFluidTypeExtensions {
    @Override
    public int getTintColor() {
      return 0x9966ffff;
    }
  }
  public static class Zinc implements  IClientFluidTypeExtensions {
    @Override
    public int getTintColor() {
      return 0x9900ff44;
    }
  }
  public static class Brass implements  IClientFluidTypeExtensions {
    @Override
    public int getTintColor() {
      return 0x99aaaa00;
    }
  }
  public static class Andesite implements  IClientFluidTypeExtensions {
    @Override
    public int getTintColor() {
      return 0x9966ffff;
    }
  }
  public static class Terrasteel implements  IClientFluidTypeExtensions {
    @Override
    public int getTintColor() {
      return 0x9900ff44;
    }
  }
  public static class Manasteel implements  IClientFluidTypeExtensions {
    @Override
    public int getTintColor() {
      return 0x99aaaa00;
    }
  }
  public static class Elementium implements  IClientFluidTypeExtensions {
    @Override
    public int getTintColor() {
      return 0x9966ffff;
    }
  }
  public static class Gaia implements  IClientFluidTypeExtensions {
    @Override
    public int getTintColor() {
      return 0x9966ffff;
    }
  }
}
