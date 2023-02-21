package zaftnotameni.creatania.mana.flowers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.DistExecutor;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.BotaniaForgeCapabilities;
import vazkii.botania.api.BotaniaForgeClientCapabilities;
import vazkii.botania.api.internal.IManaNetwork;
import vazkii.botania.api.mana.IManaCollector;
import vazkii.botania.api.mana.IManaPool;
import zaftnotameni.sharedbehaviors.KineticManaMachine;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
public class FunctionalFlowerHandler<T extends SmartTileEntity & BotaniaFlowerInterfaces> implements BotaniaFlowerInterfaces {
  public static final ResourceLocation MANA_POOL = new ResourceLocation("botania", "mana_pool");
  public static final ResourceLocation MANA_SPREADER = new ResourceLocation("botania", "mana_spreader");
  public BlockPos poolPosition;
  public IManaPool pool;
  public BlockPos spreaderPosition;
  public IManaCollector spreader;
  public boolean isFloating = false;
  public int bindRange = 10;
  public int mana = 0;
  public int maxMana = 10000;
  public int maxTransfer = 100;
  public int color = 0xffff0000;
  public int manaPerOperation = 10;
  public boolean isGenerating;
  public T self;
  public int sizeLastCheck = -1;
  public int redstoneIn = 0;
  public boolean didWork;
  public int tickCounter = 0;
  public int tickRate = 20;
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
  public FunctionalFlowerHandler withTickCounter(int x) { this.tickCounter = x; return this; }
  public FunctionalFlowerHandler withTickRate(int x) { this.tickRate = x; return this; }
  public FunctionalFlowerHandler withRequiredManaPerOperation(int x) { this.manaPerOperation = x; return this; }
  public FunctionalFlowerHandler withBindRange(int x) { this.bindRange = x; return this; }
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
          IManaCollector te = network.getClosestCollector(this.self.getBlockPos(), this.self.getLevel(), this.bindRange);
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
          IManaPool te = network.getClosestPool(this.self.getBlockPos(), this.self.getLevel(), this.bindRange);
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
  public void markPoolOrSpreaderAsDirty() { if (this.pool instanceof BlockEntity p) p.setChanged(); if (this.spreader instanceof BlockEntity s) s.setChanged(); }
  @Override
  public int doTick() {
    if (tickCounter++ < tickRate) return 0;
    tickCounter = 0;

    this.linkPool();

    //noinspection ConstantConditions
    if (!this.self.getLevel().isClientSide) {
      if (this.isGenerating) {
        if (this.spreader != null) {
          if (this.mana > 0) {
            int manaTransfer = Math.min(this.maxTransfer, Math.min(this.mana, this.spreader.getMaxMana() - this.spreader.getCurrentMana()));
            this.spreader.receiveMana(manaTransfer);
            this.mana = Mth.clamp(this.mana - manaTransfer, 0, this.maxMana);
            this.self.setChanged();
            this.markPoolOrSpreaderAsDirty();
          }
        }
      } else {
        if (this.pool != null) {
          if (this.mana < this.maxMana) {
            int manaTransfer = Math.min(this.maxTransfer, Math.min(this.maxMana - this.mana, this.pool.getCurrentMana()));
            this.pool.receiveMana(-manaTransfer);
            this.mana = Mth.clamp(this.mana + manaTransfer, 0, this.maxMana);
            this.self.setChanged();
            this.markPoolOrSpreaderAsDirty();
          }
        }
      }

      this.redstoneIn = 0;
      for (Direction dir : Direction.values()) {
        int redstonePower = this.self.getLevel().getSignal(this.self.getBlockPos().relative(dir), dir);
        this.redstoneIn = Math.max(redstonePower, this.redstoneIn);
      }
    }

    double particleChance = 1.0D - this.mana / (double) this.maxMana / 3.5;

    this.didWork = false;
    var manaConsumed = this.self.doTick();
    if (manaConsumed > 0) {
      this.mana -= manaConsumed;
      this.self.setChanged();
    }

    if (this.self.getLevel().isClientSide) {
      if (this.didWork)
        particleChance = 3 * particleChance;
      float red = (float) (this.color >> 16 & 0xFF) / 255.0f;
      float green = (float) (this.color >> 8 & 0xFF) / 255.0f;
      float blue = (float) (this.color & 255) / 255.0f;
      if (Math.random() > particleChance) {
        BotaniaAPI.instance().sparkleFX(this.self.getLevel(), (double) this.self.getBlockPos().getX() + 0.3D + Math.random() * 0.5D, (double) this.self.getBlockPos().getY() + 0.5D + Math.random() * 0.5D, (double) this.self.getBlockPos().getZ() + 0.3D + Math.random() * 0.5D, red, green, blue, (float) Math.random(), 5);
      }
    }
    return manaConsumed;
  }
  @Override
  public boolean canSelect(Player player, ItemStack wand, BlockPos pos, Direction side) { return true; }
  @Override
  public boolean bindTo(Player player, ItemStack wand, BlockPos pos, Direction side) {
    var squaredRange = this.bindRange * this.bindRange;
    double dist = pos.distSqr(this.self.getBlockPos());
    if ((double) squaredRange >= dist) {
      BlockEntity tile = player.level.getBlockEntity(pos);
      if (this.isGenerating && tile instanceof IManaCollector) {
        this.spreaderPosition = tile.getBlockPos();
        this.spreader = (IManaCollector) tile;
        this.pool = null;
        this.self.setChanged();
        return true;
      } else if (!this.isGenerating && tile instanceof IManaPool) {
        this.poolPosition = tile.getBlockPos();
        this.pool = (IManaPool) tile;
        this.spreader = null;
        this.self.setChanged();
        return true;
      }
    }
    return false;
  }
  @org.jetbrains.annotations.Nullable
  @Override
  public BlockPos getBinding() { return this.poolPosition; }
  @Override
  public void renderHUD(PoseStack ms, Minecraft mc) { KineticManaMachine.renderSimpleBotaniaHud(this.self.getLevel(), this.self.getBlockState(), ms, this.color, this.mana, this.maxMana); }
  @Override
  public boolean onUsedByWand(@org.jetbrains.annotations.Nullable Player player, ItemStack stack, Direction side) { return false; }

