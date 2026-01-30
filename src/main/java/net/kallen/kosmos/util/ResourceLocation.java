package main.java.net.kallen.kosmos.util;

import main.java.net.kallen.kosmos.Kosmos;

import java.util.Objects;

public class ResourceLocation {

    private final String namespace;
    private final String path;

    public static final String SHADERS = "shaders";
    public static final String TEXTURES = "textures";
    public static final String BLOCK_TEXTURES = "textures/block";
    public static final String ITEM_TEXTURES = "textures/item";
    public static final String ENTITY_TEXTURES = "textures/entity";

    private ResourceLocation(String namespace, String path) {
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
        if (!(obj instanceof ResourceLocation)) return false;
        ResourceLocation other = (ResourceLocation) obj;
        return namespace.equals(other.namespace) && path.equals(other.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, path);
    }

    // Factory methods
    public static ResourceLocation withDefaultNamespace(String path) {
        return new ResourceLocation(Kosmos.ID, path);
    }

    public static ResourceLocation fromNamespace(String namespace, String path) {
        return new ResourceLocation(namespace, path);
    }

    public static ResourceLocation fromNamespaceAndDirectory(String namespace, String directory, String item) {
        return new ResourceLocation(namespace, directory + "/" + item);
    }

    // Parse from "namespace:path" or just "path" (uses default namespace)
    public static ResourceLocation parse(String location) {
        String[] parts = location.split(":", 2);
        if (parts.length == 2) {
            return new ResourceLocation(parts[0], parts[1]);
        } else {
            return withDefaultNamespace(parts[0]);
        }
    }

    // Convenient helpers for common resource types
    public static ResourceLocation blockTexture(String name) {
        return withDefaultNamespace(BLOCK_TEXTURES + "/" + name);
    }

    public static ResourceLocation itemTexture(String name) {
        return withDefaultNamespace(ITEM_TEXTURES + "/" + name);
    }

    public static ResourceLocation entityTexture(String name) {
        return withDefaultNamespace(ENTITY_TEXTURES + "/" + name);
    }

    public static ResourceLocation texture(String path) {
        return withDefaultNamespace(TEXTURES + "/" + path);
    }

    // Create a new ResourceLocation with a different path but same namespace
    public ResourceLocation withPath(String newPath) {
        return new ResourceLocation(this.namespace, newPath);
    }

    // Create a new ResourceLocation with a different namespace but same path
    public ResourceLocation withNamespace(String newNamespace) {
        return new ResourceLocation(newNamespace, this.path);
    }
}