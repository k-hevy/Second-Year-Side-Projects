package com.kean.singkamasvalley.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kean.singkamasvalley.assets.GameAssets;
import com.kean.singkamasvalley.entities.Player;
import com.kean.singkamasvalley.inventory.items.ItemDatabase;
import com.kean.singkamasvalley.managers.*;
import com.kean.singkamasvalley.ui.UIManager;
import com.kean.singkamasvalley.world.World;

public class GameScreen implements Screen {

    public static final int TILE_SIZE = 16;
    private SpriteBatch spriteBatch;
    private Player player;
    private OrthographicCamera camera;
    private MapManager mapManager;
    private CollisionManager collisionManager;
    private ShapeRenderer shapeRenderer;
    private RenderManager renderManager;
    private InteractionManager interactionManager;
    private World world;
    private GameAssets gameAssets;
    private ItemDatabase itemDatabase;
    private PickupManager pickupManager;
    private UIManager uiManager;
    private Texture woodTex;

    public GameScreen () {}

    @Override
    public void show() {

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 400);

        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        mapManager = new MapManager(spriteBatch);
        collisionManager = new CollisionManager(mapManager.getTiledMap());

        int i = mapManager.getTiledMap().getProperties().get("width", Integer.class);

        world = new World(mapManager.getTiledMap().getProperties().get("width", Integer.class),
            mapManager.getTiledMap().getProperties().get("height", Integer.class));

        interactionManager = new InteractionManager();
        renderManager = new RenderManager();



        gameAssets = new GameAssets();
        itemDatabase = new ItemDatabase();
        gameAssets.load();

//        Chest chest = new Chest(200, 200);
//        world.addObject(chest);
//        interactionManager.register(chest);

        player = new Player(world, collisionManager, interactionManager, itemDatabase, gameAssets);
        pickupManager = new PickupManager(world);

        uiManager = new UIManager(player.getInventory(), player.getHotbar());


    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //updating positions
        player.update(delta); // for when the player is walking

        // Camera follows player
        camera.position.set(player.getPlayerX(), player.getPlayerY(), 0); // adjust camera location
        camera.update(); // updates position
        spriteBatch.setProjectionMatrix(camera.combined); // after camera update but before batch.begin (to avoid flushes to teh GPU)

        // Rendering World
        spriteBatch.begin(); // starts drawing
        mapManager.render(camera, spriteBatch);
        renderManager.render(spriteBatch, world, player); // renders entities like player
        spriteBatch.end();

        uiManager.render(spriteBatch); // has own Spritebatch, should be outside of prior spritebatch

        Rectangle hitbox = player.getHitBox();
        Rectangle front = player.getFrontTile();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        shapeRenderer.rect(front.x, front.y, front.width, front.height);
        shapeRenderer.end(); // seperate this from spritebatch cuz causes issues

        pickupManager.checkPickup(player);
    }

    @Override public void resize(int width, int height) {
        spriteBatch.getProjectionMatrix().setToOrtho2D(0,0, width, height);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override public void dispose() {
        spriteBatch.dispose();
        player.dispose();
        gameAssets.dispose();
    }

}
