package main.java.net.kallen.solaris.math.coordinate;

import main.java.net.kallen.solaris.math.vector.Vector2;
import main.java.net.kallen.solaris.math.vector.Vector3;

public class Polar {

    public static Vector2 fromCartesian(Vector2 vec) {
        float x = vec.getX();
        float y = vec.getY();

        float r = (float) Math.sqrt(x*x + y*y);
        float theta = (float) Math.atan2(y, x);

        return new Vector2(r, theta);
    }
    public static Vector3 fromCartesian(Vector3 vec) {
        float x = vec.getX();
        float y = vec.getY();

        float r = (float) Math.sqrt(x*x + y*y);
        float theta = (float) Math.atan2(y, x);

        return new Vector3(r, theta, 0);
    }

    public static Vector3 fromSpherical(Vector3 vec) {
        Vector3 newVec = new Vector3(vec);
        newVec.setZ(0);
        return newVec;
    }

    public static Vector3 fromCylindrical(Vector3 vec) {
        Vector3 newVec = new Vector3(vec);
        newVec.setZ(0);
        return newVec;
    }

    public static float distance(Vector2 vec1, Vector2 vec2) {
        Vector2 c1 = Cartesian.fromPolar(vec1);
        Vector2 c2 = Cartesian.fromPolar(vec2);
        return Vector2.distance(c1, c2);
    }

    public static float distance(Vector3 vec1, Vector3 vec2) {
        Vector3 c1 = Cartesian.fromPolar(vec1);
        Vector3 c2 = Cartesian.fromPolar(vec2);
        return Vector3.distance(c1, c2);
    }

    public static Vector2 center(Vector2 vec1, Vector2 vec2) {
        Vector2 c1 = Cartesian.fromPolar(vec1);
        Vector2 c2 = Cartesian.fromPolar(vec2);
        Vector2 center = Vector2.center(c1, c2);
        return Polar.fromCartesian(center);
    }

    public static Vector3 center(Vector3 vec1, Vector3 vec2) {
        Vector3 c1 = Cartesian.fromPolar(vec1);
        Vector3 c2 = Cartesian.fromPolar(vec2);
        Vector3 center = Vector3.center(c1, c2);
        return Polar.fromCartesian(center);
    }

    public static Vector2 average(Vector2... vecs) {
        float x = 0;
        float y = 0;

        for(Vector2 vec : vecs) {
            Vector2 c = Cartesian.fromPolar(vec);
            x += c.getX();
            y += c.getY();
        }

        x /= vecs.length;
        y /= vecs.length;

        return Polar.fromCartesian(new Vector2(x, y));
    }

    public static Vector3 average(Vector3... vecs) {
        float x = 0;
        float y = 0;

        for(Vector3 vec : vecs) {
            Vector3 c = Cartesian.fromPolar(vec);
            x += c.getX();
            y += c.getY();
        }

        x /= vecs.length;
        y /= vecs.length;

        return Polar.fromCartesian(new Vector3(x, y, 0));
    }

}
