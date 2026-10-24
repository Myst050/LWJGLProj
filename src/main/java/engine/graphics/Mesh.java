package engine.graphics;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

public class Mesh {
    private VertexBufferObject vbo;
    private VertexArrayObject vao;
    private int vertexCount;

    public Mesh(float[] vertices){
        this.vertexCount = vertices.length / 3;

        vbo = new VertexBufferObject();
        vbo.bind();
        vbo.uploadData(vertices);

        vao = new VertexArrayObject();
        vao.bind();
        vao.linkAttribute(0, 3, 3 * Float.BYTES, 0L);

        vao.unbind();
        vbo.bind();
    }

    public void render(){
        vao.bind();

        glDrawArrays(GL_TRIANGLES, 0, vertexCount);

        vao.unbind();
    }

    public void delete(){
        vao.delete();
        vbo.delete();
    }
}
