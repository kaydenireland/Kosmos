package main.java.net.kallen.kosmos.entity;

import main.java.net.kallen.engine.math.Vector2;
import main.java.net.kallen.engine.math.Vector3;
import main.java.net.kallen.engine.physics.AABB;

public class Entity {

    protected Vector3 position;
    protected Vector3 lastPosition;
    protected Vector3 velocity;
    protected Vector3 rotation;
    protected Vector2 size; // w, h
    protected AABB boundingBox;

    protected boolean grounded = false;
    protected boolean noClip = false;
    protected boolean isFlying = true;

    public Entity(Vector3 position, Vector2 size) {
        this.position = position;
        this.lastPosition = Vector3.add(position, new Vector3(0, -10, 0));
        this.velocity = new Vector3(0, 0, 0);
        this.rotation = new Vector3(0, 0, 0);
        this.size = size;
        updateBoundingBox();
    }

    public void updateBoundingBox() {
        float halfWidth = size.getX() / 2.0f;
        boundingBox = new AABB(
                position.getX() - halfWidth,
                position.getY(),
                position.getZ() - halfWidth,
                position.getX() + halfWidth,
                position.getY() + size.getY(),
                position.getZ() + halfWidth
        );
    }

    public void setBoundingBox(AABB boundingBox) {
        this.boundingBox = boundingBox;
    }

    public void update() {}

    private void move() {}

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public Vector3 getRotation() {
        return rotation;
    }

    public void setPosition(Vector3 newPos) {
        lastPosition = new Vector3(position);
        this.position.set(newPos.getX(), newPos.getY(), newPos.getZ());
    }

    public void setVelocity(Vector3 velocity) {
        this.velocity = velocity;
    }

    public void setRotation(Vector3 rotation) {
        this.rotation = rotation;
    }

    public Vector3 getChunkPosition() {
        return new Vector3(
                (int) Math.floor(position.getX() / 16),
                (int) Math.floor(position.getY() / 16),
                (int) Math.floor(position.getZ() / 16)
        );
    }

    public Vector3 getLastChunkPosition() {
        return new Vector3(
                (int) Math.floor(lastPosition.getX() / 16),
                (int) Math.floor(lastPosition.getY() / 16),
                (int) Math.floor(lastPosition.getZ() / 16)
        );
    }

    public Vector2 getSize() {
        return size;
    }

    public float getWidth() {
        return size.getX();
    }

    public float getHeight() {
        return size.getY();
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public boolean noClip() {
        return noClip;
    }

    public AABB getBoundingBox() {
        return boundingBox;
    }

    public boolean isFlying() {
        return isFlying;
    }

    public void setFlying(boolean flying) {
        isFlying = flying;
        if (flying) velocity.set(0f, 0f, 0f);
    }

}
