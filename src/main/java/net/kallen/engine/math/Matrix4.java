package main.java.net.kallen.engine.math;

public class Matrix4 {

    public static final int SIZE = 4;
    private float[] elements = new float[SIZE * SIZE];

    public static Matrix4 identity() {

        Matrix4 result = new Matrix4();

        result.set(0, 0, 1f);
        result.set(1, 1, 1f);
        result.set(2, 2, 1f);
        result.set(3, 3, 1f);

        return result;
    }

    public static Matrix4 translate(Vector3 translate) {
        Matrix4 result = Matrix4.identity();

        result.set(0, 3, translate.getX());
        result.set(1, 3, translate.getY());
        result.set(2, 3, translate.getZ());

        return result;
    }

    public static Matrix4 rotate(float angle, Vector3 axis) {
        Matrix4 result = Matrix4.identity();

        float cos = (float) Math.cos(Math.toRadians(angle));
        float sin = (float) Math.sin(Math.toRadians(angle));
        float C = 1 - cos;

        result.set(0, 0, cos + axis.getX() * axis.getX() * C);
        result.set(0, 1, axis.getX() * axis.getY() * C - axis.getZ() * sin);
        result.set(0, 2, axis.getX() * axis.getZ() * C + axis.getY() * sin);
        result.set(1, 0, axis.getY() * axis.getX() * C + axis.getZ() * sin);
        result.set(1, 1, cos + axis.getY() * axis.getY() * C);
        result.set(1, 2, axis.getY() * axis.getZ() * C - axis.getX() * sin);
        result.set(2, 0, axis.getZ() * axis.getX() * C - axis.getY() * sin);
        result.set(2, 1, axis.getZ() * axis.getY() * C + axis.getX() * sin);
        result.set(2, 2, cos + axis.getZ() * axis.getZ() * C);

        return result;
    }

    public static Matrix4 scale(Vector3 scalar) {
        Matrix4 result = Matrix4.identity();

        result.set(0, 0, scalar.getX());
        result.set(1, 1, scalar.getY());
        result.set(2, 2, scalar.getZ());

        return result;
    }

    public static Matrix4 transform(Vector3 position, Vector3 rotation, Vector3 scale) {
        Matrix4 result;

        Matrix4 translationMatrix = Matrix4.translate(position);
        Matrix4 rotXMatrix = Matrix4.rotate(rotation.getX(), new Vector3(1, 0, 0));
        Matrix4 rotYMatrix = Matrix4.rotate(rotation.getY(), new Vector3(0, 1, 0));
        Matrix4 rotZMatrix = Matrix4.rotate(rotation.getZ(), new Vector3(0, 0, 1));
        Matrix4 scaleMatrix = Matrix4.scale(scale);

        Matrix4 rotationMatrix = Matrix4.multiply(rotXMatrix, Matrix4.multiply(rotYMatrix, rotZMatrix));

        result = Matrix4.multiply(translationMatrix, Matrix4.multiply(rotationMatrix, scaleMatrix));

        return result;
    }

    public static Matrix4 multiply(Matrix4 matrix, Matrix4 other) {
        Matrix4 result = Matrix4.identity();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                result.set(i, j, matrix.get(i, 0) * other.get(0, j) +
                        matrix.get(i, 1) * other.get(1, j) +
                        matrix.get(i, 2) * other.get(2, j) +
                        matrix.get(i, 3) * other.get(3, j));
            }
        }

        return result;
    }

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