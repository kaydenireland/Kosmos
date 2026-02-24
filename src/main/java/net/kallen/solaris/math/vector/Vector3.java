package main.java.net.kallen.solaris.math.vector;

public class Vector3 {
    private float x, y, z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 other) {
        this.x = other.getX();
        this.y = other.getY();
        this.z = other.getZ();
    }

    public Vector3(Vector2 other) {
        this.x = other.getX();
        this.y = other.getY();
        this.z = 0;
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(Vector3 vec) {
        this.x = vec.getX();
        this.y = vec.getY();
        this.z = vec.getZ();
    }

    public static Vector2 get2D(Vector3 vector) {
        return new Vector2(vector.getX(), vector.getY());
    }

    public static Vector3 to3D(Vector2 vector) {
        return new Vector3(vector.getX(), vector.getY(), 0);
    }

    public static Vector3 add(Vector3 vector1, Vector3 vector2) {
        return new Vector3(vector1.getX() + vector2.getX(), vector1.getY() + vector2.getY(), vector1.getZ() + vector2.getZ());
    }

    public static Vector3 subtract(Vector3 vector1, Vector3 vector2) {
        return new Vector3(vector1.getX() - vector2.getX(), vector1.getY() - vector2.getY(), vector1.getZ() - vector2.getZ());
    }

    public static Vector3 multiply(Vector3 vector1, Vector3 vector2) {
        return new Vector3(vector1.getX() * vector2.getX(), vector1.getY() * vector2.getY(), vector1.getZ() * vector2.getZ());
    }

    public static Vector3 divide(Vector3 vector1, Vector3 vector2) {
        return new Vector3(vector1.getX() / vector2.getX(), vector1.getY() / vector2.getY(), vector1.getZ() / vector2.getZ());
    }

    public static float length(Vector3 vector) {
        return (float) Math.sqrt(vector.getX() * vector.getX() + vector.getY() * vector.getY() + vector.getZ() * vector.getZ());
    }

    public static Vector3 normalize(Vector3 vector) {
        float len = Vector3.length(vector);
        return Vector3.divide(vector, new Vector3(len, len, len));
    }

    public static float dot(Vector3 vector1, Vector3 vector2) {
        return vector1.getX() * vector2.getX() + vector1.getY() * vector2.getY() + vector1.getZ() * vector2.getZ();
    }

    public static float distance(Vector3 vec1, Vector3 vec2) {
        float x = vec1.getX() - vec2.getX();
        float y = vec1.getY() - vec2.getY();
        float z = vec1.getZ() - vec2.getZ();

        return (float) Math.sqrt(x*x + y*y + z*z);
    }

    public static Vector3 center(Vector3 vec1, Vector3 vec2) {
        float dx = vec1.getX() - vec2.getX();
        float dy = vec1.getY() - vec2.getY();
        float dz = vec1.getZ() - vec2.getZ();

        return new Vector3(dx/2, dy/2, dz/2);
    }

    public static Vector3 average(Vector3... vecs) {
        float x = 0;
        float y = 0;
        float z = 0;

        for(Vector3 vec : vecs) {
            x += vec.getX();
            y += vec.getY();
            z += vec.getZ();
        }

        x /= vecs.length;
        y /= vecs.length;
        z /= vecs.length;

        return new Vector3(x, y, z);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        result = prime * result + Float.floatToIntBits(z);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vector3 other = (Vector3) obj;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
            return false;
        if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "(x: " + x + ", y: " + y + ", z: " + z + ")";
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
