package main.java.net.kallen.kosmos.world;

import main.java.net.kallen.engine.graphics.Mesh;
import main.java.net.kallen.engine.graphics.Renderer;
import main.java.net.kallen.engine.graphics.Vertex;
import main.java.net.kallen.engine.math.ByteArray3D;
import main.java.net.kallen.engine.math.Vector2;
import main.java.net.kallen.engine.math.Vector3;
import main.java.net.kallen.kosmos.util.AtlasMaterial;
import main.java.net.kallen.kosmos.util.TextureAtlas;

import java.util.ArrayList;

public class Chunk {

    private final int CHUNK_SIZE = 16;
    private ByteArray3D blocks;
    public Mesh mesh;
    private final ArrayList<Vertex> vertices = new ArrayList<>();
    private final ArrayList<Integer> indices = new ArrayList<>();
    private int offset;
    private float[] uvs;
    private final TextureAtlas atlas;
    private boolean dirty;

    public Chunk(TextureAtlas atlas) {
        this.atlas = atlas;

        blocks = new ByteArray3D(CHUNK_SIZE);
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    if (y == 15) blocks.set(x, y, z, (byte) 0);
                    else if (y == 14) blocks.set(x, y, z, (byte) 3);
                    else if (y == 13) blocks.set(x, y, z, (byte) 3);
                    else blocks.set(x, y, z, (byte) 2);
                }
            }
        }
    }

    public void update() {
        if (dirty) {
            generateMesh();
        }
    }

    public void render(Renderer renderer) {
        if (mesh != null) {
            renderer.renderMesh(mesh);
        }
    }

    public byte getBlock(int x, int y, int z) {
        return blocks.get(x, y, z);
    }

    public void setBlock(int x, int y, int z, byte blockId) {
        blocks.set(x, y, z, blockId);
        dirty = true;
    }

    public void generateMesh() {
        vertices.clear();
        indices.clear();
        offset = 0;
        byte blockId;

        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    blockId = getBlock(x, y, z);

                    if (blockId == BlockRegistry.AIR) continue;

                    uvs = atlas.getUVs(BlockRegistry.getNameFromId(blockId));

                    if (shouldRenderFace(new Vector3(x, y, z), Direction.NORTH)) {

                        addFace(
                                new Vector3(x,y + 1, z),
                                new Vector3(x, y, z),
                                new Vector3(x + 1, y, z),
                                new Vector3(x + 1, y + 1, z)
                        );

                        offset += 4;
                    }

                    if (shouldRenderFace(new Vector3(x, y, z), Direction.SOUTH)) {

                        addFace(
                                new Vector3(x + 1, y + 1, z + 1),
                                new Vector3(x + 1, y, z + 1),
                                new Vector3(x, y, z + 1),
                                new Vector3(x, y + 1, z + 1)
                        );

                        offset += 4;
                    }

                    if (shouldRenderFace(new Vector3(x, y, z), Direction.EAST)) {

                        addFace(
                                new Vector3(x + 1, y + 1, z),
                                new Vector3(x + 1, y, z),
                                new Vector3(x + 1, y, z + 1),
                                new Vector3(x + 1, y + 1, z + 1)
                        );

                        offset += 4;
                    }

                    if (shouldRenderFace(new Vector3(x, y, z), Direction.WEST)) {

                        addFace(
                                new Vector3(x, y + 1, z + 1),
                                new Vector3(x, y, z + 1),
                                new Vector3(x, y, z),
                                new Vector3(x, y + 1, z)
                        );

                        offset += 4;
                    }

                    if (shouldRenderFace(new Vector3(x, y, z), Direction.ABOVE)) {

                        addFace(
                                new Vector3(x, y + 1, z),
                                new Vector3(x, y + 1, z + 1),
                                new Vector3(x + 1, y + 1, z + 1),
                                new Vector3(x + 1, y + 1, z)
                        );

                        offset += 4;
                    }

                    if (shouldRenderFace(new Vector3(x, y, z), Direction.BELOW)) {

                        addFace(
                                new Vector3(x, y, z + 1),
                                new Vector3(x, y, z),
                                new Vector3(x + 1, y, z),
                                new Vector3(x + 1, y, z + 1)
                        );

                        offset += 4;
                    }

                }
            }
        }

        mesh = new Mesh(
                vertices.toArray(new Vertex[0]),
                indices.stream().mapToInt(i -> i).toArray(),
                new AtlasMaterial(
                        atlas.getTextureID(),
                        atlas.getGridSize(),
                        atlas.getGridSize()
                )
                // TODO: ChunkMesh Class
                // TODO: World Class/ChunkManager
        );

        mesh.create();
        dirty = false;

    }

    private boolean shouldRenderFace(Vector3 pos, Vector3 dir) {
        int nx = (int) (pos.getX() + dir.getX());
        int ny = (int) (pos.getY() + dir.getY());
        int nz = (int) (pos.getZ() + dir.getZ());

        // Check chunk bounds
        if (nx < 0 || nx >= CHUNK_SIZE ||
                ny < 0 || ny >= CHUNK_SIZE ||
                nz < 0 || nz >= CHUNK_SIZE) {
            return true; // TODO: check neighbor chunk
        }

        byte neighborId = getBlock(nx, ny, nz);
        Block neighbor = BlockRegistry.getBlockFromId(neighborId);
        return !neighbor.isOpaque();

    }

    private void addFace(Vector3 v0, Vector3 v1, Vector3 v2, Vector3 v3) {
        vertices.add(new Vertex(v0, new Vector2(uvs[0], uvs[3])));
        vertices.add(new Vertex(v1, new Vector2(uvs[0], uvs[1])));
        vertices.add(new Vertex(v2, new Vector2(uvs[2], uvs[1])));
        vertices.add(new Vertex(v3, new Vector2(uvs[2], uvs[3])));

        indices.add(offset);
        indices.add(offset + 1);
        indices.add(offset + 3);
        indices.add(offset + 3);
        indices.add(offset + 1);
        indices.add(offset + 2);
    }


}
