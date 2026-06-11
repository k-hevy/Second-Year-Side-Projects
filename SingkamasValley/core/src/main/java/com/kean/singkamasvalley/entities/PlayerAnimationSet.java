package com.kean.singkamasvalley.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerAnimationSet {

    // For Idle
    public Animation<TextureRegion> idleDown;
    public Animation<TextureRegion> idleUp;
    public Animation<TextureRegion> idleRight;
    public Animation<TextureRegion> idleLeft;

    // For tool
    public Animation<TextureRegion> toolDown;
    public Animation<TextureRegion> toolUp;
    public Animation<TextureRegion> toolRight;
    public Animation<TextureRegion> toolLeft;

    // For Walking
    public Animation<TextureRegion> walkDown;
    public Animation<TextureRegion> walkUp;
    public Animation<TextureRegion> walkRight;
    public Animation<TextureRegion> walkLeft;

}
