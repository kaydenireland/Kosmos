package main.java.net.kallen.solaris.math.coordinate;

import main.java.net.kallen.solaris.math.vector.Vector2;
import main.java.net.kallen.solaris.math.vector.Vector3;

public class Cylindrical {

    public static Vector3 fromCartesian(Vector2 vec) {
        float x = vec.getX();
        float y = vec.getY();

        float r = (float) Math.sqrt(x*x + y*y);
        float theta = (float) Math.atan2(y, x);

        return new Vector3(r, theta, 0);
    }

    public static Vector3 fromCartesian(Vector3 vec) {
        float x = vec.getX();
        float y = vec.getY();
        float z = vec.getZ();

        float r = (float) Math.sqrt(x*x + y*y + z*z);
        float theta = (float) Math.atan2(y, x);

        return new Vector3(r, theta, z);
    }

    public static Vector3 fromPolar(Vector2 vec) {
        return new Vector3(vec);
    }

    public static Vector3 fromSpherical(Vector3 vec) {
        float rho = vec.getX();
        float theta = vec.getY();
        float phi = vec.getZ();

        float r = (float) (rho * Math.sin(phi));
        float z = (float) (rho * Math.cos(phi));

        return new Vector3(r, theta, z);
    }

    public static float distance(Vector3 vec1, Vector3 vec2) {
        Vector3 c1 = Cartesian.fromCylindrical(vec1);
        Vector3 c2 = Cartesian.fromCylindrical(vec2);
        return Vector3.distance(c1, c2);
    }

    public static Vector3 center(Vector3 vec1, Vector3 vec2) {
        Vector3 c1 = Cartesian.fromCylindrical(vec1);
        Vector3 c2 = Cartesian.fromCylindrical(vec2);
        Vector3 center = Vector3.center(c1, c2);
        return Cylindrical.fromCartesian(center);
    }

    public static Vector3 average(Vector3... vecs) {
        float x = 0;
        float y = 0;
        float z = 0;

        for(Vector3 vec : vecs) {
            Vector3 c = Cartesian.fromCylindrical(vec);
            x += c.getX();
            y += c.getY();
            z += c.getZ();
        }

        x /= vecs.length;
        y /= vecs.length;
        z /= vecs.length;

        return Cylindrical.fromCartesian(new Vector3(x, y, z));
    }

}
