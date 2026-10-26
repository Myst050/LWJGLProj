package engine.graphics;

import org.lwjgl.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.stb.STBImage.*;

import java.nio.*;


public class Texture {
    private String file_path;
    private int id;
    private int width, height;

    public Texture(String f){
        this.file_path = f;
        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer channelsBuffer = BufferUtils.createIntBuffer(1);

        stbi_set_flip_vertically_on_load(true);

        ByteBuffer imageBytes = stbi_load(file_path, widthBuffer, heightBuffer, channelsBuffer, 0);

        if(imageBytes != null){
            this.width = widthBuffer.get(0);
            this.height = heightBuffer.get(0);

            int format;

            if(channelsBuffer.get(0) == 4){
                format = GL_RGBA;
            } else{
                format = GL_RGB;
            }

            glTexImage2D(GL_TEXTURE_2D, 0, format, width, height, 0, format, GL_UNSIGNED_BYTE, imageBytes);

            stbi_image_free(imageBytes);
        } else {
            System.out.println("ERROR: COULD NOT FIND TEXTURE " + file_path);
        }
    }

    public void bind(int slot){
        glActiveTexture(GL_TEXTURE0 + slot);
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void unbind(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void delete(){
        glDeleteTextures(id);
    }
}