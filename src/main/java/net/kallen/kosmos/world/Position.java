package main.java.net.kallen.kosmos.world;

import main.java.net.kallen.engine.math.Vector3;

public class Position {

    public static Vector3 worldToChunkPos(Vector3 worldPos) {
        float nx = worldPos.getX() / 15;
        float ny = worldPos.getY() / 15;
        float nz = worldPos.getZ() / 15;

        return new Vector3(nx, ny, nz);
    }

    public static Vector3 chunkToWorldPos(Vector3 chunkPos) {
        float nx = chunkPos.getX() * 16;
        float ny = chunkPos.getY() * 16;
        float nz = chunkPos.getZ() * 16;

        return new Vector3(nx, ny, nz);
    }

}
