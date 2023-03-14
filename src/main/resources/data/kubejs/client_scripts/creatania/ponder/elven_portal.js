// Ponder scenes for purifying corrupt mana to pure mana

//const displayElvenPortalFromBlocksScene = (params) => (scene, util) => {
//
//  // const blockId = params.blockId;
//  // location of each block in the scene
//  const puredaisy = util.grid.at(2, 1, 2);
//
//
//  scene.showBasePlate();
//  scene.idle(20);
//
//};
//
//onEvent("ponder.registry", event => {
//  event
//    .create("creatania:mana/machines/casing") // full block id
//    .scene(
//      "mana_casing_scene", // unique scene identifier
//      "Pure Daisy doing its magic!", // scene title
//      "creatania:elven_portal", // namespace and path to nbt file inside ponder folder
//      // displayElvenPortalFromBlocksScene({ blockId: "creatania:mana/blocks/corrupt" })
//      displayElvenPortalFromBlocksScene({ blockId: "creatania:mana/machines/casing" })
//    );
//
//});