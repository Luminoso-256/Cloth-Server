package net.minecraft.cloth.file;
// Essentially a copy of PropertyManager, but copied so that tweaks required for one file dont convolute the parser for the other

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Grab string-to-id mappings
 * @author Luminoso-256
 */
public class BlockMappingsManager
{

    public BlockMappingsManager(File file)
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
                logger.log(Level.WARNING, (new StringBuilder()).append("Failed to load - generating  new ").append(file).toString(), exception);
                generateNewProperties();
            }
        } else
        {
            logger.log(Level.WARNING, (new StringBuilder()).append(file).append(" does not exist, generating new").toString());
            generateNewProperties();
        }
    }

    public void generateNewProperties()
    {
        logger.log(Level.INFO, "Generating new blocks.mappings file");
        saveProperties();
    }

    public void saveProperties()
    {
        try
        {
            serverProperties.store(new FileOutputStream(serverPropertiesFile), "Cloth - ID <--> Namespaced names mappings file");
        }
        catch(Exception exception)
        {
            logger.log(Level.WARNING, (new StringBuilder()).append("Failed to save ").append(serverPropertiesFile).toString(), exception);
            generateNewProperties();
        }
    }

    public String getString(String s, String s1)
    {
        if(!serverProperties.containsKey(s))
        {
            serverProperties.setProperty(s, s1);
            saveProperties();
        }
        return serverProperties.getProperty(s, s1);
    }

    public int getIdForString(String name, int fallback)
    {
        try
        {
            return Integer.parseInt(getString(name, (new StringBuilder()).append("").append(fallback).toString()));

        }
        catch(Exception exception)
        {
            serverProperties.setProperty(name, (new StringBuilder()).append("").append(fallback).toString());
        }
        return fallback;
    }


    public static Logger logger = Logger.getLogger("Minecraft");
    private Properties serverProperties;
    private File serverPropertiesFile;

}

