package main.java.net.kallen.kosmos.entity;

import main.java.net.kallen.engine.math.Vector3;
import main.java.net.kallen.kosmos.world.Direction;

public class Entity {

    protected Vector3 position;
    protected Vector3 lastPosition;
    protected Vector3 velocity;
    protected Vector3 rotation;

    public Entity(Vector3 position) {
        this.position = position;
        this.lastPosition = Vector3.add(position, Direction.ABOVE.getVector());
        this.velocity = new Vector3(0, 0, 0);
        this.rotation = new Vector3(0, 0, 0);
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

    public void setPosition(Vector3 position) {
        this.lastPosition = this.position;
        this.position = position;
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

}
