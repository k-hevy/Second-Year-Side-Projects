package com.kean.singkamasvalley.world;

import com.badlogic.gdx.utils.Array;

public class World {

    private Array<WorldObject> objects = new Array<>();
    private GameTile[][] tiles;

    public World (int width, int height) {

        objects = new Array<>();
        tiles = new GameTile[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = new GameTile(TileType.GRASS);
            }
        }

    }

    public GameTile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= tiles.length || y >= tiles[0].length) {
            return null;
        }
        return tiles[x][y];
    }

    public void addObject(WorldObject obj) {
        objects.add(obj);
    }

    public void removeObject(WorldObject obj) {
        objects.removeValue(obj, true);
    }

    public Array<WorldObject> getObjects() {
        return objects;
    }

    public int getHeight() {
        return tiles[0].length;
    }

    public int getWidth() {
        return tiles.length;
    }


}
