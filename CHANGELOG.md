# creatania

## changelog

### 1.18.2-0.0.13-alpha

### 1.18.2-0.0.12-alpha

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