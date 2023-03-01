// for 1.18 pls use: onEvent("ponder.tag")
// Ponder.tags((event) => {

// for 1.18 pls use: onEvent("ponder.tag")
// Ponder.tags((event) => {
/**
 * "kubejs:getting_started" -> the tag name
 * "minecraft:paper"        -> the icon
 * "Getting Started"        -> the title
 * "This is a description"  -> the description
 * [...items]               -> default items
 */

onEvent("ponder.tag", event => {
    event.createTag("creatania:mana_machines", "creatania:mana_condenser", "Machines that Interact with Mana", "Can we pull mana from thin air?", [
        "creatania:mana_condenser",
        "creatania:mana_generator",
        "creatania:mana_motor"
    ]);

    event.createTag("creatania:mana_blocks", "creatania:mana/blocks/real", "Learning About Mana", "All sorts of fancy pretty blocks!", [
        "creatania:mana/blocks/corrupt",
        "creatania:mana/blocks/pure",
        "creatania:mana/blocks/real", // does not have ponder scene
        "create:basin",
        "create:mechanical_mixer",
    ]);
});