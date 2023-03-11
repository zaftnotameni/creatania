// Ponder scenes for purifying corrupt mana to pure mana

const displayOmniboxFromBlocksScene = (params) => (scene, util) => {

  // const blockId = params.blockId;
  // location of each block in the scene
  const omnibox = util.grid.at(1, 2, 1);
  const cog_1 = util.grid.at(1, 1, 1);
  const cog_2 = util.grid.at(1, 2, 0);
  const cog_3 = util.grid.at(1, 2, 2);
  const cog_4 = util.grid.at(2, 2, 1);
  const cog_5 = util.grid.at(0, 2, 1);
  const cog_6 = util.grid.at(1, 3, 1);


  scene.showBasePlate();
  scene.idle(20);

  scene.world.modifyBlock(omnibox, (bs) => bs.with("facing", "south").with("axis", "z"), false);
  scene.world.showSection(omnibox, Facing.NORTH);
  scene.text(80, "The Omnibox has six synchronized input/output shafts.", offsetCenterOf(util)(omnibox));
  scene.idle(90);
  
  scene.text(80, "All opposing shafts rotate in the same direction.", offsetCenterOf(util)(cog_6));
  scene.world.showSection(cog_1, Facing.UP);
  scene.world.showSection(cog_6, Facing.DOWN);
  scene.idle(90);

  scene.world.showSection(cog_2, Facing.SOUTH);
  scene.world.showSection(cog_3, Facing.NORTH);
  scene.idle(30);

  scene.world.showSection(cog_4, Facing.WEST);
  scene.world.showSection(cog_5, Facing.EAST);
  scene.idle(30);


};

onEvent("ponder.registry", event => {
  event
    .create("creatania:omnibox") // full block id 
    .scene(
      "omnibox_scene", // unique scene identifier
      "Omnibox", // scene title
      "creatania:omnibox", // namespace and path to nbt file inside ponder folder
      displayOmniboxFromBlocksScene({ blockId: "creatania:omnibox" })
    );

});
