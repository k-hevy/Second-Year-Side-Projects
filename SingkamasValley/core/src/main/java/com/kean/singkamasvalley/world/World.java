package com.kean.singkamasvalley.world;

import com.badlogic.gdx.utils.Array;
import com.kean.singkamasvalley.entities.WorldObject;

public class World {

    private final Array<WorldObject> objects = new Array<>();

    public World () {
    }

    public void addObject(WorldObject obj) {
        objects.add(obj);
    }

    public Array<WorldObject> getObjects() {
        return objects;
    }


}
