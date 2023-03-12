// ponder scene for blazunia

const displayBlazuniaFromBlocksScene = (params) => (scene, util) => {

  // location of each block in the scene
  const blazunia = util.grid.at(2, 1, 2);
  const manapool = util.grid.at(4, 1, 2);
  const pylon_1 = util.grid.at(3, 1, 2);
  const pylon_2 = util.grid.at(1, 1, 2);
  const pylon_3 = util.grid.at(2, 1, 1);
  const pylon_4 = util.grid.at(2, 1, 3);
  const blaze_1 = util.grid.at(1, 3, 1);
  const blaze_2 = util.grid.at(1, 3, 2);
  const blaze_3 = util.grid.at(1, 3, 3);
  const blaze_4 = util.grid.at(2, 3, 1);
  const blaze_5 = util.grid.at(2, 3, 2);
  const blaze_6 = util.grid.at(2, 3, 3);
  const blaze_7 = util.grid.at(3, 3, 1);
  const blaze_8 = util.grid.at(3, 3, 2);
  const blaze_9 = util.grid.at(3, 3, 3);
  const tank_1 = util.grid.at(1, 4, 1);
  const tank_2 = util.grid.at(1, 4, 2);
  const tank_3 = util.grid.at(1, 4, 3);
  const tank_4 = util.grid.at(2, 4, 1);
  const tank_5 = util.grid.at(2, 4, 2);
  const tank_6 = util.grid.at(2, 4, 3);
  const tank_7 = util.grid.at(3, 4, 1);
  const tank_8 = util.grid.at(3, 4, 2);
  const tank_9 = util.grid.at(3, 4, 3);
  const arrBLAZE_BURNERS = [blaze_1, blaze_2, blaze_3, blaze_4, blaze_5, blaze_6, blaze_7, blaze_8, blaze_9];
  
  scene.showBasePlate();
  scene.text(60, "What if a Mana functional flower could power Blaze Burners?");
  scene.idle(70);

  /* blazeState = (block, state, facing)
    @param block variable: 
    @param state string: "smouldering", "kindled", "seething"
    @param state string: "north", "south", "east", "west", "up", "down"
  */
  const blazeState = (block, state, facing) => {
    scene.world.modifyBlock(block, (blockstate) => {
      return blockstate.with("blaze", state).with("facing", facing);
    }, false);
  };


  arrBLAZE_BURNERS.forEach(block => {
    blazeState(block, "smouldering", "west");
  });

  scene.world.showSection(blaze_1, Facing.DOWN);
  scene.world.showSection(blaze_2, Facing.DOWN);
  scene.world.showSection(blaze_3, Facing.DOWN);
  scene.world.showSection(blaze_4, Facing.DOWN);
  scene.world.showSection(blaze_5, Facing.DOWN);
  scene.world.showSection(blaze_6, Facing.DOWN);
  scene.world.showSection(blaze_7, Facing.DOWN);
  scene.world.showSection(blaze_8, Facing.DOWN);
  scene.world.showSection(blaze_9, Facing.DOWN);
  scene.idle(20);

  scene.world.showSection(tank_1, Facing.DOWN);
  scene.world.showSection(tank_2, Facing.DOWN);
  scene.world.showSection(tank_3, Facing.DOWN);
  scene.world.showSection(tank_4, Facing.DOWN);
  scene.world.showSection(tank_5, Facing.DOWN);
  scene.world.showSection(tank_6, Facing.DOWN);
  scene.world.showSection(tank_7, Facing.DOWN);
  scene.world.showSection(tank_8, Facing.DOWN);
  scene.world.showSection(tank_9, Facing.DOWN);
  scene.idle(20);

  scene.text(80, "Let's meet the Blaznia. It's a firey one!", offsetCenterOf(util)(blazunia));
  scene.world.setBlock(blazunia, "creatania:blazunia", false);
  scene.world.showSection(blazunia, Facing.EAST);
  scene.idle(70);

  scene.addKeyframe();
  scene.text(40, "Just add Mana", offsetCenterOf(util)(manapool));
  scene.world.showSection(manapool, Facing.WEST);
  scene.idle(10);

  // ignite blazunia
  scene.world.modifyBlock(blazunia, (blockstate) => {
    return blockstate.with("has_mana_source", true).with("is_superhot", false);
  }, false);

  // display ignited blazunia
  scene.world.modifyTileNBT(blazunia, (nbt) => {
    nbt.floating = false;
    nbt.mana = 80;
    nbt.pool = { x: 2, y: 1, z: 1 };
  });

  // blaze burners changed to kindled
  arrBLAZE_BURNERS.forEach(block => {
    blazeState(block, "kindled", "west");
  });
  scene.text(60, "Burn baby burn ... watch them burn!");
  scene.idle(110);

  scene.addKeyframe();
  scene.text(60, "Super Heated Wrath of Hades!");
  scene.world.showSection(pylon_1, Facing.UP);
  scene.world.showSection(pylon_2, Facing.UP);
  scene.world.showSection(pylon_3, Facing.UP);
  scene.world.showSection(pylon_4, Facing.UP);
  scene.idle(10);

  // blaze burners changed to seething
  arrBLAZE_BURNERS.forEach(block => {
    blazeState(block, "seething", "west");
  });
  scene.idle(60);

};

onEvent("ponder.registry", event => {
  event
    .create("creatania:blazunia") // full block id
    .scene(
      "blazunia_block_scene", // unique scene identifier
      "Blazunia!", // scene title
      "creatania:blazunia", // namespace and path to nbt file inside ponder folder
      displayBlazuniaFromBlocksScene({ blockId: "creatania:blazunia" })
    );
});
