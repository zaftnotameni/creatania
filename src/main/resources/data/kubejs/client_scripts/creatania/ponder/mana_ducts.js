// Ponder scenes for purifying corrupt mana to pure mana

const displayManaDuctsFromBlocksScene = (params) => (scene, util) => {

    // const blockId = params.blockId;
    // location of each block in the scene
    const lapis_1 = util.grid.at(1, 1, 2);
    const lapis_2 = util.grid.at(2, 1, 3);
    const lapis_3 = util.grid.at(3, 1, 2);
    const lapis_4 = util.grid.at(2, 1, 1);
    const livingrock_0 = util.grid.at(2, 1, 2);
    const livingrock_4 = util.grid.at(1, 1, 3);
    const livingrock_8 = util.grid.at(3, 1, 3);
    const livingrock_12 = util.grid.at(3, 1, 1);
    const livingrock_16 = util.grid.at(1, 1, 1);
    const plate_0 = util.grid.at(2, 2, 2);
  
    // const shaft = util.grid.at(2, 2, 2);
    const omnibox = util.grid.at(2, 3, 2);
    const generator_1 = util.grid.at(1, 3, 2);
    const generator_2 = util.grid.at(2, 3, 3);
    const generator_3 = util.grid.at(3, 3, 2);
    const generator_4 = util.grid.at(2, 3, 1);
  
    const plate = util.grid.at(2, 4, 2);
    const duct_1 = util.grid.at(1, 4, 2);
    const duct_2 = util.grid.at(2, 4, 3);
    const duct_3 = util.grid.at(3, 4, 2);
    const duct_4 = util.grid.at(2, 4, 1);
  
    const tank = util.grid.at(0, 1, 2);
    const pump = util.grid.at(0, 2, 2);
    const pipe_1 = util.grid.at(0, 3, 2);
    const pipe_2 = util.grid.at(0, 3, 1);
    const pipe_3 = util.grid.at(0, 3, 3);
    const pipe_4 = util.grid.at(1, 3, 3);
    const pipe_5 = util.grid.at(1, 3, 4);
    const pipe_6 = util.grid.at(2, 3, 4);
    const pipe_7 = util.grid.at(3, 3, 4);
    const pipe_8 = util.grid.at(3, 3, 3);
    const pipe_9 = util.grid.at(4, 3, 1);
    const pipe_10 = util.grid.at(4, 3, 2);
    const pipe_11 = util.grid.at(4, 3, 3);
    const pipe_12 = util.grid.at(3, 3, 1);
    const pipe_13 = util.grid.at(3, 3, 0);
    const pipe_14 = util.grid.at(2, 3, 0);
    const pipe_15 = util.grid.at(1, 3, 0);
    const pipe_16 = util.grid.at(1, 3, 1);
  
    scene.showBasePlate();
    scene.idle(20);
    /*************************************  
    * Start of Botania terra plate setup *
    **************************************/
  
    scene.addKeyframe();
  
    // setting blocks for terra plate setup
    scene.world.setBlock(lapis_1, "minecraft:lapis_block", false);
    scene.world.setBlock(lapis_2, "minecraft:lapis_block", false);
    scene.world.setBlock(lapis_3, "minecraft:lapis_block", false);
    scene.world.setBlock(lapis_4, "minecraft:lapis_block", false);
    scene.world.setBlock(livingrock_0, "botania:livingrock", false);
    scene.world.setBlock(livingrock_4, "botania:livingrock", false);
    scene.world.setBlock(livingrock_8, "botania:livingrock", false);
    scene.world.setBlock(livingrock_12, "botania:livingrock", false);
    scene.world.setBlock(livingrock_16, "botania:livingrock", false);
    scene.world.setBlock(plate_0, "botania:terra_plate", false);
  
    scene.world.showSection(lapis_1, Facing.EAST);
    scene.world.showSection(lapis_2, Facing.NORTH);
    scene.world.showSection(lapis_3, Facing.WEST);
    scene.world.showSection(lapis_4, Facing.SOUTH);
    scene.world.showSection(livingrock_0, Facing.UP);
    scene.world.showSection(livingrock_4, Facing.UP);
    scene.world.showSection(livingrock_8, Facing.UP);
    scene.world.showSection(livingrock_12, Facing.UP);
    scene.world.showSection(livingrock_16, Facing.UP);
  
    scene.idle(20);
    scene.world.showSection(plate_0, Facing.DOWN);
  
    scene.text(50, "You know how to set up the Agglomeration Plate", offsetTopOf(util)(livingrock_0));
    scene.idle(60);
    /***********************************      
     * End of Botania terra plate setup *
    ***********************************/
    /***********************************      
     * Start clear the field *
    ************************************/
    scene.text(50, "Let's try something different!");
    scene.idle(60);
    scene.addKeyframe();
  
    scene.world.setBlock(lapis_1, "minecraft:air", true);
    scene.world.setBlock(lapis_2, "minecraft:air", true);
    scene.world.setBlock(lapis_3, "minecraft:air", true);
    scene.world.setBlock(lapis_4, "minecraft:air", true);
    scene.world.setBlock(livingrock_0, "minecraft:air", true);
    scene.world.setBlock(livingrock_4, "minecraft:air", true);
    scene.world.setBlock(livingrock_8, "minecraft:air", true);
    scene.world.setBlock(livingrock_12, "minecraft:air", true);
    scene.world.setBlock(livingrock_16, "minecraft:air", true);
    scene.world.setBlock(plate_0, "minecraft:air", true);
    /***********************************      
     * End clear the field *
     ***********************************/
  
    /***********************************      
     * Start mana duct set up *
    ***********************************/
  
    scene.text(70, "Mana Ducts efficiently transfer Real Mana from Mana Generators to the Agglomeration Plate.");
    scene.idle(80);
  
    // scene.world.showSection(shaft, Facing.UP);
    scene.world.showSection(omnibox, Facing.DOWN);
    scene.text(70, "The Omnibox was designed with Mana Ducts in mind.", offsetCenterOf(util)(omnibox));
    scene.idle(80);
    scene.addKeyframe();
  
    scene.world.modifyBlock(generator_1, facing("east"), false);
    scene.world.modifyBlock(generator_2, facing("north"), false);
    scene.world.modifyBlock(generator_3, facing("west"), false);
    scene.world.modifyBlock(generator_4, facing("south"), false);
    scene.world.showSection(generator_1, Facing.EAST);
    scene.world.showSection(generator_2, Facing.NORTH);
    scene.world.showSection(generator_3, Facing.WEST);
    scene.world.showSection(generator_4, Facing.SOUTH);
    scene.idle(20);
  
    scene.text(60, "Place your Mana Generators around an Omnibox - shaft inward.", offsetTopOf(util)(omnibox));
    scene.idle(70);
  
    scene.world.modifyBlock(pipe_1, (blockstate) => {
        return blockstate.with("east", true).with("down", false);
    }, false);
    scene.world.modifyBlock(pipe_6, (blockstate) => {
        return blockstate.with("north", true).with("down", false);
    }, false);
    scene.world.modifyBlock(pipe_10, (blockstate) => {
        return blockstate.with("west", true).with("down", false);
    }, false);
    scene.world.modifyBlock(pipe_14, (blockstate) => {
        return blockstate.with("south", true).with("down", false);
    }, false);
  
    scene.addKeyframe();
    scene.world.showSection(pipe_1, Facing.EAST);
    scene.world.showSection(pipe_2, Facing.EAST);
    scene.world.showSection(pipe_3, Facing.EAST);
    scene.world.showSection(pipe_4, Facing.NORTH);
    scene.world.showSection(pipe_5, Facing.NORTH);
    scene.world.showSection(pipe_6, Facing.NORTH);
    scene.world.showSection(pipe_7, Facing.NORTH);
    scene.world.showSection(pipe_8, Facing.NORTH);
    scene.world.showSection(pipe_9, Facing.WEST);
    scene.world.showSection(pipe_10, Facing.WEST);
    scene.world.showSection(pipe_11, Facing.WEST);
    scene.world.showSection(pipe_12, Facing.SOUTH);
    scene.world.showSection(pipe_13, Facing.SOUTH);
    scene.world.showSection(pipe_14, Facing.SOUTH);
    scene.world.showSection(pipe_15, Facing.SOUTH);
    scene.world.showSection(pipe_16, Facing.SOUTH);
  
    scene.text(70, "Connect Fluid Pipes to the copper fitting on the Mana Generators.", offsetCenterOf(util)(pipe_1));
    scene.idle(80);
  
    scene.addKeyframe();
  
    scene.text(60, "Add your Agglomeration Place on top of the Omnibox.", offsetCenterOf(util)(plate));
    scene.world.showSection(plate, Facing.DOWN);
    scene.idle(70);
    scene.world.showSection(duct_1, Facing.EAST);
    scene.world.showSection(duct_2, Facing.NORTH);
    scene.world.showSection(duct_3, Facing.WEST);
    scene.world.showSection(duct_4, Facing.SOUTH);
    scene.text(50, "And add Mana Ducts on top of the Mana Generators.", offsetCenterOf(util)(duct_1));
    scene.idle(60);
  
    scene.addKeyframe();
    scene.world.showSection(pump, Facing.NORTH);
    scene.world.showSection(tank, Facing.NORTH);
    scene.world.modifyBlock(pipe_1, (blockstate) => {
      return blockstate.with("down", true);
  }, false);
    scene.text(80, "You can now pump in some Pure Mana and start crafting!", offsetCenterOf(util)(pump));
    scene.world.showSection(tank, Facing.NORTH);
    scene.idle(90);
    scene.addKeyframe();
    scene.text(100, "Increasing pump speed or adding more pumps will help keep Mana Generators full.", offsetCenterOf(util)(pump));
  
  };
  
  onEvent("ponder.registry", event => {
    event
    .create("creatania:manaducts/manasteel") // full block id 
    .scene(
      "mana_ducts_scene", // unique scene identifier
      "Mana Ducts don't quack!", // scene title
      "creatania:mana_ducts", // namespace and path to nbt file inside ponder folder
      displayManaDuctsFromBlocksScene({ blockId: "creatania:manaducts/manasteel" })
      );
  
  });
  