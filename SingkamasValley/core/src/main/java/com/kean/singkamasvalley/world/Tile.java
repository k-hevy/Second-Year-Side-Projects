package com.kean.singkamasvalley.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tile {

    private final Texture texture;
    private final float x, y;

    public Tile (Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y =y;
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, x, y);
    }

    public void dispose() {
        texture.dispose();
    }


}
