package main.java.net.kallen.solaris.noise;

public interface INoise {

    public float getSeed();
    public void setSeed(float seed);

    public void generate();

}
