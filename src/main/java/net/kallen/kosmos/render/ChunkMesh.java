package main.java.net.kallen.kosmos.render;

import main.java.net.kallen.engine.graphics.Mesh;
import main.java.net.kallen.engine.graphics.Renderer;
import main.java.net.kallen.engine.graphics.Vertex;
import main.java.net.kallen.engine.math.Vector2;
import main.java.net.kallen.engine.math.Vector3;
import main.java.net.kallen.kosmos.texture.AtlasMaterial;
import main.java.net.kallen.kosmos.texture.TextureAtlas;
import main.java.net.kallen.kosmos.world.Block;
import main.java.net.kallen.kosmos.world.BlockRegistry;
import main.java.net.kallen.kosmos.world.Chunk;
import main.java.net.kallen.kosmos.world.Direction;

import java.util.ArrayList;

public class ChunkMesh {

    private Mesh opaqueMesh;
    private Mesh transparentMesh;

    private final Chunk chunk;
    private final Vector3 position;
    private final TextureAtlas atlas;

    private final ArrayList<Vertex> opaqueVertices = new ArrayList<>();
    private final ArrayList<Integer> opaqueIndices = new ArrayList<>();
    private int opaqueOffset;

    private final ArrayList<Vertex> transparentVertices = new ArrayList<>();
    private final ArrayList<Integer> transparentIndices = new ArrayList<>();
    private int transparentOffset;

    private float[] uvs;

    public ChunkMesh(Chunk chunk, Vector3 position, TextureAtlas atlas) {
        this.chunk = chunk;
        this.position = position;
        this.atlas = atlas;
    }

    public void renderOpaque(Renderer renderer) {
        if (opaqueMesh != null) {
            renderer.renderMesh(opaqueMesh, position);
        }
    }

    public void renderTransparent(Renderer renderer) {
        if (transparentMesh != null) {
            renderer.renderMesh(transparentMesh, position);
        }
    }

    public void generateMesh() {
        clearMeshData();

        buildGeometry();

        createMeshes();
    }

    private void clearMeshData() {
        opaqueVertices.clear();
        opaqueIndices.clear();
        transparentVertices.clear();
        transparentIndices.clear();
        opaqueOffset = 0;
        transparentOffset = 0;
    }

    private void buildGeometry() {
        for (int x = 0; x < chunk.SIZE; x++) {
            for (int y = 0; y < chunk.SIZE; y++) {
                for (int z = 0; z < chunk.SIZE; z++) {
                    byte blockId = chunk.getBlock(x, y, z);

                    if (blockId == BlockRegistry.AIR) continue;

                    Block block = BlockRegistry.getBlockFromId(blockId);
                    boolean isTransparent = !block.isOpaque();

                    uvs = atlas.getUVs(BlockRegistry.getNameFromId(blockId));

                    Vector3 pos = new Vector3(x, y, z);

                    addFaceIfVisible(pos, Direction.NORTH, isTransparent);
                    addFaceIfVisible(pos, Direction.SOUTH, isTransparent);
                    addFaceIfVisible(pos, Direction.EAST, isTransparent);
                    addFaceIfVisible(pos, Direction.WEST, isTransparent);
                    addFaceIfVisible(pos, Direction.ABOVE, isTransparent);
                    addFaceIfVisible(pos, Direction.BELOW, isTransparent);
                }
            }
        }
    }

    private void addFaceIfVisible(Vector3 pos, Direction direction, boolean isTransparent) {
        int x = (int) pos.getX();
        int y = (int) pos.getY();
        int z = (int) pos.getZ();
        byte blockId = chunk.getBlock(x, y, z);

        if (!shouldRenderFace(blockId, pos, direction)) {
            return;
        }

        Vector3[] faceVertices = getFaceVertices(x, y, z, direction);

        addFace(faceVertices[0], faceVertices[1], faceVertices[2], faceVertices[3], isTransparent);
    }

