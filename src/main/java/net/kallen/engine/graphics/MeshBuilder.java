package main.java.net.kallen.engine.graphics;

import main.java.net.kallen.engine.math.Vector2;
import main.java.net.kallen.engine.math.Vector3;

public class MeshBuilder {
    
    private static final float SIZE = 0.5f;

    public static Mesh buildTriangle(Material material) {
        return new Mesh(new Vertex[] {

                new Vertex(new Vector3(0, SIZE, SIZE), new Vector2(0.5f, 0.0f)),
                new Vertex(new Vector3(-SIZE, -SIZE, SIZE), new Vector2(1.0f, 1.0f)),
                new Vertex(new Vector3(SIZE, -SIZE, SIZE), new Vector2(0.0f, 1.0f))

        }, Faces.TRIANGLE, material);
    }

    public static Mesh buildSquare(Material material) {
        return new Mesh(new Vertex[] {
                new Vertex(new Vector3(-SIZE,  SIZE,  SIZE), new Vector2(0.0f, 0.0f)),
                new Vertex(new Vector3(-SIZE, -SIZE,  SIZE), new Vector2(0.0f, 1.0f)),
                new Vertex(new Vector3( SIZE, -SIZE,  SIZE), new Vector2(1.0f, 1.0f)),
                new Vertex(new Vector3( SIZE,  SIZE,  SIZE), new Vector2(1.0f, 0.0f))
        }, Faces.RECTANGLE, material);
    }

    public static Mesh buildTrianglePyramid(Material material) {
        return new Mesh(new Vertex[] {
                // Bottom face
                new Vertex(new Vector3(0, -SIZE, -SIZE), new Vector2(0.5f, 0.0f)),
                new Vertex(new Vector3(SIZE, -SIZE, SIZE), new Vector2(1.0f, 1.0f)),
                new Vertex(new Vector3(-SIZE, -SIZE, SIZE), new Vector2(0.0f, 1.0f)),

                // Front face
                new Vertex(new Vector3(0, -SIZE, -SIZE), new Vector2(0.5f, 1.0f)),
                new Vertex(new Vector3(SIZE, -SIZE, SIZE), new Vector2(1.0f, 1.0f)),
                new Vertex(new Vector3(0, SIZE, 0), new Vector2(0.5f, 0.0f)),

                // Right face
                new Vertex(new Vector3(SIZE, -SIZE, SIZE), new Vector2(0.0f, 1.0f)),
                new Vertex(new Vector3(-SIZE, -SIZE, SIZE), new Vector2(1.0f, 1.0f)),
                new Vertex(new Vector3(0, SIZE, 0), new Vector2(0.5f, 0.0f)),

                // Left face
                new Vertex(new Vector3(-SIZE, -SIZE, SIZE), new Vector2(0.0f, 1.0f)),
                new Vertex(new Vector3(0, -SIZE, -SIZE), new Vector2(1.0f, 1.0f)),
                new Vertex(new Vector3(0, SIZE, 0), new Vector2(0.5f, 0.0f))
        }, Faces.TRIANGLE_PYRAMID, material);
    }

    public static Mesh buildSquarePyramid(Material material) {
        return new Mesh(new Vertex[] {
                // Bottom face
                new Vertex(new Vector3(-SIZE, -SIZE, -SIZE), new Vector2(0.0f, 0.0f)),
                new Vertex(new Vector3(SIZE, -SIZE, -SIZE), new Vector2(1.0f, 0.0f)),
                new Vertex(new Vector3(SIZE, -SIZE, SIZE), new Vector2(1.0f, 1.0f)),
                new Vertex(new Vector3(-SIZE, -SIZE, SIZE), new Vector2(0.0f, 1.0f)),

                // Front face
                new Vertex(new Vector3(-SIZE, -SIZE, SIZE), new Vector2(0.0f, 1.0f)),
                new Vertex(new Vector3(SIZE, -SIZE, SIZE), new Vector2(1.0f, 1.0f)),
                new Vertex(new Vector3(0, SIZE, 0), new Vector2(0.5f, 0.0f)),

                // Back face
                new Vertex(new Vector3(SIZE, -SIZE, -SIZE), new Vector2(0.0f, 1.0f)),
                new Vertex(new Vector3(-SIZE, -SIZE, -SIZE), new Vector2(1.0f, 1.0f)),
                new Vertex(new Vector3(0, SIZE, 0), new Vector2(0.5f, 0.0f)),

                // Right face
                new Vertex(new Vector3(SIZE, -SIZE, SIZE), new Vector2(0.0f, 1.0f)),
                new Vertex(new Vector3(SIZE, -SIZE, -SIZE), new Vector2(1.0f, 1.0f)),
                new Vertex(new Vector3(0, SIZE, 0), new Vector2(0.5f, 0.0f)),

                // Left face
                new Vertex(new Vector3(-SIZE, -SIZE, -SIZE), new Vector2(0.0f, 1.0f)),
                new Vertex(new Vector3(-SIZE, -SIZE, SIZE), new Vector2(1.0f, 1.0f)),
                new Vertex(new Vector3(0, SIZE, 0), new Vector2(0.5f, 0.0f))
        }, Faces.SQUARE_PYRAMID, material);
    }

