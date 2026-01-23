package main.java.net.kallen.minecraft;

import main.java.net.kallen.engine.graphics.*;
import main.java.net.kallen.engine.io.Input;
import main.java.net.kallen.engine.io.Window;
import main.java.net.kallen.engine.math.Vector2;
import main.java.net.kallen.engine.math.Vector3;
import main.java.net.kallen.engine.objects.GameObject;
import org.lwjgl.glfw.GLFW;

public class Minecraft implements Runnable {

    public Thread gameThread;
    public Window window;
    public Renderer renderer;
    public Shader shader;
    public final int WIDTH = 1280, HEIGHT = 780;

    public Mesh mesh = new Mesh(new Vertex[] {
            new Vertex(new Vector3(-0.5f,  0.5f, 0.0f), new Vector3(1f, 0f, 0f), new Vector2(0f, 0f)),  // Top Right
            new Vertex(new Vector3(-0.5f, -0.5f, 0.0f), new Vector3(1f, 0f, 1f), new Vector2(0f, 1f)),  // Bottom Left
            new Vertex(new Vector3( 0.5f, -0.5f, 0.0f), new Vector3(1f, 1f, 0f), new Vector2(1f, 1f)),  // Bottom Right
            new Vertex(new Vector3( 0.5f,  0.5f, 0.0f), new Vector3(0f, 1f, 1f), new Vector2(1f, 0f))   // Top Right
    }, new int[] {
            0, 1, 2,
            0, 3, 2
    }, new Material("/main/resources/textures/ore.png")
    );

    public GameObject object = new GameObject(new Vector3(0,0,0), new Vector3(0,0,0), new Vector3(1,1,1), mesh);

    public void start() {
        gameThread = new Thread(this,"game");
        gameThread.start();
    }

    public void init() {
        System.out.println("Initializing Game");
        window = new Window(WIDTH, HEIGHT, "Game");
        shader = new Shader("/main/resources/shaders/mainVertex.txt", "/main/resources/shaders/mainFragment.txt");
        renderer = new Renderer(shader);
        window.setBackgroundColor(0f, .1f, .1f);
        // window.setFullscreen(true);
        window.create();
        mesh.create();
        shader.create();
    }


    public void run() {
        init();

        while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            update();
            render();

            if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
        }
        close();
    }

    private void update() {
        window.update();
        object.update();
        // if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) System.out.println("X: " + Input.getScrollX() + ", Y: " + Input.getScrollY());
    }

    private void render() {
        renderer.renderObject(object);
        window.swapBuffers();
    }

    private void close() {
        window.destroy();
        mesh.destroy();
        shader.destroy();
    }

    public static void main(String[] args) {
        new Minecraft().start();
    }
}
