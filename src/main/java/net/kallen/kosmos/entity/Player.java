package main.java.net.kallen.kosmos.entity;

import main.java.net.kallen.engine.io.Input;
import main.java.net.kallen.engine.math.Vector2;
import main.java.net.kallen.engine.math.Vector3;
import main.java.net.kallen.kosmos.physics.Physics;
import main.java.net.kallen.kosmos.world.World;
import org.lwjgl.glfw.GLFW;

public class Player extends Entity {

    private float walkSpeed = 0.04f;
    private float sprintMultiplier = 1.3f;
    private float jumpStrength = 0.42f;
    private float mouseSensitivity = 0.15f;
    private float eyeLevel = 1.6f;

    private Gamemode gamemode;

    private float flySpeed = 0.05f;

    private float reach = 5.0f;

    public Player(Vector3 position) {
        super(position, new Vector2(0.6f, 1.8f));
        this.gamemode = Gamemode.SURVIVAL;
    }

    public void update(Vector2 mousePos, Vector2 lastMousePos, World world) {
        handleView(mousePos, lastMousePos);
        getMovementData();
        Physics.applyPhysics(this, world);
    }

    private void handleView(Vector2 mousePos, Vector2 lastMousePos) {
        float dx = mousePos.getX() - lastMousePos.getX();
        float dy = mousePos.getY() - lastMousePos.getY();

        float newPitch = rotation.getX() - dy * mouseSensitivity;
        float newYaw = rotation.getY() - dx * mouseSensitivity;

        newPitch = Math.max(-90f, Math.min(90f, newPitch));

        rotation = new Vector3(newPitch, newYaw, 0);
    }

    private void getMovementData() {
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
                velocity.setY(flySpeed);
            } else if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL)) {
                velocity.setY(-flySpeed);
            } else {
                velocity.setY(0);
            }
        } else {
            if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
                if (grounded) {
                    velocity.setY(jumpStrength);
                }
            }
        }

        velocity.setX(velocity.getX() + moveDir.getX());
        velocity.setZ(velocity.getZ() + moveDir.getZ());

    }

    public float getWalkSpeed() {
        return walkSpeed;
    }

    public void setWalkSpeed(float walkSpeed) {
        this.walkSpeed = walkSpeed;
    }

    public float getSprintMultiplier() {
        return sprintMultiplier;
    }

    public void setSprintMultiplier(float sprintMultiplier) {
        this.sprintMultiplier = sprintMultiplier;
    }

    public float getJumpStrength() {
        return jumpStrength;
    }

    public void setJumpStrength(float jumpStrength) {
        this.jumpStrength = jumpStrength;
    }

    public float getMouseSensitivity() {
        return mouseSensitivity;
    }

    public void setMouseSensitivity(float mouseSensitivity) {
        this.mouseSensitivity = mouseSensitivity;
    }

    @Override
    public void setFlying(boolean flying) {
        if (!isCreative()) return;
        isFlying = flying;
        if (flying) velocity.set(0f, 0f, 0f);
    }

    public float getReach() {
        return reach;
    }

    public void setReach(float reach) {
        this.reach = reach;
    }

    public void setGamemode(Gamemode gamemode) {
        this.gamemode = gamemode;
        System.out.println("Gamemode set to " + gamemode);
        if (gamemode == Gamemode.SPECTATOR) {
            noClip = true;
            isFlying = true;
        } else if (gamemode == Gamemode.SURVIVAL) {
            noClip = false;
            isFlying = false;
        } else {
            noClip = false;
        }
    }

    public Gamemode getGamemode() {
        return gamemode;
    }

    public boolean isSurvival() {
        return gamemode == Gamemode.SURVIVAL;
    }

    public boolean isCreative() {
        return gamemode == Gamemode.CREATIVE;
    }

    public boolean isSpectator() {
        return gamemode == Gamemode.SPECTATOR;
    }

    public float getEyeLevel() {
        return eyeLevel;
    }

}
