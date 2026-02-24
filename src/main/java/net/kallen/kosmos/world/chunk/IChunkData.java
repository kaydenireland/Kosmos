package main.java.net.kallen.kosmos.world.chunk;

import main.java.net.kallen.solaris.math.vector.Vector3;

public interface IChunkData {
    public static final int CHUNK_WIDTH = 16;

    public IChunkData fill(byte id);
    public IChunkData fillLayer(byte id, int layer);
    public byte getBlock(Vector3 localPos);
    public byte getBlock(int x, int y, int z);
    public IChunkData setBlock(Vector3 localPos, byte id);
    public IChunkData setBlock(int x, int y, int z, byte id);
    public boolean isEntirely();
    public boolean layerIsEntirely(int layer);
    public IChunkData tryCompression();

}
