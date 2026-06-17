package com.kean.singkamasvalley.world.objects;

import com.kean.singkamasvalley.world.World;

public class CropManager {
    public void advanceDay(World world) {
        for (WorldObject obj : world.getObjects()) {
            if (obj instanceof Crop crop) {
                crop.grow();
            }
        }
    }
}
