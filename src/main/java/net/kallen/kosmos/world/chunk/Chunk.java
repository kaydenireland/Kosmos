package main.java.net.kallen.kosmos.world.chunk;

import main.java.net.kallen.kosmos.world.World;
import main.java.net.kallen.solaris.graphics.Renderer;
import main.java.net.kallen.solaris.math.vector.Vector3;
import main.java.net.kallen.kosmos.render.ChunkMesh;
import main.java.net.kallen.solaris.graphics.TextureAtlas;

public class Chunk {

    public final int SIZE = 16;
    private IChunkData chunkData;
    public ChunkMesh chunkMesh;
    private boolean dirty;

    public Chunk(World world, TextureAtlas atlas, Vector3 chunkPos) {
        chunkData = new FullChunkData();
        chunkMesh = new ChunkMesh(world, this, chunkPos, atlas);
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

    public void unload() {
        if (!(chunkData instanceof FullChunkData)) chunkData.tryCompression();
        chunkMesh.destroy();
    }

    public byte getBlock(int x, int y, int z) {
        return chunkData.getBlock(x, y, z);
    }

    public void setBlock(int x, int y, int z, byte id) {
        chunkData = chunkData.setBlock(x, y, z, id);
        dirty = true;
    }

}
