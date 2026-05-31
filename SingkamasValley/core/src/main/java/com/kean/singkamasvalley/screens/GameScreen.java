package com.kean.singkamasvalley.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kean.singkamasvalley.entities.Player;
import com.kean.singkamasvalley.world.World;

public class GameScreen implements Screen {

    private SpriteBatch spriteBatch;
    private Player player;
    private OrthographicCamera camera;
    private World world;

    public GameScreen () {}

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 400);
        System.out.println("Game Screen Loaded");
        spriteBatch = new SpriteBatch();
        player = new Player();
        world = new World();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        player.update(delta); // for when the player is walking
        spriteBatch.begin(); // starts drawing

        world.render(spriteBatch);
        player.render(spriteBatch); // renders player png

        camera.position.set(player.getX(), player.getY(), 0);

        spriteBatch.end();
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