    private Vector3[] getFaceVertices(int x, int y, int z, Direction direction) {
        return switch (direction) {
            case NORTH -> new Vector3[]{
                    new Vector3(x + 1, y + 1, z),
                    new Vector3(x + 1, y, z),
                    new Vector3(x, y, z),
                    new Vector3(x, y + 1, z)
            };
            case SOUTH -> new Vector3[]{
                    new Vector3(x, y + 1, z + 1),
                    new Vector3(x, y, z + 1),
                    new Vector3(x + 1, y, z + 1),
                    new Vector3(x + 1, y + 1, z + 1)
            };
            case EAST -> new Vector3[]{
                    new Vector3(x + 1, y + 1, z + 1),
                    new Vector3(x + 1, y, z + 1),
                    new Vector3(x + 1, y, z),
                    new Vector3(x + 1, y + 1, z)
            };
            case WEST -> new Vector3[]{
                    new Vector3(x, y + 1, z),
                    new Vector3(x, y, z),
                    new Vector3(x, y, z + 1),
                    new Vector3(x, y + 1, z + 1)
            };
            case ABOVE -> new Vector3[]{
                    new Vector3(x, y + 1, z),
                    new Vector3(x, y + 1, z + 1),
                    new Vector3(x + 1, y + 1, z + 1),
                    new Vector3(x + 1, y + 1, z)
            };
            case BELOW -> new Vector3[]{
                    new Vector3(x, y, z + 1),
                    new Vector3(x, y, z),
                    new Vector3(x + 1, y, z),
                    new Vector3(x + 1, y, z + 1)
            };
            default -> throw new IllegalArgumentException("Unknown direction: " + direction);
        };
    }

    private boolean shouldRenderFace(byte currentId, Vector3 pos, Direction dir) {
        int nx = (int) (pos.getX() + dir.getX());
        int ny = (int) (pos.getY() + dir.getY());
        int nz = (int) (pos.getZ() + dir.getZ());

        if (nx < 0 || nx >= chunk.SIZE ||
                ny < 0 || ny >= chunk.SIZE ||
                nz < 0 || nz >= chunk.SIZE) {
            return true; // TODO: check neighbor chunk
        }

        byte neighborId = chunk.getBlock(nx, ny, nz);

        if (neighborId == BlockRegistry.AIR) return true;

        Block currentBlock = BlockRegistry.getBlockFromId(currentId);
        Block neighborBlock = BlockRegistry.getBlockFromId(neighborId);

        if (neighborBlock.isOpaque()) return false;

        return currentBlock.isOpaque() || currentId != neighborId;
    }

    private void addFace(Vector3 v0, Vector3 v1, Vector3 v2, Vector3 v3, boolean isTransparent) {

        ArrayList<Vertex> vertices = isTransparent ? transparentVertices : opaqueVertices;
        ArrayList<Integer> indices = isTransparent ? transparentIndices : opaqueIndices;
        int offset = isTransparent ? transparentOffset : opaqueOffset;

        vertices.add(new Vertex(v0, new Vector2(uvs[0], uvs[1])));
        vertices.add(new Vertex(v1, new Vector2(uvs[0], uvs[3])));
        vertices.add(new Vertex(v2, new Vector2(uvs[2], uvs[3])));
        vertices.add(new Vertex(v3, new Vector2(uvs[2], uvs[1])));

        indices.add(offset);
        indices.add(offset + 1);
        indices.add(offset + 3);

        indices.add(offset + 3);
        indices.add(offset + 1);
        indices.add(offset + 2);

        if (isTransparent) {
            transparentOffset += 4;
        } else {
            opaqueOffset += 4;
        }
    }

    private void createMeshes() {
        destroyMeshes();

        if (!opaqueVertices.isEmpty()) {
            opaqueMesh = new Mesh(
                    opaqueVertices.toArray(new Vertex[0]),
                    opaqueIndices.stream().mapToInt(i -> i).toArray(),
                    new AtlasMaterial(
                            atlas.getTextureID(),
                            atlas.getGridSize(),
                            atlas.getGridSize()
                    )
            );
            opaqueMesh.create();
        }

        if (!transparentVertices.isEmpty()) {
            transparentMesh = new Mesh(
                    transparentVertices.toArray(new Vertex[0]),
                    transparentIndices.stream().mapToInt(i -> i).toArray(),
                    new AtlasMaterial(
                            atlas.getTextureID(),
                            atlas.getGridSize(),
                            atlas.getGridSize()
                    )
            );
            transparentMesh.create();
        }
    }

    public void destroy() {
        destroyMeshes();
    }

    private void destroyMeshes() {
        if (opaqueMesh != null) {
            opaqueMesh.destroy();
            opaqueMesh = null;
        }
        if (transparentMesh != null) {
            transparentMesh.destroy();
            transparentMesh = null;
        }
    }

    public boolean isEmpty() {
        return opaqueMesh == null && transparentMesh == null;
    }

    public boolean hasOpaqueGeometry() {
        return opaqueMesh != null;
    }

    public boolean hasTransparentGeometry() {
        return transparentMesh != null;
    }
}