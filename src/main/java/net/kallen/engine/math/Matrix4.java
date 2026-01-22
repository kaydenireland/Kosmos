package main.java.net.kallen.engine.math;

public class Matrix4 {

    public static final int SIZE = 4;
    private float[] elements = new float[SIZE * SIZE];

    public float get(int x, int y) {
        return elements[y * SIZE + x];
    }

    public void set(int x, int y, float value) {
        elements[y * SIZE + x] = value;
    }

    public float[] getAll() {
        return elements;
    }

}
