package com.kean.singkamasvalley.managers;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class CollisionManager {

    private final TiledMap map;

    public CollisionManager(TiledMap map) {
        this.map = map;
    }

//    public boolean isBlocked(int tileX, int tileY) {
//
//        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("objects"); // converts maplayer to tiledMaplayer, and returns object layer
//        TiledMapTileLayer.Cell cell = layer.getCell(tileX, tileY);
//
//        if (cell == null) return false;
//
//        return cell.getTile().getProperties().get("solid", Boolean.class);
//    }

}
