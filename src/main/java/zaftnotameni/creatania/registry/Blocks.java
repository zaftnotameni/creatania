package zaftnotameni.creatania.registry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.block.ItemUseOverrides;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import java.util.function.Function;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.eventbus.api.IEventBus;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.config.CommonConfig;
import zaftnotameni.creatania.machines.ManaCasing;
import zaftnotameni.creatania.machines.manacondenser.ManaCondenserBlock;
import zaftnotameni.creatania.machines.managenerator.ManaGeneratorBlock;
import zaftnotameni.creatania.machines.manamotor.ManaMotorBlock;
import zaftnotameni.creatania.mana.flowers.blazunia.BlazuniaFunctionalFlowerBlock;
import zaftnotameni.creatania.mana.flowers.blazunia.BlazuniaFunctionalFlowerBlockEntity;
import zaftnotameni.creatania.mana.manablock.CorruptManaBlock;
import zaftnotameni.creatania.mana.manablock.PureManaBlock;
import zaftnotameni.creatania.mana.manablock.RealManaBlock;
import zaftnotameni.creatania.mana.manaduct.ElementiumManaductBlock;
import zaftnotameni.creatania.mana.manaduct.GaiaManaductBlock;
import zaftnotameni.creatania.mana.manaduct.ManasteelManaductBlock;
import zaftnotameni.creatania.mana.manaduct.TerrasteelManaductBlock;
import zaftnotameni.creatania.stress.omnibox.OmniboxBlock;
import zaftnotameni.creatania.stress.xorlever.XorLeverBlock;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.CONDITIONAL;
import static zaftnotameni.creatania.mana.manablock.BaseManaBlock.registerManablock;
import static zaftnotameni.creatania.mana.manaduct.BaseManaductBlock.registerManaduct;
import static zaftnotameni.creatania.util.LogKt.log;

public class Blocks {
  public static final BlockEntry<ManaMotorBlock> MANA_MOTOR = Index.all()
    .block(Constants.MANA_MOTOR, ManaMotorBlock::new)
    .lang("Mana Motor")
    .initialProperties(SharedProperties::stone)
    .blockstate(BlockStateGen.directionalAxisBlockProvider())
    .addLayer(() -> RenderType::cutoutMipped)
    .transform(axeOrPickaxe())
    .transform(BlockStressDefaults.setCapacity(CommonConfig.MANA_MOTOR_SU_PER_RPM.get()))
    .item()
    .transform(customItemModel())
    .register();

  public static final BlockEntry<ManaGeneratorBlock> MANA_GENERATOR = Index.all()
    .block(Constants.MANA_GENERATOR, ManaGeneratorBlock::new)
    .lang("Mana Generator")
    .initialProperties(SharedProperties::softMetal)
    .blockstate(BlockStateGen.directionalAxisBlockProvider())
    .addLayer(() -> RenderType::cutoutMipped)
    .transform(axeOrPickaxe())
    .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
    .transform(BlockStressDefaults.setImpact(CommonConfig.MANA_GENERATOR_SU_PER_RPM.get()))
    .item()
    .transform(customItemModel())
    .register();

  public static final BlockEntry<ManaCondenserBlock> MANA_CONDENSER = Index.all()
    .block(Constants.MANA_CONDENSER, ManaCondenserBlock::new)
    .lang("Mana Condenser")
    .initialProperties(SharedProperties::softMetal)
    .blockstate(BlockStateGen.directionalAxisBlockProvider())
    .addLayer(() -> RenderType::cutoutMipped)
    .transform(axeOrPickaxe())
    .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
    .transform(BlockStressDefaults.setImpact(CommonConfig.MANA_CONDENSER_SU_PER_RPM.get()))
    .item()
    .transform(customItemModel())
    .register();

  public static final BlockEntry<OmniboxBlock> OMNIBOX = Index.all()
    .block(Constants.OMNIBOX, OmniboxBlock::new)
    .lang("Omnibox")
    .initialProperties(SharedProperties::stone)
    .blockstate(BlockStateGen.directionalBlockProvider(false))
    .addLayer(() -> RenderType::cutoutMipped)
    .properties(BlockBehaviour.Properties::noOcclusion)
    .properties(p -> p.color(MaterialColor.PODZOL))
    .transform(BlockStressDefaults.setNoImpact())
    .transform(axeOrPickaxe())
    .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
    .item()
    .transform(customItemModel())
    .register();

  public static final BlockEntry<XorLeverBlock> XOR_LEVER = Index.all()
    .block(Constants.XOR_LEVER, XorLeverBlock::new)
    .lang("XOR Lever")
    .initialProperties(() -> net.minecraft.world.level.block.Blocks.LEVER)
    .transform(axeOrPickaxe())
    .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
    .blockstate(Blocks::xorLeverModel)
    .onRegister(ItemUseOverrides::addBlock)
    .item()
    .transform(customItemModel())
    .register();

