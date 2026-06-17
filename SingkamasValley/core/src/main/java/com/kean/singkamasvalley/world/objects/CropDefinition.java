package com.kean.singkamasvalley.world.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CropDefinition {

    private final TextureRegion[] stages;
    private final int daysToGrow;

    public CropDefinition(TextureRegion[] stages, int daysToGrow) {
        this.stages = stages;
        this.daysToGrow = daysToGrow;
    }

    public TextureRegion getTexture(int growthStage) {
        int index = Math.min(growthStage - 1, daysToGrow -1);
        return  stages[index];
    }

    public int getDaysToGrow() {
        return daysToGrow;
    }



}
