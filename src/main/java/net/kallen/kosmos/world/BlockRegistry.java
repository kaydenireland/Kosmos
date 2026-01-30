package main.java.net.kallen.kosmos.world;

import java.util.HashMap;
import java.util.Map;

public class BlockRegistry {
    private static final Map<Byte, Block> ID_TO_TYPE = new HashMap<>();
    private static final Map<String, Byte> NAME_TO_ID = new HashMap<>();
    private static byte nextId = 0;

    // Block type IDs
    public static final byte AIR = register("air", new Block(
            BlockProperties.create()));
    public static final byte DIRT = register("dirt", new Block(
            BlockProperties.create()));
    public static final byte STONE = register("stone", new Block(
            BlockProperties.create()));
    public static final byte GLASS = register("glass", new Block(
            BlockProperties.create().transparent()));


    private static byte register(String name, Block block) {
        byte id = nextId++;
        ID_TO_TYPE.put(id, block);
        NAME_TO_ID.put(name, id);
        return id;
    }

    public static Block getBlockFromId(byte id) {
        return ID_TO_TYPE.get(id);
    }

    public static Block getBlockFromName(String name) {
        return ID_TO_TYPE.get(NAME_TO_ID.get(name));
    }

    public static byte getIdFromName(String name) {
        return NAME_TO_ID.get(name);
    }

}
