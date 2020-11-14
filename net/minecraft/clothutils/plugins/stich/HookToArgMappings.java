package net.minecraft.clothutils.plugins.stich;


public class HookToArgMappings {

    public static String GetArgsListForHook(String HookName){
        String ArgMapping = "Error";
        switch(HookName){
            case "OnServerInit":
                ArgMapping = "None";
                break;
            case "OnBlockCreate":
                ArgMapping = "Block,Entity";
                break;
            case "OnBlockDestroy":
                ArgMapping = "Block,Entity,WasExplosion";
                break;
        }
        return ArgMapping;

    }
}
