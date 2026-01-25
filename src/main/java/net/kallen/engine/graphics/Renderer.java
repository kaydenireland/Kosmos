package main.java.net.kallen.engine.graphics;

import main.java.net.kallen.engine.io.Window;
import main.java.net.kallen.engine.math.Matrix4;
import main.java.net.kallen.engine.objects.Camera;
import main.java.net.kallen.engine.objects.GameObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Renderer {

    private Window window;
    private Shader shader;
    private Camera camera;

    public Renderer(Window window, Shader shader, Camera camera) {
        this.window = window;
        this.shader = shader;
        this.camera = camera;
    }

    public void renderObject(GameObject object) {
        GL30.glBindVertexArray(object.getMesh().getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, object.getMesh().getMaterial().getTextureID());

        shader.bind();
        shader.setUniform("model", Matrix4.transform(object.getPosition(), object.getRotation(), object.getScale()));
        shader.setUniform("view", Matrix4.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("projection", window.getProjectionMatrix());
        GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        shader.unbind();

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

}