package org.rebotted.bot.api.data;


import org.rebotted.MenuAction;
import org.rebotted.bot.api.interactive.Inventory;
import org.rebotted.bot.api.wrappers.items.Item;
import org.rebotted.bot.core.Core;
import org.rebotted.cache.graphics.Widget;
import org.rebotted.util.Condition;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private static int[] BANK_TAB_ITEMS = {18687, 18688, 18689, 18690, 18691, 18692, 18693, 18694, 18695};


    public static Item[] getItems() {
        if (!isOpen()) {
            return null;
        }

        final List<Item> items = new ArrayList<>();
        for (int intf : BANK_TAB_ITEMS) {
            final Widget widget = Widget.interfaceCache[intf];
            final int[] ids = widget.inventoryItemId;
            final int[] stacks = widget.inventoryAmounts;
            if (ids != null && stacks != null) {
                for (int i = 0; i < ids.length; i++) {
                    if (ids[i] > 0) {
                        items.add(new Item(ids[i] - 1, stacks[i], i, widget));
                    }
                }
            }
        }
        return items.toArray(new Item[items.size()]);
    }

    public static int getSpaceUsed() {
        if (!isOpen())
            return 0;
        if (getItems() == null || getItems().length <= 0)
            return 0;

        return getItems().length;
    }

    public static Item getItem(int id) {
        if (!isOpen()) {
            return null;
        }

        Item[] items;
        if ((items = Bank.getItems()) != null) {
            for (Item i : items) {
                if (i.getId() == id) {
                    return i;
                }
            }
        }

        return null;
    }

    public static int getCount(int id) {
        if (!isOpen()) {
            return 0;
        }
        Item item;

        return ((item = getItem(id)) != null ? item.getStackSize() : 0);
    }

    public static boolean isOpen() {
        return Game.getOpenInterfaceId() == 5292;
    }

    public static void withdraw(int id, int amount) {
        if (!isOpen()) {
            return;
        }
        final Item b = getItem(id);
        if (b == null) {
            return;
        }

        if (amount >= getCount(id))
            amount = 0;

        if (amount == 1) {
            b.interact("Withdraw-1");
        } else if (amount == 5) {
            b.interact("Withdraw-5");
        } else if (amount == 10) {
            b.interact("Withdraw-10");
        } else if (amount == 0) {
            b.interact("Withdraw-All");
        } else {
            b.interact("Withdraw-X");
            Condition.wait(new Condition.Check() {
                public boolean poll() {
                    return Core.getClient().inputDialogState > 0;
                }
            }, 100, 50);
            //Keyboard.getInstance().sendKeys(""+amount, true);
        }
    }

    public static void deposit(int id, int amount) {
        if (!isOpen()) {
            return;
        }
        final Item b = Inventory.getItem(id);
        if (b == null) {
            return;
        }

        if (amount >= Inventory.getCount(id))
            amount = 0;

        if (amount == 1) {
            b.interact("Store 1");
        } else if (amount == 5) {
            b.interact("Store 5");
        } else if (amount == 10) {
            b.interact("Store 10");
        } else if (amount == 0) {
            b.interact("Store All");
        } else {
            b.interact("Store X");
            Condition.wait(new Condition.Check() {
                public boolean poll() {
                    return Core.getClient().inputDialogState > 0;
                }
            }, 100, 50);
            //Keyboard.getInstance().sendKeys(""+amount, true);
        }
    }


    public static void close() {
        final MenuAction menuAction = new MenuAction(0, "Close", "", 0, 5384, 200);
        if (menuAction == null) return;
        Core.getClient().processMenuActions(-1, menuAction);

    }
}
