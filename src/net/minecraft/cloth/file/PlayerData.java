package net.minecraft.cloth.file;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {

    // The different types of data you can store for any given player
    private int deaths = 0;
    private boolean hasFailedHardcore = false;
    private List<Location> locations = new ArrayList<Location>();

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

    // Return the amount of deaths the player has endured
    public int getDeaths(){
        return deaths;
    }

    // Add to the that list
    public void addDeath(){
        deaths = deaths + 1;
    }

    public void setDeath(int newDeaths){
        deaths = newDeaths;
    }
}