# creatania

## changelog

### 1.18.2-0.3.22-beta

#### Changes

- Runic Altar recipes no longer require living stone (as part of the recipe itself, still need it for the altar)

### 1.18.2-0.3.21-beta

#### Changes

- XOR Lever has a new model showing it is made with mana
- Minor fixes on inconsistent wording on advancements

### 1.18.2-0.3.20-beta

#### Changes

- Ponder JS files will no longer be overwritten by default on load, files must be deleted manually in case there are new ponder changes.
  Deleting the whole `creatania` folder inside kubejs assets and client_scripts should always be safe in any new update of creatania. 

### 1.18.2-0.3.19-beta

#### Balance Changes

- Mana Motor: massive buff on SU generated per RPM, a mana motor at max speed now should outperform a single unit steam engine (not superheated).
**Important**: DELETE your config files for creatania to make sure this applies

#### Behavior Changes

- Mana Generator behavior: Pure Mana Required (per rpm) => Max Pure Mana Consumption (per rpm)
  - **Before**: The mana generator used to not work at all if it is not provided the amount of pure mana it could process at a given RPM.
  At 256 RPM, a max speed create pump cannot provide it enough mana to run.
  - **Now**: The mana generator will always run and consume _up to_ the amount it can.
  So you might waste SU running at higher speeds than you can provide it fluid for, but it will _always_ run.

#### Visual Changes

- XOR Lever has a light indicating which side is on in the base

### 1.18.2-0.3.18-beta

#### Fixes

- Server crash on startup due to initializing client only code in a server environment. Places changed
  - Item colors
  - Flywheel based partial models
  - Particle factories

### 1.18.2-0.3.17-beta

#### Changes

- Added some missing model changes

### 1.18.2-0.3.16-beta

#### Changes

- Mod is finally in beta!

### 1.18.2-0.3.15-alpha  (_beta_ candidate release)

#### Fixes

- XOR Lever now correctly powers the block the lever is pointing towards
- Wrong Casing on some item/block names, everything should be "Title Case" now

#### New

- Setup ponderjs based scenes automatically

#### Changes

- Model adjustment for generator, brighter colors

### 1.18.2-0.3.14-alpha

#### Changes

- Cobblegen recipes shuffled around
  - asurine uses zinc
  - veridium uses copper
- Elven trades
  - Managel can no longer be created via trade with magmacream/slimeball
  - Manaduct upgrades are much more expensive via elven trade (in terms of materials), to compensate for them not costing mana

### 1.18.2-0.3.13-alpha

#### New

- Blazunia secret recipe
- Blazunia open recipe

#### Fixes

- Exception because of tooltip initialization in mana motor no longer happens (this had no impact on the player)
- Mana Motor now updates the mana rendering when receiving/using mana, even via spark.

### 1.18.2-0.3.11-alpha

#### Fixes

- Omnibox should no longer render rotating ghosts in ponder

#### Balance

- Terrasteel via elven portal is now more expensive, requires real mana blocks
- Elementium via elven portal is now more expensive, requires real mana blocks

#### Internals/Dev

- Blazunia uses internal APIs instead of creating a fake player to light up blaze burners. There's no _visible_ difference in this, but it should be much better for performance.
- Task to replace content in patchouli books 

### 1.18.2-0.3.10-alpha

#### Fixes

- Shafts should no longer be missing when pondering about omnibox

### 1.18.2-0.3.9-alpha

#### New

- Blazunia has a superheated mode. By surrounding the blazunia (north/south/east/west) with gaia pylons, it will superheat the blaze burners instead of normal heating.

#### Changes

- Blazunia will only apply fuew to burners if they have less than 5 seconds of burn time remaining.

#### Fixes

- Patchouli entries/categories point to `creatania:` so they display correctly

### 1.18.2-0.3.8-alpha

#### New

- Blazunia particle effects

### 1.18.2-0.3.7-alpha

#### New
 
- Blazunia model/texture
- Mana Generator internal turbine

#### Changes

- Squishier managel texture
- Updated bucket animations
- Blazunia scans vertically for blaze burners

#### Bugfixes

- Crashes should no longer happen when placing manaducts on top of each other

### 1.18.2-0.3.6-alpha

#### Changes

- Higher res managel texture
- Animated buckets

### 1.18.2-0.3.5-alpha

#### Bugfixes

- Fluid animations now render correctly
- Adds missing managel texture

### 1.18.2-0.3.4-alpha

#### New

- A couple of new recipes for the mana based ore reconstruction mechanic

#### Changes

- Vanilla to Botania flower mixer transformation is split into multiple recipes

#### Bugfixes

- Generating mana with SU entry in patchouli now shows correctly, it had an incorrect identifier before
- Remove conflicting crushing recipe tall to petal

