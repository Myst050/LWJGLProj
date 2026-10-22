package engine.graphics;


import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private int shaderProgram;
    private String vertexSrc;
    private String fragmentSrc;

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
}
