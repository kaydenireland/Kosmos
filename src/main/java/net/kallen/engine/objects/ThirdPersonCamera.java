package main.java.net.kallen.engine.objects;

import main.java.net.kallen.engine.io.Input;
import main.java.net.kallen.engine.math.Vector3;
import org.lwjgl.glfw.GLFW;

public class ThirdPersonCamera extends Camera {
    private Vector3 targetPosition;
    private float distance = 5.0f;
    private float minDistance = 1.0f;
    private float maxDistance = 20.0f;

    private float horizontalAngle = 0;
    private float verticalAngle = 20;
    private float mouseSensitivity = 0.15f;

    private double lastMouseX = 0;
    private double lastMouseY = 0;
    private boolean firstUpdate = true;

    public ThirdPersonCamera(Vector3 position, Vector3 targetPosition) {
        super(position, new Vector3(0, 0, 0));
        this.targetPosition = targetPosition;
    }

    @Override
    public void update() {
        handleMouseInput();
        updateCameraPosition();
    }

    public void setTarget(Vector3 target) {
        this.targetPosition.set(target.getX(), target.getY(), target.getZ());
    }

    private void handleMouseInput() {
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

        if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            horizontalAngle += dx * mouseSensitivity;
            verticalAngle -= dy * mouseSensitivity;
            verticalAngle = clamp(verticalAngle, -89.0f, 89.0f);
        }

        if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
            distance += dy * mouseSensitivity / 4;
            distance = clamp(distance, minDistance, maxDistance);
        }

        lastMouseX = mouseX;
        lastMouseY = mouseY;
    }

    private void updateCameraPosition() {
        float horizontalDistance = (float) (distance * Math.cos(Math.toRadians(verticalAngle)));
        float verticalDistance = (float) (distance * Math.sin(Math.toRadians(verticalAngle)));

        float xOffset = (float) (horizontalDistance * Math.sin(Math.toRadians(-horizontalAngle)));
        float zOffset = (float) (horizontalDistance * Math.cos(Math.toRadians(-horizontalAngle)));

        position.set(
                targetPosition.getX() + xOffset,
                targetPosition.getY() + verticalDistance,
                targetPosition.getZ() + zOffset
        );

        rotation.set(verticalAngle, -horizontalAngle, 0);
    }

    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    public void setDistance(float distance) {
        this.distance = clamp(distance, minDistance, maxDistance);
    }

    public void setDistanceLimits(float min, float max) {
        this.minDistance = min;
        this.maxDistance = max;
    }
}