package net.minecraft.cloth.file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameruleManager {
	private static GameruleManager instance;
	
	private Logger logger = Logger.getLogger("Minecraft");
	private Properties serverGamerules = new Properties();
	private Timer timer = new Timer();
	private File gameruleFile = new File("server.gamerules");
	private Path gamerulesPath = Paths.get("server.gamerules");
	private File gamerulesFile = gamerulesPath.toFile();
	private FileTime lastModified;
	
    private GameruleManager() {
        if(gamerulesFile.exists()) {
            try {
                Reload();
                lastModified = Files.readAttributes(gamerulesPath, BasicFileAttributes.class).lastModifiedTime();
            }
            catch(Exception exception) {
                logger.log(Level.WARNING, (new StringBuilder()).append("Failed to load ").append(gamerulesFile).toString(), exception);
                Save();
            }
        } else {
            logger.log(Level.WARNING, (new StringBuilder()).append(gamerulesFile).append(" does not exist").toString());
            Save();
        }
        timer.schedule(new ReloadCheck(), 1000, 1000);
    }
    
    static {
        instance = new GameruleManager();
    }
    
    public static GameruleManager getInstance() {
        return instance;
    }
    
    class ReloadCheck extends TimerTask {
    	public void run() {
    		FileTime newModified;
    		try {
                newModified = Files.readAttributes(gamerulesPath, BasicFileAttributes.class).lastModifiedTime();
                if(newModified != lastModified) Reload();
            }
            catch(Exception exception) {
                logger.log(Level.WARNING, "couldn't read gamerule timestamp", exception);
            }
    	}
    }
    
    public void Reload() throws FileNotFoundException, IOException {
    	serverGamerules.load(new FileInputStream(gamerulesFile));
    }
    
    public void Save() {
    	try {
            serverGamerules.store(new FileOutputStream(gamerulesFile), "Cloth Gamerule Data");
        }
        catch(Exception exception) {
            logger.log(Level.WARNING, "Failed to save gamerules", exception);
        }
    }
    
    public String getGamerule(String gamerule, String value) {
    	if(!serverGamerules.containsKey(gamerule)) {
    		serverGamerules.setProperty(gamerule, value);
            Save();
        }
        return serverGamerules.getProperty(gamerule, value);
    }
    
    public boolean getGamerule(String gamerule, boolean value) {
    	try {
            return Boolean.parseBoolean(getGamerule(gamerule, String.valueOf(value)));
        }
        catch(Exception exception) {
            setGamerule(gamerule, value);
        }
        return value;
    }
    
    public int getGamerule(String gamerule, int value) {
        try {
            return Integer.parseInt(getGamerule(gamerule, String.valueOf(value)));
        }
        catch(Exception exception) {
            setGamerule(gamerule, value);
        }
        return value;
    }
    
    public void setGamerule(String gamerule, String value) {
    	serverGamerules.setProperty(gamerule, value);
    	Save();
    }
    
    public void setGamerule(String gamerule, boolean value) {
    	serverGamerules.setProperty(gamerule, String.valueOf(value));
    	Save();
    }
    
    public void setGamerule(String gamerule, int value) {
    	serverGamerules.setProperty(gamerule, String.valueOf(value));
    	Save();
    }
}
