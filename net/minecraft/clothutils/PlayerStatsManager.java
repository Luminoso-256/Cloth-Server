package net.minecraft.clothutils;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class PlayerStatsManager {
    Yaml yaml = new Yaml();
    Logger logger = Logger.getLogger("Minecraft");
    public void updateStat(String playerUsername, String statID, String newStatValue){ //for now, only string statistics
        try {
            File statsFile = new File("stats.yml");
            if (statsFile.createNewFile()) {
                logger.info("[Cloth] No stats file exists. Creating new one.");
            } else {
                logger.info("[Cloth] Loading stats file...");
            }
            //we now safely have out file
            InputStream fileInputStream = new FileInputStream(statsFile);
            HashMap statMap = (HashMap) yaml.load(fileInputStream);
            if(statMap == null){statMap = new HashMap<String, String>();} //prevent null access
            if(!statMap.containsKey(playerUsername)){
                ArrayList<String> stats = new ArrayList<String>();

                stats.add(statID+":"+newStatValue);

                statMap.put(playerUsername, stats);
            }
            else{
                ArrayList<String> stats = (ArrayList<String>) statMap.get(playerUsername);
                    stats.add(statID+" : "+newStatValue);
                    statMap.replace(playerUsername, stats);
            }
            //Save
            String saveabledata = yaml.dump(statMap);
            FileWriter writer = new FileWriter(statsFile);
            writer.write(saveabledata);
            writer.close();

        } catch (IOException e) {
            System.out.println("[Cloth] Error attempting to access stats file: ");
            e.printStackTrace();
        }

    }
    public String getStat(String playerUsername, String statID){
        String statValue = "error : no value found";
        try{
            File statsFile = new File("stats.yml");
            if (statsFile.createNewFile()) {
                logger.info("[Cloth] No stats file exists. Creating new one.");
            } else {
                logger.info("[Cloth] Loading stats file...");
            }
            InputStream fileInputStream = new FileInputStream(statsFile);
            HashMap statMap = (HashMap) yaml.load(fileInputStream);
            if(statMap == null){statMap = new HashMap<String, String>();} //prevent null access
            if(!statMap.containsKey(playerUsername)){
                statValue="none";
            }
            else{
                ArrayList<String> stats = (ArrayList<String>) statMap.get(playerUsername);
                if(stats.contains(statID)){
                    for(String stat:stats){
                        if(stat.contains(statID)){
                            String[] statSplit= stat.split(":");
                            statValue = statSplit[1];
                        }
                    }
                }
                else{
                    statValue="none";
                }


            }


        } catch (IOException e) {
            System.out.println("[Cloth] Error attempting to access stats file: ");
            e.printStackTrace();
        }

        return statValue;
    }

}
