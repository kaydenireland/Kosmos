package main.java.net.kallen.solaris.math.array;

import main.java.net.kallen.solaris.math.vector.Vector3;

import java.util.Arrays;

public class Array3D<T> {
    private Vector3 size;
    private Object[] array;

    public Array3D(int size) {
        this.size = new Vector3(size, size, size);
        array = new Object[size * size * size];
    }

    public Array3D(int x, int y, int z) {
        this.size = new Vector3(x, y, z);
        array = new Object[x * y * z];
    }

    private int index(int x, int y, int z) {
        return (int) (x + (y * size.getX()) + (z * size.getZ() * size.getY()));
    }

    private void checkBounds(int x, int y, int z) {
        if (x < 0 || x >= size.getX() || y < 0 || y >= size.getY() || z < 0 || z >= size.getZ()) {
            throw new IndexOutOfBoundsException("Indices out of bounds: (" + x + ", " + y + ", " + z + ")");
        }
    }

    public Object get(int x, int y, int z) {
        checkBounds(x, y, z);
        return array[index(x, y, z)];
    }

    public void set(int x, int y, int z, Object value) {
        checkBounds(x, y, z);
        array[index(x, y, z)] = value;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(array);
    }
}
