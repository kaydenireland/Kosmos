package main.java.net.kallen.kosmos.world;

import main.java.net.kallen.kosmos.world.chunk.Chunk;
import main.java.net.kallen.solaris.graphics.Renderer;
import main.java.net.kallen.solaris.math.vector.Vector3;
import main.java.net.kallen.kosmos.entity.Player;
import main.java.net.kallen.solaris.graphics.TextureAtlas;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class World {

    private final double seed;
    private TerrainGenerator terrainGenerator;
    private Map<Vector3, Chunk> chunks = new HashMap<>();
    private TextureAtlas atlas;

    private int loadDistance = 8;

    public World(TextureAtlas atlas, double seed) {
        this.atlas = atlas;
        this.seed = seed;
        this.terrainGenerator = new TerrainGenerator(this.seed);
    }

    public void loadChunk(Vector3 chunkPos) {
        if(!chunks.containsKey(chunkPos) && chunkPos.getY() >= -8) {
            Chunk newChunk = new Chunk(this, atlas, chunkPos);
            terrainGenerator.generateChunk(newChunk, chunkPos);
            chunks.put(chunkPos, newChunk);
            // System.out.println("Loaded chunk at " + chunkPos + " (Total: " + chunks.size() + ")");
        }
    }

    public void update() {
        for (Chunk chunk : chunks.values()) {
            chunk.update();
        }
    }

    public void updateChunks(Player player) {
        Vector3 lastPosition = player.getLastChunkPosition();
        Vector3 currentPosition = player.getChunkPosition();

        if (!currentPosition.equals(lastPosition)) {
            loadChunksAroundPlayer(currentPosition);
            unloadDistantChunks(currentPosition);
        }

    }

    private void loadChunksAroundPlayer(Vector3 playerChunkPos) {
        int px = (int) playerChunkPos.getX();
        int py = (int) playerChunkPos.getY();
        int pz = (int) playerChunkPos.getZ();

        for (int x = px - loadDistance; x <= px + loadDistance; x++) {
            for (int z = pz - loadDistance; z <= pz + loadDistance; z++) {
                for (int y = py - 1; y <= py + 1; y++) {
                    Vector3 chunkPos = new Vector3(x, y, z);

                    int dx = x - px;
                    int dz = z - pz;
                    if (dx * dx + dz * dz <= loadDistance * loadDistance) {
                        loadChunk(chunkPos);
                    }
                }
            }
        }
    }

    private void unloadDistantChunks(Vector3 playerChunkPos) {
        int px = (int) playerChunkPos.getX();
        int pz = (int) playerChunkPos.getZ();

        List<Vector3> chunksToUnload = new ArrayList<>();

        for (Vector3 chunkPos : chunks.keySet()) {
            int dx = (int) chunkPos.getX() - px;
            int dz = (int) chunkPos.getZ() - pz;

            if (dx * dx + dz * dz > (loadDistance + 2) * (loadDistance + 2)) {
                chunksToUnload.add(chunkPos);
            }
        }

        for (Vector3 chunkPos : chunksToUnload) {
            Chunk chunk = chunks.remove(chunkPos);
            if (chunk != null) {
                chunk.unload();
                // System.out.println("Unloaded chunk at " + chunkPos);
            }
        }
    }

    public void render(Renderer renderer) {

        // Opaque pass
        for (Chunk chunk : chunks.values()) {
            chunk.renderOpaque(renderer);
        }

        // Transparent pass
        GL11.glDepthMask(false);
        for (Chunk chunk : chunks.values()) {
            chunk.renderTransparent(renderer);
        }
        GL11.glDepthMask(true);

    }

    public void destroy() {
        for (Chunk chunk : chunks.values()) {
            chunk.unload();
        }
    }

    public byte getBlock(Vector3 worldPos) {
        Vector3 chunkPos = BlockPosition.worldToChunkPos(worldPos);
        Chunk chunk = chunks.get(chunkPos);
        if (chunk == null) return Blocks.AIR;

        int localX = Math.floorMod((int) worldPos.getX(), 16);
        int localY = Math.floorMod((int) worldPos.getY(), 16);
        int localZ = Math.floorMod((int) worldPos.getZ(), 16);

        return chunk.getBlock(localX, localY, localZ);
    }

    public void setBlock(Vector3 worldPos, byte blockId) {
        Vector3 chunkPos = BlockPosition.worldToChunkPos(worldPos);
        Chunk chunk = chunks.get(chunkPos);

        if (chunk != null) {
            int localX = Math.floorMod((int) worldPos.getX(), 16);
            int localY = Math.floorMod((int) worldPos.getY(), 16);
            int localZ = Math.floorMod((int) worldPos.getZ(), 16);

            chunk.setBlock(localX, localY, localZ, blockId);
        }
    }

    public void setLoadDistance(int loadDistance) {
        this.loadDistance = loadDistance;
    }

    public int getLoadedChunkCount() {
        return chunks.size();
    }


}
