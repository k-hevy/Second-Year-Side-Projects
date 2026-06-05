package com.kean.singkamasvalley.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class ItemEntity extends WorldObject implements Renderable {

    @Override
    public float getY() {
        return 0;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
