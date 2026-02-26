package main.java.net.kallen.solaris.object;

import main.java.net.kallen.solaris.graphics.Mesh;
import main.java.net.kallen.solaris.graphics.Renderer;
import main.java.net.kallen.solaris.math.vector.Vector3;

public class GameObject {
    private Vector3 position, rotation, scale;
    private Mesh mesh;

    public GameObject(Vector3 position, Vector3 rotation, Vector3 scale, Mesh mesh) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.mesh = mesh;
    }

    public void render(Renderer renderer) {
        renderer.renderMesh(mesh, position);
    }

    public void update() {

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