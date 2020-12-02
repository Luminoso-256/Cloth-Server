package net.minecraft.cloth.file;


import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class AdvancementManager {

    Logger logger = Logger.getLogger("Minecraft");

    public boolean grantAdvancement(String playerUsername, String advancementID) {
        try {
            Gson gson = new Gson();
            File advancementsFile = new File("advancements.json");
            if (advancementsFile.createNewFile()) {
                logger.info("[Cloth] No advancements file exists. Creating new one.");
            }
            //we now safely have out file
            Reader reader = Files.newBufferedReader(Paths.get(advancementsFile.getAbsolutePath()));
            Map<String, ArrayList<String>> advancementMap = gson.fromJson(reader, Map.class);

            //HashMap advancementMap = (HashMap) yaml.load(fileInputStream);
            if (advancementMap == null) {
                advancementMap = new HashMap<>();
            } //prevent null access
            if (!advancementMap.containsKey(playerUsername)) {
                ArrayList<String> advancements = new ArrayList<String>();
                advancements.add(advancementID);
                advancementMap.put(playerUsername, advancements);
            } else {
                ArrayList<String> advancements = (ArrayList<String>) advancementMap.get(playerUsername);
                if (!advancements.contains(advancementID)) {
                    advancements.add(advancementID);
                    advancementMap.replace(playerUsername, advancements);
                } else {
                    return false;
                }
            }

            //Save
            String saveabledata = gson.toJson(advancementMap);
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

    public ArrayList<String> getAdvancementsForPlayer(String playerUsername) {
        ArrayList<String> advancements = new ArrayList<String>();
        Gson gson = new Gson();
        File advancementsFile = new File("advancements.json");
        try {
            if (advancementsFile.createNewFile()) {
                logger.info("[Cloth] No advancements file exists. Creating new one.");
            }
            //we now safely have out file
            Reader reader = Files.newBufferedReader(Paths.get(advancementsFile.getAbsolutePath()));
            Map<String, ArrayList<String>> advancementMap = gson.fromJson(reader, Map.class);

            if (advancementMap == null) {
                advancementMap = new HashMap<>();
            } //prevent null access
            if (!advancementMap.containsKey(playerUsername)) {
                advancements = new ArrayList<String>();
            } else {
                advancements = (ArrayList<String>) advancementMap.get(playerUsername);
            }

            //Save
            String saveabledata = gson.toJson(advancementMap);
            FileWriter writer = new FileWriter(advancementsFile);
            writer.write(saveabledata);
            writer.close();

        } catch (IOException e) {
            System.out.println("[Cloth] Error attempting to access Advancements file: ");
            e.printStackTrace();

        }
        return advancements;

    }
}
