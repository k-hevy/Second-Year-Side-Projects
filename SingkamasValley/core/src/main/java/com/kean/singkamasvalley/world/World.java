package com.kean.singkamasvalley.world;

import com.badlogic.gdx.utils.Array;
import com.kean.singkamasvalley.world.objects.WorldObject;

public class World {

    private Array<WorldObject> objects = new Array<>();
    public GameTile[][] tiles;

    public World () {
        objects = new Array<>();
    }

    int x, y = 0;

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

    public GameTile[][] getTiles() {
        return tiles;
    }

    public int getHeight() {
        return tiles[0].length;
    }

    public int getWidth() {
        return tiles.length;
    }


}
