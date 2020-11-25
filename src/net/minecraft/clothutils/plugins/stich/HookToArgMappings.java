package net.minecraft.clothutils.plugins.stich;


public class HookToArgMappings {

    public static String GetArgsListForHook(String HookName){
        String ArgMapping = "Error";
        switch(HookName){
            case "OnServerInit":
                ArgMapping = "None";
                break;
            case "OnBlockCreate":
                ArgMapping = "Entity,X,Y,Z";
                break;
            case "OnBlockDestroy":
                ArgMapping = "Block,Entity,WasExplosion";
                break;
            case "OnEntitySpawn":
                ArgMapping = "EntityType,X,Y,Z";

        }
        return ArgMapping;

    }
}
