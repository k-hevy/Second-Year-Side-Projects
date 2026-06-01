package com.kean.singkamasvalley.world;

public enum TileType {
    GRASS(false),
    WATER(true),
    SOIL(false),
    PATH(false);

    private final boolean solid;

    TileType(boolean solid) {
        this.solid = solid;
    }

    public boolean isSolid() {
        return solid;
    }

}
