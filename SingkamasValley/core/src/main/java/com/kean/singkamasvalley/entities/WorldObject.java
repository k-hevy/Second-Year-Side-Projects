package com.kean.singkamasvalley.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Rectangle;

public abstract class WorldObject {

    protected Rectangle bounds;

    public abstract Rectangle getBounds();

}
