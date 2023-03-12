// Ponder scenes for purifying corrupt mana to pure mana

const displayManaMotorFromBlocksScene = (params) => (scene, util) => {

  // const blockId = params.blockId;
  // location of each block in the scene
  const motor = util.grid.at(3, 1, 1);
  const manapool = util.grid.at(0, 1, 1);
  const spark_1 = util.grid.at(3, 2, 1);
  const spark_2 = util.grid.at(0, 2, 1);

  scene.showBasePlate();
  scene.idle(20);
  scene.text(60, "The mysterious Mana Motor is a puzzle, but somehow runs on Mana", offsetTopOf(util)(motor));
  scene.world.showSection(motor, Facing.NORTH);
  scene.world.showSection(manapool, Facing.NORTH);
  scene.idle(70);

  scene.addKeyframe();
  scene.text(70, "Add a Spark over the Mana Motor - unfortunately sparks are shy and won't let us show them", offsetTopOf(util)(spark_1));
  scene.idle(80);
  scene.world.showSection(spark_1, Facing.NORTH);

  scene.text(40, "And a Spark plus a Recessive Augment over the Mana Pool", offsetTopOf(util)(spark_2));
  scene.idle(50);
  scene.world.showSection(spark_2, Facing.NORTH);
  scene.addKeyframe();
  scene.idle(20);

  scene.text(70, "The Mana Motor speed is controlled with your mouse wheel over the center of this panel", offsetCenterOf(util)(motor));



  /* 
  /setblock -79 -58 -37 creatania:mana_motor[axis_along_first=true,facing=north]{Network:{AddedCapacity:1.0f,Capacity:64.0f,Id:-21440476889146L,Size:2,Stress:0.0f},ScrollValue:64,Speed:-64.0f,active:1b,duct:0b,mana:99992,manaPerTick:8}
  */

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
