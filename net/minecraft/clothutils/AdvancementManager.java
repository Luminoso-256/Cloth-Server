package net.minecraft.clothutils;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdvancementManager {
    Yaml yaml = new Yaml();
    Logger logger = Logger.getLogger("Minecraft");
    public boolean grantAdvancement(String playerUsername, String advancementID){
        try {
            File advancementsFile = new File("advancements.yml");
            if (advancementsFile.createNewFile()) {
                logger.info("[Cloth] No advancements file exists. Creating new one.");
            } else {
              //  logger.info("[Cloth] Loading advancements file...");
            }
            //we now safely have out file
            InputStream fileInputStream = new FileInputStream(advancementsFile);
            HashMap advancementMap = (HashMap) yaml.load(fileInputStream);
            if(advancementMap == null){advancementMap = new HashMap<String, String>();} //prevent null access
            if(!advancementMap.containsKey(playerUsername)){
                ArrayList<String> advancements = new ArrayList<String>();
                advancements.add(advancementID);
                advancementMap.put(playerUsername, advancements);
            }
            else{
                ArrayList<String> advancements = (ArrayList<String>) advancementMap.get(playerUsername);
                if(!advancements.contains(advancementID)) {
                    advancements.add(advancementID);
                    advancementMap.replace(playerUsername, advancements);
                }
                else{
                    return false;
                }
            }
            //Save
            String saveabledata = yaml.dump(advancementMap);
            FileWriter writer = new FileWriter(advancementsFile);
            writer.write(saveabledata);
            writer.close();

        } catch (IOException e) {
            System.out.println("[Cloth] Error attempting to access Advancements file: ");
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
