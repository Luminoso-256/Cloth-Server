package net.minecraft.cloth.file;

import java.util.Vector;

public class Location {
    private String          name    = "";
    private Vector<Double>  vec     = new Vector<>();
    private Vector<Float>   lookvec = new Vector<>();

    // Returns a Vector of the location
    public Vector<Double> getLocationVector(){
        Double x = vec.get(0);
        Double y = vec.get(1);
        Double z = vec.get(2);
        Vector<Double> locVec = new Vector<>();
        locVec.add(x);
        locVec.add(y);
        locVec.add(z);

        return locVec;
    }

    // Return the Rotation Vector of the location
    public Vector<Float> getLookVector(){
        Float lookYaw   = lookvec.get(0);
        Float lookPitch = lookvec.get(1);
        Vector<Float> lookVec = new Vector<>();
        lookVec.add(lookYaw);
        lookVec.add(lookPitch);

        return lookVec;
    }

    // Allows you to set a Vector for the location
    public void setLocationVector(Double ix, Double iy, Double iz){
        vec.clear();
        vec.add(ix);
        vec.add(iy);
        vec.add(iz);

    }

    // Sets the look vector for the location
    public void setLookVector(Float ilookYaw, Float ilookPitch ){
        lookvec.clear();
        lookvec.add(ilookYaw);
        lookvec.add(ilookPitch);

    }

    // Allow you to set the name of the location
    public void setName(String newName){
        name = newName;
    }

    // Allow you to get the name of the location
    public String getName(){
        return name;
    }
}
