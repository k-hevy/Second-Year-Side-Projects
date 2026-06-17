package com.kean.singkamasvalley.world.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Crop extends WorldObject {

    private final CropType type;
    private int growthStage;

    public Crop(float x, float y, CropType type) {

        super(x, y, 16, 16);
        this.type = type;
        this.growthStage = 1;

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        CropDefinition definition = CropRegistry.getCropDefinition(type);
        spriteBatch.draw(definition.getTexture(growthStage), bounds.x, bounds.y);
    }

    public void grow() {
        growthStage++;
        System.out.println("Stage: " + growthStage);
    }





}
