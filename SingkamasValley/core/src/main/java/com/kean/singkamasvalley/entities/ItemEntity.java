package com.kean.singkamasvalley.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.kean.singkamasvalley.assets.GameAssets;
import com.kean.singkamasvalley.inventory.items.ItemStack;
import com.kean.singkamasvalley.world.WorldObject;

public class ItemEntity extends WorldObject implements Renderable {

    private Rectangle bounds;
    private ItemStack itemStack;
    private GameAssets assets;
    private float x, y;

    public ItemEntity(GameAssets assets, ItemStack itemStack, float x, float y) {
        this.itemStack = itemStack;
        this.assets = assets;
        this.x = x;
        this.y = y;
        bounds = new Rectangle(x, y, 12, 12);
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public float getY() {
        return y;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        Texture tex = assets.getTexture(itemStack.getItem().getTexture()) ;
        spriteBatch.draw(tex, x, y, 12, 12);
    }

}
