package engine.graphics;

import org.joml.*;

public class Camera {
    private Vector3f position;
    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;

    public Camera(Vector3f position){
        this.position = position;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        adjustProjection();
    }

    public void adjustProjection(){
        this.projectionMatrix.identity();
        projectionMatrix.ortho(-1.0f, 1.0f, -1.0f, 1.0f, 0.0f, 100.0f);
    }

    public Matrix4f getViewMatrix(){
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, 1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);

        viewMatrix.identity();
        viewMatrix.translate(-position.x, -position.y, 0.0f);

        return this.viewMatrix;
    }

    public Matrix4f getProjectionMatrix(){
        return this.projectionMatrix;
    }

    public Vector3f getPosition(){
        return this.position;
    }
}
