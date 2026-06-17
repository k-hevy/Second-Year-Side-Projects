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
import com.kean.singkamasvalley.inventory.items.ItemEntity;
import com.kean.singkamasvalley.inventory.items.ItemStack;
import com.kean.singkamasvalley.managers.CollisionManager;
import com.kean.singkamasvalley.managers.InteractionManager;
import com.kean.singkamasvalley.managers.ToolManager;
import com.kean.singkamasvalley.ui.Hotbar;
import com.kean.singkamasvalley.world.GameTile;
import com.kean.singkamasvalley.world.World;
import com.kean.singkamasvalley.world.objects.Crop;
import com.kean.singkamasvalley.world.objects.CropType;

import java.util.Random;

public class Player implements Renderable {

    private Direction direction;
    private PlayerState state;

    private final Rectangle hitbox;

    private float x, y;
    private float stateTime;
    private final int TILE_SIZE = 16;

    private final Texture walkSheet;
    private final Texture idleSheet;
    private final Texture useToolSheet;

    private final CollisionManager collisionManager;
    private final InteractionManager interactionManager;
    private final World world;

    private final Inventory inventory;
    private final Hotbar hotbar;
    private final ItemDatabase itemDatabase;
    private final  GameAssets gameAssets;
    private final  ToolManager toolManager;
    private final  PlayerAnimationSet animationSet;

    private boolean toolApplied;
    private Texture cropTex;


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

        animationSet = new PlayerAnimationSet();
        walkSheet = new Texture("player/player_walking_sheet.png");
        idleSheet = new Texture("player/player_idle_sheet.png");
        useToolSheet = new Texture("player/player_using_tool_sheet.png");

        TextureRegion[][] walkGrid = TextureRegion.split(
            walkSheet,
            walkSheet.getWidth() / 4,
            walkSheet.getHeight() / 4
        );

        TextureRegion[][] idleGrid = TextureRegion.split(
            idleSheet,
            idleSheet.getWidth() / 4,
            idleSheet.getHeight() / 4
        );

        TextureRegion[][] useToolGrid = TextureRegion.split(
            useToolSheet,
            useToolSheet.getWidth() / 6,
            useToolSheet.getHeight() / 4
        );

        animationSet.walkDown  = new Animation<>(0.15f, walkGrid[0]);
        animationSet.walkUp    = new Animation<>(0.15f, walkGrid[1]);
        animationSet.walkRight = new Animation<>(0.15f, walkGrid[2]);
        animationSet.walkLeft  = new Animation<>(0.15f, walkGrid[3]);

        animationSet.idleDown  = new Animation<>(0.45f, idleGrid[0]);
        animationSet.idleUp  = new Animation<>(0.45f, idleGrid[1]);
        animationSet.idleRight  = new Animation<>(0.45f, idleGrid[2]);
        animationSet.idleLeft  = new Animation<>(0.45f, idleGrid[3]);

        animationSet.toolDown  = new Animation<>(0.08f, useToolGrid[0]);
        animationSet.toolUp  = new Animation<>(0.08f, useToolGrid[1]);
        animationSet.toolRight  = new Animation<>(0.08f, useToolGrid[2]);
        animationSet.toolLeft  = new Animation<>(0.08f, useToolGrid[3]);

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

        if (state == PlayerState.USING_TOOL) {
            updateToolAnimation(delta);
            return;
        } // locks the animation

        hotbar.update();

        float newX = x;
        float newY = y;

        float speed = 100f;

        state = PlayerState.IDLE;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            newY += speed * delta;
            direction = Direction.BACKWARD;
            state = PlayerState.WALKING;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            newX += speed * delta;
            direction = Direction.RIGHT;
            state = PlayerState.WALKING;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            newY -= speed * delta;
            direction = Direction.FORWARD;
            state = PlayerState.WALKING;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            newX -= speed * delta;
            direction = Direction.LEFT;
            state = PlayerState.WALKING;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) { // Hit
            state = PlayerState.USING_TOOL;
            toolApplied = false;
            stateTime = 0;
        }

        hitbox.setPosition(newX + 2, newY); // sets hitbox adv but when player is blocks regresses

        if (!collisionManager.isBlocked(hitbox)) {
            x = newX;
            y = newY;
            updateHitBox(x, y);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            Random rand = new Random();

            world.addObject(
                new ItemEntity(gameAssets,
                new ItemStack(itemDatabase.getItem("hoe"), 1),
                rand.nextFloat(200),
                rand.nextFloat(200))
            );

            world.addObject(
                new ItemEntity(gameAssets,
                    new ItemStack(itemDatabase.getItem("watering_can"), 1),
                    rand.nextFloat(200),
                    rand.nextFloat(200))
            );

        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            ItemStack selected = getHotbar().getSelectedItem();
            if (selected == null) return;
            ItemDefinition item = selected.getItem();
            System.out.println(item.getToolType());
        } // says what item the user selects

        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            GameTile[][] tiles = world.getTiles();
            System.out.println(tiles[(int) getFrontTile().getX() / 16][(int) getFrontTile().getY() / 16].getType());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            world.addObject(new Crop(200, 200, CropType.SINGKAMAS));
            System.out.println("Added plant");

        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) { interactionManager.tryInteract(this, world); }

        stateTime += delta;

    }

    public void updateToolAnimation(float delta) {

        stateTime += delta;

        if (!toolApplied && stateTime >= 0.24f) {
            System.out.println("Impact");
            toolManager.useTool(this, world);
            toolApplied = true;
        }

        Animation<TextureRegion> current = switch(direction) {
            case FORWARD -> animationSet.toolDown;
            case BACKWARD -> animationSet.toolUp;
            case LEFT -> animationSet.toolLeft;
            case RIGHT -> animationSet.toolRight;
        };

        if (current.isAnimationFinished(stateTime)) {
            state = PlayerState.IDLE;
            stateTime = 0;
            toolApplied = false;
        }
    }

    public TextureRegion getCurrentFrame() {

        return switch (state) {

            case WALKING -> switch (direction) {
                case FORWARD   -> animationSet.walkDown.getKeyFrame(stateTime, true);
                case BACKWARD  -> animationSet.walkUp.getKeyFrame(stateTime, true);
                case LEFT      -> animationSet.walkLeft.getKeyFrame(stateTime, true);
                case RIGHT     -> animationSet.walkRight.getKeyFrame(stateTime, true);
            };

            case USING_TOOL -> switch (direction) {
                case FORWARD   -> animationSet.toolDown.getKeyFrame(stateTime);
                case BACKWARD  -> animationSet.toolUp.getKeyFrame(stateTime);
                case LEFT      -> animationSet.toolLeft.getKeyFrame(stateTime);
                case RIGHT     -> animationSet.toolRight.getKeyFrame(stateTime);
            };

            case IDLE -> switch (direction) {
                case FORWARD   -> animationSet.idleDown.getKeyFrame(stateTime, true);
                case BACKWARD  -> animationSet.idleUp.getKeyFrame(stateTime, true);
                case LEFT      -> animationSet.idleLeft.getKeyFrame(stateTime, true);
                case RIGHT     -> animationSet.idleRight.getKeyFrame(stateTime, true);
            };

        };

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
        spriteBatch.draw(getCurrentFrame(), x, y);
    }

    @Override
    public float getY() {
        return y;
    }

    public void dispose() {
        walkSheet.dispose();
        idleSheet.dispose();
        useToolSheet.dispose();
        cropTex.dispose();
    }
}
