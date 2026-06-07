package com.kean.singkamasvalley.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.kean.singkamasvalley.inventory.Inventory;
import com.kean.singkamasvalley.inventory.items.ItemStack;

public class UIManager {

    private Inventory inventory;
    private BitmapFont font;
    private Hotbar hotbar;

    public UIManager(Inventory inventory, Hotbar hotbar) {
        this.inventory = inventory;
        this.font = new BitmapFont();
        this.hotbar = hotbar;
    }

    public void render(SpriteBatch spriteBatch) {

        spriteBatch.begin();

        font.draw(spriteBatch, "HOTBAR", 10, 200);

        int x = 10;
        int y = 180;

        Array<ItemStack> items = inventory.getSlots();

        for (int i  = 0;  i < 12; i++) {
            String text = "[ ]";

            if (i < items.size) {
                ItemStack stack = items.get(i);
                text = stack.getItem().getName() + " x" + stack.getQuantity();
            }

            if (i == hotbar.getSelectedSlot()) {
                text = "> " + text;
            }

            font.draw(spriteBatch, text, x, y );
            y-= 20;

        }

        spriteBatch.end();
    }
}
