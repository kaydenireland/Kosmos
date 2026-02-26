package main.java.net.kallen.kosmos.worldgen;

import main.java.net.kallen.kosmos.world.Blocks;
import main.java.net.kallen.solaris.noise.FastNoise;
import main.java.net.kallen.solaris.noise.PerlinNoise;

public class NormalTerrain implements ITerrainGenerator {

    private final PerlinNoise noise;
    private final FastNoise noise2;

    public NormalTerrain(long seed) {
        this.noise = new PerlinNoise(seed);
        this.noise2 = new FastNoise((int) seed);
    }

    @Override
    public byte determineBlockType(int x, int y, int z) {

        if (y == BEDROCK_LEVEL) {
            return Blocks.BEDROCK;
        }

        noise2.SetFractalOctaves(4);
        var offset = noise2.GetPerlinFractal(x, z) * 20;

        // y will be between -20 & 20
        var surfaceY = SEA_LEVEL + offset;

        if (y < surfaceY && y < SEA_LEVEL - 1) {
            return Blocks.DIRT;
        } else if (y < surfaceY) {
            return Blocks.MOSS;
        } else if (y < SEA_LEVEL) {
            return Blocks.GLASS; // Reserved for water
        } else {
            return Blocks.AIR;
        }

    }

}