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
                int height = (int) (noiseValue + 64); // Range: 0 - 64

                for (int y = 0; y < chunk.SIZE; y++) {
                    int worldY = (int) chunkPos.getY() * chunk.SIZE + y;

                    if (worldY == -64) chunk.setBlock(x, y, z, BlockRegistry.BEDROCK);
                    else if (worldY < height - 64) {
                        chunk.setBlock(x, y, z, BlockRegistry.DEEPSLATE);
                    } else if (worldY < height - 4) {
                        chunk.setBlock(x, y, z, BlockRegistry.STONE);
                    } else if (worldY <= height - 1) {
                        chunk.setBlock(x, y, z, BlockRegistry.DIRT);
                    } else if (worldY == height) {
                        chunk.setBlock(x, y, z, BlockRegistry.GRASS);
                    } else {
                        chunk.setBlock(x, y, z, BlockRegistry.AIR);
                    }
                }
            }
        }
    }

}
