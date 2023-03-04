// Purify Liquid Corrupt Mana

const purifyManaFluidFromBlocksScene = (params) => (scene, util) => {

  const fluidId = params.fluidId;
  // const basin = util.grid.at(1, 2, 1); 

  scene.showBasePlate();
  scene.idle(20);

};

onEvent("ponder.registry", event => {
  event
    .create("creatania:corrupt_mana_bucket") // full block id
    .scene(
      "purify_liquid_mana_scene", // unique scene identifier
      "Purify Liquid Corrupt Mana", // scene title
      // "creatania:mana_purify/test_1", // namespace and path to nbt file
      "creatania:mana_purify/purify_liquid_mana", // namespace and path to nbt file
      // "creatania:mana_generator/mana_generator",
      purifyManaFluidFromBlocksScene({ fluidId: "creatania:corrupt_mana_bucket" })
    );

});
