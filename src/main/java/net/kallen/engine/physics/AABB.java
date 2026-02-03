package main.java.net.kallen.engine.physics;

import main.java.net.kallen.engine.math.Vector3;

public class AABB {
    public Vector3 min;
    public Vector3 max;
    private final float EPSILON = 0.0001f;

    public AABB(Vector3 min, Vector3 max) {
        this.min = min;
        this.max = max;
    }

    public AABB(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        this.min = new Vector3(minX, minY, minZ);
        this.max = new Vector3(maxX, maxY, maxZ);
    }

    public boolean intersects(AABB other) {
        return this.max.getX() > other.min.getX() && this.min.getX() < other.max.getX() &&
                this.max.getY() > other.min.getY() && this.min.getY() < other.max.getY() &&
                this.max.getZ() > other.min.getZ() && this.min.getZ() < other.max.getZ();
    }

    public AABB expand(float x, float y, float z) {
        float newMinX = x < 0 ? min.getX() + x : min.getX();
        float newMinY = y < 0 ? min.getY() + y : min.getY();
        float newMinZ = z < 0 ? min.getZ() + z : min.getZ();
        float newMaxX = x > 0 ? max.getX() + x : max.getX();
        float newMaxY = y > 0 ? max.getY() + y : max.getY();
        float newMaxZ = z > 0 ? max.getZ() + z : max.getZ();

        return new AABB(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ);
    }

    public AABB offset(float x, float y, float z) {
        return new AABB(min.getX() + x, min.getY() + y, min.getZ() + z, max.getX() + x, max.getY() + y, max.getZ() + z);
    }

    public float calculateYOffset(AABB other, float offsetY) {
        if (other.max.getX() > this.min.getX() && other.min.getX() < this.max.getX() &&
                other.max.getZ() > this.min.getZ() && other.min.getZ() < this.max.getZ()) {

            if (offsetY < 0 && other.min.getY() >= this.max.getY() - EPSILON) {
                float diff = this.max.getY() - other.min.getY();  // negative value
                if (diff > offsetY) {
                    offsetY = diff;
                }
            }
            else if (offsetY > 0 && other.max.getY() <= this.min.getY() + EPSILON) {
                float diff = this.min.getY() - other.max.getY();  // positive value
                if (diff < offsetY) {
                    offsetY = diff;
                }
            }
        }
        return offsetY;
    }

    public float calculateXOffset(AABB other, float offsetX) {
        if (other.max.getY() > this.min.getY() && other.min.getY() < this.max.getY() &&
                other.max.getZ() > this.min.getZ() && other.min.getZ() < this.max.getZ()) {

            if (offsetX < 0 && other.min.getX() >= this.max.getX() - EPSILON) {
                float diff = this.max.getX() - other.min.getX();
                if (diff > offsetX) {
                    offsetX = diff;
                }
            } else if (offsetX > 0 && other.max.getX() <= this.min.getX() + EPSILON) {
                float diff = this.min.getX() - other.max.getX();
                if (diff < offsetX) {
                    offsetX = diff;
                }
            }
        }
        return offsetX;
    }

    public float calculateZOffset(AABB other, float offsetZ) {
        if (other.max.getX() > this.min.getX() && other.min.getX() < this.max.getX() &&
                other.max.getY() > this.min.getY() && other.min.getY() < this.max.getY()) {

            if (offsetZ < 0 && other.min.getZ() >= this.max.getZ() - EPSILON) {
                float diff = this.max.getZ() - other.min.getZ();
                if (diff > offsetZ) {
                    offsetZ = diff;
                }
            } else if (offsetZ > 0 && other.max.getZ() <= this.min.getZ() + EPSILON) {
                float diff = this.min.getZ() - other.max.getZ();
                if (diff < offsetZ) {
                    offsetZ = diff;
                }
            }
        }
        return offsetZ;
    }
}