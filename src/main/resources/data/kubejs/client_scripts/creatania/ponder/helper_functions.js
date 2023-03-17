// priority: 9000
/* 
    Some global helper functions for ponder
*/

const gentleFall = (util) => util.vector.of(0.0, -0.1, 0.0);
const offsetCenterOf = (util) => (target) => util.vector.topOf(target).add(0.0, -0.5, 0.0);
const offsetMiddleSideOf = (util) => (target) => util.vector.topOf(target).add(-0.5, -0.5, 0.0);
const offsetMiddleFrontOf = (util) => (target) => util.vector.topOf(target).add(-0.5, -0.25, 0.0);
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
