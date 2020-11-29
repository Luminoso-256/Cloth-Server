package net.minecraft.cloth.file;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PlayerDataManager {

    //--------Player data properties:
    //gamemode:<gamemode>
    //hardcore-failed:<boolean>
    //stats stuff

    Logger logger = Logger.getLogger("Minecraft");
    public void updateStat(String playerUsername, String statID, String newStatValue){ //for now, only string statistics
        Gson gson = new Gson();
        try {
            File playerDataFile = new File("playerData.json");
            if (playerDataFile.createNewFile()) {
                logger.info("[Cloth] No playerData file exists. Creating new one.");
            } else {
             //   logger.info("[Cloth] Loading playerData file...");
            }
            //we now safely have out file
            Reader reader = Files.newBufferedReader(Paths.get(playerDataFile.getAbsolutePath()));
            Map<String, ArrayList<String>> statMap = gson.fromJson(reader, Map.class);
            if(statMap == null){
                statMap = new HashMap<String, ArrayList<String>>();
            }
            if(!statMap.containsKey(playerUsername)){
                ArrayList<String> playerData = new ArrayList<String>();

                playerData.add(statID+":"+newStatValue);

                statMap.put(playerUsername, playerData);
            }
            else{
                ArrayList newplayerData = new ArrayList<String>();
                ArrayList<String> playerData = (ArrayList<String>) statMap.get(playerUsername);
                    for (String stat:playerData){
                        if (stat.contains(statID)){
                            newplayerData.add(statID+":"+newStatValue);
                        }
                        else{
                            newplayerData.add(stat);
                        }
                    }
                    System.out.println("Old playerData: "+playerData);
                    System.out.println("Setting playerData to"+newplayerData);
                    statMap.replace(playerUsername, newplayerData);
            }
            //Save

            String saveabledata = gson.toJson(statMap);
            FileWriter writer = new FileWriter(playerDataFile);
            writer.write(saveabledata);
            writer.close();

        } catch (IOException e) {
            System.out.println("[Cloth] Error attempting to access playerData file: ");
            e.printStackTrace();
        }

    }

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

    public String getStat(String playerUsername, String statID){
        Gson gson = new Gson();
        String statValue = "none";
        try{
            File playerDataFile = new File("playerdata.json");
            if (playerDataFile.createNewFile()) {
                logger.info("[Cloth] No playerData file exists. Creating new one.");
            } else {
               // logger.info("[Cloth] Loading playerData file...");
            }
            Reader reader = Files.newBufferedReader(Paths.get(playerDataFile.getAbsolutePath()));
            Map<String, ArrayList<String>> statMap = gson.fromJson(reader, Map.class);

            if(statMap == null){statMap = new HashMap<String, ArrayList<String>>(); System.out.println("Error null stat hashmap");} //prevent null access
            System.out.println(statMap);
            if(!statMap.containsKey(playerUsername)){
                statValue="none";
                System.out.println("playerData doesn't contain player username");
            }
            else{
                ArrayList<String> playerData = (ArrayList<String>) statMap.get(playerUsername);
                for(String stat:playerData){
                    if(stat.contains(statID)){
                        String[] playerDataplit = stat.split(":");
                        statValue = playerDataplit[1];
                    }
                }


            }


        } catch (IOException e) {
            System.out.println("[Cloth] Error attempting to access playerData file: ");
            e.printStackTrace();
        }

        return statValue;
    }

}
