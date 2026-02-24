package main.java.net.kallen.solaris.math.coordinate;

import main.java.net.kallen.solaris.math.vector.Vector2;
import main.java.net.kallen.solaris.math.vector.Vector3;

public class Spherical {

    public static Vector3 fromCartesian(Vector3 vec) {
        float x = vec.getX();
        float y = vec.getY();
        float z = vec.getZ();

        float rho = (float) Math.sqrt(x*x + y*y + z*z);
        float theta = (float) Math.atan2(y, x);
        float phi = (float) Math.acos(z / rho);

        return new Vector3(rho, theta, phi);
    }

    public static Vector3 fromPolar(Vector2 vec) {
        return new Vector3(vec);
    }

    public static Vector3 fromPolar(Vector3 vec) {
        return new Vector3(vec);
    }

    public static Vector3 fromCylindrical(Vector3 vec) {

        float r = vec.getX();
        float theta = vec.getY();
        float z = vec.getZ();

        float rho = (float) Math.sqrt(r*r + z*z);
        float phi = (float) Math.atan2(r,z);

        return new Vector3(rho, theta, phi);
    }

    public static float distance(Vector3 vec1, Vector3 vec2) {
        Vector3 c1 = Cartesian.fromSpherical(vec1);
        Vector3 c2 = Cartesian.fromSpherical(vec2);
        return Vector3.distance(c1, c2);
    }

    public static Vector3 center(Vector3 vec1, Vector3 vec2) {
        Vector3 c1 = Cartesian.fromSpherical(vec1);
        Vector3 c2 = Cartesian.fromSpherical(vec2);
        Vector3 center = Vector3.center(c1, c2);
        return Spherical.fromCartesian(center);
    }

    public static Vector3 average(Vector3... vecs) {
        float x = 0;
        float y = 0;
        float z = 0;

        for(Vector3 vec : vecs) {
            Vector3 c = Cartesian.fromSpherical(vec);
            x += c.getX();
            y += c.getY();
            z += c.getZ();
        }

        x /= vecs.length;
        y /= vecs.length;
        z /= vecs.length;

        return Spherical.fromCartesian(new Vector3(x, y, z));
    }

}
