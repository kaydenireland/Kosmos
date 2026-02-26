package main.java.net.kallen.kosmos.render;

import main.java.net.kallen.kosmos.Kosmos;
import main.java.net.kallen.solaris.util.Identifier;
import main.java.net.kallen.kosmos.world.Blocks;
import main.java.net.kallen.solaris.position.Direction;

import java.util.*;

public class ModelRegistry {

    private static final Map<String, BlockModel> models = new HashMap<>();

    static {
        registerModel(new BlockModel.Builder(Blocks.AIR)
                .allFaces("air")
                .build());


        registerModel(new BlockModel.Builder(Blocks.DIRT)
                .allFaces("dirt")
                .build());

        registerModel(new BlockModel.Builder(Blocks.GRASS)
                .topBottomSides("grass_top", "dirt", "grass_side")
                .build());

        registerModel(new BlockModel.Builder(Blocks.MUD)
                .allFaces("mud")
                .build());

        registerModel(new BlockModel.Builder(Blocks.STONE)
                .allFaces("stone")
                .build());

        registerModel(new BlockModel.Builder(Blocks.COBBLESTONE)
                .allFaces("cobblestone")
                .build());

        registerModel(new BlockModel.Builder(Blocks.DEEPSLATE)
                .allFaces("deepslate")
                .build());

        registerModel(new BlockModel.Builder(Blocks.OBSIDIAN)
                .allFaces("obsidian")
                .build());

        registerModel(new BlockModel.Builder(Blocks.BEDROCK)
                .allFaces("bedrock")
                .build());

        registerModel(new BlockModel.Builder(Blocks.OAK_LOG)
                .pillar("oak_log_top", "oak_log")
                .build());

        registerModel(new BlockModel.Builder(Blocks.OAK_PLANKS)
                .allFaces("oak_planks")
                .build());

        registerModel(new BlockModel.Builder(Blocks.ASH)
                .allFaces("ash")
                .build());

        registerModel(new BlockModel.Builder(Blocks.MOSS)
                .allFaces("moss")
                .build());

        registerModel(new BlockModel.Builder(Blocks.GLASS)
                .allFaces("glass")
                .build());

        registerModel(new BlockModel.Builder(Blocks.BLUE_STAINED_GLASS)
                .allFaces("blue_stained_glass")
                .build());

        registerModel(new BlockModel.Builder(Blocks.TINTED_GLASS)
                .allFaces("tinted_glass")
                .build());

        registerModel(new BlockModel.Builder(Blocks.GOLDEN_GLASS)
                .allFaces("golden_glass")
                .build());

        registerModel(new BlockModel.Builder(Blocks.WHITE_WOOl)
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
            Identifier location = Identifier.fromNamespaceAndDirectory(
                    Kosmos.ID,
                    Identifier.BLOCK_TEXTURES,
                    textureName
            );
            texturePaths.add(location.toImagePath());
        }

        return texturePaths.toArray(new String[0]);
    }
}