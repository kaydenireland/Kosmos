package main.java.net.kallen.kosmos;

import main.java.net.kallen.engine.graphics.*;
import main.java.net.kallen.engine.io.Input;
import main.java.net.kallen.engine.io.Window;
import main.java.net.kallen.engine.math.Vector2;
import main.java.net.kallen.engine.math.Vector3;
import main.java.net.kallen.engine.objects.FirstPersonCamera;
import main.java.net.kallen.kosmos.entity.Player;
import main.java.net.kallen.kosmos.util.ResourceLocation;
import main.java.net.kallen.kosmos.texture.TextureAtlas;
import main.java.net.kallen.kosmos.world.BlockRegistry;
import main.java.net.kallen.kosmos.world.World;
import org.lwjgl.glfw.GLFW;

public class Kosmos implements Runnable {

    public static final String ID = "kosmos";
    public Thread gameThread;
    public Window window;
    public Renderer renderer;
    public Shader shader;
    public final int WIDTH = 1280, HEIGHT = 780;
    private boolean thirdPerson = false;

    public World world;
    public static TextureAtlas blockAtlas;

    public Player player;
    public FirstPersonCamera camera;

    private Vector2 lastMousePos = new Vector2(0f, 0f);

    public void start() {
        gameThread = new Thread(this,"game");
        gameThread.start();
    }

    public void init() {

        System.out.println("Initializing Game");
        window = new Window(WIDTH, HEIGHT, "Window");
        shader = new Shader(
                ResourceLocation.fromNamespaceAndDirectory(Kosmos.ID, ResourceLocation.SHADERS, "mainVertex").toFilePath(".txt"),
                ResourceLocation.fromNamespaceAndDirectory(Kosmos.ID, ResourceLocation.SHADERS, "mainFragment").toFilePath(".txt")
        );

        window.setBackgroundColor(.5f, .7f, 1f);
        // window.setFullscreen(true);
        window.create();
        window.mouseState(true);
        shader.create();

        blockAtlas = new TextureAtlas(16, BlockRegistry.getAllTextures());
        player = new Player(new Vector3(0, 80, 0));
        camera = new FirstPersonCamera(player.getPosition(), new Vector3(0f, 0f,0f));
        world = new World(blockAtlas, 999);

        renderer = new Renderer(window, shader, camera);

        lastMousePos.set((float) Input.getMouseX(), (float) Input.getMouseY());
    }


    public void run() {
        init();

        while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            update();
            render();

            if (Input.isKeyDown(GLFW.GLFW_KEY_F3)) {
                System.out.println("Player pos: " + player.getPosition().toString());
                System.out.println("Player rot: " + player.getRotation().toString());
                System.out.println("Player chunk: " + player.getChunkPosition().toString());
            }
            if (Input.isKeyDown(GLFW.GLFW_KEY_F5)) thirdPerson = !thirdPerson;
            if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
            if (Input.isKeyDown(GLFW.GLFW_KEY_L)) window.mouseState(!window.getMouseLock());
        }
        close();
    }

    private void update() {
        window.update();
        Vector2 currentMousePos = new Vector2((float) Input.getMouseX(), (float) Input.getMouseY());

        player.update(currentMousePos, lastMousePos);

        camera.followTarget(player.getPosition(), player.getRotation());

        world.updateChunks(player);
        world.update();

        lastMousePos.set(currentMousePos.getX(), currentMousePos.getY());
    }

    private void render() {
        world.render(renderer);

        window.swapBuffers();
    }

    private void close() {
        window.destroy();
        shader.destroy();
        world.destroy();
        blockAtlas.destroy();
    }

    public static void main(String[] args) {
        new Kosmos().start();
    }
}
