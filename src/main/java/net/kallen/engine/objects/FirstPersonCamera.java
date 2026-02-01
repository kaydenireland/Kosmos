package main.java.net.kallen.engine.objects;

import main.java.net.kallen.engine.io.Input;
import main.java.net.kallen.engine.math.Vector3;

public class FirstPersonCamera extends Camera {
    private float mouseSensitivity = 0.15f;
    private float minPitch = -90f;
    private float maxPitch = 90f;
    private boolean limitPitch = true;

    private double lastMouseX = 0;
    private double lastMouseY = 0;
    private boolean firstUpdate = true;

    public FirstPersonCamera(Vector3 position, Vector3 rotation) {
        super(position, rotation);
    }

    @Override
    public void update() {
        handleMouseLook();
    }

    public void followTarget(Vector3 targetPosition, Vector3 targetRotation) {
        this.position.set(targetPosition.getX(), targetPosition.getY(), targetPosition.getZ());
        this.rotation.set(targetRotation.getX(), targetRotation.getY(), targetRotation.getZ());
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

        if (limitPitch) newPitch = clamp(newPitch, minPitch, maxPitch);

        rotation.set(newPitch, newYaw, 0);

        lastMouseX = mouseX;
        lastMouseY = mouseY;
    }

    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    public void setMouseSensitivity(float sensitivity) {
        this.mouseSensitivity = sensitivity;
    }

    public void setPitchLimits(float min, float max) {
        this.minPitch = min;
        this.maxPitch = max;
    }
}