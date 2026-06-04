package com.kean.singkamasvalley.managers;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;

public class CollisionManager {

    private final TiledMap map;

    public CollisionManager(TiledMap map) {
        this.map = map;
    }

    public boolean isBlocked(int tileX, int tileY) {
        return isTileLayerBlocked("Ground layer", tileX, tileY) || isObjectLayerBlocked("collisions", tileX, tileY);
    }

    public boolean isTileLayerBlocked(String layerName, int tileX, int tileY) {

        MapLayer layer = map.getLayers().get(layerName);
        if (layer == null) return false;

        if (layer instanceof TiledMapTileLayer) {

            TiledMapTileLayer tiledlayer = (TiledMapTileLayer) layer;
            Cell cell = tiledlayer.getCell(tileX, tileY);

            if (cell == null) return false;

            Boolean isSolid = cell.getTile().getProperties().get("solid", Boolean.class);
            return isSolid != null && isSolid;

        } else {

            return false;

        }
    }

    public boolean isObjectLayerBlocked(String layerName, int tileX, int tileY) {
        MapLayer layer = map.getLayers().get(layerName);

        if (layer == null) return false;

        for (MapObject object : layer.getObjects()) {
            Boolean isSolid = object.getProperties().get("solid", Boolean.class);
            if (isSolid != null && isSolid) {
                if (object instanceof RectangleMapObject) {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();
                    int startTileX = (int) (rect.x / 16);
                    int startTileY = (int) (rect.y / 16);
                    int endTileX = (int) ((rect.x + rect.width) / 16);
                    int endTileY = (int) ((rect.y + rect.height) / 16);
                    if (tileX >= startTileX && tileX <= endTileX &&
                        tileY >= startTileY && tileY <= endTileY) {
                        System.out.println("cell at" + tileX + " " + tileY + ". solid property is " + isSolid);
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
