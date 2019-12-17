package org.rebotted.bot.api.interaction;

import org.rebotted.MenuAction;
import org.rebotted.bot.api.data.Bank;
import org.rebotted.bot.api.data.Shop;
import org.rebotted.bot.api.wrappers.entities.NPC;
import org.rebotted.bot.api.wrappers.items.Item;
import org.rebotted.bot.core.Core;
import org.rebotted.bot.interfaces.Interactable;
import org.rebotted.util.Condition;
import org.rebotted.util.Random;

import java.awt.*;
import java.util.Arrays;
import java.util.function.Supplier;

public class InteractionHandler {

    private static final Rectangle view = new Rectangle(0, 0, 515, 330);
    private static int INVENTORY_INDEX = 3214;
    private static int INVENTORY_SHOP_INDEX = 3823;
    private static int INVENTORY_BANK_INDEX = 5064;
    private final boolean debug = true;
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
        } else if (interactable instanceof Item) {
            itemId = ((Item) interactable).getId();
            int parentId = ((Item) interactable).getParentID();
            System.out.println(Arrays.toString(interactable.getActions()));
            actionId = indexOf(interactable.getActions(), action);
            if (parentId == 3213) {
                parentId = 3214;
            }
            switch (action.toLowerCase()) {
                case "use":
                    actionId = 447;
                    break;
                case "drop":
                    actionId = 847;
                    break;
                case "examine":
                    actionId = 1125;
                    break;
                case "cancel":
                    actionId = 1107;
                    break;
                case "wear":
                    actionId = 454;
                    break;
                case "use with":
                    actionId = 870;
                    break;
            }
            if (actionId == -1) {
                switch (actionId) {
                    case 0:
                        actionId = 454;
                        break;
                    case 1:
                        actionId = 74;
                        break;
                    case 2:
                        actionId = 447;
                        break;
                    case 3:
                        actionId = 493;
                        break;
                    case 4:
                        actionId = 847;
                        break;
                    case 5:
                        actionId = 1125;
                        break;
                    case 6:
                        actionId = 870;
                        break;
                }
            }
            if (actionId == -1) {
                if (Shop.isOpen()) {
                    actionId = indexOf(interactable.getActions(), action);
                    parentId = INVENTORY_SHOP_INDEX;
                } else if (Bank.isOpen()) {
                    actionId = indexOf(interactable.getActions(), action);
                    parentId = INVENTORY_BANK_INDEX;
                } else {

                }

                switch (actionId) {
                    case 0:
                    default:
                        actionId = 632;
                        break;
                    case 1:
                        actionId = 78;
                        break;
                    case 2:
                        actionId = 867;
                        break;
                    case 3:
                        actionId = 431;
                        break;
                    case 4:
                        actionId = 53;
                        break;
                    case 5:
                        actionId = 775;
                        break;
                }
            }
            return new MenuAction(itemId, action, "target", ((Item) interactable).getSlot(), parentId, actionId);
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
        if (Core.getClient().menuActionText == null || Core.getClient().menuActionText.length <= 0) {
            Core.getClient().menuActionText[0] = "idk";
        }
        Core.getClient().processMenuActions(0, menuAction);
        if (debug) {
            System.out.println(menuAction.getAction() + " - actionID: " + menuAction.getActionId() + " - hash: " + menuAction.getHash() + " - X: " + menuAction.getMouseX() + " - Y: " + menuAction.getMouseY());
        }
        return true;
    }

}
