// Purify Corrupt Mana Liquid

const purifyManaFluidFromBlocksScene = (params) => (scene, util) => {

  const fluidId = params.fluidId;
  // const basin = util.grid.at(1, 2, 1);
  const tank_corrupt = util.grid.at(5, 1, 3);
  const shaft = util.grid.at(5 ,2 ,3);
  const tank_pure = util.grid.at(5, 3, 3);
  const pump_1 = util.grid.at(4, 1, 3);
  const pump_2 = util.grid.at(4, 3, 3);
  const cog = util.grid.at(4, 2, 3);
  const pipe_1 = util.grid.at(3, 1, 3);
  const pipe_2 = util.grid.at(3, 3, 3);
  const pipe_3 = util.grid.at(2, 3, 3);
  const smartpipe = util.grid.at(2, 2, 3);
  const glass_1 = util.grid.at(2, 1, 2);
  const glass_2 = util.grid.at(2, 1, 4);
  const glass_3 = util.grid.at(1, 1, 3);
  const fluid_mana = util.grid.at(2, 1, 3);
  const puredaisy = util.grid.at(1, 1, 2);

  scene.showBasePlate();
  scene.idle(20);
  
  scene.world.replaceBlocks([2, 1, 3, 2, 1, 3], "creatania:corrupt_mana", false);
  scene.world.showSection(tank_corrupt, Facing.NORTH);
  scene.world.showSection(pump_1, Facing.NORTH);
  scene.world.showSection(pump_2, Facing.NORTH);
  scene.world.showSection(pipe_1, Facing.NORTH);
  scene.world.showSection(pipe_2, Facing.NORTH);
  scene.world.showSection(pipe_3, Facing.NORTH);
  scene.world.showSection(glass_1, Facing.NORTH);
  scene.world.showSection(glass_2, Facing.NORTH);
  scene.world.showSection(glass_3, Facing.NORTH);
  scene.world.showSection(fluid_mana, Facing.NORTH);
  scene.world.showSection(puredaisy, Facing.NORTH);
  scene.world.showSection(cog, Facing.NORTH);
  scene.world.showSection(shaft, Facing.NORTH);
  scene.world.showSection(tank_pure, Facing.NORTH);
  scene.world.showSection(smartpipe, Facing.NORTH);


  scene.idle(20);
  scene.addKeyframe();
  scene.text(60, "Corrupted Mana Fluid is also purified via Pure Daisy", offsetCenterOf(util)(fluid_mana));
  scene.idle(70);

  scene.world.replaceBlocks([2, 1, 3, 2, 1, 3], "creatania:pure_mana", true);
  scene.text(80, "and the Corrupt Mana Blocks become Pure!", offsetCenterOf(util)(fluid_mana));
  scene.idle(80);
  
  scene.addKeyframe();
  scene.text(60, "Filter the Smart Fluid Pipe for Pure Mana Fluid", offsetCenterOf(util)(smartpipe));
  scene.idle(70);

  scene.text(60, "When purified, the Pure Mana Fluid is pumped away", offsetCenterOf(util)(fluid_mana));
  scene.idle(70);

  scene.world.replaceBlocks([2, 1, 3, 2, 1, 3], "minecraft:air", false);
  scene.idle(20);
  scene.world.setBlock(fluid_mana, "creatania:corrupt_mana", false);

};

onEvent("ponder.registry", event => {
  event
    .create("creatania:corrupt_mana_bucket") // full block id
    .scene(
      "purify_liquid_mana_scene", // unique scene identifier
      "Purify Corrupt Mana Liquid", // scene title
      // "creatania:mana_purify/test_1", // namespace and path to nbt file
      "creatania:mana_purify/purify_liquid_mana", // namespace and path to nbt file
      // "creatania:mana_generator/mana_generator",
      purifyManaFluidFromBlocksScene({ fluidId: "creatania:corrupt_mana_bucket" })
    );

});
