package main.java.net.kallen.kosmos.world;

public class BlockProperties {

    public boolean solid = true;
    public boolean opaque = true;
    public boolean transparent = false;

    private BlockProperties() {}

    public static BlockProperties create() {
        return new BlockProperties();
    }

    public BlockProperties solid(boolean solid) {
        this.solid = solid;
        return this;
    }

    public BlockProperties transparent() {
        this.transparent = true;
        this.opaque = false;
        return this;
    }

    public BlockProperties opaque(boolean opaque) {
        this.opaque = opaque;
        return this;
    }

    // Getters
    public boolean isSolid() { return solid; }
    public boolean isTransparent() { return transparent; }
    public boolean isOpaque() { return opaque; }

}
