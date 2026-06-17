package com.kean.singkamasvalley.world.objects;

import java.util.HashMap;
import java.util.Map;

public class CropRegistry {

    private static final Map<CropType, CropDefinition> crops = new HashMap<>();

    public static void register (CropType type, CropDefinition definition) {
        crops.put(type, definition);
    }

    public static CropDefinition getCropDefinition(CropType type) {
        return crops.get(type);
    }
}
