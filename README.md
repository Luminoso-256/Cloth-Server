![Cloth Server Logo](https://github.com/Luminoso-256/Cloth-Server/blob/main/res/Cloth_logo.png)


A Modded server  for  minecraft alpha 1.2.6 back-porting modern minecraft server features to alpha 1.2.6

## Features

#### QOL/Commands
- backported lighting system from beta to lessen chunk lighting errors
- fixed `/kill` as vanilla implementation is bugged
- `/heal` to heal a player
- ingame read out of `/help`
- `/sleepvote` for sleep-vote related commands
- Check for, and remove invalid item ids from player inventory on join
- lua scripting system for plugins (Stitch)
- `/give` takes a string name for each block, the string -> id mappings are specififyable in the cloth config file `blocks.mappings`
- `/giveid` added to take raw block ID input
- `/tpcord` - teleports a given player to a specified set of cords
- `/version` - reports the cloth version
- `/gamerule` - change gamerules in game in the format <gamerule> <value>
-  Advancement system. Similar to modern minecraft. Enable with gamerule `enableadvancements`
-  cause aware death messages. ex: Redbunny1 went off with a bang for an explosion
- `/advancements` - lists all advancements and shows the ones you have
- `/grantadvancement` - grant a given advancement id to yourself. ex. `/grant advancement inventory.diamond`. 
#### Server Properties
- seed support with the `seed`property <string/integer>

- `doleafdecay` <boolean> : If cloth should use a simplified leaf decay algorithm.
- experimental support for "amplified" terrain by exposing octave multipliers in properties `OctaveMultiplierA`,  `OctaveMultiplierB', and  `OctaveMultiplierC` <integer>
- `usemineonlinebackend` <boolean> : if true, cloth will use a technique similar to mine-online's to use modern Mojang session server for online-mode authentication
- `motd` <string> this string is printed whenever a player joins. use %player% to inject the players name and %world% to inject the world name.
#### Gamerules
Cloth adds another property file to the game called `server.gamerules`. Gamerules are adjustments to the gameplay and QOL tweaks that dont require a server reload to see changes.

- announcedeath <boolean> : If cloth should announce when a player dies
- inversemobspawnrate <integer> : Controls the chance a mob will spawn on a given tick. Lower values = higher rates
- domobgriefing <boolean> : if creepers should cause an explosion on death
- dosleepvote <boolean> : if the sleepvote system should be active
- inverseskeletonjockeyspawnrate <integer> : the chance for a skeleton jockey to spawn on a spider. Lower values = higher rates
- itemidblacklist <string> : Contains a string of blocked item ids, seperated by spaces.
- domoderntrample <boolean> : if crop trampling should work like it does in modern versions.
- freezetime <boolean> : if cloth should freeze time

- Check out the Wiki for more information, and a list matching the latest non-release commits!

## How To Build
 - clone github repository
 - Open in IDE of choice and set startup file to net.minecraft.Main
 - Run!
Note: Cloth uses JDK8 as of the Beta 1.0.0 release
## License

Cloth is directly based on the Minecraft Server 0.28.0 Source Code, deobfuscated by Mod Coder Pack. Any original Minecraft source code is property of Mojang. Cloth additions are licensed to MIT.

## Why?

Why not?
