package main.java.net.kallen.kosmos.render;

import main.java.net.kallen.kosmos.Kosmos;
import main.java.net.kallen.kosmos.util.ResourceLocation;
import main.java.net.kallen.kosmos.world.BlockRegistry;
import main.java.net.kallen.kosmos.world.Direction;

import java.util.*;

public class ModelRegistry {

    private static final Map<String, BlockModel> models = new HashMap<>();

    static {
        registerModel(new BlockModel.Builder(BlockRegistry.AIR)
                .allFaces("air")
                .build());


        registerModel(new BlockModel.Builder(BlockRegistry.DIRT)
                .allFaces("dirt")
                .build());

        registerModel(new BlockModel.Builder(BlockRegistry.GRASS)
                .topBottomSides("grass_top", "dirt", "grass_side")
                .build());

        registerModel(new BlockModel.Builder(BlockRegistry.MUD)
                .allFaces("mud")
                .build());

        registerModel(new BlockModel.Builder(BlockRegistry.STONE)
                .allFaces("stone")
                .build());

        registerModel(new BlockModel.Builder(BlockRegistry.COBBLESTONE)
                .allFaces("cobblestone")
                .build());

        registerModel(new BlockModel.Builder(BlockRegistry.DEEPSLATE)
                .allFaces("deepslate")
                .build());

        registerModel(new BlockModel.Builder(BlockRegistry.OBSIDIAN)
                .allFaces("obsidian")
                .build());

        registerModel(new BlockModel.Builder(BlockRegistry.BEDROCK)
                .allFaces("bedrock")
                .build());

        registerModel(new BlockModel.Builder(BlockRegistry.OAK_LOG)
                .pillar("oak_log_top", "oak_log")
                .build());

        registerModel(new BlockModel.Builder(BlockRegistry.OAK_PLANKS)
                .allFaces("oak_planks")
                .build());

        registerModel(new BlockModel.Builder(BlockRegistry.ASH)
                .allFaces("ash")
                .build());

        registerModel(new BlockModel.Builder(BlockRegistry.MOSS)
                .allFaces("moss")
                .build());

        registerModel(new BlockModel.Builder(BlockRegistry.GLASS)
                .allFaces("glass")
                .build());

        registerModel(new BlockModel.Builder(BlockRegistry.TINTED_GLASS)
                .allFaces("tinted_glass")
                .build());

        registerModel(new BlockModel.Builder(BlockRegistry.GOLDEN_GLASS)
                .allFaces("golden_glass")
                .build());

        registerModel(new BlockModel.Builder(BlockRegistry.WHITE_WOOl)
                .allFaces("white_wool")
                .build());
    }

    private static void registerModel(BlockModel model) {
        models.put(model.getId(), model);
    }

    public static BlockModel getModel(String blockId) {
        return models.getOrDefault(blockId, models.get("air"));
    }

    public static String[] getAllTextures() {
        Set<String> uniqueTextures = new HashSet<>();

        for (BlockModel model : models.values()) {
            for (Direction dir : Direction.values()) {
                String textureName = model.getTextureForFace(dir);
                if (textureName != null && !textureName.equals("air")) {
                    uniqueTextures.add(textureName);
                }
            }
        }

        List<String> texturePaths = new ArrayList<>();
        for (String textureName : uniqueTextures) {
            ResourceLocation location = ResourceLocation.fromNamespaceAndDirectory(
                    Kosmos.ID,
                    ResourceLocation.BLOCK_TEXTURES,
                    textureName
            );
            texturePaths.add(location.toImagePath());
        }

        return texturePaths.toArray(new String[0]);
    }
}