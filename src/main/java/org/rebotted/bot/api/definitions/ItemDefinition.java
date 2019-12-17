package org.rebotted.bot.api.definitions;

import java.util.Arrays;

public class ItemDefinition {
    private int id;
    private org.rebotted.cache.def.ItemDefinition accessor;

    public ItemDefinition(int id) {
        this.id = id;
        this.accessor = org.rebotted.cache.def.ItemDefinition.lookup(id);
    }

    public int getValue() {
        if (accessor == null)
            return -1;
        return accessor.value;
    }

    public int[] getAmounts() {
        if (accessor == null)
            return null;
        return accessor.stackVariantSize;
    }

    public int[] getStackIds() {
        if (accessor == null)
            return null;
        return accessor.stackVariantId;
    }

    public boolean isStackable() {
        if (accessor == null)
            return false;
        return accessor.stackable;
    }

    public int getNoteInfoId() {
        if (accessor == null)
            return -1;
        return accessor.unnotedItemId;
    }

    public String getName() {
        if (accessor == null)
            return null;
        return accessor.name;
    }

    public String[] getActions() {
        if (accessor == null)
            return null;
        return accessor.actions;
    }

    public int getId() {
        return id;
    }

    public String[] getGroundActions() {
        if (accessor == null)
            return null;
        return accessor.groundActions;
    }

}