  public static ModelFile xorLeverModel(DataGenContext<Block, XorLeverBlock> ctx, RegistrateBlockstateProvider prov) {
    var block = ctx.get();
    Function<BlockState, ModelFile> modelFunc = x -> AssetLookup.partialBaseModel(ctx, prov);
    Function<BlockState, Integer> rotationOnYAxis = (state) -> {
      var xInitialValue = state.getValue(BlockStateProperties.ATTACH_FACE).ordinal() * 90;
      var yInitialValue = (((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) + (state.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING ? 180 : 0)) % 360;
      var isWall = (xInitialValue == 90 || xInitialValue == 270);
      var isCeiling = (state.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING);
      var isFloor = !isWall && !isCeiling;
      var onOffYRotationCeilingDelta = state.getValue(CONDITIONAL) ? 0 : 180;
      var onOffYRotationFloorDelta = state.getValue(CONDITIONAL) ? 180 : 0;
      var onOffYRotationWallDelta = state.getValue(CONDITIONAL) ? 180 : 0;
      if (isCeiling) return (yInitialValue + onOffYRotationCeilingDelta) % 360;
      if (isFloor) return (yInitialValue + onOffYRotationFloorDelta) % 360;
      // isWall
      return (yInitialValue + onOffYRotationWallDelta) % 360;
    };
    Function<BlockState, Integer> rotationOnXAxis = (state) -> {
      var xInitialValue = state.getValue(BlockStateProperties.ATTACH_FACE).ordinal() * 90;
      var yInitialValue = (((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) + (state.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING ? 180 : 0)) % 360;
      var isWall = (xInitialValue == 90 || xInitialValue == 270);
      var isCeiling = (state.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING);
      var isFloor = !isWall && !isCeiling;
      var onOffXRotationCeilingDelta = 0;
      var onOffXRotationFloorDelta = 0;
      var onOffXRotationWallDelta = state.getValue(CONDITIONAL) ? 180 : 0;
      if (isCeiling) return (xInitialValue + onOffXRotationCeilingDelta) % 360;
      if (isFloor) return (xInitialValue + onOffXRotationFloorDelta) % 360;
      // isWall
      return (xInitialValue + onOffXRotationWallDelta) % 360;
    };
    prov.getVariantBuilder(block)
      .forAllStates(state -> ConfiguredModel.builder()
        .modelFile(modelFunc.apply(state))
        .rotationX(rotationOnXAxis.apply(state))
        .rotationY(rotationOnYAxis.apply(state))
        .build()
      );
    return null;
  }

  public static final BlockEntry<ManasteelManaductBlock> MANASTEEL_MANADUCT_BLOCK = registerManaduct(
    ManasteelManaductBlock.NAME, "Manasteel Manaduct", Tags.Blocks.TIER_1, ManasteelManaductBlock::new);

  public static final BlockEntry<TerrasteelManaductBlock> TERRASTEEL_MANADUCT_BLOCK = registerManaduct(
    TerrasteelManaductBlock.NAME, "Terrasteel Manaduct", Tags.Blocks.TIER_2, TerrasteelManaductBlock::new);

  public static final BlockEntry<ElementiumManaductBlock> ELEMENTIUM_MANADUCT_BLOCK = registerManaduct(
    ElementiumManaductBlock.NAME, "Elementium Manaduct", Tags.Blocks.TIER_3, ElementiumManaductBlock::new);

  public static final BlockEntry<GaiaManaductBlock> GAIA_MANADUCT_BLOCK = registerManaduct(
    GaiaManaductBlock.NAME, "Gaia Manaduct", Tags.Blocks.TIER_4, GaiaManaductBlock::new);

  public static final BlockEntry<CorruptManaBlock> CORRUPT_MANA_BLOCK = registerManablock(
    CorruptManaBlock.NAME, "Corrupt Mana Block", Tags.Blocks.CORRUPT_MANA, Tags.Blocks.INERT_MANA, CorruptManaBlock::new);

  public static final BlockEntry<PureManaBlock> PURE_MANA_BLOCK = registerManablock(
    PureManaBlock.NAME, "Pure Mana Block", Tags.Blocks.PURE_MANA, Tags.Blocks.INERT_MANA, PureManaBlock::new);

  public static final BlockEntry<RealManaBlock> REAL_MANA_BLOCK = registerManablock(
    RealManaBlock.NAME, "Real Mana Block", Tags.Blocks.REAL_MANA, Tags.Blocks.ACTIVE_MANA, RealManaBlock::new);

  public static final BlockEntry<ManaCasing> MANA_CASING = ManaCasing.registerSelf();

  public static final BlockEntry<BlazuniaFunctionalFlowerBlock> BLAZUNIA_BLOCK = Index.all()
    .block("blazunia", p -> new BlazuniaFunctionalFlowerBlock())
    .lang("Blazunia")
    .transform(axeOrPickaxe())
    .blockstate((c, b) -> b.simpleBlock(c.get(), AssetLookup.standardModel(c, b)))
    .simpleItem()
    .simpleBlockEntity(BlazuniaFunctionalFlowerBlockEntity::new)
    .register();

  public static void register(IEventBus bus) {
    log(l -> l.debug("register blocks"));
  }

  public static JsonElement provideLangEntries() {
    var json = new JsonObject();
    return json;
  }

}