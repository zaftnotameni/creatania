// Ponder scenes for purifying corrupt mana to pure mana

const displayBlazuniaFromBlocksScene = (params) => (scene, util) => {

  // const blockId = params.blockId;
  // location of each block in the scene
  const blazunia = util.grid.at(1, 1, 1);
  const manapool = util.grid.at(2, 1, 1);
  const blaze_1 = util.grid.at(0, 3, 0);
  const blaze_2 = util.grid.at(0, 3, 1);
  const blaze_3 = util.grid.at(0, 3, 2);
  const blaze_4 = util.grid.at(1, 3, 0);
  const blaze_5 = util.grid.at(1, 3, 1);
  const blaze_6 = util.grid.at(1, 3, 2);
  const blaze_7 = util.grid.at(2, 3, 0);
  const blaze_8 = util.grid.at(2, 3, 1);
  const blaze_9 = util.grid.at(2, 3, 2);
  const tank_1 = util.grid.at(0, 4, 0);
  const tank_2 = util.grid.at(0, 4, 1);
  const tank_3 = util.grid.at(0, 4, 2);
  const tank_4 = util.grid.at(1, 4, 0);
  const tank_5 = util.grid.at(1, 4, 1);
  const tank_6 = util.grid.at(1, 4, 2);
  const tank_7 = util.grid.at(2, 4, 0);
  const tank_8 = util.grid.at(2, 4, 1);
  const tank_9 = util.grid.at(2, 4, 2);

  scene.showBasePlate();
  scene.idle(20);

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

  scene.world.showSection(manapool, Facing.WEST);
  scene.idle(20);

  scene.world.setBlock(blazunia, "creatania:blazunia", false);
  scene.world.showSection(blazunia, Facing.EAST);
  scene.idle(20);

  /*
    F3+i Info:
    /setblock -50 -58 -32 creatania:blazunia[has_mana_source=true,is_superhot=false]{floating:0b,mana:80,pool:{x:-49,y:-58,z:-32}}
  */
  scene.world.modifyTileNBT(blazunia, (nbt) => {
    nbt.has_mana_source = true;
    nbt.is_superhot = false;
    nbt.floating = false;
    nbt.mana = 80;
    nbt.pool = {};
  });

  /*
    modify blaze burners 1, 2, 3, 4, 7
    F3+i Info:
  /setblock -50 -56 -31 create:blaze_burner[blaze=kindled,facing=west]{burnTimeRemaining:661,fuelLevel:1}
  */
  // scene.world.modifyTileNBT(blaze_1, (nbt) => {
  //   blaze = kindled;
  //   nbt.burnTimeRemaining = 1000;
  //   nbt.fuelLevel = 1;
  // });
};

onEvent("ponder.registry", event => {
  event
    .create("creatania:blazunia") // full block id
    .scene(
      "blazunia_block_scene", // unique scene identifier
      "blazunia!", // scene title
      "creatania:blazunia", // namespace and path to nbt file inside ponder folder
      // displayBlazuniaFromBlocksScene({ blockId: "creatania:mana/blocks/corrupt" })
      displayBlazuniaFromBlocksScene({ blockId: "creatania:blazunia" })
    );

});
