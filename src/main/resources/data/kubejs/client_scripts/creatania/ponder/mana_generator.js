// Ponder scene for creatania:mana_generator

const displayManaGeneratorFromBlocksScene = (params) => (scene, util) => {

    // const blockId = params.blockId;
    // location of each block in the scene
    const whiteconcrete = util.grid.at(1, 0, 3);
    const shaft1 = util.grid.at(1, 1, 4);
    const cog = util.grid.at(2, 1, 4);
    const pump = util.grid.at(2, 1, 3);
    const tank1 = util.grid.at(1, 1, 3);
    const pipe = util.grid.at(3, 1, 3);
    const generator = util.grid.at(4, 1, 3);
    const shaft2 = util.grid.at(5, 1, 3);
    const manapool = util.grid.at(4, 2, 3);

    // replace pipe in the .nbt with white concrete
    scene.world.setBlock(whiteconcrete, "minecraft:white_concrete", false);

    // fix the mana generator cardinal facing direction
    scene.world.modifyBlock(generator, blockstate => blockstate.with("facing", "east"), false);

    scene.showBasePlate();

    scene.world.showSection(generator, Facing.WEST);
    scene.idle(20);

    scene.text(70, "The Mana Generator converts liquid Pure Mana into Botania Real Mana.", offsetCenterOf(util)(generator));
    scene.idle(80);

    scene.addKeyframe();
    scene.world.showSection(pump, Facing.EAST);
    scene.world.showSection(pipe, Facing.EAST);
    scene.world.showSection(tank1, Facing.EAST);
    scene.world.showSection(cog, Facing.EAST);
    scene.world.showSection(shaft1, Facing.EAST);

    scene.text(80, "Pipe in liquid Pure Mana into the Mana Generator.", offsetCenterOf(util)(pipe));
    scene.idle(90);

    scene.addKeyframe();
    scene.text(80, "Place a Mana Pool on top of the Mana Generator.", offsetCenterOf(util)(manapool));
    scene.world.showSection(manapool, Facing.DOWN);
    scene.idle(90);

    scene.addKeyframe();
    scene.text(80, "Provide rotational force (SU) to the Mana Generator.", offsetCenterOf(util)(shaft2));
    scene.world.showSection(shaft2, Facing.WEST);
    scene.idle(90);

    // /setblock -19 -58 -19 botania:mana_pool[waterlogged=false]{ForgeCaps:{},canAccept:1b,canSpare:1b,color:0,inputKey:"",mana:53760,manaCap:1000000,outputKey:"",outputting:1b}

 
    scene.world.modifyTileNBT(manapool, (nbt) => {

                nbt.ForgeCaps = {};
                nbt.canAccept = true;
                nbt.canSpare = true;
                nbt.color = 0;
                nbt.inputKey = "";
                nbt.mana = 53760;
                nbt.manaCap = 1000000;
                nbt.outputKey = "";
                nbt.outputting = true 

    });
    

    scene.text(100, "The mana pool will slowly fill. You now have real mana!", offsetCenterOf(util)(manapool));

};

onEvent("ponder.registry", event => {
    event
        .create("creatania:mana_generator") // full block id
        .scene(
            "mana_generator_scene", // unique scene identifier
            "The Mysterious Mana Generator", // scene title
            "creatania:mana_generator/mana_generator", // namespace and path to nbt file
            displayManaGeneratorFromBlocksScene({ blockId: "creatania:mana_generator" })
        );

});
