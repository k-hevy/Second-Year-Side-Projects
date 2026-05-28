package managers;

import java.awt.Graphics;
import java.util.ArrayList;

import objects.Entity;

public class EntityManager {

    private ArrayList<Entity> entities;

    public EntityManager() {
        entities = new ArrayList<Entity>();
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void update() {

        for (int i = 0; i < entities.size(); i++) {

            Entity e = entities.get(i);
            e.update();

            if (!e.isActive()) {
                entities.remove(e);
                i--;
            }

        }

    }

    public void draw(Graphics g) {
        for (Entity e : entities) {
            e.draw(g);
        }
    }

}
