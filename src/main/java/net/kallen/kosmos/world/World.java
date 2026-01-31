package main.java.net.kallen.kosmos.world;

import main.java.net.kallen.engine.graphics.Renderer;
import main.java.net.kallen.engine.math.Vector3;
import main.java.net.kallen.kosmos.texture.TextureAtlas;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class World {

    private Map<Vector3, Chunk> chunks = new HashMap<>();
    private TextureAtlas atlas;

    public World(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    public void loadChunk(Vector3 position) {
        if(!chunks.containsKey(position)) {
            Chunk newChunk = new Chunk(atlas, chunkToWorldPos(position));
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
        Vector3 chunkPos = worldToChunkPos(worldPos);
        Chunk chunk = chunks.get(chunkPos);
        if (chunk == null) return BlockRegistry.AIR;

        int localX = (int) worldPos.getX() % 16;
        int localY = (int) worldPos.getY() % 16;
        int localZ = (int) worldPos.getZ() % 16;

        return chunk.getBlock(localX, localY, localZ);
    }

    public void setBlock(Vector3 worldPos, byte blockId) {
        Vector3 chunkPos = worldToChunkPos(worldPos);
        Chunk chunk = chunks.get(chunkPos);

        if (chunk != null) {
            int localX = (int) worldPos.getX() & 15;
            int localY = (int) worldPos.getY() & 15;
            int localZ = (int) worldPos.getZ() & 15;

            chunk.setBlock(localX, localY, localZ, blockId);
        }
    }

    private Vector3 worldToChunkPos(Vector3 worldPos) {
        float nx = worldPos.getX() / 15;
        float ny = worldPos.getY() / 15;
        float nz = worldPos.getZ() / 15;

        return new Vector3(nx, ny, nz);
    }

    private Vector3 chunkToWorldPos(Vector3 chunkPos) {
        float nx = chunkPos.getX() * 16;
        float ny = chunkPos.getY() * 16;
        float nz = chunkPos.getZ() * 16;

        return new Vector3(nx, ny, nz);
    }

}
