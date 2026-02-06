package main.java.net.kallen.kosmos;

import main.java.net.kallen.engine.graphics.*;
import main.java.net.kallen.engine.graphics.Renderer;
import main.java.net.kallen.engine.io.Input;
import main.java.net.kallen.engine.io.Window;
import main.java.net.kallen.engine.math.Vector2;
import main.java.net.kallen.engine.math.Vector3;
import main.java.net.kallen.engine.objects.FirstPersonCamera;
import main.java.net.kallen.kosmos.entity.Gamemode;
import main.java.net.kallen.kosmos.entity.Player;
import main.java.net.kallen.kosmos.render.ModelRegistry;
import main.java.net.kallen.kosmos.util.ResourceLocation;
import main.java.net.kallen.kosmos.texture.TextureAtlas;
import main.java.net.kallen.kosmos.world.BlockRegistry;
import main.java.net.kallen.kosmos.world.World;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLUtil;

import javax.swing.*;

public class Kosmos implements Runnable {

    public static final String ID = "kosmos";
    public Thread gameThread;
    public Window window;
    public Renderer renderer;
    public Shader shader;
    public final int WIDTH = 1280, HEIGHT = 780;
    private boolean thirdPerson = false;

    private static final int TICKS_PER_SECOND = 20;
    private static final double TICK_INTERVAL = 1.0 / TICKS_PER_SECOND;

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
        window = new Window(WIDTH, HEIGHT, "Kosmos");
        shader = new Shader(
                ResourceLocation.fromNamespaceAndDirectory(Kosmos.ID, ResourceLocation.SHADERS, "mainVertex").toFilePath(".txt"),
                ResourceLocation.fromNamespaceAndDirectory(Kosmos.ID, ResourceLocation.SHADERS, "mainFragment").toFilePath(".txt")
        );

        window.setBackgroundColor(.5f, .7f, 1f);
        // window.setFullscreen(true);
        window.create();
        window.mouseState(true);
        shader.create();

        blockAtlas = new TextureAtlas(16, ModelRegistry.getAllTextures());
        player = new Player(new Vector3(0, 50, 0));
        camera = new FirstPersonCamera(new Vector3(player.getPosition()), new Vector3(0f, 0f,0f));
        world = new World(blockAtlas, 999);
        world.updateChunks(player);
        world.update();
        player.setGamemode(Gamemode.SURVIVAL);

        renderer = new Renderer(window, shader, camera);

        lastMousePos.set((float) Input.getMouseX(), (float) Input.getMouseY());
    }


    public void run() {
        try{
            this.init();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Failed to Start Kosmos", 0);
        }

        double previousTime = System.nanoTime() / 1_000_000_000.0;
        double accumulator = 0.0;

        while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {

            double currentTime = System.nanoTime() / 1_000_000_000.0;
            double dt = currentTime - previousTime;
            previousTime = currentTime;
            accumulator += dt;

            update();
            render();

            while (accumulator >= TICK_INTERVAL) {
                tick();
                accumulator -= TICK_INTERVAL;
            }

            if (Input.isKeyDown(GLFW.GLFW_KEY_F3)) {
                System.out.println("Player pos: " + player.getPosition().toString());
                System.out.println("Player rot: " + player.getRotation().toString());
                System.out.println("Player chunk: " + player.getChunkPosition().toString());
                System.out.println("Loaded Chunks: " + world.getLoadedChunkCount());
            }
            if (Input.isKeyPressed(GLFW.GLFW_KEY_F5)) thirdPerson = !thirdPerson;
            if (Input.isKeyPressed(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
            if (Input.isKeyPressed(GLFW.GLFW_KEY_L)) window.mouseState(!window.getMouseLock());
            if (Input.isKeyPressed(GLFW.GLFW_KEY_EQUAL)) checkGlError();

            if (Input.isKeyPressed(GLFW.GLFW_KEY_F)) player.setFlying(!player.isFlying());

            // Game Mode Toggles
            if (Input.isKeyPressed(GLFW.GLFW_KEY_KP_0)) player.setGamemode(Gamemode.SURVIVAL);
            if (Input.isKeyPressed(GLFW.GLFW_KEY_KP_1)) player.setGamemode(Gamemode.CREATIVE);
            if (Input.isKeyPressed(GLFW.GLFW_KEY_KP_2)) player.setGamemode(Gamemode.SPECTATOR);

            Input.update();

        }
        close();
    }

    // Constant Updating
    private void update() {
        window.update();
        Vector2 currentMousePos = new Vector2((float) Input.getMouseX(), (float) Input.getMouseY());

        player.updateView(currentMousePos, lastMousePos);

        camera.followTarget(Vector3.add(player.getPosition(), new Vector3(0f, player.getEyeLevel(), 0)), player.getRotation());

        lastMousePos.set(currentMousePos.getX(), currentMousePos.getY());
    }

    // Update Every Tick
    private void tick() {
        player.tick(world);
        world.updateChunks(player);
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

    private void checkGlError() {
        int e = GL11.glGetError();
        if (e != 0) throw new IllegalStateException();
    }

    public static void main(String[] args) {
        new Kosmos().start();
    }
}
