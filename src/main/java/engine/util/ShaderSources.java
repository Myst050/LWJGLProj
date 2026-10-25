package engine.util;

public class ShaderSources {
    private static String vertexSrc = "#version 330 core\n" +
            "\n" +
            "layout (location = 0) in vec3 aPos;\n" +
            "\n" +
            "\n" +
            "uniform mat4 uProjection;\n" +
            "uniform mat4 uView;\n" +
            "\n" +
            "void main() {\n" +
            "    gl_Position = uProjection * uView * vec4(aPos, 1.0);\n" +
            "}";
    private static String fragmentSrc = "#version 330 core\n" +
            "\n" +
            "out vec4 FragColor;\n" +
            "\n" +
            "void main() {\n" +
            "    FragColor = vec4(1.0, 0.5, 0.2, 1.0); // RGBA (Orange)\n" +
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
