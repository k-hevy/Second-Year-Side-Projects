package com.kean.singkamasvalley.inventory.items;

import com.kean.singkamasvalley.entities.ToolType;

public class ItemDefinition {

    private String id;
    private String name;
    private String texture;
    private String category;
    private ToolType toolType;

    public String getID() {
        return id;
    }

    public String getName() { return name; }

    public String getTexture() {
        return texture;
    }

    public String getCategory() { return category; }

    public ToolType getToolType() { return toolType; }

}
