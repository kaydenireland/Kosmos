package main.java.net.kallen.kosmos.world;

import main.java.net.kallen.engine.graphics.Renderer;
import main.java.net.kallen.engine.math.ByteArray3D;
import main.java.net.kallen.engine.math.Vector3;
import main.java.net.kallen.kosmos.render.ChunkMesh;
import main.java.net.kallen.kosmos.texture.TextureAtlas;

public class Chunk {

    public final int SIZE = 16;
    private ByteArray3D blocks = new ByteArray3D(SIZE);
    public ChunkMesh chunkMesh;
    private boolean dirty;

    public Chunk(TextureAtlas atlas, Vector3 chunkPosition) {
        chunkMesh = new ChunkMesh(this, chunkPosition, atlas);
        dirty = true;
    }

    public void update() {
        if (dirty) {
            chunkMesh.generateMesh();
            dirty = false;
        }
    }

    public void renderOpaque(Renderer renderer) {
        chunkMesh.renderOpaque(renderer);
    }

    public void renderTransparent(Renderer renderer) {
        chunkMesh.renderTransparent(renderer);
    }

    public void destroy() {
        chunkMesh.destroy();
    }

    public byte getBlock(int x, int y, int z) {
        return blocks.get(x, y, z);
    }

    public void setBlock(int x, int y, int z, byte blockId) {
        blocks.set(x, y, z, blockId);
        dirty = true;
    }




}
