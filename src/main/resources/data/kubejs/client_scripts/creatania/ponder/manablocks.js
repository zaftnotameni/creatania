// Ponder scene for creatania:mana/blocks/corrupt

/*   
  // helper functions, recommend SKIPPING over them on first read, the names should be self explanatory, you can come back and check the specifics afterwards
  const gentleFall = (util) => util.vector.of(0.0, -0.1, 0.0);
  const offsetTopOf = (util) => (target) => util.vector.topOf(target).add(0.0, 0.5, 0.0);
  const dropBlocksOnto = (scene, util) => (target, blockId, howMany, afterEach) => { while(howMany-- > 0) { dropBlockOnto(scene, util)(target, blockId); afterEach(); } }
  const dropBlockOnto = (scene, util) => (target, blockId) => scene.world.createItemEntity(offsetTopOf(util)(target), gentleFall(util), blockId);

// defines empty tanks
  const emptyTank = () => ({ Level:{Speed:0.25,Target:0.0,Value:0.0}, TankContent:{Amount:0,FluidName:"minecraft:empty"} });
  const tankWithAmount = (fluidId, amount) => ({ Level:{Speed:0.25,Target:1.0,Value:0.0}, TankContent:{Amount: amount,FluidName: fluidId}})
  const startProcessing = () => (nbt) => { nbt.Running = true; }
  const emptyInputItems = () => (nbt) => { nbt.InputItems = {Items:[],Size:9}; }
  const emptyTanks = (propertyName) => (nbt) => { nbt[propertyName] = [emptyTank(), emptyTank()]; };
  const fillTanks = (propertyName, fluidId, amount) => (nbt) => { nbt[propertyName] = [tankWithAmount(fluidId, amount || 1000), emptyTank()]; };

// "facing" in this line specifies the basin output
  const facing = (cardinalDirection) => (blockState) => blockState.with("facing", cardinalDirection);
   */
  // Good places to look at:
  // -> Create Scenes: https://github.com/Creators-of-Create/Create/blob/mc1.18/dev/src/main/java/com/simibubi/create/foundation/ponder/content/ProcessingScenes.java
  // -> Translating Create to Kubejs: https://github.com/AlmostReliable/ponderjs/blob/1.18-forge/src/main/java/com/almostreliable/ponderjs/api/ExtendedSceneBuilder.java
  const produceManaFluidFromBlocksScene = (params) => (scene, util) => {
    const blockId = params.blockId;
    const fluidId = params.fluidId;
    const basin = util.grid.at(1, 2, 1); // find each block in the world, there is a client setting to show coords in game: https://github.com/AlmostReliable/ponderjs/wiki/6.-Coordinates
    const mixer = util.grid.at(1, 4, 1);
    const blaze = util.grid.at(1, 1, 1);
    const drain = util.grid.at(0, 1, 1); // you can even find blocks that don't yet exist in the NBT file and you plan to add later

    // console.log("scene:\n\t -" + Object.keys(scene).join("\n\t -"));
    // console.log("scene.world:\n\t -" + Object.keys(scene.world).join("\n\t -"));
    // console.log("util:\n\t -" + Object.keys(util).join("\n\t -"));
    // console.log("util.vector:\n\t -" + Object.keys(util.vector).join("\n\t -"));
    
    // using scene.showBasePlant() instead of scene.showStructure() lets us animate each piece coming in, instead of showing the full NBT file right away
    scene.showBasePlate(); 
    scene.idle(20);
    scene.world.showSection(blaze, Facing.EAST); // the first parameter is just the position of the block in the NBT template
    scene.world.modifyBlock(blaze, blockstate => blockstate.with("blaze", "none"), false); // empty
    scene.world.modifyBlock(blaze, blockstate => blockstate.with("blaze", "smouldering"), false); // off
    
    
    scene.idle(20);
    scene.world.showSection(basin, Facing.WEST); // the second parameter (Facing) is the direction the block slide towards during the "appear" animation
    scene.idle(20);
    scene.world.showSection(mixer, Facing.UP);
    scene.idle(20);
    // by doing it like this, even more importantly than animating each piece, we could have extra blocks _already in the NBT template_ that we only show later
    
    scene.addKeyframe();
    // the 60 represents the number of ticks the message is displayed
    scene.text(60, "Throw some mana blocks into the mix", util.vector.topOf(basin));
    scene.idle(60);
    
    scene.addKeyframe();
    // scene.world.modifyBlock(blaze, blockstate => blockstate.with("blaze", "kindled"), false); // yellow
    scene.text(60, "Superheat your Blaze Burner", offsetCenterOf(util)(blaze));
    scene.showControls(60, [2, 1, 2], "down").rightClick().withItem("create:blaze_cake")
    scene.world.modifyBlock(blaze, blockstate => blockstate.with("blaze", "seething"), false); // blue
    
    // this shows each block dropped one at a time with 20 tick delay
    dropBlocksOnto(scene, util)(basin, blockId, 4, () => scene.idle(20));
  
    // How to figure this out? Check for blocks in world with either F3 + I or /data get block
    // Compare what changes when the mixer is running vs idle
    // very important, when looking at nbt data: "0b" and "1b" need to be translated to false and true respectively
    scene.world.modifyTileNBT(mixer, startProcessing());
    scene.idle(20);
  
    // tile entities tend to have input and output inventories if they perform some sort of processing
    scene.world.modifyTileNBT(basin, emptyInputItems());
    scene.idle(20);
  
    // determining which NBT properties to set can be done with the help of F3 + I
    scene.world.modifyTileNBT(basin, fillTanks("OutputTanks", fluidId, 1000));
    scene.idle(20);
    
    // a better way of doing this instead of manually setting blocks is to use this API: https://github.com/AlmostReliable/ponderjs/wiki/4.-Sections
    // for that, the NBT scene file should contain the full final result with all blocks in,
    // then calling scene.world.showSection block by block to bring them into view (instead of using showStructure)
    // if the drain was part of the NBT template, we could just call scene.world.showSection(drain, Facing.EAST) here instead
    scene.world.setBlock(drain, "create:item_drain", true);
    // this is important and very non obvious: calling setBlock is enough to show something _if_ we went with the showStructure approach
    // but since I changed it to do showBasePlate + showSection, after setting a block I need to animate it in with showSection
    scene.world.showSection(drain, Facing.EAST); // this is the transition animation
    scene.idle(1);
  
    // like NBT, blockstates can also be seen (in square brackets) via F3 + I
    // call the function to show the basin spout
    scene.world.modifyBlock(basin, facing("west"), false);
    scene.idle(1);
  
    // a bit of internals of how most forge fluid handlers work, specially create
    // they tend to have at the minimum 1 input and 1 output tank
    // the basin in particular has 2 input and 2 output tanks
    scene.world.modifyTileNBT(basin, emptyTanks("OutputTanks"));
    scene.idle(1);
  
    // the drain is a simpler block and has only a pair of tanks, no differentiation between input/output
    scene.world.modifyTileNBT(drain, fillTanks("Tanks", fluidId, 1000));
    scene.idle(1);
  
    scene.text(40, "Generates fluid mana", util.vector.topOf(drain));
  };
  
  onEvent("ponder.registry", event => {
    event
      .create("creatania:mana/blocks/corrupt") // full block id
      .scene(
        "corrupt_mana_scene", // unique scene identifier
        "Liquefy Corrupt Mana", // scene title
        "creatania:manablocks_liquefy", // namespace and path to nbt file inside ponder folder
        // by being careful about how we structure this function, we can use the same scene structure for corrupt and pure mana
        produceManaFluidFromBlocksScene({ blockId: "creatania:mana/blocks/corrupt", fluidId: "creatania:corrupt_mana" })
      );
  
    event
      .create("creatania:mana/blocks/pure") // full block id
      .scene(
        "pure_mana_scene", // unique scene identifier
        "Liquefy Pure Mana", // scene title
        "creatania:manablocks_liquefy", // namespace and path to nbt file inside ponder folder
        // by being careful about how we structure this function, we can use the same scene structure for corrupt and pure mana
        produceManaFluidFromBlocksScene({ blockId: "creatania:mana/blocks/pure", fluidId: "creatania:pure_mana" })
      );
  });
  