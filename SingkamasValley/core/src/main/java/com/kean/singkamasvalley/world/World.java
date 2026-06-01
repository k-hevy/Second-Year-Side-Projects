package com.kean.singkamasvalley.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class World {

    private static final int TILE_SIZE = 16;

    private final Tile[][] tiles;
    private final Texture grass;
    private final Texture water;
    private final Texture soil;
    private final Texture path;
    private int[][] map;

    public World () {

        grass = new Texture("grass.png");
        water = new Texture("water.png");
        soil = new Texture("soil.png");
        path = new Texture("path.png");

         map = new int[][] {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 0, 0, 3, 3, 0, 2, 2, 2, 2, 2, 0, 0, 1, 1, 0, 0, 0},
            {1, 1, 1, 0, 0, 3, 3, 0, 2, 2, 2, 2, 2, 0, 0, 1, 1, 0, 0, 0},
            {1, 1, 1, 0, 0, 3, 3, 0, 2, 2, 2, 2, 2, 0, 0, 1, 1, 0, 0, 0},
            {1, 1, 1, 0, 0, 3, 3, 0, 2, 2, 2, 2, 2, 0, 0, 1, 1, 0, 0, 0},
            {1, 1, 1, 0, 0, 3, 3, 0, 2, 2, 2, 2, 2, 0, 0, 1, 1, 0, 0, 0},
            {1, 1, 1, 0, 0, 3, 3, 0, 2, 2, 2, 2, 2, 0, 0, 1, 1, 0, 0, 0},
            {1, 1, 1, 0, 0, 3, 3, 0, 2, 2, 2, 2, 2, 0, 0, 1, 1, 0, 0, 0},
            {1, 1, 1, 0, 0, 3, 3, 0, 2, 2, 2, 2, 2, 0, 0, 1, 1, 0, 0, 0},
            {1, 1, 1, 0, 0, 3, 3, 0, 2, 2, 2, 2, 2, 0, 0, 1, 1, 0, 0, 0},
            {1, 1, 1, 0, 0, 3, 3, 0, 2, 2, 2, 2, 2, 0, 0, 1, 1, 0, 0, 0},
            {1, 1, 1, 0, 0, 3, 3, 0, 2, 2, 2, 2, 2, 0, 0, 1, 1, 0, 0, 0},
            {1, 1, 1, 0, 0, 3, 3, 0, 2, 2, 2, 2, 2, 0, 0, 1, 1, 0, 0, 0},
            {1, 1, 1, 0, 0, 3, 3, 0, 2, 2, 2, 2, 2, 0, 0, 1, 1, 0, 0, 0},
            {1, 1, 1, 0, 0, 3, 3, 0, 2, 2, 2, 2, 2, 0, 0, 1, 1, 0, 0, 0},
            {1, 1, 1, 0, 0, 3, 3, 0, 2, 2, 2, 2, 2, 0, 0, 1, 1, 0, 0, 0},
            {1, 1, 1, 0, 0, 3, 3, 0, 2, 2, 2, 2, 2, 0, 0, 1, 1, 0, 0, 0},
            {1, 1, 1, 0, 0, 3, 3, 0, 2, 2, 2, 2, 2, 0, 0, 1, 1, 0, 0, 0},
            {1, 1, 1, 0, 0, 3, 3, 0, 2, 2, 2, 2, 2, 0, 0, 1, 1, 0, 0, 0},
            {1, 1, 1, 0, 0, 3, 3, 0, 2, 2, 2, 2, 2, 0, 0, 1, 1, 0, 0, 0}
        };

        tiles = new Tile[map.length][map[0].length];

        for (int row = 0; row < map.length; row++) { // y UP OR DOWN
            for (int col = 0; col < map[row].length; col++) { // x LEFT OR RIGHT

                int x = col * TILE_SIZE;
                int y = ((map.length - 1 - row)) * TILE_SIZE;

                // accesing Array = TOP LEFT (col, row)
                // accessing SpriteBatch BOTTOM LEFT (x, y)

                switch (map[row][col]) {
                    case 0 : tiles[row][col] = new Tile(grass, TileType.GRASS, x, y);
                        break;
                    case 1 : tiles[row][col] = new Tile(water, TileType.WATER, x, y);
                        break;
                    case 2 : tiles[row][col] = new Tile(soil, TileType.SOIL, x, y);
                        break;
                    case 3 : tiles[row][col] = new Tile(path, TileType.PATH, x, y);
                        break;
                }

            }
        }

    }

    public Tile getTileAt(float x, float y) {

        int row = (tiles.length -1) - ((int) y / TILE_SIZE);
        int col = (int) x / TILE_SIZE;

        if (row < 0 || col < 0 || row >= tiles.length || col >= tiles[0].length) return null;

        return tiles[row][col];

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
        water.dispose();
        soil.dispose();
        path.dispose();
    }


}
