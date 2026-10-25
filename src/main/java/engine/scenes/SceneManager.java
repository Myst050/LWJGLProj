package engine.scenes;

public class SceneManager {
    public static Scene current_scene;

    public void changeScene(Scene new_scene){
        if(current_scene != null){
            current_scene.delete();
        }

        current_scene = new_scene;
        new_scene.init();
    }

    public void updateScene(float dt){
        if(current_scene != null){
            current_scene.update(dt);
        }
    }

    public void renderScene(){
        if(current_scene != null){
            current_scene.render();
        }
    }
}
