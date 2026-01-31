package main.java.net.kallen.kosmos.world;

import main.java.net.kallen.engine.graphics.Renderer;
import main.java.net.kallen.engine.math.ByteArray3D;
import main.java.net.kallen.kosmos.render.ChunkMesh;
import main.java.net.kallen.kosmos.texture.TextureAtlas;

public class Chunk {

    public final int SIZE = 16;
    private ByteArray3D blocks;
    public ChunkMesh chunkMesh;
    private boolean dirty;

    public Chunk(TextureAtlas atlas) {
        chunkMesh = new ChunkMesh(this, atlas);
        dirty = true;

        blocks = new ByteArray3D(SIZE);
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                for (int z = 0; z < SIZE; z++) {
                    if (y == 15) blocks.set(x, y, z, (byte) 0);
                    else if (y == 14) blocks.set(x, y, z, (byte) 2);
                    else if (y == 13) blocks.set(x, y, z, (byte) 1);
                    else blocks.set(x, y, z, (byte) 5);
                }
            }
        }
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
