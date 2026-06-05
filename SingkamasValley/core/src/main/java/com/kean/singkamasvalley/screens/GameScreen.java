package com.kean.singkamasvalley.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kean.singkamasvalley.entities.Chest;
import com.kean.singkamasvalley.entities.Player;
import com.kean.singkamasvalley.managers.CollisionManager;
import com.kean.singkamasvalley.managers.InteractionManager;
import com.kean.singkamasvalley.managers.MapManager;
import com.kean.singkamasvalley.managers.RenderManager;
import com.kean.singkamasvalley.world.World;

public class GameScreen implements Screen {

    private SpriteBatch spriteBatch;
    private Player player;
    private OrthographicCamera camera;
    private MapManager mapManager;
    private CollisionManager collisionManager;
    private ShapeRenderer shapeRenderer;
    private RenderManager renderManager;
    private InteractionManager interactionManager;
    private World world;
    private static final int TILE_SIZE = 16;

    public GameScreen () {}

    @Override
    public void show() {

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 400);

        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        mapManager = new MapManager(spriteBatch);
        collisionManager = new CollisionManager(mapManager.getTiledMap());

        world = new World();
        interactionManager = new InteractionManager();
        renderManager = new RenderManager();

        Chest chest = new Chest(200, 200);
        world.addObject(chest);
        interactionManager.register(chest);

        player = new Player(world, collisionManager, interactionManager);

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

        Rectangle hitbox = player.getHitBox();
        Rectangle front = player.getFrontTile();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        shapeRenderer.rect(front.x, front.y, front.width, front.height);
        shapeRenderer.end(); // seperate this from spritebatch cuz causes issues
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
    }

}
