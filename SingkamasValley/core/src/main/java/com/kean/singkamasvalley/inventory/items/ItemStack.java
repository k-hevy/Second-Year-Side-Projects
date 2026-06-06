package com.kean.singkamasvalley.inventory.items;

public class ItemStack {
    private ItemDefinition itemDefinition;
    private int quantity;

    public ItemStack(ItemDefinition itemDefinition, int quantity) {
        this.itemDefinition = itemDefinition;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public ItemDefinition getItem() {
        return itemDefinition;
    }

   public void add(int amount) {
        quantity += amount;
   }

   public void remove(int amount) {
        quantity -= amount;
   }
}
