package main.java.net.kallen.engine.math;
import java.util.Arrays;

public class ByteArray3D {
    private Vector3 size;
    private Byte[][][] array;

    public ByteArray3D(int size) {
        this.size = new Vector3(size, size, size);
        array = new Byte[size][size][size];
    }

    public ByteArray3D(int x, int y, int z) {
        this.size = new Vector3(x, y, z);
        array = new Byte[z][y][x];
    }

    public Byte get(int x, int y, int z) {
        return array[x][y][z];
    }

    public void set(int x, int y, int z, Byte value) {
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

