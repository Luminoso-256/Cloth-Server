package net.minecraft.cloth.file;
// Essentially a copy of PropertyManager, but copied so that tweaks required for one file dont convolute the parser for the other

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * core gamerule class
 * @author Luminoso-256
 */
public class GameruleManager
{

    public GameruleManager()
    {
        serverProperties = new Properties();
        File file = new File("server.gamerules");
        if(file.exists())
        {
            try
            {
                serverProperties.load(new FileInputStream(file));
            }
            catch(Exception exception)
            {
                logger.log(Level.WARNING, (new StringBuilder()).append("Failed to load ").append(file).toString(), exception);
                generateNewProperties();
            }
        } else
        {
            logger.log(Level.WARNING, (new StringBuilder()).append(file).append(" does not exist").toString());
            generateNewProperties();
        }
    }

    public static void generateNewProperties()
    {
        logger.log(Level.INFO, "Generating new gamerules file");
        saveNewProperties();
    }

    public static void saveNewProperties()
    {
        Properties serverProperties = new Properties();
        File serverPropertiesFile = new File("server.gamerules");

        try
        {
            serverProperties.store(new FileOutputStream(serverPropertiesFile), "Cloth Gamerule Data");
        }
        catch(Exception exception)
        {
            logger.log(Level.WARNING, (new StringBuilder()).append("Failed to save ").append(serverPropertiesFile).toString(), exception);
            generateNewProperties();
        }
    }

    public static Properties loadProperties() {
    	try
        {
    		Properties serverProperties = new Properties();
            serverProperties.load(new FileInputStream(new File("server.gamerules")));
            return serverProperties;
        }
        catch(Exception exception)
        {
            logger.log(Level.WARNING, "Failed to load gamerules", exception);
        }
    	return null;
    }
    
    public static void saveProperties(Properties serverProperties) {
    	try
        {
            serverProperties.store(new FileOutputStream(new File("server.gamerules")), "Cloth Gamerule Data");
        }
        catch(Exception exception)
        {
            logger.log(Level.WARNING, "Failed to save gamerules", exception);
        }
    }
    
    public static String getStringGamerule(String s, String s1)
    {
        Properties serverProperties = loadProperties();
        if(!serverProperties.containsKey(s))
        {
            serverProperties.setProperty(s, s1);
            saveProperties(serverProperties);
        }
        return serverProperties.getProperty(s, s1);
    }

    public static int getIntGamerule(String s, int i)
    {
    	Properties serverProperties = loadProperties();
        try
        {
            return Integer.parseInt(getStringGamerule(s, (new StringBuilder()).append("").append(i).toString()));
        }
        catch(Exception exception)
        {
            serverProperties.setProperty(s, (new StringBuilder()).append("").append(i).toString());
            saveProperties(serverProperties);
        }
        return i;
    }

    public static boolean getBooleanGamerule(String s, boolean flag)
    {
    	Properties serverProperties = loadProperties();
        try
        {
            return Boolean.parseBoolean(getStringGamerule(s, (new StringBuilder()).append("").append(flag).toString()));
        }
        catch(Exception exception)
        {
            serverProperties.setProperty(s, (new StringBuilder()).append("").append(flag).toString());
            saveProperties(serverProperties);
        }
        return flag;
    }

    public static void setBooleanGamerule(String Gamerule, boolean flag){
    	Properties serverProperties = loadProperties();
        serverProperties.setProperty(Gamerule, (new StringBuilder()).append("").append(flag).toString());
        saveProperties(serverProperties);
    }
    public static void setStringGamerule(String Gamerule, String value){
    	Properties serverProperties = loadProperties();
        serverProperties.setProperty(Gamerule, (new StringBuilder()).append("").append(value).toString());
        saveProperties(serverProperties);
    }
    public static void setIntGamerule(String Gamerule, int value){
    	Properties serverProperties = loadProperties();
        serverProperties.setProperty(Gamerule, (new StringBuilder()).append("").append(value).toString());
        saveProperties(serverProperties);
    }
    public static Logger logger = Logger.getLogger("Minecraft");
    private Properties serverProperties;

}

