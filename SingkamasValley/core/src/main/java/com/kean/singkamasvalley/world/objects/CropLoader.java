package com.kean.singkamasvalley.world.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;

public class CropLoader {

    public static void load() {

        Json json = new Json();
        FileHandle file = Gdx.files.internal("data/crops.json");
        CropData[] crops = json.fromJson(CropData[].class, file);

        for (CropData crop : crops) {
            registerCrop(crop);
        }

    }

    public static void registerCrop(CropData crop) {

        CropType type = CropType.valueOf(crop.type);
        Texture sheet = new Texture(crop.texture);
        int daysToGrow = crop.daysToGrow;

        TextureRegion[][] split = TextureRegion.split(sheet, 16, 16);
        TextureRegion[] stages = split[0];

        CropRegistry.register(type, new CropDefinition(stages, daysToGrow));
    }

}
