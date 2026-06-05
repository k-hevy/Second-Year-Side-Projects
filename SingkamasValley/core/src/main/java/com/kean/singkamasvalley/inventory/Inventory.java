package com.kean.singkamasvalley.inventory;

import com.badlogic.gdx.utils.Array;

public class Inventory {

    private Array<ItemStack> slots;

    public Inventory () {
        slots = new Array<ItemStack>();
    }

    public Array<ItemStack> getSlots() {
        return slots;
    }

    public void addItem(Item item, int quantity) {

        for (ItemStack stack : slots) {
            if (stack.getItem().getType() == item.getType()) {
                stack.setQuantity(stack.getQuantity() + quantity);
                return;
            }
        }

        slots.add(new ItemStack(item, quantity));
    }

}
