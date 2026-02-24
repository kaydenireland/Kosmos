package main.java.net.kallen.solaris.graphics;


import main.java.net.kallen.solaris.math.vector.Vector2;
import main.java.net.kallen.solaris.math.vector.Vector3;


public class Vertex {
    private Vector3 position;
    private Vector2 texturePos;

    public Vertex(Vector3 position, Vector2 texturePos) {
        this.position = position;
        this.texturePos = texturePos;
    }

    public Vector3 getPosition() {
        return position;
    }


    public Vector2 getTexturePos() {
        return texturePos;
    }
}
