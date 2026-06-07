package com.kean.singkamasvalley.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ObjectMap;

public class GameAssets {

    private ObjectMap<String, Texture> textures;

    public GameAssets() {
        textures = new ObjectMap<>();
    }

    public void load() {
        loadTexture("stone");
        loadTexture("wood");
        loadTexture("axe");
        loadTexture("singkamas_seed");
        loadTexture("error_item");
    }

    public void loadTexture(String name) {
        Texture texture = new Texture(Gdx.files.internal("textures/" + name + ".png"));
        textures.put(name, texture);
    }

    public Texture getTexture(String texture) {
        return textures.get(texture);
    }

    public void dispose() {
        for (Texture texture  : textures.values()) {
            texture.dispose();
        }

    }

}
