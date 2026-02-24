package main.java.net.kallen.solaris.graphics;

import main.java.net.kallen.solaris.io.Window;
import main.java.net.kallen.solaris.math.matrix.Matrix4;
import main.java.net.kallen.solaris.math.vector.Vector3;
import main.java.net.kallen.solaris.object.camera.Camera;
import main.java.net.kallen.solaris.object.GameObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;


public class Renderer {

    private Window window;
    private Shader shader;
    private Camera camera;
    private Vector3 skyColor;

    private float fogDensity = 0.007f;
    private float fogGradient = 1.5f;

    public Renderer(Window window, Shader shader, Camera camera, Vector3 skyColor) {
        this.window = window;
        this.shader = shader;
        this.camera = camera;
        this.skyColor = skyColor;
    }

    public void renderMesh(Mesh mesh, Vector3 position) {

        GL30.glBindVertexArray(mesh.getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, mesh.getMaterial().getTextureID());
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        shader.bind();

        // Back Face Culling
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glFrontFace(GL11.GL_CCW);

        // Enable transparency
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);

        // Uniforms
        setUniforms(position);

        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        shader.unbind();

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    public void renderMesh(Mesh mesh) {
        renderMesh(mesh, new Vector3(0, 0, 0));
    }

    private void setUniforms(Vector3 position) {
        shader.setUniform("model", Matrix4.translate(position));
        shader.setUniform("view", Matrix4.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("projection", window.getProjectionMatrix());
        shader.setUniform("skyColor", skyColor);
        shader.setUniform("fogDensity", fogDensity);
        shader.setUniform("fogGradient", fogGradient);
    }

    public Vector3 getSkyColor() {
        return skyColor;
    }

    public float getFogDensity() {
        return fogDensity;
    }

    public float getFogGradient() {
        return fogGradient;
    }

    public void setSkyColor(Vector3 color) {
        this.skyColor = color;
    }

    public void setFogDensity(float density) {
        this.fogDensity = density;
    }

    public void setFogGradient(float gradient) {
        this.fogGradient = gradient;
    }
}