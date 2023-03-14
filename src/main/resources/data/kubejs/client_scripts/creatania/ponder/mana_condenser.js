// Ponder scene for creatania:mana_condenser

const displayManaCondenserFromBlocksScene = (params) => (scene, util) => {

  // const blockId = params.blockId;
  // location of each block in the scene
  const barrel = util.grid.at(2, 1, 2);
  const condenser = util.grid.at(2, 2, 2);
  const shaft = util.grid.at(1, 2, 2);

  // fix the mana condenser cardinal facing direction
  scene.world.modifyBlock(condenser, blockstate => blockstate.with("facing", "west"), false);

  scene.showBasePlate();
  scene.idle(20);

  scene.world.showSection(condenser, Facing.WEST);
  scene.idle(20);

  scene.world.showSection(shaft, Facing.EAST);
  scene.idle(20);

  scene.text(80, "Providing rotational force to Mana Condensers produces Corrupt Inert Mana Blocks", offsetCenterOf(util)(shaft));
  scene.idle(90);

  scene.world.showSection(barrel, Facing.NORTH);
  scene.idle(20);

  scene.text(80, "Place Mana Condenser over any inventory with space to collect mana blocks", offsetCenterOf(util)(barrel));

};

const displayManaCondenserFromBlocksScene2 = (params) => (scene, util) => {

  // const blockId = params.blockId;
  // location of each block in the scene
  const belt_shaft1 = util.grid.at(2, 1, 2);
  const belt_shaft2 = util.grid.at(4, 1, 2);
  const belt = util.grid.at(3, 1, 2);
  const condenser = util.grid.at(2, 2, 2);
  const shaft = util.grid.at(1, 2, 2);
  const barrel = util.grid.at(5, 2, 2);
  const funnel = util.grid.at(4, 2, 2);
  // const mana_block = util.grid.at(4, 1, 2);
  const outputCondenser = util.grid.at(2, 1, 2);

  // fix the mana condenser cardinal facing direction
  scene.world.modifyBlock(condenser, blockstate => blockstate.with("facing", "west"), false);

  scene.showBasePlate();
  scene.idle(20);
  scene.world.showSection(condenser, Facing.WEST);
  scene.world.showSection(shaft, Facing.EAST);
  scene.idle(20);
  scene.world.showSection(belt_shaft1, Facing.NORTH);
  scene.world.showSection(belt, Facing.NORTH);
  scene.world.showSection(belt_shaft2, Facing.NORTH);
  scene.idle(20);

  scene.world.showSection(barrel, Facing.NORTH);
  scene.world.showSection(funnel, Facing.NORTH);
  scene.idle(10);

  scene.addKeyframe();
  scene.text(80, "Mana Condensers can also be placed over Mechanical Belts", offsetCenterOf(util)(belt));
  
  // removed mana block from belt
  scene.world.removeItemsFromBelt(belt_shaft2);
  scene.idle(10);


  // add a few more mana blocks
  for (let index = 0; index < 20; index++) {
    if (index < 20) {
      scene.world.createItemOnBeltLike(outputCondenser, Direction.UP, "creatania:mana/blocks/corrupt");
      scene.idle(10);
      // removed mana block from belt
      scene.world.removeItemsFromBelt(belt_shaft2);
    }
  }

  scene.addKeyframe();
  scene.idle(20);
  scene.text(120, "If the inventory or belt is full, then the Mana Condensers will stop sending out blocks.", offsetCenterOf(util)(barrel));

};

onEvent("ponder.registry", event => {
  event
    .create("creatania:mana_condenser") // full block id
    .scene(
      "mana_condenser_scene", // unique scene identifier
      "Sucking Mana from Thin Air", // scene title
      "creatania:mana_condenser/mana_condenser",
      displayManaCondenserFromBlocksScene({ blockId: "creatania:mana_condenser" })
    );

  event
    .create("creatania:mana_condenser") // full block id
    .scene(
      "mana_condenser_belt_scene", // unique scene identifier
      "Automating Mana Condensing", // scene title
      "creatania:mana_condenser/mana_condenser_belt",
      displayManaCondenserFromBlocksScene2({ blockId: "creatania:mana_condenser" })
    );

});
