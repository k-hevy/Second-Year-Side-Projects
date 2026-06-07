package com.kean.singkamasvalley.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.kean.singkamasvalley.inventory.Inventory;
import com.kean.singkamasvalley.inventory.items.ItemStack;

public class Hotbar {

    private Inventory inventory;
    private int selectedSlot;

    public Hotbar(Inventory inventory) {
        this.inventory = inventory;
    }

    public void update() {
        handleKeys();
    }

    private void handleKeys() {
        for (int i = 0; i < 9; i++) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1 + i)) selectedSlot = i;
        }
    }

    public ItemStack getSelectedItem() {

        Array<ItemStack> items = inventory.getSlots();
        if (selectedSlot >= items.size) return null;

        return items.get(selectedSlot);
    }

    public int getSelectedSlot() {
        return selectedSlot;
    }

}
