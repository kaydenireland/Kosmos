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
            //Back face
            new Vertex(new Vector3(-0.5f,  0.5f, -0.5f), new Vector2(0.0f, 0.0f)),
            new Vertex(new Vector3(-0.5f, -0.5f, -0.5f), new Vector2(0.0f, 1.0f)),
            new Vertex(new Vector3( 0.5f, -0.5f, -0.5f), new Vector2(1.0f, 1.0f)),
            new Vertex(new Vector3( 0.5f,  0.5f, -0.5f), new Vector2(1.0f, 0.0f)),

            //Front face
            new Vertex(new Vector3(-0.5f,  0.5f,  0.5f), new Vector2(0.0f, 0.0f)),
            new Vertex(new Vector3(-0.5f, -0.5f,  0.5f), new Vector2(0.0f, 1.0f)),
            new Vertex(new Vector3( 0.5f, -0.5f,  0.5f), new Vector2(1.0f, 1.0f)),
            new Vertex(new Vector3( 0.5f,  0.5f,  0.5f), new Vector2(1.0f, 0.0f)),

            //Right face
            new Vertex(new Vector3( 0.5f,  0.5f, -0.5f), new Vector2(0.0f, 0.0f)),
            new Vertex(new Vector3( 0.5f, -0.5f, -0.5f), new Vector2(0.0f, 1.0f)),
            new Vertex(new Vector3( 0.5f, -0.5f,  0.5f), new Vector2(1.0f, 1.0f)),
            new Vertex(new Vector3( 0.5f,  0.5f,  0.5f), new Vector2(1.0f, 0.0f)),

            //Left face
            new Vertex(new Vector3(-0.5f,  0.5f, -0.5f), new Vector2(0.0f, 0.0f)),
            new Vertex(new Vector3(-0.5f, -0.5f, -0.5f), new Vector2(0.0f, 1.0f)),
            new Vertex(new Vector3(-0.5f, -0.5f,  0.5f), new Vector2(1.0f, 1.0f)),
            new Vertex(new Vector3(-0.5f,  0.5f,  0.5f), new Vector2(1.0f, 0.0f)),

            //Top face
            new Vertex(new Vector3(-0.5f,  0.5f,  0.5f), new Vector2(0.0f, 0.0f)),
            new Vertex(new Vector3(-0.5f,  0.5f, -0.5f), new Vector2(0.0f, 1.0f)),
            new Vertex(new Vector3( 0.5f,  0.5f, -0.5f), new Vector2(1.0f, 1.0f)),
            new Vertex(new Vector3( 0.5f,  0.5f,  0.5f), new Vector2(1.0f, 0.0f)),

            //Bottom face
            new Vertex(new Vector3(-0.5f, -0.5f,  0.5f), new Vector2(0.0f, 0.0f)),
            new Vertex(new Vector3(-0.5f, -0.5f, -0.5f), new Vector2(0.0f, 1.0f)),
            new Vertex(new Vector3( 0.5f, -0.5f, -0.5f), new Vector2(1.0f, 1.0f)),
            new Vertex(new Vector3( 0.5f, -0.5f,  0.5f), new Vector2(1.0f, 0.0f)),
    }, new int[] {
            //Back face
            0, 1, 3,
            3, 1, 2,

            //Front face
            4, 5, 7,
            7, 5, 6,

            //Right face
            8, 9, 11,
            11, 9, 10,

            //Left face
            12, 13, 15,
            15, 13, 14,

            //Top face
            16, 17, 19,
            19, 17, 18,

            //Bottom face
            20, 21, 23,
            23, 21, 22
    }, new Material("/main/resources/textures/cat.png"));

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
