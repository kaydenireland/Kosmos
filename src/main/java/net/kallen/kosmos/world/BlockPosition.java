package main.java.net.kallen.kosmos.world;

import main.java.net.kallen.solaris.math.vector.Vector3;

public class BlockPosition {

    public static Vector3 worldToChunkPos(Vector3 worldPos) {
        float nx = Math.floorDiv((int) worldPos.getX(), 16);
        float ny = Math.floorDiv((int) worldPos.getY(), 16);
        float nz = Math.floorDiv((int) worldPos.getZ(), 16);

        return new Vector3(nx, ny, nz);
    }

    public static Vector3 chunkToWorldPos(Vector3 chunkPos) {
        float nx = chunkPos.getX() * 16;
        float ny = chunkPos.getY() * 16;
        float nz = chunkPos.getZ() * 16;

        return new Vector3(nx, ny, nz);
    }

}
