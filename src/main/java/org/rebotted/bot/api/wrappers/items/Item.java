package org.rebotted.bot.api.wrappers.items;

import org.rebotted.bot.api.definitions.ItemDefinition;
import org.rebotted.bot.api.interaction.InteractionHandler;
import org.rebotted.bot.interfaces.Interactable;
import org.rebotted.cache.graphics.Widget;

public class Item implements Interactable {
    private final Widget widget;
    private final InteractionHandler interactionHandler;
    private int id, stackSize;
    private int slot;
    private ItemDefinition itemDefinition;
    public Item(int id, int stackSize, int slot, Widget widget) {
        this.id = id;
        this.stackSize = stackSize;
        this.slot = slot;
        this.widget = widget;
        this.interactionHandler = new InteractionHandler(this);
        this.itemDefinition = new ItemDefinition(id);
    }

    public int getId() {
        return id;
    }

    public int getStackSize() {
        return stackSize;
    }

    public int getSlot() {
        return slot;
    }

    public ItemDefinition getItemDefinition() {
        return itemDefinition;
    }

    @Override
    public boolean interact(String action) {
        return interactionHandler.interact(action);
    }

    @Override
    public String[] getActions() {
        if(getParentID() == 3213) {
            return getItemDefinition().getActions();
        }
        if (widget != null && widget.actions != null && widget.actions.length > 0)
            return widget.actions;
        return new String[0];
    }

    public int getParentID() {
        if (widget != null)
            return widget.parent;
        return -1;
    }
}