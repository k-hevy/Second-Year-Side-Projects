package com.kean.singkamasvalley.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kean.singkamasvalley.entities.Player;
import com.kean.singkamasvalley.managers.CollisionManager;
import com.kean.singkamasvalley.managers.MapManager;
import com.kean.singkamasvalley.world.World;

public class GameScreen implements Screen {

    private SpriteBatch spriteBatch;
    private Player player;
    private OrthographicCamera camera;
    private World world;
    private MapManager mapManager;
    private CollisionManager collisionManager;
    private ShapeRenderer shapeRenderer;
    private static final int TILE_SIZE = 16;

    public GameScreen () {}

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 400);

        spriteBatch = new SpriteBatch();

        mapManager = new MapManager(spriteBatch);
        collisionManager = new CollisionManager(mapManager.getTiledMap());
        shapeRenderer = new ShapeRenderer();

        world = new World();
        player = new Player(collisionManager, shapeRenderer);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //updating positions
        player.update(delta); // for when the player is walking
        camera.position.set(player.getX(), player.getY(), 0); // adjust camera location
        camera.update(); // updates position

        spriteBatch.setProjectionMatrix(camera.combined); // after camera update but before batch.begin (to avoid flushes to teh GPU)


        spriteBatch.begin(); // starts drawing

        mapManager.render(camera, spriteBatch);
        player.render(spriteBatch); // renders player png

        spriteBatch.end();

        Rectangle hitbox = player.getHitBox();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
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
        world.dispose();
    }

}
