package engine.graphics;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;

public class VertexBufferObject {
    private int id;

    public VertexBufferObject(){
        this.id = glGenBuffers();
    }

    public void bind(){
        glBindBuffer(GL_ARRAY_BUFFER, id);
    }

    public void unbind(){
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void uploadData(float[] vertices){
        FloatBuffer buffer = MemoryUtil.memAllocFloat(vertices.length);
        buffer.put(vertices).flip();
        MemoryUtil.memFree(buffer);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    }

    public void delete(){
        glDeleteBuffers(id);
    }
}
