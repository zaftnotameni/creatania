// Ponder scenes for purifying corrupt mana to pure mana

const displayManaMotorFromBlocksScene = (params) => (scene, util) => {

  // const blockId = params.blockId;
  // location of each block in the scene
  const motor = util.grid.at(3, 1, 1);
  const manapool = util.grid.at(0, 1, 1);
  const spark_1 = util.grid.at(3, 2, 1);
  const spark_2 = util.grid.at(0, 2, 1);
  // const agument = util.grid.at(3, 2, 1);

  scene.showBasePlate();
  scene.idle(20);
  scene.world.showSection(motor, Facing.NORTH);
  scene.world.showSection(manapool, Facing.NORTH);
  scene.world.showSection(spark_1, Facing.NORTH);
  scene.world.showSection(spark_2, Facing.NORTH);
  // scene.world.showSection(agument, Facing.NORTH);
  scene.addKeyframe();
  scene.idle(20);


};

onEvent("ponder.registry", event => {
  event
    .create("creatania:mana_motor") // full block id 
    .scene(
      "mana_motor_scene", // unique scene identifier
      "mana_motor!", // scene title
      "creatania:mana_motor", // namespace and path to nbt file inside ponder folder
      displayManaMotorFromBlocksScene({ blockId: "creatania:mana_motor" })
    );

});
