package com.kean.singkamasvalley.world;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class WorldBuilder {

    public static World build(TiledMap map) {

        World world = new World();

        TiledMapTileLayer groundLayer = (TiledMapTileLayer) map.getLayers().get("Ground layer");

        int width = groundLayer.getWidth();
        int height = groundLayer.getHeight();

        world.tiles = new GameTile[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                TiledMapTileLayer.Cell cell = groundLayer.getCell(x, y);
                TileType type = parseTileType(cell);
                world.tiles[x][y] = new GameTile(type);
            }
        }
        return world;
    }

    private static TileType parseTileType(TiledMapTileLayer.Cell cell) {

        if (cell == null || cell.getTile() == null) return TileType.GRASS;
        String type = cell.getTile().getProperties().get("TYPE", String.class);
        return TileType.valueOf(type);

    }


}
