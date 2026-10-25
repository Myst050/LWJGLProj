package engine.graphics;

import engine.scenes.*;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.joml.*;
import java.nio.FloatBuffer;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Renderer {
    private long window;
    private int width;
    private int height;
    private String title;
    private SceneManager sceneManager;

    public Renderer(int w, int h, String t){
        this.width = w;
        this.height = h;
        this.title = t;
    }

    public void run(){
        init();
        loop();
        terminate();
    }

    public void init(){
        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit()){
            throw new IllegalStateException("GLFW not initialized!");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);

        window = glfwCreateWindow(width, height, title, NULL, NULL);

        if(window == NULL){
            throw new RuntimeException("Window is NULL");
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
        GL.createCapabilities();

        glfwSetKeyCallback(window, KeyListener.get()::setKeyCallback);

        sceneManager = new SceneManager();
        sceneManager.changeScene(new Menu());

        glViewport(0, 0, width, height);
    }

    public void loop(){
        float beginTime = (float)glfwGetTime();
        float endTime;
        float dt = -1.0f;

        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        while(!glfwWindowShouldClose(window)){
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            sceneManager.updateScene(dt);
            sceneManager.renderScene();

            endTime = (float)glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;
            System.out.println(dt);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public void terminate(){
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
