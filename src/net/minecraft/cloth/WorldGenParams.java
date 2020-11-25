package src.net.minecraft.cloth;

import src.net.minecraft.core.PropertyManager;

import java.io.File;
import java.util.Random;
/**
 * Helper functions to hook into world gen
 * @author Luminoso-256
 */
public class WorldGenParams {
    public long GetSeedFromPropertiesFile(){
     //  long seed = 0l;//failsafe
         PropertyManager propertyManager = new PropertyManager(new File("server.properties"));
        Random random = new Random();
        System.out.println("Debug: PropSeedGetOutput: "+ propertyManager.getLongProperty("seed", 1l));
        return propertyManager.getLongProperty("seed", random.nextLong());

    }
    public long GetOctavesA(){
        PropertyManager propertyManager = new PropertyManager(new File("server.properties"));
        return propertyManager.getLongProperty("OctaveMultiplierA", 9871L);
    }
    public long GetOctavesB(){
        PropertyManager propertyManager = new PropertyManager(new File("server.properties"));
        return propertyManager.getLongProperty("OctaveMultiplierB", 39811L);
    }
    public long GetOctavesC(){
        PropertyManager propertyManager = new PropertyManager(new File("server.properties"));
        return propertyManager.getLongProperty("OctaveMultiplierC", 0x84a59L);
    }
}
