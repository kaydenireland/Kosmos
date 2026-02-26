package main.java.net.kallen.kosmos.worldgen;

import main.java.net.kallen.kosmos.world.Blocks;

public class FlatTerrain implements ITerrainGenerator {

    private byte grassBlock;
    private byte dirtBlock;
    private byte stoneBlock;

    public FlatTerrain() {
        this.grassBlock = Blocks.GRASS;
        this.dirtBlock = Blocks.DIRT;
        this.stoneBlock = Blocks.STONE;
    }

    public FlatTerrain(byte grass, byte dirt, byte stone) {
        this.grassBlock = grass;
        this.dirtBlock = dirt;
        this.stoneBlock = stone;
    }

    @Override
    public byte determineBlockType(int x, int y, int z) {
        if (y == BEDROCK_LEVEL) return Blocks.BEDROCK;
        else if (y == SEA_LEVEL) return grassBlock;
        else if (y >= SEA_LEVEL - 3 && y < SEA_LEVEL) return dirtBlock;
        else if (y < SEA_LEVEL - 3) return stoneBlock;
        else return Blocks.AIR;
    }
}
