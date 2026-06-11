package com.kean.singkamasvalley.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TileRenderer {

    private final TileTexture tilledOverlay;
    private final int TILE_SIZE = 16;

    public TileRenderer(TileTexture tilledOverlay) {
        this.tilledOverlay = tilledOverlay;
    }

    public void render(World world, SpriteBatch spriteBatch) {
        for(int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                GameTile tile = world.getTile(x, y);
                if (tile == null) continue;
                Texture texture = resolveTexture(tile);
                if (texture != null) spriteBatch.draw(texture, x * TILE_SIZE, y * TILE_SIZE);
            }
        }
    }

    public Texture resolveTexture(GameTile tile) {

        if (tile.isWatered()) {
            return tilledOverlay.watered;
        }

        if (tile.isTilled()) {
            return tilledOverlay.tilled;
        }

        return null;
    }

}
