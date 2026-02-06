package main.java.net.kallen.kosmos.world;

import main.java.net.kallen.engine.math.Vector3;
import main.java.net.kallen.engine.noise.PerlinNoiseGenerator;

public class TerrainGenerator {

    private final PerlinNoiseGenerator noise;

    public TerrainGenerator(double seed) {
        this.noise = new PerlinNoiseGenerator(seed);
    }

    public void generateChunk(Chunk chunk, Vector3 chunkPos) {
        for (int x = 0; x < chunk.SIZE; x++) {
            for (int z = 0; z < chunk.SIZE; z++) {
                int worldX = (int) chunkPos.getX() * chunk.SIZE + x;
                int worldZ = (int) chunkPos.getZ() * chunk.SIZE + z;

                double frequency1 = 0.01;   // Large features (hills)
                double frequency2 = 0.05;   // Medium features (slopes)
                double frequency3 = 0.1;    // Small features (detail)

                double noise1 = noise.noise(worldX * frequency1, worldZ * frequency1);
                double noise2 = noise.noise(worldX * frequency2, worldZ * frequency2);
                double noise3 = noise.noise(worldX * frequency3, worldZ * frequency3);

                double noiseValue = (noise1 * 0.6) + (noise2 * 0.3) + (noise3 * 0.1);

                int surfaceHeight = (int) ((noiseValue * 0.5 + 0.5) * 72 - 8); // Range: -8 to 64 (centered at 0)

                for (int y = 0; y < chunk.SIZE; y++) {
                    int worldY = (int) chunkPos.getY() * chunk.SIZE + y;

                    byte blockType;

                    if (worldY == -128) {
                        blockType = BlockRegistry.BEDROCK;
                    } else if (worldY >= -127 && worldY < -64) {
                        blockType = BlockRegistry.DEEPSLATE;
                    } else if (worldY >= -64 && worldY <= surfaceHeight - 3) {
                        blockType = BlockRegistry.STONE;
                    } else if (worldY == surfaceHeight - 2 || worldY == surfaceHeight - 1) {
                        blockType = BlockRegistry.DIRT;
                    } else if (worldY == surfaceHeight) {
                        blockType = BlockRegistry.GRASS;
                    } else {
                        blockType = BlockRegistry.AIR;
                    }

                    chunk.setBlock(x, y, z, blockType);
                }
            }
        }
    }
}
