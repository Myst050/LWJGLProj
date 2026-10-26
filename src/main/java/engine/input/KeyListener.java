package engine.input;

import static org.lwjgl.glfw.GLFW.*;

public class KeyListener {
    private static KeyListener keyListener;
    boolean[] keys = new boolean[GLFW_KEY_LAST];

    private KeyListener(){

    }

    public static KeyListener get(){
        if(keyListener == null){
            keyListener = new KeyListener();
        }

        return keyListener;
    }

    public void setKeyCallback(long window, int key, int scancode, int action, int mods){
        if(key >= 0 && key < get().keys.length){
            if(action == 1){
                get().keys[key] = true;
            } else if (action == 0) {
                get().keys[key] = false;
            }
        }
    }

    public static boolean isKeyPressed(int key){
        return get().keys[key];
    }
}
