package com.kean.singkamasvalley.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kean.singkamasvalley.managers.CollisionManager;

public class Player {

    private enum Direction {
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT
    }

    private Direction direction = Direction.FORWARD;

    private Rectangle hitbox;

    private float x, y;
    private final Texture playerSpriteSheet;
    private float stateTime;
    private final int TILE_SIZE = 16;

    private final Animation<TextureRegion> walkBackward;
    private final Animation<TextureRegion> walkForward;
    private final Animation<TextureRegion> walkLeft;
    private final Animation<TextureRegion> walkRight;
    private Animation<TextureRegion> currentAnimation;

    private final CollisionManager collisionManager;
    private final ShapeRenderer shapeRenderer;

    public Player (CollisionManager collisionManager, ShapeRenderer shapeRenderer) {

        this.x = 200;
        this.y = 200;
        this.collisionManager = collisionManager;
        this.shapeRenderer = shapeRenderer;

        hitbox = new Rectangle(x+2, y, 12, 8 );

        //sprite = 16x16, hitbox = 12x8

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

    public int getTileX () { return (int) x / TILE_SIZE; }

    public int getTileY () { return (int) y / TILE_SIZE; }

    public void updateHitBox(float x, float y) {
        hitbox.setPosition(x + 2, y);
    }

    public Rectangle getHitBox() {
        return hitbox;
    }

    public void update(float delta) {

        boolean moving = false;

        float newX = x;
        float newY = y;

        float speed = 100f;

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

        hitbox.setPosition(newX + 2, newY); // sets hitbox adv but when player is blocks regresses

        if (!collisionManager.isBlocked(hitbox)) {
            x = newX;
            y = newY;
            updateHitBox(x, y);
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
