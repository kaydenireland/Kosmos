package main.java.net.kallen.engine.math;

import java.util.Arrays;

public class Array3D<T> {
    private Vector3 size;
    private Object[][][] array;

    public Array3D(int size) {
        this.size = new Vector3(size, size, size);
        array = new Object[size][size][size];
    }

    public Array3D(int x, int y, int z) {
        this.size = new Vector3(x, y, z);
        array = new Object[z][y][x];
    }

    public Object get(int x, int y, int z) {
        return array[x][y][z];
    }

    public void set(int x, int y, int z, T value) {
        if (z >= 0 && z < size.getZ() && y >= 0 && y < size.getY() && x >= 0 && x < size.getX()) {
            array[z][y][x] = value;
        } else {
            throw new IndexOutOfBoundsException("Indices out of bounds: (" + x + ", " + y + ", " + z + ")");
        }
    }

    @Override
    public String toString() {
        return Arrays.deepToString(array);
    }
}
