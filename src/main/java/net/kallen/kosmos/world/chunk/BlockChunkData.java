package main.java.net.kallen.kosmos.world.chunk;

import main.java.net.kallen.solaris.math.vector.Vector3;

public class BlockChunkData implements IChunkData {

    private byte[] blocks = new byte[CHUNK_WIDTH * CHUNK_WIDTH * CHUNK_WIDTH];

    public BlockChunkData() {}

    @Override
    public IChunkData fill(byte id) {
        return new FullChunkData(id);
    }

    @Override
    public IChunkData fillLayer(byte id, int layer) {
        for (int x = 0; x < CHUNK_WIDTH; x++) {
            for (int z = 0; z < CHUNK_WIDTH; z++) {
                setBlock(x, layer, z, id);
            }
        }
        return this;
    }

    @Override
    public byte getBlock(Vector3 localPos) {
        return getBlock((int) localPos.getX(), (int) localPos.getY(), (int) localPos.getZ());
    }

    @Override
    public byte getBlock(int x, int y, int z) {
        checkBounds(x, y, z);
        return blocks[index(x, y, z)];
    }

    @Override
    public IChunkData setBlock(Vector3 localPos, byte id) {
        return setBlock((int) localPos.getX(), (int) localPos.getY(), (int) localPos.getZ(), id);
    }

    @Override
    public IChunkData setBlock(int x, int y, int z, byte id) {
        checkBounds(x, y, z);
        blocks[index(x, y, z)] = id;
        return this;
    }

    @Override
    public boolean isEntirely() {
        for (int y = 0; y < CHUNK_WIDTH; y++) {
            if (!layerIsEntirely(y)) return false;
        }
        return true;
    }

    @Override
    public boolean layerIsEntirely(int layer) {
        byte first = getBlock(0, layer, 0);
        for (int x = 0; x < CHUNK_WIDTH; x++) {
            for (int z = 0; z < CHUNK_WIDTH; z++) {
                if (first != getBlock(x, layer, z)) return false;
            }
        }
        return true;
    }

    @Override
    public IChunkData tryCompression() {
        if (isEntirely()) return new FullChunkData(getBlock(0, 0, 0));

        boolean layered = true;
        byte[] layers = new byte[16];

        for (int y = 0; y < 16; y++) {
            if (!layerIsEntirely(y)) {
                layered = false;
                break;
            }
            layers[y] = getBlock(0, y, 0);
        }

        if (layered) {
            LayerChunkData chunkData = new LayerChunkData();
            for (int y = 0; y < 16; y++) {
                chunkData.fillLayer(layers[y], y);
            }
            return chunkData;
        }

        return this;

    }

    private int index(int x, int y, int z) {
        return x + (y * CHUNK_WIDTH) + (z * CHUNK_WIDTH * CHUNK_WIDTH);
    }

    public void checkBounds(int x, int y, int z) {
        if (x < 0 || x >= CHUNK_WIDTH || y < 0 || y >= CHUNK_WIDTH || z < 0 || z >= CHUNK_WIDTH) {
            throw new IndexOutOfBoundsException("Indices out of bounds: (" + x + ", " + y + ", " + z + ")");
        }
    }
}
