package main.java.net.kallen.kosmos.world;

import main.java.net.kallen.engine.math.Array3D;

public class Chunk {

    private final int CHUNK_SIZE = 16;
    private Array3D<Block> blocks;

    public Chunk() {
        blocks = new Array3D<>(CHUNK_SIZE);
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    blocks.set(x, y, z, new Block(BlockId.DIRT));
                }
            }
        }
    }

    public void update() {}

    public void render() {}

}
