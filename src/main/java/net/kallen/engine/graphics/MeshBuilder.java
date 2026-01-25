package main.java.net.kallen.engine.graphics;

import main.java.net.kallen.engine.math.Vector2;
import main.java.net.kallen.engine.math.Vector3;

public class MeshBuilder {
    
    private static final float SIZE = 0.5f;

    public static Mesh buildCube(Material material) {
        return new Mesh(new Vertex[] {
                // Back face
                new Vertex(new Vector3(-SIZE,  SIZE, -SIZE), new Vector2(0.0f, 0.0f)),
                new Vertex(new Vector3(-SIZE, -SIZE, -SIZE), new Vector2(0.0f, 1.0f)),
                new Vertex(new Vector3( SIZE, -SIZE, -SIZE), new Vector2(1.0f, 1.0f)),
                new Vertex(new Vector3( SIZE,  SIZE, -SIZE), new Vector2(1.0f, 0.0f)),

                // Front face
                new Vertex(new Vector3(-SIZE,  SIZE,  SIZE), new Vector2(0.0f, 0.0f)),
                new Vertex(new Vector3(-SIZE, -SIZE,  SIZE), new Vector2(0.0f, 1.0f)),
                new Vertex(new Vector3( SIZE, -SIZE,  SIZE), new Vector2(1.0f, 1.0f)),
                new Vertex(new Vector3( SIZE,  SIZE,  SIZE), new Vector2(1.0f, 0.0f)),

                // Right face
                new Vertex(new Vector3( SIZE,  SIZE, -SIZE), new Vector2(0.0f, 0.0f)),
                new Vertex(new Vector3( SIZE, -SIZE, -SIZE), new Vector2(0.0f, 1.0f)),
                new Vertex(new Vector3( SIZE, -SIZE,  SIZE), new Vector2(1.0f, 1.0f)),
                new Vertex(new Vector3( SIZE,  SIZE,  SIZE), new Vector2(1.0f, 0.0f)),

                // Left face
                new Vertex(new Vector3(-SIZE,  SIZE, -SIZE), new Vector2(0.0f, 0.0f)),
                new Vertex(new Vector3(-SIZE, -SIZE, -SIZE), new Vector2(0.0f, 1.0f)),
                new Vertex(new Vector3(-SIZE, -SIZE,  SIZE), new Vector2(1.0f, 1.0f)),
                new Vertex(new Vector3(-SIZE,  SIZE,  SIZE), new Vector2(1.0f, 0.0f)),

                // Top face
                new Vertex(new Vector3(-SIZE,  SIZE,  SIZE), new Vector2(0.0f, 0.0f)),
                new Vertex(new Vector3(-SIZE,  SIZE, -SIZE), new Vector2(0.0f, 1.0f)),
                new Vertex(new Vector3( SIZE,  SIZE, -SIZE), new Vector2(1.0f, 1.0f)),
                new Vertex(new Vector3( SIZE,  SIZE,  SIZE), new Vector2(1.0f, 0.0f)),

                // Bottom face
                new Vertex(new Vector3(-SIZE, -SIZE,  SIZE), new Vector2(0.0f, 0.0f)),
                new Vertex(new Vector3(-SIZE, -SIZE, -SIZE), new Vector2(0.0f, 1.0f)),
                new Vertex(new Vector3( SIZE, -SIZE, -SIZE), new Vector2(1.0f, 1.0f)),
                new Vertex(new Vector3( SIZE, -SIZE,  SIZE), new Vector2(1.0f, 0.0f)),
        }, Faces.CUBE, material);
    }
}
