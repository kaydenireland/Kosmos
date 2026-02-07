package main.java.net.kallen.kosmos.render;

import main.java.net.kallen.kosmos.world.Blocks;
import main.java.net.kallen.kosmos.world.Direction;

import java.util.HashMap;
import java.util.Map;

public class BlockModel {
    private final String id;
    private final Map<Direction, String> faceTextures;

    private BlockModel(String id, Map<Direction, String> faceTextures) {
        this.id = id;
        this.faceTextures = faceTextures;
    }

    public String getTextureForFace(Direction direction) {
        return faceTextures.get(direction);
    }

    public String getId() {
        return id;
    }

    public static class Builder {
        private final String id;
        private final Map<Direction, String> faceTextures = new HashMap<>();

        public Builder(Byte id) {
            this.id = Blocks.getNameFromId(id);
        }

        public Builder allFaces(String texture) {
            for (Direction dir : Direction.values()) {
                faceTextures.put(dir, texture);
            }
            return this;
        }

        public Builder topBottomSides(String top, String bottom, String sides) {
            faceTextures.put(Direction.ABOVE, top);
            faceTextures.put(Direction.BELOW, bottom);
            faceTextures.put(Direction.NORTH, sides);
            faceTextures.put(Direction.SOUTH, sides);
            faceTextures.put(Direction.EAST, sides);
            faceTextures.put(Direction.WEST, sides);
            return this;
        }

        public Builder pillar(String horizontal, String sides) {
            faceTextures.put(Direction.ABOVE, horizontal);
            faceTextures.put(Direction.BELOW, horizontal);
            faceTextures.put(Direction.NORTH, sides);
            faceTextures.put(Direction.SOUTH, sides);
            faceTextures.put(Direction.EAST, sides);
            faceTextures.put(Direction.WEST, sides);
            return this;
        }

        public Builder face(Direction direction, String texture) {
            faceTextures.put(direction, texture);
            return this;
        }

        public BlockModel build() {
            for (Direction dir : Direction.values()) {
                if (!faceTextures.containsKey(dir)) {
                    throw new IllegalStateException("Model " + id + " missing texture for face " + dir);
                }
            }
            return new BlockModel(id, faceTextures);
        }
    }
}