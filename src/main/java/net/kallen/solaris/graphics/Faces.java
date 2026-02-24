package main.java.net.kallen.solaris.graphics;

public class Faces {

    public static int[] TRIANGLE = {
            0, 1, 2
    };

    public static int[] RECTANGLE = {
            0, 1, 3,
            3, 1, 2
    };

    public static int[] TRIANGLE_PYRAMID = {
            // Bottom face
            0, 1, 2,

            // Front face
            3, 4, 5,

            // Right face
            6, 7, 8,

            // Left face
            9, 10, 11
    };

    public static int[] SQUARE_PYRAMID = {
            // Bottom face
            0, 1, 2,
            0, 2, 3,

            // Front face
            4, 5, 6,

            // Back face
            7, 8, 9,

            // Right face
            10, 11, 12,

            // Left face
            13, 14, 15
    };

    public static int[] CUBE = {

            // Back face
            0, 1, 3,
            3, 1, 2,

            // Front face
            4, 5, 7,
            7, 5, 6,

            // Right face
            8, 9, 11,
            11, 9, 10,

            // Left face
            12, 13, 15,
            15, 13, 14,

            // Top face
            16, 17, 19,
            19, 17, 18,

            // Bottom face
            20, 21, 23,
            23, 21, 22
    };



}
