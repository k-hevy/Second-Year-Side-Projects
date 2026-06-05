package com.kean.singkamasvalley.inventory;

public abstract class Item {

    private ItemType type;

    public Item(ItemType type) {
        this.type = type;
    }

    public ItemType getType() {
        return type;
    }

}
