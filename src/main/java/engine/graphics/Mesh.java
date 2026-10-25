package engine.graphics;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

public class Mesh {
    private VertexBufferObject vbo;
    private VertexArrayObject vao;
    private ElementBufferObject ebo;
    private int vertexCount;

    public Mesh(float[] vertices, int[] indices){
        this.vertexCount = vertices.length / 3;

        vao = new VertexArrayObject();
        vao.bind();

        vbo = new VertexBufferObject();
        vbo.bind();
        vbo.uploadData(vertices);

        ebo = new ElementBufferObject();
        ebo.bind();
        ebo.uploadData(indices);

        vao.linkAttribute(0, 3, 3 * Float.BYTES, 0L);

        vao.unbind();
        vbo.unbind();
        ebo.unbind();
    }

    public void render(){
        vao.bind();

        glDrawElements(GL_TRIANGLES, ebo.returnCount(), GL_UNSIGNED_INT, 0);

        vao.unbind();
    }

    public void delete(){
        vao.delete();
        vbo.delete();
        ebo.delete();
    }
}
