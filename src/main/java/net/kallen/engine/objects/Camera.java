package main.java.net.kallen.engine.objects;


import main.java.net.kallen.engine.io.Input;
import main.java.net.kallen.engine.math.Vector2;
import main.java.net.kallen.engine.math.Vector3;
import org.lwjgl.glfw.GLFW;

public class Camera {
    private Vector3 position, rotation;
    private float moveSpeed = 0.05f;
    private float mouseSensitivity = 0.15f;
    private Vector2 oldMousePos = new Vector2(0f, 0f);
    private Vector2 newMousePos = new Vector2(0f, 0f);

    public Camera(Vector3 position, Vector3 rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public void update() {
        newMousePos.setX((float) Input.getMouseX());
        newMousePos.setY((float) Input.getMouseY());

        float x = (float) Math.sin(Math.toRadians(rotation.getY())) * moveSpeed;
        float z = (float) Math.cos(Math.toRadians(rotation.getY())) * moveSpeed;

        if (Input.isKeyDown(GLFW.GLFW_KEY_A)) position = Vector3.add(position, new Vector3(-z, 0, x));
        if (Input.isKeyDown(GLFW.GLFW_KEY_D)) position = Vector3.add(position, new Vector3(z, 0, -x));
        if (Input.isKeyDown(GLFW.GLFW_KEY_W)) position = Vector3.add(position, new Vector3(-x, 0, -z));
        if (Input.isKeyDown(GLFW.GLFW_KEY_S)) position = Vector3.add(position, new Vector3(x, 0, z));
        if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) position = Vector3.add(position, new Vector3(0, moveSpeed, 0));
        if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) position = Vector3.add(position, new Vector3(0, -moveSpeed, 0));
        
        float dx = newMousePos.getX() - oldMousePos.getX();
        float dy = newMousePos.getY() - oldMousePos.getY();

        rotation = Vector3.add(rotation, new Vector3(-dy * mouseSensitivity, -dx * mouseSensitivity, 0));

        oldMousePos.set(newMousePos.getX(), newMousePos.getY());
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getRotation() {
        return rotation;
    }
}