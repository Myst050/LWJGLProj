package engine.graphics;

import static org.lwjgl.opengl.GL15.*;

public class ElementBufferObject {
    private int id;
    private int elementCount;

    public ElementBufferObject(){
        this.id = glGenBuffers();
    }

    public void bind(){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
    }

    public void unbind(){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void uploadData(int[] indices){
        this.elementCount = indices.length;
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
    }

    public void delete(){
        glDeleteBuffers(id);
    }

    public int returnCount(){
        return this.elementCount;
    }
}
