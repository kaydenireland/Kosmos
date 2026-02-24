package main.java.net.kallen.solaris.math.coordinate;

import main.java.net.kallen.solaris.math.vector.Vector2;
import main.java.net.kallen.solaris.math.vector.Vector3;

public class Cartesian {

    public static Vector2 fromPolar(Vector2 vec) {
        float r = vec.getX();
        float theta = vec.getY();

        float x = (float) (r * Math.cos(theta));
        float y = (float) (r * Math.sin(theta));

        return new Vector2(x, y);
    }

    public static Vector3 fromPolar(Vector3 vec) {
        float r = vec.getX();
        float theta = vec.getY();
        float z = vec.getZ();

        float x = (float) (r * Math.cos(theta));
        float y = (float) (r * Math.sin(theta));

        return new Vector3(x, y, z);
    }

    public static Vector3 fromSpherical(Vector3 vec) {
        float rho = vec.getX();
        float theta = vec.getY();
        float phi = vec.getZ();

        float x = (float) (rho * Math.sin(phi) * Math.cos(theta));
        float y = (float) (rho * Math.sin(phi) * Math.sin(theta));
        float z = (float) (rho * Math.cos(phi));

        return new Vector3(x, y, z);
    }

    public static Vector3 fromCylindrical(Vector3 vec) {
        float r = vec.getX();
        float theta = vec.getY();
        float z = vec.getZ();

        float x = (float) (r * Math.cos(theta));
        float y = (float) (r * Math.sin(theta));

        return new Vector3(x, y, z);
    }

    public static float distance(Vector2 vec1, Vector2 vec2) {
        return Vector2.distance(vec1, vec2);
    }

    public static float distance(Vector3 vec1, Vector3 vec2) {
        return Vector3.distance(vec1, vec2);
    }

    public static Vector2 center(Vector2 vec1, Vector2 vec2) {
        return Vector2.center(vec1, vec2);
    }

    public static Vector3 center(Vector3 vec1, Vector3 vec2) {
        return Vector3.center(vec1, vec2);
    }

    public static Vector2 average(Vector2... vecs) {
        return Vector2.average(vecs);
    }

    public static Vector3 average(Vector3... vecs) {
        return Vector3.average(vecs);
    }

}
