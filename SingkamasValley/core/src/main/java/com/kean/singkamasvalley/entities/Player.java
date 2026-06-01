package com.kean.singkamasvalley.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kean.singkamasvalley.world.World;

public class Player {

    private enum Direction {
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT;
    }

    private Direction direction = Direction.FORWARD;

    private float x, y;
    private float speed = 100f;
    private Texture playerSpriteSheet;
    private float stateTime;
    private World world;

    private Animation<TextureRegion> walkBackward;
    private Animation<TextureRegion> walkForward;
    private Animation<TextureRegion> walkLeft;
    private Animation<TextureRegion> walkRight;
    private Animation<TextureRegion> currentAnimation;

    public Player (World world) {
        this.x = 100;
        this.y = 100;
        this.world = world;
        playerSpriteSheet = new Texture("player_walking_sheet.png");
        TextureRegion[][] tmp = TextureRegion.split(
            playerSpriteSheet,
            playerSpriteSheet.getWidth() / 4,
            playerSpriteSheet.getHeight() / 4
            );

        walkForward = new Animation<>(0.15f, tmp[0]);
        walkBackward = new Animation<>(0.15f, tmp[1]);
        walkRight = new Animation<>(0.15f, tmp[2]);
        walkLeft = new Animation<>(0.15f, tmp[3]);

        currentAnimation = walkForward;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void update(float delta) {

        boolean moving = false;

        float newX = x;
        float newY = y;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            newY += speed * delta;
            direction = Direction.BACKWARD;
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            newX += speed * delta;
            direction = Direction.RIGHT;
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            newY -= speed * delta;
            direction = Direction.FORWARD;
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            newX -= speed * delta;
            direction = Direction.LEFT;
            moving = true;
        }



        if (world.getTileAt(newX, y) != null && !world.getTileAt(newX, y).getType().isSolid()) {
            x = newX;
        }

        if (world.getTileAt(x, newY) != null && !world.getTileAt(x, newY).getType().isSolid()) {
            y = newY;
        }

        switch(direction) {
            case FORWARD  : currentAnimation = walkForward; break;
            case BACKWARD : currentAnimation = walkBackward; break;
            case RIGHT    : currentAnimation = walkRight; break;
            case LEFT     : currentAnimation = walkLeft; break;
        }

        if (moving) {
            stateTime += delta;
        }

    }

    public void render (SpriteBatch spriteBatch) {
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);
        spriteBatch.draw(frame, x, y);
    }

    public void dispose() {
        playerSpriteSheet.dispose();
    }
}
