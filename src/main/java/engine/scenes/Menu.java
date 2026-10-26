package engine.scenes;

import engine.graphics.*;
import engine.input.KeyListener;
import engine.util.ShaderSources;
import org.joml.*;

import static org.lwjgl.glfw.GLFW.*;
public class Menu extends Scene{
    private Shader shader;
    private Camera camera;
    private Mesh mesh;
    private Texture dirt;

    @Override
    public void init() {
        shader = new Shader(ShaderSources.getSource("vertex"), ShaderSources.getSource("fragment"));
        camera = new Camera(new Vector3f(0.0f, 0.0f, 3.0f));
        dirt = new Texture("C:/dev/LWJGLProj/resources/textures/dirt.png");

        shader.compile();

        float[] vertices = {
                // Position (x, y, z)     // UV Coordinates (u, v)
                -0.5f, -0.5f,  0.0f,      0.0f, 0.0f,  // 0. Bottom-Left
                0.5f, -0.5f,  0.0f,      1.0f, 0.0f,  // 1. Bottom-Right
                0.5f,  0.5f,  0.0f,      1.0f, 1.0f,  // 2. Top-Right
                -0.5f,  0.5f,  0.0f,      0.0f, 1.0f   // 3. Top-Left
        };

        int[] indices = {
                0, 1, 2,
                0, 2, 3
        };

         mesh = new Mesh(vertices, indices);
    }

    @Override
    public void update(float dt) {
        float speed = 1.0f;


        if(KeyListener.isKeyPressed(GLFW_KEY_A)){
            camera.getPosition().x -= speed * dt;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_D)) {
            camera.getPosition().x += speed * dt;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_W)){
            camera.getPosition().y += speed * dt;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_S)) {
            camera.getPosition().y -= speed * dt;
        }
    }

    @Override
    public void render() {
        shader.use();
        dirt.bind(1);
        shader.uploadInt("uTexture", 1);

        shader.uploadMat4f("uProjection", camera.getProjectionMatrix());
        shader.uploadMat4f("uView", camera.getViewMatrix());

        mesh.render();
        dirt.unbind();
        shader.detach();
    }

    @Override
    public void delete() {
        if(shader != null){
            shader.delete();
        }

        mesh.delete();
        dirt.delete();
    }
}
