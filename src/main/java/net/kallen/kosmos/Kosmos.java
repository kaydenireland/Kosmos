package main.java.net.kallen.kosmos;

import main.java.net.kallen.engine.graphics.*;
import main.java.net.kallen.engine.io.Input;
import main.java.net.kallen.engine.io.Window;
import main.java.net.kallen.engine.math.Vector3;
import main.java.net.kallen.engine.objects.Camera;
import main.java.net.kallen.kosmos.util.ResourceLocation;
import main.java.net.kallen.kosmos.texture.TextureAtlas;
import main.java.net.kallen.kosmos.world.BlockRegistry;
import main.java.net.kallen.kosmos.world.Chunk;
import main.java.net.kallen.kosmos.world.World;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

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

    public Camera camera = new Camera(new Vector3(0f, 0f, 1f), new Vector3(0f, 0f,0f));

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
        renderer = new Renderer(window, shader, camera);
        window.setBackgroundColor(0f, .1f, .1f);
        // window.setFullscreen(true);
        window.create();
        window.mouseState(true);
        shader.create();

        blockAtlas = new TextureAtlas(16, BlockRegistry.getAllTextures());
        world = new World(blockAtlas);

        world.loadChunk(new Vector3(0, 0, 0));
        world.loadChunk(new Vector3(1, 0, 0));
        world.loadChunk(new Vector3(0, 1, 0));
        world.loadChunk(new Vector3(-1, -1, -1));

    }


    public void run() {
        init();

        while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            update();
            render();

            if (Input.isKeyDown(GLFW.GLFW_KEY_F)) thirdPerson = !thirdPerson;
            if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
            if (Input.isKeyDown(GLFW.GLFW_KEY_L)) window.mouseState(!window.getMouseLock());
        }
        close();
    }

    private void update() {
        window.update();
        camera.update();
        world.update();
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
