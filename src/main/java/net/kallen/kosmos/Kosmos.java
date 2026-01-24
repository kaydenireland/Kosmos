package main.java.net.kallen.kosmos;

import main.java.net.kallen.engine.graphics.*;
import main.java.net.kallen.engine.io.Input;
import main.java.net.kallen.engine.io.Window;
import main.java.net.kallen.engine.math.Vector2;
import main.java.net.kallen.engine.math.Vector3;
import main.java.net.kallen.engine.objects.Camera;
import main.java.net.kallen.engine.objects.GameObject;
import org.lwjgl.glfw.GLFW;

public class Kosmos implements Runnable {

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
    }, new Material("/main/resources/textures/cat.png")
    );

    public GameObject object = new GameObject(new Vector3(0f,0f,0f), new Vector3(0f,0f,0f), new Vector3(1f,1f,1f), mesh);

    public Camera camera = new Camera(new Vector3(0f, 0f, 1f), new Vector3(0f, 0f,0f));

    public void start() {
        gameThread = new Thread(this,"game");
        gameThread.start();
    }

    public void init() {
        System.out.println("Initializing Game");
        window = new Window(WIDTH, HEIGHT, "Game");
        shader = new Shader("/main/resources/shaders/mainVertex.txt", "/main/resources/shaders/mainFragment.txt");
        renderer = new Renderer(window, shader);
        window.setBackgroundColor(0f, .1f, .1f);
        // window.setFullscreen(true);
        window.create();
        window.mouseState(true);
        mesh.create();
        shader.create();
    }


    public void run() {
        init();

        while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            update();
            render();

            if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
            if (Input.isKeyDown(GLFW.GLFW_KEY_L)) window.mouseState(!window.getMouseLock());
        }
        close();
    }

    private void update() {
        window.update();
        camera.update();
    }

    private void render() {
        renderer.renderObject(object, camera);
        window.swapBuffers();
    }

    private void close() {
        window.destroy();
        mesh.destroy();
        shader.destroy();
    }

    public static void main(String[] args) {
        new Kosmos().start();
    }
}
