package engine.scenes;

public abstract class Scene {
    public abstract void init();
    public abstract void update(float dt);
    public abstract void render();
    public abstract void delete();
}
