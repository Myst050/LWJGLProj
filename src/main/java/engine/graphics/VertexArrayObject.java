package engine.graphics;

import static org.lwjgl.opengl.GL30.*;

public class VertexArrayObject {
    private int id;

    public VertexArrayObject(){
        this.id = glGenVertexArrays();
    }

    public void bind(){
        glBindVertexArray(id);
    }

    public void unbind(){
        glBindVertexArray(0);
    }

    public void linkAttribute(int index, int size, int stride, long offSet){
        glVertexAttribPointer(index, size, GL_FLOAT, false, stride, offSet);
        glEnableVertexAttribArray(index);

        glVertexAttribPointer(1, 2, GL_FLOAT, false, stride, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);
    }

    public void delete(){
        glDeleteVertexArrays(id);
    }

}