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
    private final int TILE_SIZE  = 16;

    public CollisionManager(TiledMap map) {
        this.map = map;
    }

    public boolean isBlocked(Rectangle hitbox) {

        return isTileLayerBlocked("Ground layer", hitbox) ||
            isObjectLayerBlocked("collisions", hitbox);
    }

    public boolean isTileLayerBlocked(String layerName, Rectangle hitbox) {

        MapLayer layer = map.getLayers().get(layerName);

        if (layer == null) return false;
        if (!(layer instanceof TiledMapTileLayer)) return false;

        TiledMapTileLayer tiled = (TiledMapTileLayer) layer;

        int startX = (int) (hitbox.x / TILE_SIZE);
        int endX   = (int) ((hitbox.x + hitbox.width) / TILE_SIZE);
        int startY = (int) (hitbox.y / TILE_SIZE);
        int endY   = (int) ((hitbox.y + hitbox.height) / TILE_SIZE);

        // Loops through all the tiles the player is touching, does this by having a start tile then end tile
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {

                Cell cell = tiled.getCell(x, y);
                if (cell == null) continue;

                Boolean isSolid = cell.getTile().getProperties().get("solid", Boolean.class);

                if (isSolid != null && isSolid) {
                    return true;
                }

            }
        }
        return false;
    }

    public boolean isObjectLayerBlocked(String layerName, Rectangle hitbox) {

        MapLayer layer = map.getLayers().get(layerName);
        if (layer == null) return false;

        for (MapObject object : layer.getObjects()) {
            Boolean isSolid = object.getProperties().get("solid", Boolean.class);
            if (isSolid == null || !isSolid) continue;

                if (object instanceof RectangleMapObject) {
                    Rectangle recObj = ((RectangleMapObject) object).getRectangle();
                    if (hitbox.overlaps(recObj)) {
                        return true;
                    }
                }

        }
        return false;
    }

}
