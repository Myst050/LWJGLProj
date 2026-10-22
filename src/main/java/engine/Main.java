package engine;

import engine.graphics.Renderer;

public class Main {
    public static void main(String[] args){
        Renderer renderer = new Renderer(800, 600, "LWJGL");
        renderer.run();
    }
}