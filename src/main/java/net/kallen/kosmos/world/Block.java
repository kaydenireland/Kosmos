package main.java.net.kallen.kosmos.world;

public class Block {

    private BlockProperties properties;

    public Block(BlockProperties properties) {
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
