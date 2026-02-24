package main.java.net.kallen.solaris.object.camera;

import main.java.net.kallen.solaris.math.vector.Vector3;

public class Camera {
    protected Vector3 position;
    protected Vector3 rotation;

    public Camera(Vector3 position, Vector3 rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public void update() {
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getRotation() {
        return rotation;
    }

    public void setPosition(Vector3 position) {
        this.position.set(position.getX(), position.getY(), position.getZ());
    }

    public void setRotation(Vector3 rotation) {
        this.rotation.set(rotation.getX(), rotation.getY(), rotation.getZ());
    }

    public Vector3 getForward() {
        float yaw = (float) Math.toRadians(rotation.getY());
        float pitch = (float) Math.toRadians(rotation.getX());

        return new Vector3(
                -(float) Math.sin(yaw) * (float) Math.cos(pitch),
                -(float) Math.sin(pitch),
                -(float) Math.cos(yaw) * (float) Math.cos(pitch)
        );
    }

    public Vector3 getRight() {
        float yaw = (float) Math.toRadians(rotation.getY());
        return new Vector3(
                (float) Math.cos(yaw),
                0,
                -(float) Math.sin(yaw)
        );
    }

    public Vector3 getUp() {
        return new Vector3(0, 1, 0);
    }
}