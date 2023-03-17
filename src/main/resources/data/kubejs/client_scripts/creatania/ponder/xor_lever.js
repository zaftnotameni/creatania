// Ponder scenes for Vertical XOR Lever
const displayXORVerticalFromBlocksScene = (params) => (scene, util) => {

    // location of each block in the scene
    const redstone_lamp_1 = util.grid.at(1, 1, 1);
    const redstone_lamp_2 = util.grid.at(1, 2, 2);
    const redstone_lamp_3 = util.grid.at(1, 3, 1);
    const xor_lever = util.grid.at(1, 2, 1);

    scene.showBasePlate();
    scene.idle(20);
    scene.world.showSection(redstone_lamp_1, Facing.NORTH);
    scene.world.showSection(redstone_lamp_2, Facing.NORTH);
    scene.world.showSection(redstone_lamp_3, Facing.NORTH);
    scene.world.showSection(xor_lever, Facing.NORTH);

    scene.text(80, "The XOR Lever can also be mounted vertically.", offsetMiddleFrontOf(util)(xor_lever));
    scene.idle(90);

    scene.addKeyframe();
    scene.text(80, "Like before, it does not pass a redstone signal through the block mounted to.", offsetMiddleSideOf(util)(redstone_lamp_2));
    scene.idle(90);

    scene.text(80, "And the lever only powers the block it is pointing to.", offsetMiddleSideOf(util)(redstone_lamp_3));
    scene.world.modifyBlock(xor_lever, (blockstate) => {
        return blockstate.with("conditional", false).with("face", "wall");
    }, false);

    scene.world.modifyTileNBT(xor_lever, (nbt) => {
        nbt.ChangeTimer = 0;
        nbt.State = 0;
    });
    scene.idle(5);

    scene.world.modifyBlock(redstone_lamp_1, (blockstate) => {
        return blockstate.with("lit", false);
    }, false);

    scene.world.modifyBlock(redstone_lamp_3, (blockstate) => {
        return blockstate.with("lit", true);
    }, false);

};

// Ponder scenes for Horizontal XOR Lever
const displayXORHorizontalFromBlocksScene = (params) => (scene, util) => {

    // location of each block in the scene
    const redstone_lamp_1 = util.grid.at(1, 1, 1);
    const redstone_lamp_2 = util.grid.at(2, 2, 1);
    const redstone_lamp_3 = util.grid.at(0, 2, 1);
    const xor_lever = util.grid.at(1, 2, 1);

    scene.showBasePlate();
    scene.idle(20);
    scene.world.showSection(redstone_lamp_1, Facing.NORTH);
    scene.world.showSection(redstone_lamp_2, Facing.NORTH);
    scene.world.showSection(redstone_lamp_3, Facing.NORTH);
    scene.world.showSection(xor_lever, Facing.NORTH);

    scene.text(80, "The XOR Lever behaves like a XOR logic gate.", offsetTopOf(util)(redstone_lamp_1));
    scene.idle(90);

    scene.addKeyframe();
    scene.text(80, "Notice how it does not pass a redstone signal through the block mounted to.", offsetCenterOf(util)(redstone_lamp_1));
    scene.idle(90);

    scene.text(80, "The lever only powers the block it is pointing to.", offsetMiddleSideOf(util)(redstone_lamp_2));
    scene.world.modifyBlock(xor_lever, (blockstate) => {
        return blockstate.with("conditional", false).with("face", "floor");
    }, false);

    scene.world.modifyTileNBT(xor_lever, (nbt) => {
        nbt.ChangeTimer = 0;
        nbt.State = 0;
    });

    scene.world.modifyBlock(redstone_lamp_3, (blockstate) => {
        return blockstate.with("lit", false);
    }, false);

    scene.world.modifyBlock(redstone_lamp_2, (blockstate) => {
        return blockstate.with("lit", true);
    }, false);
};


onEvent("ponder.registry", event => {
    event
        .create("creatania:xor_lever") // full block id
        .scene(
            "xor_lever_horizontal", // unique scene identifier
            "XOR Lever Horizontal", // scene title
            "creatania:xor_lever_horizontal", // namespace and path to nbt file inside ponder folder
            displayXORHorizontalFromBlocksScene({ blockId: "creatania:xor_lever" })
        );
});

onEvent("ponder.registry", event => {
    event
        .create("creatania:xor_lever") // full block id
        .scene(
            "xor_lever_vertical", // unique scene identifier
            "XOR Lever Vertical", // scene title
            "creatania:xor_lever_vertical", // namespace and path to nbt file inside ponder folder
            displayXORVerticalFromBlocksScene({ blockId: "creatania:xor_lever" })
        );
});