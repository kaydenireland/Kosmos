package main.java.net.kallen.kosmos;

import main.java.net.kallen.engine.graphics.*;
import main.java.net.kallen.engine.io.Input;
import main.java.net.kallen.engine.io.Window;
import main.java.net.kallen.engine.math.Vector3;
import main.java.net.kallen.engine.objects.Camera;
import main.java.net.kallen.kosmos.util.ResourceLocation;
import main.java.net.kallen.kosmos.util.TextureAtlas;
import main.java.net.kallen.kosmos.world.BlockRegistry;
import main.java.net.kallen.kosmos.world.Chunk;
import org.lwjgl.glfw.GLFW;

public class Kosmos implements Runnable {

    public static final String ID = "kosmos";
    public Thread gameThread;
    public Window window;
    public Renderer renderer;
    public Shader shader;
    public final int WIDTH = 1280, HEIGHT = 780;
    private boolean thirdPerson = false;

    public Chunk chunk;
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
        chunk = new Chunk(blockAtlas);
        chunk.generateMesh();

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
        chunk.update();
    }

    private void render() {
        chunk.render(renderer);
        window.swapBuffers();
    }

    private void close() {
        window.destroy();
        shader.destroy();
        chunk.mesh.destroy();
        blockAtlas.destroy();
    }

    public static void main(String[] args) {
        new Kosmos().start();
    }
}
