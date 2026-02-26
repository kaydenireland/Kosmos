package main.java.net.kallen.kosmos.render;

import main.java.net.kallen.kosmos.world.*;
import main.java.net.kallen.kosmos.world.chunk.Chunk;
import main.java.net.kallen.solaris.graphics.Mesh;
import main.java.net.kallen.solaris.graphics.Renderer;
import main.java.net.kallen.solaris.graphics.Vertex;
import main.java.net.kallen.solaris.math.vector.Vector2;
import main.java.net.kallen.solaris.math.vector.Vector3;
import main.java.net.kallen.solaris.graphics.AtlasMaterial;
import main.java.net.kallen.solaris.graphics.TextureAtlas;
import main.java.net.kallen.solaris.position.Direction;

import java.util.ArrayList;

public class ChunkMesh {

    private Mesh opaqueMesh;
    private Mesh transparentMesh;

    private final World world;
    private final Chunk chunk;
    private final Vector3 chunkPos;
    private final TextureAtlas atlas;

    private final ArrayList<Vertex> opaqueVertices = new ArrayList<>();
    private final ArrayList<Integer> opaqueIndices = new ArrayList<>();
    private int opaqueOffset;

    private final ArrayList<Vertex> transparentVertices = new ArrayList<>();
    private final ArrayList<Integer> transparentIndices = new ArrayList<>();
    private int transparentOffset;

    public ChunkMesh(World world, Chunk chunk, Vector3 chunkPos, TextureAtlas atlas) {
        this.world = world;
        this.chunk = chunk;
        this.chunkPos = chunkPos;
        this.atlas = atlas;
    }

    public void renderOpaque(Renderer renderer) {
        if (opaqueMesh != null) {
            renderer.renderMesh(opaqueMesh, BlockPosition.chunkToWorldPos(chunkPos));
        }
    }

    public void renderTransparent(Renderer renderer) {
        if (transparentMesh != null) {
            renderer.renderMesh(transparentMesh, BlockPosition.chunkToWorldPos(chunkPos));
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

                    if (blockId == Blocks.AIR) continue;

                    Block block = Blocks.getBlockFromId(blockId);
                    String blockName = Blocks.getNameFromId(blockId);
                    BlockModel model = ModelRegistry.getModel(blockName);

                    boolean isTransparent = !block.isOpaque();

                    Vector3 pos = new Vector3(x, y, z);

                    addFaceIfVisible(pos, Direction.NORTH, model, isTransparent);
                    addFaceIfVisible(pos, Direction.SOUTH, model, isTransparent);
                    addFaceIfVisible(pos, Direction.EAST, model, isTransparent);
                    addFaceIfVisible(pos, Direction.WEST, model, isTransparent);
                    addFaceIfVisible(pos, Direction.UP, model, isTransparent);
                    addFaceIfVisible(pos, Direction.DOWN, model, isTransparent);
                }
            }
        }
    }

    private void addFaceIfVisible(Vector3 pos, Direction direction, BlockModel model, boolean isTransparent) {
        int x = (int) pos.getX();
        int y = (int) pos.getY();
        int z = (int) pos.getZ();
        byte blockId = chunk.getBlock(x, y, z);

        if (!shouldRenderFace(blockId, pos, direction)) {
            return;
        }

        Vector3[] faceVertices = getFaceVertices(x, y, z, direction);

        String textureName = model.getTextureForFace(direction);
        float[] uvs = atlas.getUVs(textureName);

        addFace(faceVertices[0], faceVertices[1], faceVertices[2], faceVertices[3], uvs, isTransparent);
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
            case UP -> new Vector3[]{
                    new Vector3(x, y + 1, z),
                    new Vector3(x, y + 1, z + 1),
                    new Vector3(x + 1, y + 1, z + 1),
                    new Vector3(x + 1, y + 1, z)
            };
            case DOWN -> new Vector3[]{
                    new Vector3(x, y, z + 1),
                    new Vector3(x, y, z),
                    new Vector3(x + 1, y, z),
                    new Vector3(x + 1, y, z + 1)
            };
            default -> throw new IllegalArgumentException("Unknown direction: " + direction);
        };
    }

    private boolean shouldRenderFace(byte currentId, Vector3 localPos, Direction dir) {
        Vector3 worldPos = Vector3.add(BlockPosition.chunkToWorldPos(chunkPos), localPos);
        Vector3 neightborPos = Vector3.add(worldPos, dir.getVector());

        byte neighborId = world.getBlock(neightborPos);

        if (neighborId == Blocks.AIR) return true;

        Block current = Blocks.getBlockFromId(currentId);
        Block neighbor = Blocks.getBlockFromId(neighborId);

        if (neighbor.isOpaque()) return false;

        return current.isOpaque() || currentId != neighborId;
    }

    private void addFace(Vector3 v0, Vector3 v1, Vector3 v2, Vector3 v3, float[] uvs, boolean isTransparent) {
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