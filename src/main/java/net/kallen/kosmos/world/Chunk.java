package main.java.net.kallen.kosmos.world;

import main.java.net.kallen.engine.math.Array3D;
import main.java.net.kallen.engine.math.ByteArray3D;

public class Chunk {

    private final int CHUNK_SIZE = 16;
    private ByteArray3D blocks;

    public Chunk() {
        blocks = new ByteArray3D(CHUNK_SIZE);
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    blocks.set(x, y, z, (byte) 1);
                }
            }
        }
    }

    public void update() {}

    public void render() {}

}
