package net.minecraft.clothutils;

import net.minecraft.src.PropertyManager;

import java.io.File;
import java.util.Random;

public class WorldGenParams {
    public long GetSeedFromPropertiesFile(){
     //  long seed = 0l;//failsafe
         PropertyManager propertyManager = new PropertyManager(new File("server.properties"));
        Random random = new Random();
        return propertyManager.getLongProperty("seed", random.nextLong());
    }
}
