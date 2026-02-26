package main.java.net.kallen.kosmos.worldgen;

import main.java.net.kallen.kosmos.world.chunk.Chunk;
import main.java.net.kallen.solaris.math.vector.Vector3;

public interface ITerrainGenerator {
    public static final int SEA_LEVEL = 0;
    public static final int BEDROCK_LEVEL = -128;

    public default void generateChunk(Chunk chunk, Vector3 chunkPos) {
        for (int x = 0; x < chunk.SIZE; x++) {
            int worldX = (int) chunkPos.getX() * chunk.SIZE + x;

            for (int z = 0; z < chunk.SIZE; z++) {
                int worldZ = (int) chunkPos.getZ() * chunk.SIZE + z;

                for (int y = 0; y < chunk.SIZE; y++) {
                    int worldY = (int) chunkPos.getY() * chunk.SIZE + y;

                    byte blockType = determineBlockType(worldX, worldY, worldZ);
                    chunk.setBlock(x, y, z, blockType);
                }
            }
        }
    }

    public byte determineBlockType(int x, int y, int z);

}