### 1.18.2-0.3.3-alpha

#### Changes

- Mana Generator now renders fluid inside representing the amount of mana
- Mana Condenser now shows fluid inside while extracting mana from the air

#### Fixes

- Bucket tint now matches the fluid associated with it
- Mana Blocks models are smaller, so they fit better in belts/basin
- Added guards preventing some cases where placing a manaduct could result in a crash

### 1.18.2-0.3.2-alpha

#### Changes

- Cobblegen mechanics for source blocks slightly more like vanilla
- Ponder scenes for cobblegen show the names of the items involved

### 1.18.2-0.3.1-alpha

#### New

- Dependency on forge kotlin loader for scripting

#### Changes

- Disables in-mod ponder scenes (except cobblegen)

#### Bugfixes

- Fixes loading error introduced in 0.3.0

### 1.18.2-0.3.0-alpha

#### New (potentially BREAKING!)

- Added dependency on ponderjs (likely will be removed in the future)

#### Bugfixes

- Mana Generator now correctly fills a mana pool above
- Mana Generator pure mana internal buffer size is now adjusted to make sure it can always have enough storage to match the configured values

### 1.18.2-0.2.1-alpha

#### New

- Temporary models for buckets
- Ponder scenes for cobblegen mechanics

#### Fixes

- Removed edge cases where some cobblegen interactions would generate blocks without mana
- Adds missing icons for creatania ponder categories

### 1.18.2-0.2.0-alpha

#### New

- Ponder scenes for mana blocks

#### New (potentially BREAKING!)

- Dependency on kubejs

### 1.18.2-0.1.7-alpha

#### New

- JEI plugin for mysterious cobblegen
- Mana based "Cobblegen" fully customizable with standard JSON recipes

#### Changes

- Mana Fluid tick rate increased

### 1.18.2-0.1.6-alpha

#### New

- Alternative Mana Pylon recipe using a mixer
- Alternative Natura Pylon recipe using filling
- Ender Pearl recipe using filling
- Magical ore restoration
- Cheap Rose Quartz via elven trade
- Polishing rose quartz using mana
- Bucket trading with elves

### 1.18.2-0.1.5-alpha

#### Changes

- Defaults balancing (make sure to delete your config files if you want the new defaults)
- Remove custom "rpm per su" and "su per rpm" tooltips now that they show automatically via create

#### New

- Mana Infusion recipe: Mana Casing (super expensive in terms of mana, intentional)
- Mana Infusion recipe: Mana Gel (super expensive in terms of mana, intentional)
- Mana Infusion recipe: XOR Lever (super expensive in terms of mana, intentional)
- Elven Trade recipe: manasteel manaduct for terrasteel manaduct
- Elven Trade recipe: andesite alloy for manasteel
- Elven Trade recipe: magma cream for mana gel
- Elven Trade recipe: slime ball for mana gel
- Elven Trade recipe: zinc for terrasteel
- Elven Trade recipe: brass for elementium
- Elven Trade recipe: analog lever for xor lever
- Elven Trade casing loop: andesite -> mana -> copper -> brass -> train -> andesite

### 1.18.2-0.1.4-alpha

#### New

- Proper registration with the create stress system now shows the usual "Stress Impact", "Stress Capacity" in tooltips for mana motor/generator
- Initial version of extending the botania patchouli book with creatania mana machines by ClaudiusMinimums

#### Changes

- Small improvements to auto translations

### 1.18.2-0.1.3-alpha

#### New

- Command to create a checkerboard floor, great for demos like ponder scenes
  `/creatania checkerboardfloor <int range> <block block1> <block block2>`

#### Changes

- Mana Motor/Generator/Condenser show information about production/consumption rate in the goggles tooltip

### 1.18.2-0.1.2-alpha

#### New

- Mana Motor shows a mana hud (from botania) when looking at it with the wand of the forest
- Mana Generator shows a mana hud (from botania) when looking at it with the wand of the forest
- Prototype version of a functional flower to feed blaze burners given mana

#### Changes

- Flower/Petal crushing recipes produce more output
- Improved translations by using translations straight from Registrate instead of using logic

#### Fixes

- Mana fluids are tagged correctly
- Added missing translations
- Transparent blocks no longer let you see through the world

### 1.18.2-0.1.1-alpha

#### Fixes

- Fluids render in their designated color again
- Advancements related to molten fluids are back
- Includes missing recipe file for fluid conversion

#### Known Issues

- Mana Blocks let you see the bottom of the world

### 1.18.2-0.1.0-alpha

#### Breaking Changes

- !!! many item IDs have changed, it will result in existing worlds likely having some items/blocks deleted

#### Changes

- Manaducts (all) are now attached to surfaces, breaking the block under it will make it pop out

