package com.kean.singkamasvalley.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kean.singkamasvalley.entities.Player;
import com.kean.singkamasvalley.entities.Renderable;
import com.kean.singkamasvalley.entities.WorldObject;
import com.kean.singkamasvalley.world.World;

import java.util.ArrayList;
import java.util.Comparator;

public class RenderManager {

    private final ArrayList<Renderable> renderables = new ArrayList<>();

    public RenderManager() {}

    public void add(Renderable r) {
        renderables.add(r);
    }

    public void remove(Renderable r) {
        renderables.remove(r);
    }

    public void render(SpriteBatch spriteBatch, World world, Player player) {

        renderables.clear();

        for (WorldObject obj : world.getObjects()) {
            if (obj instanceof Renderable) {
                renderables.add((Renderable) obj);
            }
        }
        renderables.add(player);

        renderables.sort(Comparator.comparing(Renderable::getY).reversed()); // sorts by Y value
        for (Renderable e : renderables) {
            e.render(spriteBatch);
        }
    }

}
