package com.kean.singkamasvalley.managers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.kean.singkamasvalley.entities.Interactable;
import com.kean.singkamasvalley.entities.Player;
import com.kean.singkamasvalley.entities.WorldObject;
import com.kean.singkamasvalley.world.World;

public class InteractionManager {
    private Array<Interactable> interactables;

    public InteractionManager() {
        interactables = new Array<>();
    }

    public void register(Interactable obj) {
        interactables.add(obj);
    }

    public void tryInteract(Player player, World world) {

        Rectangle playerFrontTile = player.getFrontTile();

        for (WorldObject obj : world.getObjects()) {

            if (obj instanceof Interactable ) {

                if (obj.getBounds().overlaps(playerFrontTile)) {
                    ((Interactable) obj).interact(player);
                    return;
                }

            }
        }

    }

}
