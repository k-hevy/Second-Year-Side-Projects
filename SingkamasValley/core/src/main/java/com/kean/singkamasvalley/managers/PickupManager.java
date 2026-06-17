package com.kean.singkamasvalley.managers;

import com.badlogic.gdx.utils.Array;
import com.kean.singkamasvalley.inventory.items.ItemEntity;
import com.kean.singkamasvalley.entities.Player;
import com.kean.singkamasvalley.world.World;
import com.kean.singkamasvalley.world.objects.WorldObject;

import java.util.Iterator;

public class PickupManager {

    private World world;

    public PickupManager (World world) {
        this.world = world;
    }

    public void checkPickup(Player player) {

        Array<WorldObject> objects = world.getObjects();
        Iterator<WorldObject> iterator = objects.iterator();

        while(iterator.hasNext()) {
            WorldObject object = iterator.next();

            if (!(object instanceof ItemEntity)) continue;

            ItemEntity item = (ItemEntity) object;

            if (player.getHitBox().overlaps(object.getBounds())) {
                player.getInventory().addItem(
                    item.getItemStack().getItem(),
                    item.getItemStack().getQuantity()
                );
                iterator.remove();
            }
        }

    }
}
