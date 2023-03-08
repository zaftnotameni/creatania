// Ponder scenes for purifying corrupt mana to pure mana

const displayManaPurifyBlockFromBlocksScene = (params) => (scene, util) => {

  // const blockId = params.blockId;
  // location of each block in the scene
  const puredaisy = util.grid.at(2, 1, 2);
  const manacorrupt1 = util.grid.at(3, 1, 1);


  scene.showBasePlate();
  scene.idle(20);

};

onEvent("ponder.registry", event => {
  event
    // .create("creatania:mana/blocks/corrupt") // full block id  
    .create("botania:pure_daisy") // full block id 
    .scene(
      "mana_purify_block_scene", // unique scene identifier
      "Pure Daisy doing its magic!", // scene title
      "creatania:blazetunia", // namespace and path to nbt file inside ponder folder
      // displayManaPurifyBlockFromBlocksScene({ blockId: "creatania:mana/blocks/corrupt" })
      displayManaPurifyBlockFromBlocksScene({ blockId: "creatania:blazetunia" })
    );

});
