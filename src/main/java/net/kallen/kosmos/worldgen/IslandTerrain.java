package main.java.net.kallen.kosmos.worldgen;

import main.java.net.kallen.kosmos.world.Blocks;

public class IslandTerrain implements ITerrainGenerator {

    public IslandTerrain() { }

    @Override
    public byte determineBlockType(int x, int y, int z) {

        if (y == BEDROCK_LEVEL) {
            return Blocks.BEDROCK;
        }

        float frequency = 0.05f;
        float amplitude = 10f;

        var xOffset = Math.sin(x * frequency) * amplitude;
        var zOffset = Math.sin(z * frequency) * amplitude;

        var surfaceY = SEA_LEVEL + xOffset + zOffset - 12;

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