#### Internals

- Complete migration to Registrate (same that Create uses)
- No more blockstates in json files, everything is generated
- No more items/blocks definitions in json files unless there is a custom model for them
- **ALL** recipes are now generated by code, no more bugs due to the awfulness of json files

#### Known Issues

- Fluids might render as lava or white while in world
- A few advancements are disabled

### 1.18.2-0.0.23-alpha

#### Fixes

- Botania Mana Block shows the correct icon in inventory now
- Mana Condenser shows the correct icon in inventory now
- Gaia manaduct renders correctly in world now
- Elementium manaduct renders correctly in world now
- Melting terrasteel recipe now produces the correct fluid
- Melting elementium recipe now produces the correct fluid
- Melting gaia recipe now produces the correct fluid

#### Changes

- Molten Metal buckets have a tint

### 1.18.2-0.0.22-alpha

#### New

- Tall Mystical Flower to Mystical Flower crushing recipe
- Mystical Flower to Flower Petal crushing recipe
- Mystical Mystical Flower to Tall Flower filling recipe
- Flower Petal to Mystical Flower filling recipe
- Botania flowers can be created from Vanilla Flowers + mana in a mixer
- Introduced molten botania metals (no use for them yet)
- Introduced molten create metals (no use for them yet)
- Introduced molten vanilla metals (no use for them yet)

#### Fixes

- Stepping on purified mana blocks no longer kills the player

#### Changes

- Mana Blocks are now emissive
- Mana Blocks have new models
- Mana Gel has new model
- Improved inventory perspectives for mana machines

#### Internals

- Complete rework of fluid registration

### 1.18.2-0.0.21-alpha

#### New

- XOR Lever
- Alternative Omnibox recipe in the runic altar
- Manaduct progression tiers: manasteel -> terrasteel -> elementium -> gaia
- Manaduct upgrade recipes
- Elementium Manaduct (tier 3)

#### Changes

- Ominibox recipe in agglo plate now requires 3 shafts and 3 cogs instead of 6 shafts
- Gaia Manaduct is now tier 4 (from tier 3)

### 1.18.2-0.0.20-alpha

#### New

- Managel is now obtainable by harming a slime-like creature while it is inside mana fluid
- Managel is now a food source (saturation, nutrition, fast eat, always eat configurable)
- Eating managel now provides 30s of flight (configurable)
- Buffs/Debuffs on mana blocks are disabled while sneaking (configurable)

#### Changes

- Manaduct inventory view is now 3d-ish

### 1.18.2-0.0.19-alpha

#### New

- New blocks that can be used as a part of the multiblock terra agglomeration structure
  - Pipe and Encased Pipe (create)
  - Fluid Tank (create)
  - Omnibox (creatania)
- Recipes Added:
  - Manasteel manaduct
  - Terrasteel manaduct
  - Gaia manaduct
  - Omnibox

### 1.18.2-0.0.18-alpha

#### Changes

- Omnibox, a gearbox that propagates rotational force in every direction now has the correct model and behavior
- Improved terrasteel mana duct model

### 1.18.2-0.0.17-alpha

#### New

- Manaduct block used to connect Mana Generator with agglomeration plate
- Omnibox, a gearbox that propagates rotational force in every direction (draft state, no functionality)
- Mana Generator can now also inject mana into agglomeration plates
- Mana Generator can now also inject mana into runic altars
- Mana Generator can transfer mana via a manaduct, model by ClaudiusMinimums
- Mana Generator can replace lapis blocks as part of the agglomeration plate multiblock structure
- Pipes (from create) can replace livingrock blocks as part of the agglomeration plate multiblock structure

### 1.18.2-0.0.16-alpha

#### Changes

- Mana Motor mana rendering has a smaller bounding box, all paddings are now configurable in client configs

#### New

- Secret feature 1 + hidden advancement for it (botania/create related)
- Secret feature 2 + hidden advancement for it (mana blocks related)
- (not ready) First pass for a way of obtaining managel

### 1.18.2-0.0.15-alpha

#### Changes

- Mana Generator only shows particles when actively producing mana
- Mana Condenser only shows particles when actively producing mana
- Improved Mana Generator model by ClaudiusMinimus
- Improved Mana Condenser model by ClaudiusMinimus
- Improved Mana Motor model by ClaudiusMinimus
- Improved Mana Machine Component model by ClaudiusMinimus
- Mana Machine Component assembly recipe:
  - no longer requires a shaft
- Mana Generator altar recipe now requires a shaft
  - no longer requires mana diamond
  - now requires mana powder
  - now requires a pipe
  - now requires living rock
- Mana Condenser altar recipe:
  - no longer requires mana pearl
  - now requires mana powder
  - now requires a shaft
  - now requires iron bars
  - now requires a propeller
