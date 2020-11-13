package net.minecraft.clothutils;
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

    public GameruleManager(File file)
    {
        serverProperties = new Properties();
        serverPropertiesFile = file;
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

    public void generateNewProperties()
    {
        logger.log(Level.INFO, "Generating new gamerules file");
        saveProperties();
    }

    public void saveProperties()
    {
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

    public String getStringGamerule(String s, String s1)
    {
        if(!serverProperties.containsKey(s))
        {
            serverProperties.setProperty(s, s1);
            saveProperties();
        }
        return serverProperties.getProperty(s, s1);
    }

    public int getIntGamerule(String s, int i)
    {
        try
        {
            return Integer.parseInt(getStringGamerule(s, (new StringBuilder()).append("").append(i).toString()));
        }
        catch(Exception exception)
        {
            serverProperties.setProperty(s, (new StringBuilder()).append("").append(i).toString());
        }
        return i;
    }

    public boolean getBooleanGamerule(String s, boolean flag)
    {
        try
        {
            return Boolean.parseBoolean(getStringGamerule(s, (new StringBuilder()).append("").append(flag).toString()));
        }
        catch(Exception exception)
        {
            serverProperties.setProperty(s, (new StringBuilder()).append("").append(flag).toString());
        }
        return flag;
    }

    public void setBooleanGamerule(String Gamerule, boolean flag){
        serverProperties.setProperty(Gamerule, (new StringBuilder()).append("").append(flag).toString());
    }
    public void setStringGamerule(String Gamerule, String value){
        serverProperties.setProperty(Gamerule, (new StringBuilder()).append("").append(value).toString());
    }
    public void setIntGamerule(String Gamerule, int value){
        serverProperties.setProperty(Gamerule, (new StringBuilder()).append("").append(value).toString());
    }
    public static Logger logger = Logger.getLogger("Minecraft");
    private Properties serverProperties;
    private File serverPropertiesFile;

}

