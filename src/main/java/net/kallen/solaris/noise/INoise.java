package main.java.net.kallen.solaris.noise;

public interface INoise {

    public float getSeed();
    public void setSeed(long seed);

    public void sample1D(float x);
    public void sample2D(float x, float y);
    public void sample3D(float x, float y, float z);
    public void sample3D(float x, float y, float z, int octaves);

}
