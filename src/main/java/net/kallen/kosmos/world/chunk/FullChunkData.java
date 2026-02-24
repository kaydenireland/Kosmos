package main.java.net.kallen.kosmos.world.chunk;

import main.java.net.kallen.solaris.math.vector.Vector3;

public class FullChunkData implements IChunkData {

    private byte blockId;

    public FullChunkData() {}

    public FullChunkData(byte id) {
        this.blockId = id;
    }

    @Override
    public IChunkData fill(byte id) {
        this.blockId = id;
        return this;
    }

    @Override
    public IChunkData fillLayer(byte id, int layer) {
        if (blockId == id) return this;

        // Convert to LayerChunk Data
        LayerChunkData chunkData = new LayerChunkData();
        chunkData.fill(blockId);
        chunkData.fillLayer(id, layer);
        return chunkData;
    }

    @Override
    public byte getBlock(Vector3 localPos) {
        return blockId;
    }

    @Override
    public byte getBlock(int x, int y, int z) {
        return blockId;
    }

    @Override
    public IChunkData setBlock(Vector3 localPos, byte id) {
        return setBlock((int) localPos.getX(), (int) localPos.getY(), (int) localPos.getZ(), id);
    }

    @Override
    public IChunkData setBlock(int x, int y, int z, byte id) {
        if (blockId == id) return this;

        // Convert to BlockChunk Data
        BlockChunkData chunkData = new BlockChunkData();
        chunkData.fill(blockId);
        chunkData.setBlock(x, y, z, id);

        return chunkData;
    }

    @Override
    public boolean isEntirely() {
        return true;
    }

    @Override
    public boolean layerIsEntirely(int layer) {
        return true;
    }

    @Override
    public IChunkData tryCompression() {
        return this;
    }
}
