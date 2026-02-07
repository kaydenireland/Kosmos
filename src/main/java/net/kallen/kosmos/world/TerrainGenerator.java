package main.java.net.kallen.kosmos.world;

import main.java.net.kallen.engine.math.Vector3;
import main.java.net.kallen.engine.noise.PerlinNoiseGenerator;

public class TerrainGenerator {


    private static final int SEA_LEVEL = 0;


    public TerrainGenerator(double seed) {
    }

    public void generateChunk(Chunk chunk, Vector3 chunkPos) {
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

    private byte determineBlockType(int worldX, int worldY, int worldZ) {

        if (worldY == -128) {
            return Blocks.BEDROCK;
        }

        return  (worldY < SEA_LEVEL) ? Blocks.STONE : Blocks.AIR;

    }

}