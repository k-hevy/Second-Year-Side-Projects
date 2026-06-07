package com.kean.singkamasvalley.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.kean.singkamasvalley.assets.GameAssets;
import com.kean.singkamasvalley.inventory.Inventory;
import com.kean.singkamasvalley.inventory.items.ItemDatabase;
import com.kean.singkamasvalley.inventory.items.ItemDefinition;
import com.kean.singkamasvalley.inventory.items.ItemStack;
import com.kean.singkamasvalley.managers.CollisionManager;
import com.kean.singkamasvalley.managers.InteractionManager;
import com.kean.singkamasvalley.managers.ToolManager;
import com.kean.singkamasvalley.ui.Hotbar;
import com.kean.singkamasvalley.world.GameTile;
import com.kean.singkamasvalley.world.World;

import java.util.Random;

public class Player implements Renderable {

    private Direction direction;
    private PlayerState state;

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
    private final InteractionManager interactionManager;
    private final World world;

    private Inventory inventory;
    private Hotbar hotbar;
    private ItemDatabase itemDatabase;
    private GameAssets gameAssets;
    private ToolManager toolManager;


    public Player (World world, CollisionManager collisionManager,
                   InteractionManager interactionManager, ItemDatabase itemDatabase,
                   GameAssets gameAssets) {

        this.x = 216;
        this.y = 200;
        this.collisionManager = collisionManager;
        this.interactionManager = interactionManager;
        this.itemDatabase = itemDatabase;
        this.gameAssets = gameAssets;
        this.world = world;

        direction = Direction.FORWARD;
        state = PlayerState.IDLE;



        hitbox = new Rectangle(x+2, y, 12, 8 ); //sprite = 16x16, hitbox = 12x8

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
        toolManager = new ToolManager();

        inventory = new Inventory();
        hotbar = new Hotbar(this.getInventory());

    }

    public float getPlayerX() {
        return x;
    }

    public float getPlayerY() {
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

//        System.out.println(direction);
//        System.out.println(state);

        hotbar.update();
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

        if (moving) {
            state = PlayerState.WALKING;
        } else {
            state = PlayerState.IDLE;
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


        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            Random rand = new Random();

            world.addObject(
                new ItemEntity(gameAssets,
                new ItemStack(itemDatabase.getItem("woodsps"), 1),
                rand.nextFloat(200),
                rand.nextFloat(200))
            );

            world.addObject(
                new ItemEntity(gameAssets,
                    new ItemStack(itemDatabase.getItem("singkamas_seed"), 1),
                    rand.nextFloat(200),
                    rand.nextFloat(200))
            );

            world.addObject(
                new ItemEntity(gameAssets,
                    new ItemStack(itemDatabase.getItem("stone"), 1),
                    rand.nextFloat(200),
                    rand.nextFloat(200))
            );
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            ItemStack selected = getHotbar().getSelectedItem();
            if (selected == null) return;
            ItemDefinition item = selected.getItem();
            System.out.println(item.getToolType());
        }


//        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
//            interactionManager.tryInteract(this, world);
//        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {

            toolManager.useTool(this, world);
//
//
//            Rectangle frontTile = getFrontTile();
//            int tileX = (int) frontTile.x / 16;
//            int tileY = (int) frontTile.y / 16;
//
//            GameTile tile = world.getTile(tileX, tileY);
//
//            if (tile !=  null && !tile.isTilled()) {
//                tile.setTilled(true);
//                System.out.println("Sucecsfully tiled");
//            } else if (tile !=  null && tile.isTilled()) {
//                tile.setTilled(false);
//                System.out.println("Sucecsfully untiled");
//            }

        }


    }

    public Rectangle getFrontTile () {

        int px = (int) x / TILE_SIZE;
        int py = (int) y / TILE_SIZE;

        switch (direction) {
            case FORWARD  : py -= 1; break;
            case BACKWARD : py += 1; break;
            case RIGHT    : px += 1; break;
            case LEFT     : px -= 1; break;
        }

        return new Rectangle(px * TILE_SIZE, py * TILE_SIZE, TILE_SIZE, TILE_SIZE);

    }

    public Inventory getInventory() {
        return inventory;
    }

    public Hotbar getHotbar() {
        return hotbar;
    }

    @Override
    public void render (SpriteBatch spriteBatch) {
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);
        spriteBatch.draw(frame, x, y);
    }

    @Override
    public float getY() {
        return y;
    }

    public void dispose() {
        playerSpriteSheet.dispose();
    }
}
