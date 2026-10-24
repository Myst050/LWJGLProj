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
    private Matrix4f matrix;

    public Mesh(float[] vertices){
        this.vertexCount = vertices.length / 3;
        this.matrix = new Matrix4f();

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

    public void uploadMatrix(int location){
        try(MemoryStack stack = MemoryStack.stackPush()){
            FloatBuffer fb = stack.mallocFloat(16);
            matrix.get(fb);
            glUniformMatrix4fv(location, false, fb);
        }
    }

    public Matrix4f getCurrentMatrix(){
        return matrix;
    }
}
