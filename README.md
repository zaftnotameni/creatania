
Creatania
-------------------------------------------
![](https://cf.way2muchnoise.eu/title/825000.svg)![](https://img.shields.io/badge/environment-both-4caf50?style=flat-square)![]( https://img.shields.io/badge/mod%20loader-forge-1976d2?style=flat-square)![](https://cf.way2muchnoise.eu/versions/825000.svg)


Connecting ![Botania](https://cf.way2muchnoise.eu/title/225643.svg) and ![Create](https://cf.way2muchnoise.eu/title/328085.svg).

### Design

##### Tenants:

- Connect Create and Botania, leveraging mechanics from both mods.
- Strongly favor sided blocks.
- Don't break balance in both Botania and Create, no overpowered items/machines.
- Highly configurable: every production rate, exchange rate, SU/mana requirements can be tuned via config.
- Respect Botania and Create mod philosophies the best we can:
  - Never ever show numeric values for mana.
  - Mana generation requires some interaction with botania.
  - Avoid GUIs as much as possible.

### Machines

- Mana Motor (botania mana -> SU, requires mana)
- Mana Condenser (SU -> corrupted inert mana blocks, requires SU)
- Mana Generator (purified inert mana fluid -> botania mana, requires SU)

### Food?

- Mana gel is a mysterious item, it is said to provide great benefits when eaten, but seems like the way to acquire it has been lost. Maybe there is a secret?

### Fluids

- Inert mana fluid: a fluid version of mana, can be used for transportation in pipes/trains but must go through another machine to become botania mana.
- Molten metals: Botania, Create, and Vanilla metals are given a molten form (not utilized at the moment)

### Flowers

- Blazunia: functional flower, consumes mana, lights up blaze burners in a radius.

### Blocks

- Purified Inert Mana block: can be melted to inert mana fluid using a superheated mixer.
- Corrupted Inert Mana block: can be purified via pure daisy to become a purified inert mana block.
- Real Mana block: requires infusing lots of mana into a inert mana block.

### Mechanics

#### Mana Generation

SU can be used to generate mana through an 4-step process.

1. SU is used in a mana condenser to generate corrupted mana blocks
2. corrupted mana blocks are used with a pure daisy to make pure inert mana blocks
3. Pure Inert mana blocks can be superheated into Inert mana fluid
4. Pure Inert mana fluid can be converted to mana using a mana generator

Alternatively, steps 2 and 3 can be reversed:

1. SU is used in a mana condenser to generate corrupted mana blocks
2. Corrupted Inert mana blocks can be superheated into Corrupted Inert mana fluid
3. Corrupted mana fluid can be purified by a pure daisy to pure inert mana fluid
4. Pure Inert mana fluid can be converted to mana using a mana generator

#### SU Generation

Mana can be converted directly to SU via a Mana Motor.

### Development

Setup Process:
==============================

Step 1: Open your command-line and browse to the folder where you extracted the zip file.

1. Open IDEA, and import project.
2. Select your build.gradle file and have it import.
3. Run the following command: `gradlew genIntellijRuns` (`./gradlew genIntellijRuns` if you are on Mac/Linux)
4. Refresh the Gradle Project in IDEA if required.

If at any point you are missing libraries in your IDE, or you've run into problems you can 
run `gradlew --refresh-dependencies` to refresh the local cache. `gradlew clean` to reset everything 
{this does not affect your code} and then start the process again.

Additional Resources (from Forge): 
=========================
Community Documentation: http://mcforge.readthedocs.io/en/latest/gettingstarted/  
LexManos' Install Video: https://www.youtube.com/watch?v=8VEdtQLuLO0  
Forge Forum: https://forums.minecraftforge.net/  
Forge Discord: https://discord.gg/UvedJ9m  