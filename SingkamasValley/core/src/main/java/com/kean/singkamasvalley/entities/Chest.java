package com.kean.singkamasvalley.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.kean.singkamasvalley.world.WorldObject;

public class Chest extends WorldObject implements Interactable, Renderable {

    private Rectangle bounds;
    private int TILE_SIZE = 16;
    private Texture texture;

    public Chest(float x, float y) {
        bounds = new Rectangle(x, y, TILE_SIZE, TILE_SIZE);
        texture = new Texture("Chest.png");
    }

    @Override
    public void interact(Player player) {
        System.out.println("Chest opened");
    }

    @Override
    public float getY() {
        return bounds.y;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, bounds.x, bounds.y);
    }

    public void dispose() {
        texture.dispose();
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

}
