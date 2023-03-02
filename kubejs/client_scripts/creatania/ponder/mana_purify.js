// Ponder scenes for purifying corrupt mana to pure mana

const displayManaPurifyBlockFromBlocksScene = (params) => (scene, util) => {

    // const blockId = params.blockId;
    // location of each block in the scene
    const puredaisy = util.grid.at(2, 1, 2);
    const manacorrupt1 = util.grid.at(3, 1, 1);
    const manacorrupt2 = util.grid.at(2, 1, 1);
    const manacorrupt3 = util.grid.at(1, 1, 1);
    const manacorrupt4 = util.grid.at(3, 1, 2);
    const manacorrupt5 = util.grid.at(1, 1, 2);
    const manacorrupt6 = util.grid.at(1, 1, 3);
    const manacorrupt7 = util.grid.at(3, 1, 3);
    const manacorrupt8 = util.grid.at(2, 1, 3);
    
    // const manapure1 = util.grid.at(3, 1, 1);
    // const manapure2 = util.grid.at(2, 1, 1);
    // const manapure3 = util.grid.at(1, 1, 1);
    // const manapure4 = util.grid.at(3, 1, 2);
    // const manapure5 = util.grid.at(1, 1, 2);
    // const manapure6 = util.grid.at(1, 1, 3);
    // const manapure7 = util.grid.at(3, 1, 3);
    // const manapure8 = util.grid.at(2, 1, 3);  
    
    scene.showBasePlate();
    scene.idle(20);
    scene.world.setBlock(puredaisy, "botania:pure_daisy", true);
    scene.world.showSection(puredaisy, Facing.DOWN);
    
    scene.text(80, "Surround a Pure Daisy with Corrupt Mana Blocks", offsetCenterOf(util)(puredaisy));
    scene.idle(60);
    
    scene.world.showSection(manacorrupt1, Facing.SOUTH);
    scene.world.showSection(manacorrupt2, Facing.SOUTH);
    scene.world.showSection(manacorrupt3, Facing.SOUTH);
    scene.world.showSection(manacorrupt4, Facing.SOUTH);
    scene.world.showSection(manacorrupt5, Facing.NORTH);
    scene.world.showSection(manacorrupt6, Facing.NORTH);
    scene.world.showSection(manacorrupt7, Facing.NORTH);
    scene.world.showSection(manacorrupt8, Facing.NORTH);
    
    // scene.idle(80);
    // scene.world.showSection(manapure1, Facing.SOUTH);
    // scene.world.showSection(manapure2, Facing.SOUTH);
    // scene.world.showSection(manapure3, Facing.SOUTH);
    // scene.world.showSection(manapure4, Facing.SOUTH);
    // scene.world.showSection(manapure5, Facing.NORTH);
    // scene.world.showSection(manapure6, Facing.NORTH);
    // scene.world.showSection(manapure7, Facing.NORTH);
    // scene.world.showSection(manapure8, Facing.NORTH);
    scene.idle(20);
    
    
    
  };
  
  onEvent("ponder.registry", event => {
    event
    // .create("creatania:mana/blocks/corrupt") // full block id  
    .create("botania:pure_daisy") // full block id 
      .scene(
        "mana_purify_block_scene", // unique scene identifier
        "Pure Daisy doing its magic!", // scene title
        "creatania:mana_purify/manablocks_puredaisy", // namespace and path to nbt file inside ponder folder
        // displayManaPurifyBlockFromBlocksScene({ blockId: "creatania:mana/blocks/corrupt" })
        displayManaPurifyBlockFromBlocksScene({ blockId: "botania:pure_daisy" })
      );
  
  });
  