package engine.graphics;


import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private int shaderProgram;
    private String vertexSrc;
    private String fragmentSrc;

    private FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public Shader(String vertexSrc, String fragmentSrc){
        this.vertexSrc = vertexSrc;
        this.fragmentSrc = fragmentSrc;
    }

    public void compile(){
        int vertexID, fragmentID;

        vertexID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexID, vertexSrc);
        glCompileShader(vertexID);
        compliationErrorCheck(vertexID, "VERTEX");

        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentID, fragmentSrc);
        glCompileShader(fragmentID);
        compliationErrorCheck(fragmentID, "FRAGMENT");

        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexID);
        glAttachShader(shaderProgram, fragmentID);
        glLinkProgram(shaderProgram);
        compliationErrorCheck(shaderProgram, "PROGRAM");

        glDeleteShader(vertexID);
        glDeleteShader(fragmentID);
    }

    public void use(){
        glUseProgram(shaderProgram);
    }

    public void detach(){
        glUseProgram(0);
    }

    private void compliationErrorCheck(int shaderID, String type){
        int success;

        if(type.equals("PROGRAM")){
            success = glGetProgrami(shaderID, GL_LINK_STATUS);
            if(success == GL_FALSE){
                System.err.println("Error with Shader Program: " + glGetProgramInfoLog(shaderID));
            }
        } else {
            success = glGetShaderi(shaderID, GL_COMPILE_STATUS);
            if(success == GL_FALSE){
                System.err.println("Error: " + glGetShaderInfoLog(shaderID));
            }
        }
    }

    public int returnProgramID(){
        return shaderProgram;
    }

    public void uploadMat4f(String uni, Matrix4f mat){
        int loc = glGetUniformLocation(shaderProgram, uni);

        if(loc == -1){
            System.out.println("UNIFORM LOCATION ERROR *** Error at Shader.java line 78");
            return;
        }

        mat.get(matrixBuffer);
        glUniformMatrix4fv(loc, false, matrixBuffer);
    }

    public void delete(){
        if(shaderProgram != 0){
            glDeleteProgram(shaderProgram);
        }
    }
}
