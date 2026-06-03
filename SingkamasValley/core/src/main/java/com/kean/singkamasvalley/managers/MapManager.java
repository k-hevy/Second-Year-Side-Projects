package com.kean.singkamasvalley.managers;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapManager {

    private TiledMap map;
    private SpriteBatch spriteBatch;
    private OrthogonalTiledMapRenderer renderer;
    private int[] backgroundLayers = {0};

    public MapManager (SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
        map = new TmxMapLoader().load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
    }

    public void render(Camera camera, SpriteBatch spriteBatch) {
        renderer.setView((OrthographicCamera) camera); // draws tiles that are currently visible
        renderer.render(backgroundLayers); // draws the groundlayer

        // texture - the raw image that loaded into memory
        // TextureRegion - a speciifed cropped rectangle of the image, a part
        // Sprite - A TextureRegion with extra powers (position, rotation, scale).

        // Trees and houses are treated as TexturedMapObject entities
        MapLayer objLayer = map.getLayers().get("objects");
        if (objLayer != null) {
            for (MapObject object : objLayer.getObjects()) {
                if (object instanceof TextureMapObject) {
                    TextureMapObject textureObject = (TextureMapObject) object;
                    TextureRegion region = textureObject.getTextureRegion(); // the specific image .png
                    float x = textureObject.getX(); // from Tiled
                    float y = textureObject.getY();
                    float width = textureObject.getProperties().get("width", Float.class); // Width and height of images
                    float height = textureObject.getProperties().get("height", Float.class);
                    spriteBatch.draw(region, x, y, width, height);
                }
            }
        }
    }

    public TiledMap getTiledMap() {
        return map;
    }

    public void dispose() {
        map.dispose();
        renderer.dispose();
    } // when changing levels or maps
}
