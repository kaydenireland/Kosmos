package main.java.net.kallen.kosmos.physics;

import main.java.net.kallen.engine.math.Vector3;
import main.java.net.kallen.engine.physics.AABB;
import main.java.net.kallen.kosmos.entity.Entity;
import main.java.net.kallen.kosmos.world.BlockRegistry;
import main.java.net.kallen.kosmos.world.World;

import java.util.ArrayList;
import java.util.List;

public class Physics {

    public static final float GRAVITY = -0.08f;
    public static final float TERMINAL_VELOCITY = -0.4f;
    public static final float FRICTION = 0.6f;

    public static void applyPhysics(Entity entity, World world) {

        if (!entity.noClip()) {
            applyGravity(entity);
            checkBlockCollisions(entity, world);
        } else {
            AABB box = entity.getBoundingBox();
            Vector3 vel = entity.getVelocity();
            entity.setBoundingBox(box.offset(vel.getX(), vel.getY(), vel.getZ()));
        }

        moveEntity(entity);
        applyFriction(entity);

    }

    public static void applyGravity(Entity entity) {
        Vector3 velocity = entity.getVelocity();
        if (!entity.isGrounded() && !entity.isFlying()) velocity.setY(Math.max(velocity.getY() + GRAVITY, TERMINAL_VELOCITY));
    }

    public static void checkBlockCollisions(Entity entity, World world) {
        Vector3 originalVelo = new Vector3(entity.getVelocity());
        float xVelo = entity.getVelocity().getX();
        float yVelo = entity.getVelocity().getY();
        float zVelo = entity.getVelocity().getZ();
        List<AABB> blockBoxes = getBlockBoxes(entity.getBoundingBox().expand(xVelo, yVelo, zVelo), world);

        AABB entityBox = entity.getBoundingBox();
        for (AABB blockBox : blockBoxes) {
            yVelo = blockBox.calculateYOffset(entity.getBoundingBox(), yVelo);
        }

        entityBox = entityBox.offset(0f, yVelo, 0);

        for (AABB blockBox : blockBoxes) {
            xVelo = blockBox.calculateXOffset(entityBox, xVelo);
        }
        entityBox = entityBox.offset(xVelo, 0f, 0f);

        for (AABB blockBox : blockBoxes) {
            zVelo = blockBox.calculateZOffset(entityBox, zVelo);
        }
        entityBox = entityBox.offset(0f, 0f, zVelo);

        if (xVelo != originalVelo.getX()) entity.getVelocity().setX(0);
        if (yVelo != originalVelo.getY()) entity.getVelocity().setY(0);
        if (zVelo != originalVelo.getZ()) entity.getVelocity().setZ(0);

        entity.setGrounded(originalVelo.getY() != yVelo && originalVelo.getY() < 0f);

        entity.setBoundingBox(entityBox);
    }

    public static ArrayList<AABB> getBlockBoxes(AABB targetBox, World world) {
        ArrayList<AABB> blockBoxes = new ArrayList<>();

        int minX = (int) Math.floor(targetBox.min.getX()) - 1;
        int maxX = (int) Math.ceil(targetBox.max.getX()) + 1;
        int minY = (int) Math.floor(targetBox.min.getY()) - 1;
        int maxY = (int) Math.ceil(targetBox.max.getY()) + 1;
        int minZ = (int) Math.floor(targetBox.min.getZ()) - 1;
        int maxZ = (int) Math.ceil(targetBox.max.getZ()) + 1;

        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                for (int z = minZ; z < maxZ; z++) {
                    byte blockId = world.getBlock(new Vector3(x, y, z));
                    if (blockId != 0 && BlockRegistry.getBlockFromId(blockId).isSolid()) {
                        blockBoxes.add(new AABB(x, y, z, x + 1, y + 1, z + 1));
                    }
                }
            }
        }

        return blockBoxes;
    }

    public static void moveEntity(Entity entity) {
        // Entity position is the center of the bottom of the AABB
        AABB box = entity.getBoundingBox();

        float x = (box.min.getX() + box.max.getX()) * 0.5f;
        float y = box.min.getY();
        float z = (box.min.getZ() + box.max.getZ()) * 0.5f;

        entity.setPosition(new Vector3(x, y, z));
    }

    public static void applyFriction(Entity entity) {
        float veloX = entity.getVelocity().getX() * FRICTION;
        float veloY = entity.getVelocity().getY();
        float veloZ = entity.getVelocity().getZ() * FRICTION;

        entity.getVelocity().set(veloX, veloY, veloZ);
    }


}
