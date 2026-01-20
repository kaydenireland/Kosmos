package net.kallen.minecraft;

public class Minecraft implements Runnable {

    public Thread gameThread;

    public void start() {
        gameThread = new Thread(this,"game");
        gameThread.start();
    }

    public static void init() {
        System.out.println("Initializing Minecraft");
    }


    public void run() {
        init();

        while (true) {
            update();
            render();
        }

    }

    private void update() {

    }

    private void render() {

    }

    public static void main(String[] args) {
        new Minecraft().start();
    }
}
