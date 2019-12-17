package org.rebotted.bot.api.interaction;

import org.rebotted.MenuAction;
import org.rebotted.bot.api.wrappers.NPC;
import org.rebotted.bot.core.Core;
import org.rebotted.bot.interfaces.Interactable;
import org.rebotted.util.Condition;
import org.rebotted.util.Random;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class InteractionHandler {

    private static int INVENTORY_INDEX = 3214;
    private static int INVENTORY_SHOP_INDEX = 3823;
    private static int INVENTORY_BANK_INDEX = 5064;
    private final boolean debug = false;
    private static final Rectangle view = new Rectangle(0, 0, 515, 330);

    protected Interactable interactable;

    public InteractionHandler(Interactable interactable) {
        this.interactable = interactable;
    }

    public MenuAction getAction(String action) {
        int actionId = -1;
        int itemId = -1;
        if (interactable instanceof NPC) {
            actionId = indexOf(((NPC) interactable).getNpcDefinition().actions, action);
            switch (actionId) {
                case 0:
                default:
                    actionId = 20;
                    break;
                case 1:
                    actionId = 412;
                    break;
                case 2:
                    actionId = 225;
                    break;
                case 3:
                    actionId = 965;
                    break;
                case 4:
                    actionId = 478;
                    break;
            }
            return new MenuAction(((NPC) interactable).getIndex(), action, "target", actionId);
        }
        return null;
    }

    private int indexOf(String[] actions, String action) {
        int i;
        if (action == null) return -1;
        if (actions == null) {
            return -1;
        }
        int n = i = 0;
        while (n < actions.length) {
            final String s = actions[i];
            if (action.equalsIgnoreCase(s)) {
                return i;
            }
            n = ++i;
        }
        return -1;
    }

    public boolean interact(String action) {
        return this.interact(() -> getAction(action));
    }

    private boolean interact(Supplier<MenuAction> supplier) {
        final MenuAction menuAction = supplier.get();
        if (menuAction == null) return false;
        Condition.sleep(Random.nextInt(50, 150));
        Core.getClient().processMenuActions(-1, menuAction);
        if (debug) {
            /**
             * TODO: add debug info.
             */
        }
        return true;
    }

}
