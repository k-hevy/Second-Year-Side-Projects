package com.kean.singkamasvalley.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tile {

    private final Texture texture;
    private final float x, y;
    private final TileType tileType;

    public Tile (Texture texture, TileType tileType, float x, float y) {
        this.texture = texture;
        this.tileType = tileType;
        this.x = x;
        this.y =y;
    }

    public TileType getType() {
        return tileType;
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, x, y);
    }

    public void dispose() {
        texture.dispose();
    }


}
