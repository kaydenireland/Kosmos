package main.java.net.kallen.solaris.util;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class TextureLoader {

    public static ByteBuffer loadTexture(String path) {
        try (InputStream is = TextureLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                System.err.println("Resource not found: " + path);
                return null;
            }

            byte[] imageData = is.readAllBytes();
            ByteBuffer imageBuffer = BufferUtils.createByteBuffer(imageData.length);
            imageBuffer.put(imageData);
            imageBuffer.flip();

            IntBuffer width = BufferUtils.createIntBuffer(1);
            IntBuffer height = BufferUtils.createIntBuffer(1);
            IntBuffer channels = BufferUtils.createIntBuffer(1);

            ByteBuffer image = STBImage.stbi_load_from_memory(imageBuffer, width, height, channels, 4);

            if (image == null) {
                System.err.println("STBImage failed: " + STBImage.stbi_failure_reason());
                return null;
            }

            return image;
        } catch (Exception e) {
            System.err.println("Error loading texture: " + path);
            e.printStackTrace();
            return null;
        }
    }

    public static ByteBuffer loadTexture(int size, String path) {
        try (InputStream is = TextureLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                System.err.println("Resource not found: " + path);
                return null;
            }

            byte[] imageData = is.readAllBytes();
            ByteBuffer imageBuffer = BufferUtils.createByteBuffer(imageData.length);
            imageBuffer.put(imageData);
            imageBuffer.flip();

            IntBuffer width = BufferUtils.createIntBuffer(1);
            IntBuffer height = BufferUtils.createIntBuffer(1);
            IntBuffer channels = BufferUtils.createIntBuffer(1);

            ByteBuffer image = STBImage.stbi_load_from_memory(imageBuffer, width, height, channels, 4);

            if (image == null) {
                System.err.println("STBImage failed: " + STBImage.stbi_failure_reason());
                return null;
            }

            // Verify size
            if (width.get(0) != size || height.get(0) != size) {
                System.err.println("Warning: " + path + " is " + width.get(0) + "x" + height.get(0) +
                        ", expected " + size + "x" + size);
            }

            return image;
        } catch (Exception e) {
            System.err.println("Error loading texture: " + path);
            e.printStackTrace();
            return null;
        }
    }

}
