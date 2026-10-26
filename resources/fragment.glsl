#version 330 core

in vec2 fTexCoords;
out vec4 FragColor;

uniform sampler2D uTexture;

void main() {
    FragColor = texture(uTexture, fTexCoords);
}