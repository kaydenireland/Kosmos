package main.java.net.kallen.kosmos.world;

import main.java.net.kallen.solaris.math.vector.Vector3;
import main.java.net.kallen.solaris.noise.PerlinNoise;

public class TerrainGenerator {

    private final PerlinNoise noise;

    private static final int SEA_LEVEL = 0;


    public TerrainGenerator(double seed) {
        this.noise = new PerlinNoise(seed);
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

    private byte determineBlockType(int x, int y, int z) {

        if (y == -128) {
            return Blocks.BEDROCK;
        }

        var frequency = 0.1f;
        var amplitude = 10;

        var xOffset = Math.sin(x * frequency) * amplitude;
        var zOffset = Math.sin(z * frequency) * amplitude;

        var surfaceY = SEA_LEVEL + xOffset + zOffset;

        if (y < surfaceY && y < SEA_LEVEL - 1) {
            return Blocks.STONE;
        } else if (y < surfaceY) {
            return Blocks.MUD;
        } else if (y < SEA_LEVEL) {
            return Blocks.BLUE_STAINED_GLASS;
        } else {
            return Blocks.AIR;
        }

    }

}