package main.java.net.kallen.kosmos.entity;

import main.java.net.kallen.engine.io.Input;
import main.java.net.kallen.engine.math.Vector2;
import main.java.net.kallen.engine.math.Vector3;
import org.lwjgl.glfw.GLFW;

public class Player extends Entity {

    private float walkSpeed = 0.05f;
    private float sprintMultiplier = 2.0f;
    private float mouseSensitivity = 0.15f;

    private boolean isFlying = true;

    public Player(Vector3 position) {
        super(position);
    }

    public void update(Vector2 mousePos, Vector2 lastMousePos) {
        handleView(mousePos, lastMousePos);
        move();
    }

    private void handleView(Vector2 mousePos, Vector2 lastMousePos) {
        float dx = mousePos.getX() - lastMousePos.getX();
        float dy = mousePos.getY() - lastMousePos.getY();

        float newPitch = rotation.getX() - dy * mouseSensitivity;
        float newYaw = rotation.getY() - dx * mouseSensitivity;

        newPitch = Math.max(-89.0f, Math.min(89.0f, newPitch));

        rotation = new Vector3(newPitch, newYaw, 0);
    }

    private void move() {
        float velo = walkSpeed;

        if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            velo *= sprintMultiplier;
        }

        float yaw = (float) Math.toRadians(rotation.getY());
        float forward = (float) Math.cos(yaw);
        float right = (float) Math.sin(yaw);

        Vector3 moveDir = new Vector3(0, 0, 0);

        if (Input.isKeyDown(GLFW.GLFW_KEY_W)) {
            moveDir = Vector3.add(moveDir, new Vector3(-right * velo, 0, -forward * velo));
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_S)) {
            moveDir = Vector3.add(moveDir, new Vector3(right * velo, 0, forward * velo));
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_A)) {
            moveDir = Vector3.add(moveDir, new Vector3(-forward * velo, 0, right * velo));
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_D)) {
            moveDir = Vector3.add(moveDir, new Vector3(forward * velo, 0, -right * velo));
        }

        if (isFlying) {
            if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
                moveDir = Vector3.add(moveDir, new Vector3(0, velo, 0));
            }
            if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL)) {
                moveDir = Vector3.add(moveDir, new Vector3(0, -velo, 0));
            }
        }

        position = Vector3.add(position, moveDir);
    }


    public boolean isFlying() {
        return isFlying;
    }

    public void setFlying(boolean flying) {
        isFlying = flying;
    }
}