- Mana Motor altar recipe:
  - now requires mana powder
  - now requires a shaft
  - now requires iron bars
  - now requires 4 iron sheets


### 1.18.2-0.0.14-alpha

#### New

- Advancements, including some hidden easter eggs
- Recipe for true mana block using the agglomeration plate
- Automatically generate best effort human readable strings

### 1.18.2-0.0.13-alpha

#### Fixes

- Mana Generator works with negative rotation speeds now
- No longer crashes due to bad initialization order for flight effect

### 1.18.2-0.0.12-alpha (bad version)

#### Warning! This version has a game breaking bug

- Crash on startup due to bad initialization of the flight effect

#### Fixes

- Mana Condenser inner faces are no longer culled

#### Changes

- Improved Mana Generator model by ClaudiusMinimus

#### New

- Balancing/Config that allows a single unit of mana to generate multiple RPM in a mana motor
- Stepping on a Purified Inert Mana Block will give any entity absorption, heal, and saturation buffs for 30s (configurable)
- Stepping on a Corrupted Inert Mana Block will give any entity poison, wither, hunger, and harm effects for 30s (configurable)
- Stepping on a Botania Mana Block will give any entity creative flight for 60s (configurable)

### 1.18.2-0.0.11-alpha

#### Changes

- Mana Condenser throttles the rate of corrupt mana generated based on RPM (1 per tick at max RPM)
- Increase default amount of particles in Mana Condenser/Generator (ticks per particle still configurable and client config)

#### New

- JEI category with brief explanation for the process of mana generation
- JEI category with brief explanation for the process of mana condensing

#### Fixes

- ? Mana Generator should populate the mana pool above it properly

#### Internals

- Extra logs around Mana Generator since we had some problems with it

### 1.18.2-0.0.10-alpha

#### Fixes

- Mana Generator fluid connection in the model matches the actual functionality

#### New

- Mana Motor now renders a fan inside representing a "mana wheel" as it generates SU

#### Removed

- Mana Generator/Condenser/Motor dev recipes with only sticks

#### Changes

- Mana Generator is now sided, only accepts fluid from the side opposite to the shaft
- Mana Motor is now sided, only accepts mana from the bottom or the sides orthogonal to the shaft

#### Fixes

- Mana Motor recipe in the botania rune altar was creating a mana generator instead
- ? Mana Generator generates mana on a mana pool above
- ? Mana Condenser generates mana on an inventory below

### 1.18.2-0.0.9-alpha

#### New

- Mana Generator spawns particles outwards
- Mana Condenser spawns particles inwards
- Corrupted mana fluid
- Pure Daisy inert mana conversion also works for fluid versions

#### Changes

- Mana Machine Component looks more like a manasteel casing now
- Mana Motor shows fans spinning inside
- Uses vanilla spark particles for mana condenser/generator

#### Fixes

- Mana Generator shaft now renders correctly
- Mana Condenser shaft now renders correctly
- Mana Machines are now locked to horizontal orientation

#### Internals

- Create sequence assembly recipes are generated instead of relying on horrible json files

### 1.18.2-0.0.8-alpha

#### Fixes

- Correct intermediate item in the mana machine component assembly
- Added missing Mana Condenser recipe
- Removed Duplicated recipe for Mana Generator

#### dev environment

- JEI added to mods.toml so recipes show correctly in the dev instance

### 1.18.2-0.0.7-alpha

#### New

- Mana Machine Component, created via assembly line, used to craft mana machines
- Recipes for Mana Motor/Generator/Condenser using the botania runic altar

#### Fixes

- Mana Machines models showing the incorrect orientation.

### 1.18.2-0.0.6-alpha

- Mana generator requires **fluid inert mana** to operate as well as SU
- New tag: `creatania:blocks/pure_inert_mana`
- New tag: `creatania:blocks/corrupt_inert_mana`
- New tag: `creatania:fluids/pure_inert_mana`

#### internals

- Tags are now generated via code
- Blockstates are now generated via code
- Recipes are now generated via code
- Loot Tables are now generated via code

### 1.18.2-0.0.5-alpha

- Mana motor renders amount of mana currently stored
- Mana motor supports sparks
- Pure Daisy Purified Inert Mana block recipe
- Pure Daisy Corrupted Inert Mana block recipe

### 1.18.2-0.0.4-alpha

- Shaft is correctly rendered in the mana motor
- Fluid inert mana introduced (no functionality)
- Mana Gel introduced (no functionality)

### 1.18.2-0.0.3-alpha

- Initial version of mana condenser

### 1.18.2-0.0.2-alpha

- Initial version of mana generator

### 1.18.2-0.0.1-alpha

- Initial version of mana motor
- Initial version supports minecraft 1.18.2