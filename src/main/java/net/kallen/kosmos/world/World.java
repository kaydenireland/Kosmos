package main.java.net.kallen.kosmos.world;

import main.java.net.kallen.engine.graphics.Renderer;
import main.java.net.kallen.engine.math.Vector3;
import main.java.net.kallen.kosmos.texture.TextureAtlas;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class World {

    private double seed;
    private TerrainGenerator terrainGenerator;
    private Map<Vector3, Chunk> chunks = new HashMap<>();
    private TextureAtlas atlas;

    public World(TextureAtlas atlas, double seed) {
        this.atlas = atlas;
        this.seed = seed;
        this.terrainGenerator = new TerrainGenerator(seed);
    }

    public void loadChunk(Vector3 position) {
        if(!chunks.containsKey(position)) {
            Chunk newChunk = new Chunk(atlas, Position.chunkToWorldPos(position));
            terrainGenerator.generateChunk(newChunk, position);
            chunks.put(position, newChunk);
        }
    }

    public void update() {
        for (Chunk chunk : chunks.values()) {
            chunk.update();
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
            chunk.destroy();
        }
    }

    public byte getBlock(Vector3 worldPos) {
        Vector3 chunkPos = Position.worldToChunkPos(worldPos);
        Chunk chunk = chunks.get(chunkPos);
        if (chunk == null) return BlockRegistry.AIR;

        int localX = (int) worldPos.getX() % 16;
        int localY = (int) worldPos.getY() % 16;
        int localZ = (int) worldPos.getZ() % 16;

        return chunk.getBlock(localX, localY, localZ);
    }

    public void setBlock(Vector3 worldPos, byte blockId) {
        Vector3 chunkPos = Position.worldToChunkPos(worldPos);
        Chunk chunk = chunks.get(chunkPos);

        if (chunk != null) {
            int localX = (int) worldPos.getX() & 15;
            int localY = (int) worldPos.getY() & 15;
            int localZ = (int) worldPos.getZ() & 15;

            chunk.setBlock(localX, localY, localZ, blockId);
        }
    }

}
