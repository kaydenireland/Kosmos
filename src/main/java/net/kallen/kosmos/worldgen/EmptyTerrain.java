package main.java.net.kallen.kosmos.worldgen;

import main.java.net.kallen.kosmos.world.Blocks;

public class EmptyTerrain implements ITerrainGenerator {

    public EmptyTerrain() {}

    @Override
    public byte determineBlockType(int x, int y, int z) {
        return Blocks.AIR;
    }
}
