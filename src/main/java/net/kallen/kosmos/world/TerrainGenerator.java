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

                double noiseValue = noise.noise(worldX * 0.01, worldZ * 0.01);
                int surfaceHeight = (int) ((noiseValue * 0.5 + 0.5) * 16 - 8); // Range: -8 to 8 (centered at 0)

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
