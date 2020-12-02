package net.minecraft.cloth;

/**
 * Hard-coded fallback block mappings
 *
 * @author Luminoso-256
 */
public class FallbackIdMaps {
    public int GetIDForNamespacedBlockName(String NamespacedName) {
        int ID = 0; // air if all else fails
        //Hardcoded maps if blocks.mappings falls short
        //if(NamespacedName.equals("air")){ID = 0;}
        if (NamespacedName.equals("stone")) {
            ID = 1;
        }
        if (NamespacedName.equals("grass")) {
            ID = 2;
        }
        if (NamespacedName.equals("dirt")) {
            ID = 3;
        }
        if (NamespacedName.equals("cobblestone")) {
            ID = 4;
        }
        if (NamespacedName.equals("planks")) {
            ID = 5;
        }
        if (NamespacedName.equals("oak_sapling")) {
            ID = 6;
        }
        if (NamespacedName.equals("bedrock")) {
            ID = 7;
        }
        if (NamespacedName.equals("flowing_water")) {
            ID = 8;
        }
        if (NamespacedName.equals("still_water")) {
            ID = 9;
        }
        if (NamespacedName.equals("flowing_lava")) {
            ID = 10;
        }
        if (NamespacedName.equals("still_lava")) {
            ID = 11;
        }
        if (NamespacedName.equals("sand")) {
            ID = 12;
        }
        if (NamespacedName.equals("gravel")) {
            ID = 13;
        }
        if (NamespacedName.equals("gold_ore")) {
            ID = 14;
        }
        if (NamespacedName.equals("iron_ore")) {
            ID = 15;
        }
        if (NamespacedName.equals("coal_ore")) {
            ID = 16;
        }
        if (NamespacedName.equals("log")) {
            ID = 17;
        }
        if (NamespacedName.equals("leaves")) {
            ID = 18;
        }
        if (NamespacedName.equals("sponge")) {
            ID = 19;
        }
        //if(NamespacedName == "wet_sponge"){ID = 1;}
        if (NamespacedName.equals("glass")) {
            ID = 20;
        }
        if (NamespacedName.equals("red_cloth")) {
            ID = 21;
        }
        if (NamespacedName.equals("orange_cloth")) {
            ID = 22;
        }
        if (NamespacedName.equals("yellow_cloth")) {
            ID = 23;
        }
        if (NamespacedName.equals("light_green_cloth")) {
            ID = 24;
        }
        if (NamespacedName.equals("green_cloth")) {
            ID = 25;
        }
        if (NamespacedName.equals("teal_green_cloth")) {
            ID = 26;
        }
        if (NamespacedName.equals("cyan_cloth")) {
            ID = 27;
        }
        if (NamespacedName.equals("blue_cloth")) {
            ID = 28;
        }
        if (NamespacedName.equals("dark_blue_cloth")) {
            ID = 29;
        }
        if (NamespacedName.equals("violet_cloth")) {
            ID = 30;
        }
        if (NamespacedName.equals("purple_cloth")) {
            ID = 31;
        }
        if (NamespacedName.equals("magenta_cloth")) {
            ID = 32;
        }
        if (NamespacedName.equals("rose_cloth")) {
            ID = 33;
        }
        if (NamespacedName.equals("dark_gray_cloth")) {
            ID = 34;
        }
        if (NamespacedName.equals("light_gray_cloth")) {
            ID = 35;
        }
        if (NamespacedName.equals("white_cloth")) {
            ID = 36;
        }
        if (NamespacedName.equals("dandelion")) {
            ID = 37;
        }
        if (NamespacedName.equals("rose")) {
            ID = 38;
        }
        if (NamespacedName.equals("brown_mushroom")) {
            ID = 39;
        }
        if (NamespacedName.equals("red_mushroom")) {
            ID = 40;
        }
        if (NamespacedName.equals("gold_block")) {
            ID = 41;
        }
        if (NamespacedName.equals("iron_block")) {
            ID = 42;
        }
        if (NamespacedName.equals("double_slab")) {
            ID = 43;
        }
        if (NamespacedName.equals("slab")) {
            ID = 44;
        }
        if (NamespacedName.equals("bricks")) {
            ID = 45;
        }
        if (NamespacedName.equals("tnt")) {
            ID = 46;
        }
        if (NamespacedName.equals("bookshelf")) {
            ID = 47;
        }
        if (NamespacedName.equals("mossy_cobblestone")) {
            ID = 48;
        }
        if (NamespacedName.equals("obsidian")) {
            ID = 49;
        }
        if (NamespacedName.equals("torch")) {
            ID = 50;
        }
        if (NamespacedName.equals("fire")) {
            ID = 51;
        }
        if (NamespacedName.equals("mob_spawner")) {
            ID = 52;
        }
        if (NamespacedName.equals("wooden_stairs")) {
            ID = 53;
        }
        if (NamespacedName.equals("chest")) {
            ID = 54;
        }
        if (NamespacedName.equals("redstone_dust")) {
            ID = 55;
        }
        if (NamespacedName.equals("diamond_ore")) {
            ID = 56;
        }
        if (NamespacedName.equals("diamond_block")) {
            ID = 57;
        }
        if (NamespacedName.equals("crafting_table")) {
            ID = 58;
        }
        if (NamespacedName.equals("crops")) {
            ID = 59;
        }
        if (NamespacedName.equals("farmland")) {
            ID = 60;
        }
        if (NamespacedName.equals("furnace")) {
            ID = 61;
        }
        if (NamespacedName.equals("lit_furnace")) {
            ID = 62;
        }
        if (NamespacedName.equals("sign_post")) {
            ID = 63;
        }
        if (NamespacedName.equals("wooden_door")) {
            ID = 64;
        }
        if (NamespacedName.equals("ladder")) {
            ID = 65;
        }
        if (NamespacedName.equals("rail")) {
            ID = 66;
        }
        if (NamespacedName.equals("cobblestone_stairs")) {
            ID = 67;
        }
        if (NamespacedName.equals("sign_wall")) {
            ID = 68;
        }
        if (NamespacedName.equals("lever")) {
            ID = 69;
        }
        if (NamespacedName.equals("stone_pressure_plate")) {
            ID = 70;
        }
        if (NamespacedName.equals("iron_door")) {
            ID = 71;
        }
        if (NamespacedName.equals("wooden_pressure_plate")) {
            ID = 72;
        }
        if (NamespacedName.equals("redstone_ore")) {
            ID = 73;
        }
        if (NamespacedName.equals("lighted_redstone_ore")) {
            ID = 74;
        }
        if (NamespacedName.equals("redstone_torch")) {
            ID = 75;
        }
        if (NamespacedName.equals("redstone_torch_off")) {
            ID = 76;
        }
        if (NamespacedName.equals("stone_button")) {
            ID = 77;
        }
        if (NamespacedName.equals("snow")) {
            ID = 78;
        }
        if (NamespacedName.equals("ice")) {
            ID = 79;
        }
        if (NamespacedName.equals("snow_block")) {
            ID = 80;
        }
        if (NamespacedName.equals("cactus")) {
            ID = 81;
        }
        if (NamespacedName.equals("clay")) {
            ID = 82;
        }
        if (NamespacedName.equals("reed")) {
            ID = 83;
        }
        if (NamespacedName.equals("jukebox")) {
            ID = 84;
        }
        if (NamespacedName.equals("fence")) {
            ID = 85;
        }
        if (NamespacedName.equals("pumpkin")) {
            ID = 86;
        }
        if (NamespacedName.equals("netherrack")) {
            ID = 87;
        }
        if (NamespacedName.equals("soulsand")) {
            ID = 88;
        }
        if (NamespacedName.equals("glowstone")) {
            ID = 89;
        }
        if (NamespacedName.equals("portal")) {
            ID = 90;
        }
        if (NamespacedName.equals("jackolantern")) {
            ID = 91;
        }
        //Items
        if (NamespacedName.equals("iron_shovel")) {
            ID = 256;
        }
        if (NamespacedName.equals("iron_pickaxe")) {
            ID = 257;
        }
        if (NamespacedName.equals("iron_axe")) {
            ID = 258;
        }
        if (NamespacedName.equals("flint_and_steel")) {
            ID = 259;
        }
        if (NamespacedName.equals("apple")) {
            ID = 260;
        }
        if (NamespacedName.equals("bow")) {
            ID = 261;
        }
        if (NamespacedName.equals("arrow")) {
            ID = 262;
        }
        if (NamespacedName.equals("coal")) {
            ID = 263;
        }
        if (NamespacedName.equals("diamond")) {
            ID = 264;
        }
        if (NamespacedName.equals("iron_ingot")) {
            ID = 265;
        }
        if (NamespacedName.equals("gold_ingot")) {
            ID = 266;
        }
        if (NamespacedName.equals("iron_sword")) {
            ID = 267;
        }
        if (NamespacedName.equals("wooden_sword")) {
            ID = 268;
        }
        if (NamespacedName.equals("wooden_shovel")) {
            ID = 269;
        }
        if (NamespacedName.equals("wooden_pickaxe")) {
            ID = 270;
        }
        if (NamespacedName.equals("wooden_axe")) {
            ID = 271;
        }
        if (NamespacedName.equals("stone_sword")) {
            ID = 272;
        }
        if (NamespacedName.equals("stone_shovel")) {
            ID = 273;
        }
        if (NamespacedName.equals("stone_pickaxe")) {
            ID = 274;
        }
        if (NamespacedName.equals("stone_axe")) {
            ID = 275;
        }
        if (NamespacedName.equals("diamond_sword")) {
            ID = 276;
        }
        if (NamespacedName.equals("diamond_shovel")) {
            ID = 277;
        }
        if (NamespacedName.equals("diamond_pickaxe")) {
            ID = 278;
        }
        if (NamespacedName.equals("diamond_axe")) {
            ID = 279;
        }
        if (NamespacedName.equals("stick")) {
            ID = 280;
        }
        if (NamespacedName.equals("bowl")) {
            ID = 281;
        }
        if (NamespacedName.equals("mushroom_stew")) {
            ID = 282;
        }
        if (NamespacedName.equals("golden_sword")) {
            ID = 283;
        }
        if (NamespacedName.equals("golden_shovel")) {
            ID = 284;
        }
        if (NamespacedName.equals("golden_pickaxe")) {
            ID = 285;
        }
        if (NamespacedName.equals("golden_axe")) {
            ID = 286;
        }
        if (NamespacedName.equals("string")) {
            ID = 287;
        }
        if (NamespacedName.equals("feather")) {
            ID = 288;
        }
        if (NamespacedName.equals("gunpowder")) {
            ID = 289;
        }
        if (NamespacedName.equals("wooden_hoe")) {
            ID = 290;
        }
        if (NamespacedName.equals("stone_hoe")) {
            ID = 291;
        }
        if (NamespacedName.equals("iron_hoe")) {
            ID = 292;
        }
        if (NamespacedName.equals("diamond_hoe")) {
            ID = 293;
        }
        if (NamespacedName.equals("golden_hoe")) {
            ID = 294;
        }
        if (NamespacedName.equals("seeds")) {
            ID = 295;
        }
        if (NamespacedName.equals("wheat")) {
            ID = 296;
        }
        if (NamespacedName.equals("bread")) {
            ID = 297;
        }
        if (NamespacedName.equals("leather_cap")) {
            ID = 298;
        }
        if (NamespacedName.equals("leather_tunic")) {
            ID = 299;
        }
        if (NamespacedName.equals("leather_pants")) {
            ID = 300;
        }
        if (NamespacedName.equals("leather_boots")) {
            ID = 301;
        }
        if (NamespacedName.equals("chain_helmet")) {
            ID = 302;
        }
        if (NamespacedName.equals("chain_chestplate")) {
            ID = 303;
        }
        if (NamespacedName.equals("chain_leggings")) {
            ID = 304;
        }
        if (NamespacedName.equals("chain_boots")) {
            ID = 305;
        }
        if (NamespacedName.equals("iron_helmet")) {
            ID = 306;
        }
        if (NamespacedName.equals("iron_chestplate")) {
            ID = 307;
        }
        if (NamespacedName.equals("iron_leggings")) {
            ID = 308;
        }
        if (NamespacedName.equals("iron_boots")) {
            ID = 309;
        }
        if (NamespacedName.equals("diamond_helmet")) {
            ID = 310;
        }
        if (NamespacedName.equals("diamond_chestplate")) {
            ID = 311;
        }
        if (NamespacedName.equals("diamond_leggings")) {
            ID = 312;
        }
        if (NamespacedName.equals("diamond_boots")) {
            ID = 313;
        }
        if (NamespacedName.equals("golden_helmet")) {
            ID = 314;
        }
        if (NamespacedName.equals("golden_chestplate")) {
            ID = 315;
        }
        if (NamespacedName.equals("golden_leggings")) {
            ID = 316;
        }
        if (NamespacedName.equals("golden_boots")) {
            ID = 317;
        }
        if (NamespacedName.equals("flint")) {
            ID = 318;
        }
        if (NamespacedName.equals("pork")) {
            ID = 319;
        }
        if (NamespacedName.equals("cooked_pork")) {
            ID = 320;
        }
        if (NamespacedName.equals("painting")) {
            ID = 321;
        }
        if (NamespacedName.equals("golden_apple")) {
            ID = 322;
        }
        if (NamespacedName.equals("sign")) {
            ID = 323;
        }
        if (NamespacedName.equals("wooden_door_item")) {
            ID = 324;
        }
        if (NamespacedName.equals("bucket")) {
            ID = 325;
        }
        if (NamespacedName.equals("water_bucket")) {
            ID = 326;
        }
        if (NamespacedName.equals("lava_bucket")) {
            ID = 327;
        }
        if (NamespacedName.equals("minecart")) {
            ID = 328;
        }
        if (NamespacedName.equals("saddle")) {
            ID = 329;
        }
        if (NamespacedName.equals("iron_door_item")) {
            ID = 330;
        }
        if (NamespacedName.equals("redstone_dust")) {
            ID = 331;
        }
        if (NamespacedName.equals("snowball")) {
            ID = 332;
        }
        if (NamespacedName.equals("boat")) {
            ID = 333;
        }
        if (NamespacedName.equals("leather")) {
            ID = 334;
        }
        if (NamespacedName.equals("milk_bucket")) {
            ID = 335;
        }
        if (NamespacedName.equals("clay_brick")) {
            ID = 336;
        }
        if (NamespacedName.equals("clay_balls")) {
            ID = 337;
        }
        if (NamespacedName.equals("reed")) {
            ID = 338;
        }
        if (NamespacedName.equals("paper")) {
            ID = 339;
        }
        if (NamespacedName.equals("book")) {
            ID = 340;
        }
        if (NamespacedName.equals("slime_ball")) {
            ID = 341;
        }
        if (NamespacedName.equals("storage_minecart")) {
            ID = 342;
        }
        if (NamespacedName.equals("powered_minecart")) {
            ID = 343;
        }
        if (NamespacedName.equals("egg")) {
            ID = 344;
        }
        if (NamespacedName.equals("gold_record")) {
            ID = 2256;
        }
        if (NamespacedName.equals("green_record")) {
            ID = 2257;
        }
        return ID;
    }
}
