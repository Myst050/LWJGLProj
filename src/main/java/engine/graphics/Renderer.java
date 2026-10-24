package engine.graphics;

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
    private Mesh triangle;

    Camera camera = new Camera(new Vector3f(0.0f, 0.0f, 0.0f));

    private String vertexSrc = "#version 330 core\n" +
            "\n" +
            "layout (location = 0) in vec3 aPos;\n" +
            "\n" +
            "\n" +
            "uniform mat4 uProjection;\n" +
            "uniform mat4 uView;\n" +
            "\n" +
            "void main() {\n" +
            "    gl_Position = uProjection * uView * vec4(aPos, 1.0);\n" +
            "}";
    private String fragmentSrc = "#version 330 core\n" +
            "\n" +
            "out vec4 FragColor;\n" +
            "\n" +
            "void main() {\n" +
            "    FragColor = vec4(1.0, 0.5, 0.2, 1.0); // RGBA (Orange)\n" +
            "}";

    Shader shader;

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

        glViewport(0, 0, width, height);

        float[] vertices = {
                400.0f, 500.0f, 0.0f, // Top (Center-ish, higher up)
                300.0f, 300.0f, 0.0f, // Bottom Left
                500.0f, 300.0f, 0.0f  // Bottom Right
        };

        triangle = new Mesh(vertices);

        shader = new Shader(vertexSrc, fragmentSrc);
        shader.compile();
    }

    public void loop(){

        float Xpos = 0.0f;

        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        while(!glfwWindowShouldClose(window)){
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            shader.use();

            shader.uploadMat4f("uProjection", camera.getProjectionMatrix());
            shader.uploadMat4f("uView", camera.getViewMatrix());

            if(KeyListener.isKeyPressed(GLFW_KEY_W)){
                camera.getPosition().y += -1.0f;
            } else if (KeyListener.isKeyPressed(GLFW_KEY_S)) {
                camera.getPosition().y += 1.0f;
            } else if (KeyListener.isKeyPressed(GLFW_KEY_A)) {
                camera.getPosition().x += 1.0f;
            } else if (KeyListener.isKeyPressed(GLFW_KEY_D)) {
                camera.getPosition().x += -1.0f;
            }

            triangle.render();

            shader.detach();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public void terminate(){
        triangle.delete();

        if(shader != null)
            shader.detach();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
