package main.java.net.kallen.engine.math;

import java.util.Arrays;

public class ByteArray3D {

    private final int sizeX;
    private final int sizeY;
    private final int sizeZ;

    private final Byte[] data;

    public ByteArray3D(int size) {
        this(size, size, size);
    }

    public ByteArray3D(int x, int y, int z) {
        this.sizeX = x;
        this.sizeY = y;
        this.sizeZ = z;
        this.data = new Byte[x * y * z];
    }

    private int index(int x, int y, int z) {
        return x + (y * sizeX) + (z * sizeX * sizeY);
    }

    public Byte get(int x, int y, int z) {
        checkBounds(x, y, z);
        return data[index(x, y, z)];
    }

    public void set(int x, int y, int z, Byte value) {
        checkBounds(x, y, z);
        data[index(x, y, z)] = value;
    }

    private void checkBounds(int x, int y, int z) {
        if (x < 0 || x >= sizeX || y < 0 || y >= sizeY || z < 0 || z >= sizeZ) {
            throw new IndexOutOfBoundsException("Indices out of bounds: (" + x + ", " + y + ", " + z + ")");
        }
    }
    @Override
    public String toString() {
        return Arrays.toString(data);
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getSizeZ() {
        return sizeZ;
    }
}
