package main.java.net.kallen.engine.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;


public class Material {
    private String path;
    private int width, height;
    private int textureID;

    public Material(String path) {
        this.path = path;
    }

    public Material(int textureID, int width, int height) {
        this.textureID = textureID;
        this.width = width;
        this.height = height;
    }

    public void create() {
        try (InputStream is = Material.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new IOException("Resource not found: " + path);
            }

            // Read the input stream into a ByteBuffer
            byte[] imageData = is.readAllBytes();
            ByteBuffer imageBuffer = BufferUtils.createByteBuffer(imageData.length);
            imageBuffer.put(imageData);
            imageBuffer.flip();

            // Load image with STB
            IntBuffer widthBuf = BufferUtils.createIntBuffer(1);
            IntBuffer heightBuf = BufferUtils.createIntBuffer(1);
            IntBuffer channelsBuf = BufferUtils.createIntBuffer(1);

            ByteBuffer image = STBImage.stbi_load_from_memory(imageBuffer, widthBuf, heightBuf, channelsBuf, 4);

            if (image == null) {
                throw new RuntimeException("Failed to load image: " + STBImage.stbi_failure_reason());
            }

            width = widthBuf.get(0);
            height = heightBuf.get(0);

            // Create OpenGL texture
            textureID = GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

            // Set texture parameters
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

            // Upload texture data
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0,
                    GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);

            // Free the image memory
            STBImage.stbi_image_free(image);

        } catch (IOException e) {
            System.err.println("Can't find texture at " + path);
        }
    }

    public void destroy() {
        GL13.glDeleteTextures(textureID);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getTextureID() {
        return textureID;
    }

}
