package main.java.net.kallen.kosmos.worldgen;

import main.java.net.kallen.kosmos.world.Blocks;
import main.java.net.kallen.kosmos.world.chunk.Chunk;
import main.java.net.kallen.solaris.math.vector.Vector3;
import main.java.net.kallen.solaris.noise.PerlinNoise;

public class TerrainGenerator implements ITerrainGenerator {

    private final PerlinNoise noise;

    public TerrainGenerator(double seed) {
        this.noise = new PerlinNoise(seed);
    }



    @Override
    public byte determineBlockType(int x, int y, int z) {

        if (y == BEDROCK_LEVEL) {
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