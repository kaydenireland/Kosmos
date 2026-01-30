package main.java.net.kallen.kosmos.util;

import main.java.net.kallen.engine.math.Vector2;
import main.java.net.kallen.engine.util.TextureLoader;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class TextureAtlas {

    private int textureID;
    private int gridSize;
    private int atlasPixels;
    private final int textureSize;
    private Map<String, Vector2> textureLocations = new HashMap<>();

    public TextureAtlas(int textureSize, String[] paths) {
        this.textureSize = textureSize;
        int totalEntries = paths.length;
        gridSize = (int) Math.ceil(Math.sqrt(totalEntries));
        build(paths);
    }

      private void build(String[] paths) {
        atlasPixels = gridSize * textureSize;
        ByteBuffer atlasBuffer = BufferUtils.createByteBuffer(atlasPixels * atlasPixels * 4);

        // Fill with transparent black
        for (int i = 0; i < atlasPixels * atlasPixels * 4; i++) {
            atlasBuffer.put((byte) 0);
        }
        atlasBuffer.flip();

        for (int i = 0; i < paths.length && i < gridSize * gridSize; i++) {
            int gridX = i % gridSize;
            int gridY = i / gridSize;

            ByteBuffer textureData = TextureLoader.loadTexture(textureSize, paths[i]);

            if (textureData != null) {
                // Copy texture to atlas
                copyToAtlas(atlasBuffer, textureData, gridX * textureSize, gridY * textureSize, atlasPixels);

                // Store position
                String key = paths[i].substring(paths[i].lastIndexOf("/") + 1).replace(".png", "");
                textureLocations.put(key, new Vector2(gridX, gridY));

                STBImage.stbi_image_free(textureData);
                System.out.println("Added " + paths[i] + " to atlas at (" + gridX + ", " + gridY + ")");
            } else {
                System.err.println("Failed to load texture: " + paths[i]);
            }
        }

        // Upload to GPU
        textureID = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

        atlasBuffer.position(0); // Reset position for upload
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, atlasPixels, atlasPixels,
                0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, atlasBuffer);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        System.out.println("Atlas built with " + textureLocations.size() + " textures");

    }

    private void copyToAtlas(ByteBuffer atlas, ByteBuffer texture, int offsetX, int offsetY, int atlasWidth) {
        for (int y = 0; y < textureSize; y++) {
            for (int x = 0; x < textureSize; x++) {
                int srcIndex = (y * textureSize + x) * 4;
                int dstIndex = ((offsetY + y) * atlasWidth + (offsetX + x)) * 4;

                atlas.put(dstIndex + 0, texture.get(srcIndex + 0)); // R
                atlas.put(dstIndex + 1, texture.get(srcIndex + 1)); // G
                atlas.put(dstIndex + 2, texture.get(srcIndex + 2)); // B
                atlas.put(dstIndex + 3, texture.get(srcIndex + 3)); // A
            }
        }
    }

    public int getTextureID() {
        return textureID;
    }

    public int getGridSize() {
        return gridSize;
    }

    public Vector2 getPosition(String name) {
        return textureLocations.getOrDefault(name, new Vector2(0, 0));
    }

    public float[] getUVs(String name) {
        Vector2 pos = getPosition(name);
        // System.out.println(pos.getX() + " " + pos.getY());
        float pixelSize = 1.0f / gridSize;

        float u0 = pos.getX() * pixelSize;
        float v0 = pos.getY() * pixelSize;
        float u1 = (pos.getX() + 1) * pixelSize;
        float v1 = (pos.getY() + 1) * pixelSize;

        float padding = 0.5f / atlasPixels;
        u0 += padding;
        v0 += padding;
        u1 -= padding;
        v1 -= padding;


        return new float[] { u0, v0, u1, v1 };
    }

    public void destroy() {
        GL11.glDeleteTextures(textureID);
    }

}
