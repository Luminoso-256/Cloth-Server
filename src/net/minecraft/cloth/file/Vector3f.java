package net.minecraft.cloth.file;

public class Vector3f {
    private float yaw;
    private float pitch;
    private float roll;

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public float getRoll() {
        return this.roll;
    }

    public void setVector3f(float yaw, float pitch, float roll) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
    }
}
