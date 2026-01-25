package main.java.net.kallen.kosmos.world;

public class Block {

    private boolean dirty;

    public Block(BlockProperties properties) {
        dirty = false;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

}
