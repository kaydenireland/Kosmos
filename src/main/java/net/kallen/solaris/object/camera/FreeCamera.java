package main.java.net.kallen.solaris.object.camera;

import main.java.net.kallen.solaris.io.Input;
import main.java.net.kallen.solaris.math.vector.Vector3;
import org.lwjgl.glfw.GLFW;

public class FreeCamera extends Camera {
    private float moveSpeed = 0.1f;
    private float mouseSensitivity = 0.15f;

    private double lastMouseX = 0;
    private double lastMouseY = 0;
    private boolean firstUpdate = true;

    public FreeCamera(Vector3 position, Vector3 rotation) {
        super(position, rotation);
    }

    @Override
    public void update() {
        handleMouseLook();
        handleMovement();
    }

    private void handleMouseLook() {
        double mouseX = Input.getMouseX();
        double mouseY = Input.getMouseY();

        if (firstUpdate) {
            lastMouseX = mouseX;
            lastMouseY = mouseY;
            firstUpdate = false;
            return;
        }

        float dx = (float) (mouseX - lastMouseX);
        float dy = (float) (mouseY - lastMouseY);

        float newPitch = rotation.getX() - dy * mouseSensitivity;
        float newYaw = rotation.getY() - dx * mouseSensitivity;

        newPitch = clamp(newPitch, -89.0f, 89.0f);

        rotation.set(newPitch, newYaw, 0);

        lastMouseX = mouseX;
        lastMouseY = mouseY;
    }

    private void handleMovement() {
        float speed = moveSpeed;

        if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL)) {
            speed *= 2.0f;
        }

        float yaw = (float) Math.toRadians(rotation.getY());
        float forward = (float) Math.cos(yaw);
        float right = (float) Math.sin(yaw);

        if (Input.isKeyDown(GLFW.GLFW_KEY_W)) {
            position = Vector3.add(position, new Vector3(-right * speed, 0, -forward * speed));
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_S)) {
            position = Vector3.add(position, new Vector3(right * speed, 0, forward * speed));
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_A)) {
            position = Vector3.add(position, new Vector3(-forward * speed, 0, right * speed));
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_D)) {
            position = Vector3.add(position, new Vector3(forward * speed, 0, -right * speed));
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
            position = Vector3.add(position, new Vector3(0, speed, 0));
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            position = Vector3.add(position, new Vector3(0, -speed, 0));
        }
    }

    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    public void setMoveSpeed(float speed) {
        this.moveSpeed = speed;
    }

    public void setMouseSensitivity(float sensitivity) {
        this.mouseSensitivity = sensitivity;
    }
}