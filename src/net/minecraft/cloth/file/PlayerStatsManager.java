package net.minecraft.cloth.file;


import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PlayerStatsManager {

    Logger logger = Logger.getLogger("Minecraft");
    public void updateStat(String playerUsername, String statID, String newStatValue){ //for now, only string statistics
        Gson gson = new Gson();
        try {
            File statsFile = new File("stats.json");
            if (statsFile.createNewFile()) {
                logger.info("[Cloth] No stats file exists. Creating new one.");
            } else {
             //   logger.info("[Cloth] Loading stats file...");
            }
            //we now safely have out file
            Reader reader = Files.newBufferedReader(Paths.get(statsFile.getAbsolutePath()));
            Map<String, ArrayList<String>> statMap = gson.fromJson(reader, Map.class);
            if(statMap == null){
                statMap = new HashMap<String, ArrayList<String>>();
            }
            if(!statMap.containsKey(playerUsername)){
                ArrayList<String> stats = new ArrayList<String>();

                stats.add(statID+":"+newStatValue);

                statMap.put(playerUsername, stats);
            }
            else{
                ArrayList newStats = new ArrayList<String>();
                ArrayList<String> stats = (ArrayList<String>) statMap.get(playerUsername);
                    for (String stat:stats){
                        if (stat.contains(statID)){
                            newStats.add(statID+":"+newStatValue);
                        }
                        else{
                            newStats.add(stat);
                        }
                    }
                    System.out.println("Old Stats: "+stats);
                    System.out.println("Setting stats to"+newStats);
                    statMap.replace(playerUsername, newStats);
            }
            //Save

            String saveabledata = gson.toJson(statMap);
            FileWriter writer = new FileWriter(statsFile);
            writer.write(saveabledata);
            writer.close();

        } catch (IOException e) {
            System.out.println("[Cloth] Error attempting to access stats file: ");
            e.printStackTrace();
        }

    }
    public String getStat(String playerUsername, String statID){
        Gson gson = new Gson();
        String statValue = "none";
        try{
            File statsFile = new File("stats.json");
            if (statsFile.createNewFile()) {
                logger.info("[Cloth] No stats file exists. Creating new one.");
            } else {
               // logger.info("[Cloth] Loading stats file...");
            }
            Reader reader = Files.newBufferedReader(Paths.get(statsFile.getAbsolutePath()));
            Map<String, ArrayList<String>> statMap = gson.fromJson(reader, Map.class);

            if(statMap == null){statMap = new HashMap<String, ArrayList<String>>(); System.out.println("Error null stat hashmap");} //prevent null access
            System.out.println(statMap);
            if(!statMap.containsKey(playerUsername)){
                statValue="none";
                System.out.println("Stats doesnt contai player username");
            }
            else{
                ArrayList<String> stats = (ArrayList<String>) statMap.get(playerUsername);
                for(String stat:stats){
                    if(stat.contains(statID)){
                        String[] statSplit = stat.split(":");
                        statValue = statSplit[1];
                    }
                }


            }


        } catch (IOException e) {
            System.out.println("[Cloth] Error attempting to access stats file: ");
            e.printStackTrace();
        }

        return statValue;
    }

}
