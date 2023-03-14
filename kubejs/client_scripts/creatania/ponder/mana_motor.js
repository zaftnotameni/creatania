// Ponder scenes for Mana Motor

const displayManaMotorFromBlocksScene = (params) => (scene, util) => {
  
  // location of each block in the scene
  const motor = util.grid.at(3, 1, 1);
  const cogPos = util.grid.at(3, 1, 2);
  const manapool = util.grid.at(0, 1, 1);
  const spark_1 = util.grid.at(3, 2, 1);
  const spark_2 = util.grid.at(0, 2, 1);
  const inputVec = util.vector.of(3.5, 1.5, 1);

  scene.world.modifyBlock(motor, (blockstate) => {
    return blockstate.with("facing", "south");
  }, false);

  scene.showBasePlate();
  scene.idle(20);
  scene.text(60, "The mysterious Mana Motor is a puzzle, but somehow runs on Mana", offsetCenterOf(util)(motor));
  scene.world.showSection(motor, Facing.NORTH);
  scene.world.showSection(manapool, Facing.NORTH);
  scene.idle(20);
  scene.world.showSection(cogPos, Facing.NORTH);
  scene.idle(50);

  scene.addKeyframe();
  scene.text(70, "Add a Spark over the Mana Motor - unfortunately sparks are shy and won't let us show them", offsetCenterOf(util)(spark_1));
  scene.idle(80);
  scene.world.showSection(spark_1, Facing.NORTH);

  scene.addKeyframe();
  scene.text(40, "And a Spark plus a Recessive Augment over the Mana Pool", offsetCenterOf(util)(spark_2));
  scene.world.showSection(spark_2, Facing.NORTH);
  scene.idle(50);

  scene.addKeyframe();
  scene.overlay.showFilterSlotInput(inputVec, 200);
  scene.overlay.showText(100)
    .placeNearTarget()
    .pointAt(inputVec)
    .text("The Mana Motor speed is controlled with your mouse wheel over the center of this panel");
  scene.idle(40);

  // let's speed up the motor
  scene.world.multiplyKineticSpeed(util.select.fromTo(3, 1, 2, 3, 1, 2), 4);
  scene.effects.rotationSpeedIndicator(cogPos);
  scene.idle(55);
  // slow the motor
  scene.world.multiplyKineticSpeed(util.select.fromTo(3, 1, 2, 3, 1, 2), 0.05);
  scene.effects.rotationSpeedIndicator(cogPos);
  scene.idle(55);
  // reverse the motor
  scene.addKeyframe();
  scene.world.multiplyKineticSpeed(util.select.fromTo(3, 1, 2, 3, 1, 2), -2);
  scene.effects.rotationSpeedIndicator(cogPos);
  scene.idle(55);
  // speed up in reverse
  scene.world.multiplyKineticSpeed(util.select.fromTo(3, 1, 2, 3, 1, 2), 4);
  scene.effects.rotationSpeedIndicator(cogPos);
  scene.idle(55);

};

onEvent("ponder.registry", event => {
  event
    .create("creatania:mana_motor") // full block id 
    .scene(
      "mana_motor_scene", // unique scene identifier
      "Mana Motor, a motor that runs on Mana?", // scene title
      "creatania:mana_motor", // namespace and path to nbt file inside ponder folder
      displayManaMotorFromBlocksScene({ blockId: "creatania:mana_motor" })
    );

});
