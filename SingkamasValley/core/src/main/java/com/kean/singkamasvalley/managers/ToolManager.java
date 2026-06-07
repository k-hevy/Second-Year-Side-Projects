package com.kean.singkamasvalley.managers;

import com.badlogic.gdx.math.Rectangle;
import com.kean.singkamasvalley.entities.Player;
import com.kean.singkamasvalley.entities.ToolType;
import com.kean.singkamasvalley.inventory.items.ItemDefinition;
import com.kean.singkamasvalley.inventory.items.ItemStack;
import com.kean.singkamasvalley.screens.GameScreen;
import com.kean.singkamasvalley.world.GameTile;
import com.kean.singkamasvalley.world.TileType;
import com.kean.singkamasvalley.world.World;

public class ToolManager {

    int TILE_SIZE = GameScreen.TILE_SIZE;

    public ToolManager() {

    }

    public void useTool(Player player, World world) {

        ItemStack selected = player.getHotbar().getSelectedItem();
        if (selected == null) return;
        ItemDefinition item = selected.getItem();

        Rectangle frontTile = player.getFrontTile();
        int x = (int) frontTile.getX() / TILE_SIZE;
        int y = (int) frontTile.getY() / TILE_SIZE;
        GameTile tile = world.getTile(x,y);

        switch(item.getToolType()) {
            case HOE : useHoe(tile); break;
            case AXE : useAxe(tile); break;
            case PICKAXE : usePick(tile); break;
            case SWORD : useSword(tile); break;
            case WATERING_CAN : useWatering_Can(tile); break;
        }

    }

    public void useHoe(GameTile tile) {

        if (tile != null && tile.getType() == TileType.GRASS && !tile.isTilled()) {
            tile.setTilled(true);
            System.out.println("Tilled");
        }

    }

    public void useAxe(GameTile tile) {}
    public void usePick(GameTile tile) {}
    public void useSword(GameTile tile) {}
    public void useWatering_Can(GameTile tile) {}
}
