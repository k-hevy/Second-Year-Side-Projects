package com.kean.singkamasvalley.world;

import com.badlogic.gdx.graphics.Texture;

public class TileTexture {

    public Texture grass;
    public Texture tilled;
    public Texture watered;

    public TileTexture() {
        grass = new Texture("tiles/grass.png");
        tilled = new Texture("tiles/tilled.png");
        watered = new Texture("tiles/watered.png");
    }
}
