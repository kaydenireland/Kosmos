package main.java.net.kallen.engine.objects;


import main.java.net.kallen.engine.graphics.Mesh;
import main.java.net.kallen.engine.math.Vector3;

public class GameObject {
    private Vector3 position, rotation, scale;
    private Mesh mesh;
    private double temp;

    public GameObject(Vector3 position, Vector3 rotation, Vector3 scale, Mesh mesh) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.mesh = mesh;
    }

    public void update() {
        temp += 0.02;
        position.setX((float) Math.sin(temp));
        rotation.set((float) Math.sin(temp) * 360, (float) Math.sin(temp) * 360, (float) Math.sin(temp) * 360);
        scale.set((float) Math.sin(temp), (float) Math.sin(temp), (float) Math.sin(temp));
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getRotation() {
        return rotation;
    }

    public Vector3 getScale() {
        return scale;
    }

    public Mesh getMesh() {
        return mesh;
    }
}