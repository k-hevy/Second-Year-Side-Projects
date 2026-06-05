package com.kean.singkamasvalley.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameTile {

    private final TileType type;
    private boolean tilled;
    private boolean watered;
    private boolean occupied;

    public GameTile(TileType type) {
        this.type = type;
    }

    public TileType getType() {
        return type;
    }

    public boolean isTilled() {
        return tilled;
    }

    public boolean isWatered() {
        return watered;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setTilled(boolean tilled) {
        this.tilled = tilled;
    }

    public void setWatered(boolean watered) {
        this.watered = watered;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }




}
