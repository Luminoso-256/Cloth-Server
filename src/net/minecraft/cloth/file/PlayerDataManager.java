package net.minecraft.cloth.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class PlayerDataManager {

    //--------Player Data Statistics
    // Deaths
    // Locations
    // hasFailedHardcore

    Logger logger = Logger.getLogger("Minecraft");

    public PlayerData getPlayerData(String username){
        Gson gson = new Gson();

        try {
            File playerDataFile = new File("players/"+username+".json");
            if (playerDataFile.createNewFile()) {
                logger.info("[Cloth] No playerData file exists. Creating new one.");
            }

            Reader reader = Files.newBufferedReader(Paths.get(playerDataFile.getAbsolutePath()));
            PlayerData playerDataObj = gson.fromJson(reader, PlayerData.class);
            reader.close();

            return playerDataObj;

        } catch (IOException e) {
            System.out.println("[Cloth] Error attempting to access playerData file: ");

            e.printStackTrace();
            return null;
        }
    }

    public void setPlayerData(String username, PlayerData newPlayerData){
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try {
            File playerDataFile = new File("players/"+username+".json");
            if (playerDataFile.createNewFile()) {
                logger.info("[Cloth] No playerData file exists. Creating new one.");
            }

            String saveabledata = gson.toJson(newPlayerData);
            FileWriter writer = new FileWriter(playerDataFile);
            writer.write(saveabledata);
            writer.close();

        } catch (IOException e) {
            System.out.println("[Cloth] Error attempting to access playerData file: ");

            e.printStackTrace();
        }
    }

}
