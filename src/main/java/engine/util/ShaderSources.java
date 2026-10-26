package engine.util;

public class ShaderSources {
    private static String vertexSrc = "#version 330 core\n" +
            "\n" +
            "layout (location = 0) in vec3 aPos;\n" +
            "layout (location = 1) in vec2 aTexCoords;\n" +
            "\n" +
            "out vec2 fTexCoords;\n" +
            "\n" +
            "uniform mat4 uProjection;\n" +
            "uniform mat4 uView;\n" +
            "\n" +
            "void main() {\n" +
            "    fTexCoords = aTexCoords;\n" +
            "    gl_Position = uProjection * uView * vec4(aPos, 1.0);\n" +
            "}";
    private static String fragmentSrc = "#version 330 core\n" +
            "\n" +
            "in vec2 fTexCoords;\n" +
            "out vec4 FragColor;\n" +
            "\n" +
            "uniform sampler2D uTexture;\n" +
            "\n" +
            "void main() {\n" +
            "    FragColor = texture(uTexture, fTexCoords);\n" +
            "}";
    public static String getSource(String type){
        if(type.equalsIgnoreCase("vertex")){
            return vertexSrc;
        } else if (type.equalsIgnoreCase("fragment")) {
            return fragmentSrc;
        } else {
            return "";
        }
    }
}
