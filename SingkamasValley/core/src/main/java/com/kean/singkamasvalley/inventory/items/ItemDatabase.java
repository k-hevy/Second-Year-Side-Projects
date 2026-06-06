package com.kean.singkamasvalley.inventory.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;

public class ItemDatabase {

    private ObjectMap <String, ItemDefinition> items;

    public ItemDatabase() {
        items = new ObjectMap<>();
        loadItems();
    }

    private void loadItems() {

        Json json = new Json();
        FileHandle file = Gdx.files.internal("data/items.json");

        @SuppressWarnings("unchecked")
        Array<ItemDefinition> definitions = json.fromJson(Array.class, ItemDefinition.class, file);

        for (ItemDefinition definition : definitions) {
            items.put(definition.getID(), definition);
        }

    }

    public ItemDefinition getItem(String id) {
        return items.get(id);
    }

    public ObjectMap <String, ItemDefinition> getItems() {
        return items;
    }

}