  public Integer getCurrentMana() { return this.mana; }
  public Integer getManaPerOperation() { return this.manaPerOperation; }
  public Boolean hasEnoughManaForOneOperation() { return this.mana > this.manaPerOperation; }

  public void load(@Nonnull CompoundTag nbt) {
    if (nbt.contains("mana", Tag.TAG_INT)) {
      this.mana = Mth.clamp(nbt.getInt("mana"), 0, this.maxMana);
    } else {
      this.mana = 0;
    }
    if (nbt.contains("pool")) {
      CompoundTag poolTag = nbt.getCompound("pool");
      this.poolPosition = new BlockPos(poolTag.getInt("x"), poolTag.getInt("y"), poolTag.getInt("z"));
    } else {
      this.poolPosition = null;
    }
    this.isFloating = nbt.getBoolean("floating");
  }

  public void saveAdditional(@Nonnull CompoundTag compound) {
    compound.putInt("mana", Mth.clamp(this.mana, 0, this.maxMana));
    if (this.poolPosition != null) {
      CompoundTag poolTag = new CompoundTag();
      poolTag.putInt("x", this.poolPosition.getX());
      poolTag.putInt("y", this.poolPosition.getY());
      poolTag.putInt("z", this.poolPosition.getZ());
      compound.put("pool", poolTag);
    }
    compound.putBoolean("floating", this.isFloating);
  }

  public CompoundTag getUpdateTag(CompoundTag tag) {
    //noinspection ConstantConditions
    if (!this.self.getLevel().isClientSide) {
      tag.putInt("mana", Mth.clamp(this.mana, 0, this.maxMana));
      if (this.poolPosition != null) {
        CompoundTag poolTag = new CompoundTag();
        poolTag.putInt("x", this.poolPosition.getX());
        poolTag.putInt("y", this.poolPosition.getY());
        poolTag.putInt("z", this.poolPosition.getZ());
        tag.put("pool", poolTag);
      }
      tag.putBoolean("floating", this.isFloating);
    }
    return tag;
  }

  public void handleUpdateTag(CompoundTag nbt) {
    //noinspection ConstantConditions
    if (this.self.getLevel().isClientSide) {
      this.mana = Mth.clamp(nbt.getInt("mana"), 0, this.maxMana);
      if (nbt.contains("pool")) {
        CompoundTag poolTag = nbt.getCompound("pool");
        this.poolPosition = new BlockPos(poolTag.getInt("x"), poolTag.getInt("y"), poolTag.getInt("z"));
      } else {
        this.poolPosition = null;
      }
      this.isFloating = nbt.getBoolean("floating");
    }
  }
  public boolean isValidBinding() {
    Object theTileObj = this.isGenerating ? this.spreader : this.pool;
    if (!(theTileObj instanceof BlockEntity theTile))
      return false;
    // noinspection ConstantConditions,deprecation,deprecation
    return this.poolPosition != null && theTile != null && theTile.hasLevel() && !theTile.isRemoved()
      && this.self.getLevel().hasChunkAt(theTile.getBlockPos()) && this.self.getLevel().getBlockEntity(this.poolPosition) == theTile;
  }
}
