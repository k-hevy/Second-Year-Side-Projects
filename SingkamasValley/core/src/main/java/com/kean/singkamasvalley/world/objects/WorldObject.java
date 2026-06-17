package com.kean.singkamasvalley.world.objects;

import com.badlogic.gdx.math.Rectangle;
import com.kean.singkamasvalley.entities.Renderable;
import com.kean.singkamasvalley.managers.RenderManager;

public abstract class WorldObject implements Renderable {

    protected Rectangle bounds;

    public WorldObject (float x, float y, float width, float height) {
        this.bounds = new Rectangle(x, y, width, height);
    }

    public Rectangle getBounds( ) {
        return bounds;
    }

    public float getY() {
        return bounds.y;
    }

    protected float getX() {
        return bounds.x;
    }

}
