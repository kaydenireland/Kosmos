package main.java.net.kallen.kosmos.world;

public class Block {

    private Properties properties;

    public Block(Properties properties) {
        this.properties = properties;
    }

    public boolean isSolid() {
        return properties.isSolid();
    }

    public boolean isOpaque() {
        return properties.isOpaque();
    }

    public boolean isTransparent() {
        return properties.isTransparent();
    }

}

class Properties {

    public boolean solid = true;
    public boolean opaque = true;
    public boolean transparent = false;

    private Properties() {}

    public static Properties create() {
        return new Properties();
    }

    public Properties solid(boolean solid) {
        this.solid = solid;
        return this;
    }

    public Properties transparent() {
        this.transparent = true;
        this.opaque = false;
        return this;
    }

    public Properties opaque(boolean opaque) {
        this.opaque = opaque;
        return this;
    }

    // Getters
    public boolean isSolid() { return solid; }
    public boolean isTransparent() { return transparent; }
    public boolean isOpaque() { return opaque; }

}