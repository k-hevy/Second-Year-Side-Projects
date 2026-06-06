package com.kean.singkamasvalley.inventory;

import com.badlogic.gdx.utils.Array;
import com.kean.singkamasvalley.inventory.items.ItemDefinition;
import com.kean.singkamasvalley.inventory.items.ItemStack;

public class Inventory {

    private Array<ItemStack> slots;

    public Inventory () {
        slots = new Array<>();
    }

    public Array<ItemStack> getSlots() {
        return slots;
    }

    public void addItem(ItemDefinition itemDefinition, int quantity) {

        for (ItemStack stack : slots) {
            if (stack.getItem().getID().equals(itemDefinition.getID())) {
                stack.add(quantity);
                return;
            }
        }

        slots.add(new ItemStack(itemDefinition, quantity));
    }

}
