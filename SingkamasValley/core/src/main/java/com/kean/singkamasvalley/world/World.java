package com.kean.singkamasvalley.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class World {

    private final Tile[][] tiles;
    private final Texture grass;

    public World () {

        grass = new Texture("grass.png");
        tiles = new Tile[20][20];

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                tiles[i][j] = new Tile(grass, i * 16, j * 16);
            }
        }

    }

    public void render(SpriteBatch spriteBatch) {

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                tiles[i][j].render(spriteBatch);
            }
        }

    }

    public void dispose() {
        grass.dispose();
    }


}
