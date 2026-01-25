package main.java.net.kallen.kosmos.world;

public class Block {

    private boolean isActive;
    private BlockId id;

    public Block(BlockId id) {
        isActive = false;
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

}
