package com.kean.singkamasvalley.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Renderable {

    float getY(); // used to get Y position for depth sorting (higher Y == back)

    void render(SpriteBatch spriteBatch);

}
