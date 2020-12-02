package net.minecraft.cloth.file;

public class Location {
    private String name = "";
    private Vector3d vec = new Vector3d();
    private Vector3f lookvec = new Vector3f();

    //private Vector<Double>  vec     = new Vector<>();
    //private Vector<Float>   lookvec = new Vector<>();

    // Returns a Vector of the location
    public Vector3d getLocationVector() {
        Double x = this.vec.getX();
        Double y = this.vec.getY();
        Double z = this.vec.getZ();
        vec.setVector3d(x, y, z);

        return vec;
    }

    // Return the Rotation Vector of the location
    public Vector3f getLookVector() {
        float lookYaw = this.lookvec.getYaw();
        float lookPitch = this.lookvec.getPitch();
        lookvec.setVector3f(lookYaw, lookPitch, 0);
        return lookvec;
    }

    // Allows you to set a Vector for the location
    public void setLocationVector(Double ix, Double iy, Double iz) {
        vec.setVector3d(ix, iy, iz);
    }

    // Sets the look vector for the location
    public void setLookVector(Float ilookYaw, Float ilookPitch) {
        lookvec.setVector3f(ilookYaw, ilookPitch, 0);
    }

    // Allow you to get the name of the location
    public String getName() {
        return name;
    }

    // Allow you to set the name of the location
    public void setName(String newName) {
        name = newName;
    }
}
