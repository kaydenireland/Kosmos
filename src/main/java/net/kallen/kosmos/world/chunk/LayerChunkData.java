package main.java.net.kallen.kosmos.world.chunk;

import main.java.net.kallen.solaris.math.vector.Vector3;

public class LayerChunkData implements IChunkData {

    private byte[] layers = new byte[16];

    public LayerChunkData() {}

    @Override
    public IChunkData fill(byte id) {
        return new FullChunkData(id);
    }

    @Override
    public IChunkData fillLayer(byte id, int layer) {
        layers[layer] = id;
        return this;
    }

    @Override
    public byte getBlock(Vector3 localPos) {
        return getBlock((int) localPos.getY());
    }

    @Override
    public byte getBlock(int x, int y, int z) {
        return getBlock(y);
    }

    public byte getBlock(int y) {
        checkBounds(y);
        return layers[y];
    }

    @Override
    public IChunkData setBlock(Vector3 localPos, byte id) {
        return setBlock((int) localPos.getX(), (int) localPos.getY(), (int) localPos.getZ(), id);
    }

    @Override
    public IChunkData setBlock(int x, int y, int z, byte id) {
        checkBounds(y);
        if (layers[y] == id) return this;
        // Convert to BlockChunk Data
        BlockChunkData chunkData = new BlockChunkData();
        for (int i = 0; i < CHUNK_WIDTH; i++) {
            chunkData.fillLayer(layers[i], i);
        }
        chunkData.setBlock(x, y, z, id);
        return chunkData;
    }

    @Override
    public boolean isEntirely() {
        byte id = layers[0];
        for (int i = 1; i < CHUNK_WIDTH; i++) {
            if (layers[i] != id) return false;
        }
        return true;
    }

    @Override
    public boolean layerIsEntirely(int y) {
        checkBounds(y);
        return true;
    }

    @Override
    public IChunkData tryCompression() {
        if (isEntirely()) return new FullChunkData(layers[0]);
        return this;
    }

    public void checkBounds(int y) {
        if (y < 0 || y >= CHUNK_WIDTH) {
            throw new IndexOutOfBoundsException("Y Index out of bounds: " + y);
        }
    }
}
