package com.kean.singkamasvalley.inventory.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.kean.singkamasvalley.assets.GameAssets;
import com.kean.singkamasvalley.entities.Renderable;
import com.kean.singkamasvalley.world.objects.WorldObject;

public class ItemEntity extends WorldObject implements Renderable {

    private GameAssets assets;
    private ItemStack itemStack;
    private Rectangle bounds;

    public ItemEntity(GameAssets assets, ItemStack itemStack, float x, float y) {
        super(x, y, 12, 12);
        this.assets = assets;
        this.itemStack = itemStack;
    }

//    @Override
//    public float getY() {
//        float ye = 0;
//        return ye;
//    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        Texture tex = assets.getTexture(itemStack.getItem().getTexture());
        spriteBatch.draw(tex, getX(), getY(), 12, 12);
    }

}
