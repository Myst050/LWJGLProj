package engine.scenes;

import engine.graphics.*;
import engine.util.ShaderSources;
import org.joml.*;
import org.lwjgl.opengl.GL;

public class Menu extends Scene{
    private Shader shader;
    private Camera camera;
    private Mesh triangle;

    @Override
    public void init() {
        shader = new Shader(ShaderSources.getSource("vertex"), ShaderSources.getSource("fragment"));
        camera = new Camera(new Vector3f(0.0f, 0.0f, 3.0f));
        shader.compile();

        float[] vertices = {
                0.0f,  0.5f, 0.0f,  // Top center
                -0.5f, -0.5f, 0.0f,  // Bottom-left
                0.5f, -0.5f, 0.0f   // Bottom-right
        };

        int[] indices = {
                0, 1, 2,
        };

        triangle = new Mesh(vertices, indices);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render() {
        shader.use();

        shader.uploadMat4f("uProjection", camera.getProjectionMatrix());
        shader.uploadMat4f("uView", camera.getViewMatrix());

        triangle.render();
        shader.detach();
    }

    @Override
    public void delete() {
        if(shader != null){
            shader.delete();
        }

        triangle.delete();
    }
}
