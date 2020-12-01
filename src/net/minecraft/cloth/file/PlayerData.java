package net.minecraft.cloth.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class PlayerData {

    // The different types of data you can store for any given player
    private int deaths = 0;
    private int backuses = 0;
    private boolean hasFailedHardcore = false;
    private List<Location> locations = new ArrayList<Location>();
    private Location lastDeathLocation = new Location();
    private HashMap<Integer, Integer> playerServerInventory = new HashMap<Integer, Integer>();

    // Return a location with a matching name
    public Location getLocation(String locationName, ArrayList<Location> Locations){
        for(int counter = 0; counter < locations.size(); counter++){
            if(locations.get(counter).getName().equals(locationName)){
                return locations.get(counter);
            }
        }
        return null;
    }

    // Add a new location to the list from Location object
    public void addLocation(Location newLocation){
        locations.add(newLocation);
    }

    // Remove a specific location
    public void removeLocation(Location newLocation){
        locations.remove(newLocation);
    }

    // Remove all locations
    public void clearLocations(){
        this.locations.clear();
    }

    // Return the full list of locations as an ArrayList
    public ArrayList<Location> getLocations(){
        return (ArrayList<Location>) locations;
    }

    // Return the location of the players last death
    public Location getLastDeathLocation(){ return this.lastDeathLocation; }

    // Set the location of the players last death
    public void setLastDeathLocation( Double deathX, Double deathY, Double deathZ, Float deathLookYaw, Float deathLookPitch ){ this.lastDeathLocation.setName("Last Death Location"); this.lastDeathLocation.setLocationVector( deathX, deathY, deathZ ); this.lastDeathLocation.setLookVector( deathLookYaw, deathLookPitch ); }

    // Check if a player has failed harcore
    public boolean hasFailedHardcore() { return this.hasFailedHardcore; }

    // set if a player has failed the hardcore challenge or not
    public void setHasFailedHardcore(boolean failState) { this.hasFailedHardcore = failState; }

    // Remove a location from the list based on name
    public void removeLocation(String locationName, ArrayList<Location> Locations){
        for(int counter = 0; counter < locations.size(); counter++){
            if(locations.get(counter).getName().equals(locationName)){
                locations.remove(counter);
            }
        }
    }

    public int getDeaths(){
        return deaths;
    }
    public void addDeath(){
        deaths = deaths + 1;
    }
    public void setDeath(int newDeaths){
        deaths = newDeaths;
    }

    public int getBackUsages(){ return backuses; }
    public void addBackUsage(){
        backuses = backuses + 1;
    }
    public void resetBackUsages(){ backuses = 0; }

    public HashMap<Integer, Integer> getPlayerServerInventory(){return playerServerInventory;}
    public void addToServerSideInventory(int ItemID, int StackSize){
        if(!playerServerInventory.containsKey(ItemID)) {
            playerServerInventory.put(ItemID, StackSize);
        }
        else{
            playerServerInventory.replace(ItemID, playerServerInventory.get(ItemID)+StackSize);
        }
    }
    public void removeFromServerSideInventory(int ItemID, int StackSize){
        if(playerServerInventory.containsKey(ItemID)) {
          if(playerServerInventory.get(ItemID) == StackSize){
            playerServerInventory.remove(ItemID);
          }
          else{
            playerServerInventory.replace(ItemID, playerServerInventory.get(ItemID) - StackSize);
          }
        }
    }


}