// Ponder scenes for purifying corrupt mana to pure mana

const displayManaDuctsFromBlocksScene = (params) => (scene, util) => {

  // const blockId = params.blockId;
  // location of each block in the scene
  const shaft = util.grid.at(2, 2, 2);
  const omnibox = util.grid.at(2, 3, 2);
  const generator_1 = util.grid.at(1, 3, 2);
  const generator_2 = util.grid.at(2, 3, 3);
  const generator_3 = util.grid.at(3, 3, 2);
  const generator_4 = util.grid.at(2, 3, 1);

  const plate = util.grid.at(2, 4, 2);
  const duct_1 = util.grid.at(1, 4, 2);
  const duct_2 = util.grid.at(2, 4, 3);
  const duct_3 = util.grid.at(3, 4, 2);
  const duct_4 = util.grid.at(2, 4, 1);

  const tank = util.grid.at(0, 1, 2);
  const pump = util.grid.at(0, 2, 2);
  const pipe_1 = util.grid.at(0, 3, 2);
  const pipe_2 = util.grid.at(0, 3, 1);
  const pipe_3 = util.grid.at(0, 3, 3);
  const pipe_4 = util.grid.at(1, 3, 3);
  const pipe_5 = util.grid.at(1, 3, 4);
  const pipe_6 = util.grid.at(2, 3, 4);
  const pipe_7 = util.grid.at(3, 3, 4);
  const pipe_8 = util.grid.at(3, 3, 3);
  const pipe_9 = util.grid.at(4, 3, 1);
  const pipe_10 = util.grid.at(4, 3, 2);
  const pipe_11 = util.grid.at(4, 3, 3);
  const pipe_12 = util.grid.at(3, 3, 1);
  const pipe_13 = util.grid.at(3, 3, 0);
  const pipe_14 = util.grid.at(2, 3, 0);
  const pipe_15 = util.grid.at(1, 3, 0);
  const pipe_16 = util.grid.at(1, 3, 1);

  scene.showBasePlate();
  scene.idle(20);

  scene.world.showSection(shaft, Facing.UP);
  scene.world.showSection(omnibox, Facing.DOWN);
  scene.idle(20);

  scene.world.modifyBlock(generator_1, facing("east"), false);
  scene.world.modifyBlock(generator_2, facing("north"), false);
  scene.world.modifyBlock(generator_3, facing("west"), false);
  scene.world.modifyBlock(generator_4, facing("south"), false);

  scene.world.showSection(generator_1, Facing.EAST);
  scene.world.showSection(generator_2, Facing.NORTH);
  scene.world.showSection(generator_3, Facing.WEST);
  scene.world.showSection(generator_4, Facing.SOUTH);
  scene.idle(20);

  scene.world.showSection(tank, Facing.NORTH);
  scene.world.showSection(pump, Facing.NORTH);
  scene.idle(20);

  scene.world.modifyBlock(pipe_1, (blockstate) => {
    return blockstate.with("east", true);
  }, false);
  scene.world.modifyBlock(pipe_6, (blockstate) => {
    return blockstate.with("north", true);
  }, false);
  scene.world.modifyBlock(pipe_10, (blockstate) => {
    return blockstate.with("west", true);
  }, false);
  scene.world.modifyBlock(pipe_14, (blockstate) => {
    return blockstate.with("south", true);
  }, false);

  scene.addKeyframe();
  scene.world.showSection(pipe_1, Facing.EAST);
  scene.world.showSection(pipe_2, Facing.EAST);
  scene.world.showSection(pipe_3, Facing.EAST);
  scene.world.showSection(pipe_4, Facing.NORTH);
  scene.world.showSection(pipe_5, Facing.NORTH);
  scene.world.showSection(pipe_6, Facing.NORTH);
  scene.world.showSection(pipe_7, Facing.NORTH);
  scene.world.showSection(pipe_8, Facing.NORTH);
  scene.world.showSection(pipe_9, Facing.WEST);
  scene.world.showSection(pipe_10, Facing.WEST);
  scene.world.showSection(pipe_11, Facing.WEST);
  scene.world.showSection(pipe_12, Facing.SOUTH);
  scene.world.showSection(pipe_13, Facing.SOUTH);
  scene.world.showSection(pipe_14, Facing.SOUTH);
  scene.world.showSection(pipe_15, Facing.SOUTH);
  scene.world.showSection(pipe_16, Facing.SOUTH);
  scene.idle(20);

  scene.addKeyframe();
  scene.world.showSection(plate, Facing.DOWN);
  scene.idle(20);
  scene.world.showSection(duct_1, Facing.EAST);
  scene.world.showSection(duct_2, Facing.NORTH);
  scene.world.showSection(duct_3, Facing.WEST);
  scene.world.showSection(duct_4, Facing.SOUTH);

};

onEvent("ponder.registry", event => {
  event
    .create("creatania:manaducts/manasteel") // full block id 
    .scene(
      "mana_ducts_scene", // unique scene identifier
      "mana_ducts!", // scene title
      "creatania:mana_ducts", // namespace and path to nbt file inside ponder folder
      displayManaDuctsFromBlocksScene({ blockId: "creatania:manaducts/manasteel" })
    );

});
