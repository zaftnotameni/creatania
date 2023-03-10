// Ponder scenes for purifying corrupt mana to pure mana

const displayManaMotorFromBlocksScene = (params) => (scene, util) => {

  // const blockId = params.blockId;
  // location of each block in the scene
  const tank = util.grid.at(0, 1, 2);

  scene.showBasePlate();
  scene.idle(20);
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
