package engine.graphics;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Renderer {
    private long window;
    private int width;
    private int height;
    private String title;

    private String vertexSrc = "#version 330 core\n" +
            "\n" +
            "layout (location = 0) in vec3 aPos;\n" +
            "\n" +
            "void main() {\n" +
            "    gl_Position = vec4(aPos, 1.0);\n" +
            "}";
    private String fragmentSrc = "#version 330 core\n" +
            "\n" +
            "out vec4 FragColor;\n" +
            "\n" +
            "void main() {\n" +
            "    FragColor = vec4(1.0, 0.5, 0.2, 1.0); // RGBA (Orange)\n" +
            "}";

    Shader shader;
    Mesh triangle;

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

        float[] vertices = {
                0.0f,  0.5f, 0.0f, // Top
                -0.5f, -0.5f, 0.0f, // Bottom Left
                0.5f, -0.5f, 0.0f  // Bottom Right
        };

        triangle = new Mesh(vertices);

        shader = new Shader(vertexSrc, fragmentSrc);
        shader.compile();
    }

    public void loop(){

        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        while(!glfwWindowShouldClose(window)){
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            shader.use();

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
