package com.kean.singkamasvalley.inventory;

public class ItemStack {
    private Item item;
    private int quantity;

    public ItemStack(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setQuantity(int num) {
        quantity = num;
    }

    public void setItem(Item it) {
        item = it;
    }
}
