package main.java.net.kallen.engine.graphics;


import main.java.net.kallen.engine.math.Vector2;
import main.java.net.kallen.engine.math.Vector3;


public class Vertex {
    private Vector3 position, color;
    private Vector2 texturePos;

    public Vertex(Vector3 position, Vector3 color, Vector2 texturePos) {
        this.position = position;
        this.color = color;
        this.texturePos = texturePos;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getColor() {
        return color;
    }

    public Vector2 getTexturePos() {
        return texturePos;
    }
}
