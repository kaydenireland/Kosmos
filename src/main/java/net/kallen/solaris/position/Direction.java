package main.java.net.kallen.solaris.position;

import main.java.net.kallen.solaris.math.vector.Vector3;

public enum Direction {
    NORTH(0, 0, -1),
    SOUTH(0, 0, 1),
    EAST(1, 0, 0),
    WEST(-1, 0, 0),
    UP(0, 1, 0),
    DOWN(0, -1, 0);

    private final int x, y, z;
    private final Vector3 vector;

    Direction(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.vector = new Vector3(x, y, z);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public Vector3 getVector() {
        return vector;
    }

    public Direction getOpposite() {
        switch (this) {
            case NORTH: return SOUTH;
            case SOUTH: return NORTH;
            case EAST: return WEST;
            case WEST: return EAST;
            case UP: return DOWN;
            case DOWN: return UP;
            default: return this;
        }
    }

    public boolean isHorizontal() {
        return this == NORTH || this == SOUTH || this == EAST || this == WEST;
    }

    public boolean isVertical() {
        return this == UP || this == DOWN;
    }

    public static Direction[] getHorizontal() {
        return new Direction[] { NORTH, SOUTH, EAST, WEST };
    }

    public static Direction[] getAll() {
        return values();
    }
}