    public static Mesh buildCube(Material material) {
        return new Mesh(new Vertex[] {

                // Back face
                new Vertex(new Vector3(SIZE,  SIZE, -SIZE), new Vector2(0.0f, 0.0f)),
                new Vertex(new Vector3(SIZE, -SIZE, -SIZE), new Vector2(0.0f, 1.0f)),
                new Vertex(new Vector3( -SIZE, -SIZE, -SIZE), new Vector2(1.0f, 1.0f)),
                new Vertex(new Vector3( -SIZE,  SIZE, -SIZE), new Vector2(1.0f, 0.0f)),

                // Front face
                new Vertex(new Vector3(-SIZE,  SIZE,  SIZE), new Vector2(0.0f, 0.0f)),
                new Vertex(new Vector3(-SIZE, -SIZE,  SIZE), new Vector2(0.0f, 1.0f)),
                new Vertex(new Vector3( SIZE, -SIZE,  SIZE), new Vector2(1.0f, 1.0f)),
                new Vertex(new Vector3( SIZE,  SIZE,  SIZE), new Vector2(1.0f, 0.0f)),

                // Right face
                new Vertex(new Vector3( SIZE,  SIZE, SIZE), new Vector2(0.0f, 0.0f)),
                new Vertex(new Vector3( SIZE, -SIZE, SIZE), new Vector2(0.0f, 1.0f)),
                new Vertex(new Vector3( SIZE, -SIZE,  -SIZE), new Vector2(1.0f, 1.0f)),
                new Vertex(new Vector3( SIZE,  SIZE,  -SIZE), new Vector2(1.0f, 0.0f)),

                // Left face
                new Vertex(new Vector3(-SIZE,  SIZE, -SIZE), new Vector2(0.0f, 0.0f)),
                new Vertex(new Vector3(-SIZE, -SIZE, -SIZE), new Vector2(0.0f, 1.0f)),
                new Vertex(new Vector3(-SIZE, -SIZE,  SIZE), new Vector2(1.0f, 1.0f)),
                new Vertex(new Vector3(-SIZE,  SIZE,  SIZE), new Vector2(1.0f, 0.0f)),

                // Top face
                new Vertex(new Vector3(-SIZE,  SIZE,  -SIZE), new Vector2(0.0f, 0.0f)),
                new Vertex(new Vector3(-SIZE,  SIZE, SIZE), new Vector2(0.0f, 1.0f)),
                new Vertex(new Vector3( SIZE,  SIZE, SIZE), new Vector2(1.0f, 1.0f)),
                new Vertex(new Vector3( SIZE,  SIZE,  -SIZE), new Vector2(1.0f, 0.0f)),

                // Bottom face
                new Vertex(new Vector3(-SIZE, -SIZE,  SIZE), new Vector2(0.0f, 0.0f)),
                new Vertex(new Vector3(-SIZE, -SIZE, -SIZE), new Vector2(0.0f, 1.0f)),
                new Vertex(new Vector3( SIZE, -SIZE, -SIZE), new Vector2(1.0f, 1.0f)),
                new Vertex(new Vector3( SIZE, -SIZE,  SIZE), new Vector2(1.0f, 0.0f)),
        }, Faces.CUBE, material);
    }


}
