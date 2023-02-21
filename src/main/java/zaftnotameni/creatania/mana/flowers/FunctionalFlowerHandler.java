package zaftnotameni.creatania.mana.flowers;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.DistExecutor;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.BotaniaForgeCapabilities;
import vazkii.botania.api.BotaniaForgeClientCapabilities;
import vazkii.botania.api.block.IWandBindable;
import vazkii.botania.api.block.IWandHUD;
import vazkii.botania.api.block.IWandable;
import vazkii.botania.api.internal.IManaNetwork;
import vazkii.botania.api.mana.IManaCollector;
import vazkii.botania.api.mana.IManaPool;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
public class FunctionalFlowerHandler<T extends SmartTileEntity & FunctionalFlowerHandler.BotaniaFlower> {
  public static final ResourceLocation MANA_POOL = new ResourceLocation("botania", "mana_pool");
  public static final ResourceLocation MANA_SPREADER = new ResourceLocation("botania", "mana_spreader");
  public static final int DEFAULT_MAX_MANA = 300;
  public static final int DEFAULT_MAX_TRANSFER = 30;
  public interface BotaniaFlower extends IWandBindable, IWandable, IWandHUD {}

  public BlockPos poolPosition;
  public IManaPool pool;
  public BlockPos spreaderPosition;
  public IManaCollector spreader;
  public boolean isFloating;
  public int mana;
  public int maxMana;
  public int maxTransfer;
  public int color;
  public boolean isGenerating;
  public T self;
  public int sizeLastCheck = -1;
  public int redstoneIn = 0;
  public boolean didWork;
  public FunctionalFlowerHandler(T self) { this.self = self; }
  public FunctionalFlowerHandler withColor(int color) { this.color = color; return this; }
  public FunctionalFlowerHandler withMana(int x) { this.mana = x; return this; }
  public FunctionalFlowerHandler withMaxMana(int x) { this.maxMana = x; return this; }
  public FunctionalFlowerHandler withMaxTransfer(int x) { this.maxTransfer = x; return this; }
  public FunctionalFlowerHandler withPool(IManaPool x) { this.pool = x; return this; }
  public FunctionalFlowerHandler withSpreader(IManaCollector x) { this.spreader = x; return this; }
  public FunctionalFlowerHandler withSelf(T x) { this.self = x; return this; }
  public FunctionalFlowerHandler withIsGenerating(boolean x) { this.isGenerating = x; return this; }
  public FunctionalFlowerHandler withIsFloating(boolean x) { this.isFloating = x; return this; }

  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    if (cap == BotaniaForgeCapabilities.WANDABLE) {
      return LazyOptional.of(() -> self).cast();
    } else {
      return DistExecutor.unsafeRunForDist(
        () -> () -> cap == BotaniaForgeClientCapabilities.WAND_HUD ? LazyOptional.of(() -> this).cast() : LazyOptional.empty(),
        () -> () -> LazyOptional.empty()
      );
    }
  }
  public void linkPool() {
    Object theTileObj = this.isGenerating ? this.spreader : this.pool;
    BlockEntity theTile;
    if (!(theTileObj instanceof BlockEntity)) {
      theTile = null;
    } else {
      theTile = (BlockEntity) theTileObj;
    }
    //noinspection ConstantConditions
    if ((this.poolPosition != null && theTile == null) || this.poolPosition != null && !this.poolPosition.equals(theTile.getBlockPos()) || (this.poolPosition != null && this.self.getLevel().getBlockEntity(this.poolPosition) != theTile)) {
      // tile is outdated. Update it
      //noinspection ConstantConditions
      BlockEntity te = this.self.getLevel().getBlockEntity(this.poolPosition);
      if (this.isGenerating) {
        this.pool = null;
        if (!(te instanceof IManaCollector)) {
          this.spreader = null;
        } else {
          this.spreader = (IManaCollector) te;
        }
      } else {
        if (!(te instanceof IManaPool)) {
          this.pool = null;
        } else {
          this.pool = (IManaPool) te;
        }
        this.spreader = null;
      }
    }

    if (this.pool == null) {
      if (this.isGenerating) {
        IManaNetwork network = BotaniaAPI.instance().getManaNetworkInstance();
        int size = network.getAllCollectorsInWorld(this.self.getLevel()).size();
        if (size != this.sizeLastCheck) {
          IManaCollector te = network.getClosestCollector(this.self.getBlockPos(), this.self.getLevel(), 10);
          if (te != null) {
            this.poolPosition = te.getManaReceiverPos();
            this.pool = null;
            this.spreader = te;
            this.self.setChanged();
          }
          this.sizeLastCheck = size;
        }
      } else {
        IManaNetwork network = BotaniaAPI.instance().getManaNetworkInstance();
        int size = network.getAllPoolsInWorld(this.self.getLevel()).size();
        if (size != this.sizeLastCheck) {
          IManaPool te = network.getClosestPool(this.self.getBlockPos(), this.self.getLevel(), 10);
          if (te != null) {
            this.poolPosition = te.getManaReceiverPos();
            this.pool = te;
            this.spreader = null;
            this.self.setChanged();
          }
          this.sizeLastCheck = size;
        }
      }
    }

    this.self.setChanged();
  }
  public final void tick() {
    this.linkPool();
//
//    //noinspection ConstantConditions
//    if (!this.level.isClientSide) {
//      if (this.isGenerating) {
//        if (this.spreaderTile != null) {
//          if (this.mana > 0) {
//            int manaTransfer = Math.min(this.maxTransfer, Math.min(this.mana, this.spreaderTile.getMaxMana() - this.spreaderTile.getCurrentMana()));
//            this.spreaderTile.receiveMana(manaTransfer);
//            this.mana = Mth.clamp(this.mana - manaTransfer, 0, this.maxMana);
//            this.setChanged();
//            this.markPoolDirty();
//          }
//        }
//      } else {
//        if (this.poolTile != null) {
//          if (this.mana < this.maxMana) {
//            int manaTransfer = Math.min(this.maxTransfer, Math.min(this.maxMana - this.mana, this.poolTile.getCurrentMana()));
//            this.poolTile.receiveMana(-manaTransfer);
//            this.mana = Mth.clamp(this.mana + manaTransfer, 0, this.maxMana);
//            this.setChanged();
//            this.markPoolDirty();
//          }
//        }
//      }
//
//      this.redstoneIn = 0;
//      for (Direction dir : Direction.values()) {
//        int redstonePower = this.level.getSignal(this.getBlockPos().relative(dir), dir);
//        this.redstoneIn = Math.max(redstonePower, this.redstoneIn);
//      }
//    }
//
//    double particleChance = 1.0D - this.mana / (double) this.maxMana / 3.5;
//
//    this.didWork = false;
//    this.tickFlower();
//
//    if (this.level.isClientSide) {
//      if (this.didWork)
//        particleChance = 3 * particleChance;
//      float red = (float) (this.color >> 16 & 0xFF) / 255.0f;
//      float green = (float) (this.color >> 8 & 0xFF) / 255.0f;
//      float blue = (float) (this.color & 255) / 255.0f;
//      if (Math.random() > particleChance) {
//        BotaniaAPI.instance().sparkleFX(this.getLevel(), (double) this.getBlockPos().getX() + 0.3D + Math.random() * 0.5D, (double) this.getBlockPos().getY() + 0.5D + Math.random() * 0.5D, (double) this.getBlockPos().getZ() + 0.3D + Math.random() * 0.5D, red, green, blue, (float) Math.random(), 5);
//      }
//    }
  }
}
