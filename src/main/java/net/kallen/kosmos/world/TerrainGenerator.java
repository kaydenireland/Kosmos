package main.java.net.kallen.kosmos.world;

import main.java.net.kallen.engine.math.Vector3;
import main.java.net.kallen.engine.noise.PerlinNoiseGenerator;

import java.util.Random;

public class TerrainGenerator {

    private final PerlinNoiseGenerator noise;

    private static final int SEA_LEVEL = 0;


    public TerrainGenerator(double seed) {
        this.noise = new PerlinNoiseGenerator(seed);
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

        var frequency = 0.1f;
        var amplitude = 10;

        var xOffset = Math.sin(worldX * frequency) * amplitude;
        var zOffset = Math.sin(worldZ * frequency) * amplitude;

        var surfaceY = SEA_LEVEL + xOffset + zOffset;

        return  (worldY < surfaceY) ? Blocks.STONE : Blocks.AIR;

    }

}