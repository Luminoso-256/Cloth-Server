## Cloth-Server
A Modded server  for  minecraft alpha 1.2.6 back-porting modern minecraft server features to alpha 1.2.6

#### Features
 - give command now takes string names like `stone` instead of numeric ids like `1`
 - giveid command exists to take ids if you desire
 - tpcord command teleports you to a given set of cordinates
 - timeset command from modern minecraft
 - Seed support! -  server.properties has a `seed` property that  takes a number value
 - seed command which tells you the world seed (random if no seed set in server.properties)
 - Other misc server.properties params that let you mess with world gen (see added server parameters section)
 - a gamerules system
 - rudementary death messages (default off)
 - blocks.mappings - a file that lets you customize which string means  which block!
 - version command gives you the current Cloth version
  - MOTD message when a player joins
  - death messages
 - control the chances of jockies spawning
  - Full control over spawnrates
  - kill command 
  - heal command  which puts you back at full health
 - clear command drops all items in your inventory
 - Item blacklist
 
  
#### Roadmap
 - plugin support of some  kind - with docs
 - investigate and fix broken spawn chunks
 - protect against invalid block/item Ids
 - Patch nether to work correctly in multiplayer
 - "Rulesets" - gamerule files that allow you to tweak gameplay
  - @p prefix for commands
#### Added Server Parameters
 - seed - takes integer, governs world seed
 - OctaveMultiplierA - int, Unknown effects on noise octaves
 - OctaveMultiplierB - int, Unknown effects on noise octaves
 - OctaveMultiplierC - int, Unknown effects on noise octaves
 
 
#### How To Build
 - clone github repository
 - open repository as Intellij Idea(tm) project
 - run the `MinecraftServer` configuration
 - enjoy!

## Why?

Why not?
