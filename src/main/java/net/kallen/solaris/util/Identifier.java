package main.java.net.kallen.solaris.util;

import java.util.Objects;

public class Identifier {

    private final String namespace;
    private final String path;

    public static final String ICONS = "icons";
    public static final String SHADERS = "shaders";
    public static final String TEXTURES = "textures";
    public static final String BLOCK_TEXTURES = "textures/block";
    public static final String ITEM_TEXTURES = "textures/item";
    public static final String ENTITY_TEXTURES = "textures/entity";

    private Identifier(String namespace, String path) {
        this.namespace = namespace.toLowerCase();
        this.path = path.toLowerCase();
        validate();
    }

    private void validate() {
        if (namespace.isEmpty()) {
            throw new IllegalArgumentException("Namespace cannot be empty");
        }
        if (path.isEmpty()) {
            throw new IllegalArgumentException("Path cannot be empty");
        }
        if (!isValidString(namespace)) {
            throw new IllegalArgumentException("Invalid namespace: " + namespace);
        }
        if (!isValidString(path)) {
            throw new IllegalArgumentException("Invalid path: " + path);
        }
    }

    private boolean isValidString(String str) {
        return str.matches("[a-z0-9_\\-./]+");
    }

    public String getNamespace() {
        return namespace;
    }

    public String getPath() {
        return path;
    }

    // namespace:path
    @Override
    public String toString() {
        return namespace + ":" + path;
    }

    public String toFilePath() {
        return "/main/resources/" + namespace + "/" + path;
    }

    public String toFilePath(String extension) {
        return "/main/resources/" + namespace + "/" + path + extension;
    }

    public String toImagePath() {
        return "/main/resources/" + namespace + "/" + path + ".png";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Identifier)) return false;
        Identifier other = (Identifier) obj;
        return namespace.equals(other.namespace) && path.equals(other.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, path);
    }

    // Factory methods
    public static Identifier fromNamespace(String namespace, String path) {
        return new Identifier(namespace, path);
    }

    public static Identifier fromNamespaceAndDirectory(String namespace, String directory, String item) {
        return new Identifier(namespace, directory + "/" + item);
    }

    // Parse from "namespace:path" or just "path"
    public static Identifier parse(String location) {
        String[] parts = location.split(":", 2);
        if (parts.length == 2) {
            return new Identifier(parts[0], parts[1]);
        } else {
            return fromNamespace(null, parts[0]);
        }
    }

    // Convenient helpers for common resource types
    public static Identifier blockTexture(String namespace, String name) {
        return fromNamespace(namespace, BLOCK_TEXTURES + "/" + name);
    }

    public static Identifier itemTexture(String namespace,String name) {
        return fromNamespace(namespace, ITEM_TEXTURES + "/" + name);
    }

    public static Identifier entityTexture(String namespace,String name) {
        return fromNamespace(namespace, ENTITY_TEXTURES + "/" + name);
    }

    public static Identifier texture(String namespace,String path) {
        return fromNamespace(namespace, TEXTURES + "/" + path);
    }

    // Create a new Identifier with a different path but same namespace
    public Identifier withPath(String newPath) {
        return new Identifier(this.namespace, newPath);
    }

    // Create a new Identifier with a different namespace but same path
    public Identifier withNamespace(String newNamespace) {
        return new Identifier(newNamespace, this.path);
    }
}