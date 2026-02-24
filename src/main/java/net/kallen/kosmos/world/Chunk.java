package main.java.net.kallen.kosmos.world;

import main.java.net.kallen.solaris.graphics.Renderer;
import main.java.net.kallen.solaris.math.vector.Vector3;
import main.java.net.kallen.kosmos.render.ChunkMesh;
import main.java.net.kallen.solaris.graphics.TextureAtlas;

public class Chunk {

    public final int SIZE = 16;
    private byte[] blocks = new byte[SIZE * SIZE * SIZE];
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

    public Byte getBlock(int x, int y, int z) {
        checkBounds(x, y, z);
        return blocks[index(x, y, z)];
    }

    public void setBlock(int x, int y, int z, byte blockId) {
        checkBounds(x, y, z);
        blocks[index(x, y, z)] = blockId;
        dirty = true;
    }

    private void checkBounds(int x, int y, int z) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE || z < 0 || z >= SIZE) {
            throw new IndexOutOfBoundsException("Indices out of bounds: (" + x + ", " + y + ", " + z + ")");
        }
    }

    private int index(int x, int y, int z) {
        return (int) (x + (y * SIZE) + (z * SIZE * SIZE));
    }

}
