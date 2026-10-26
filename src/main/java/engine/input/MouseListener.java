package engine.input;

import static org.lwjgl.glfw.GLFW.*;

public class MouseListener {
    private static MouseListener mouseListener;
    private double xPos, yPos, lastX, lastY;
    private double scrollX, scrollY;
    private boolean mouseButtonPressed[] = new boolean[3];
    private boolean isDragging;

    private MouseListener(){
        this.xPos = 0.0f;
        this.yPos = 0.0f;
        this.lastX = 0.0f;
        this.lastY = 0.0f;
        this.scrollX = 0.0f;
        this.scrollY = 0.0f;
    }

    public static MouseListener get(){
        if(mouseListener == null){
            mouseListener = new MouseListener();
        }

        return MouseListener.mouseListener;
    }


    public static void mousePosCallback(long window, double xpos, double ypos){
        get().lastX = xpos;
        get().lastY = ypos;
        get().xPos = xpos;
        get().yPos = ypos;
        get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2];
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods){
        if(action == GLFW_PRESS){
            if(button < get().mouseButtonPressed.length){
                get().mouseButtonPressed[button] = true;
            }
        } else if (action == GLFW_RELEASE){
            if(button < get().mouseButtonPressed.length){
                get().mouseButtonPressed[button] = false;
                get().isDragging = false;
            }
        }
    }

    public static void mouseScrollCallback(long window, double xoffset, double yoffset) {
        get().scrollX = xoffset;
        get().scrollY = yoffset;
    }

    public static void endFrame() {
        get().scrollX = 0.0;
        get().scrollY = 0.0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }

    public static float getX() {
        return (float) get().xPos;
    }

    public static float getY() {
        return (float) get().yPos;
    }

    public static float getDx() {
        return (float) (get().lastX - get().xPos);
    }

    public static float getDy() {
        return (float) (get().lastY - get().yPos);
    }

    public static boolean mouseButtonDown(int button) {
        if (button < get().mouseButtonPressed.length) {
            return get().mouseButtonPressed[button];
        }
        return false;
    }
}