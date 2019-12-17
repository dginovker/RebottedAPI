package org.rebotted.bot.api.data;


import org.rebotted.bot.api.interactive.Inventory;
import org.rebotted.bot.api.wrappers.items.Item;
import org.rebotted.cache.graphics.Widget;
import org.rebotted.util.Condition;

public class Shop {

    public static boolean isOpen() {
        return Game.getOpenInterfaceId() == 3824;
    }

    private static Widget getInterface() {
        return Widget.interfaceCache[3900];
    }

    public static String[] getActions() {
        return getInterface().actions;
    }

    public static void sell(int id, int amount) {
        final Item i = Inventory.getItem(id);
        final int itemAmount = Inventory.getCount(id);
        if (i != null && i.getStackSize() > 0) {
            String sellAmount = "x";
            switch (amount) {
                case 1:
                    sellAmount = "1";
                    break;
                case 5:
                    sellAmount = "5";
                    break;
                case 10:
                    sellAmount = "10";
                    break;
            }
            if (sellAmount.equals("x")) {
                //sell xx blah
            } else {
                i.interact("Sell " + sellAmount);
                Condition.wait(new Condition.Check() {
                    public boolean poll() {
                        return itemAmount != Inventory.getCount(id);
                    }
                }, 100, 50);
            }
        }
    }

    public static void sellAll(int id) {
        for (Item i : Inventory.getItems(id)) {
            while (Shop.isOpen() && Inventory.getCount(i.getId()) > 0) {
                final int previous = Inventory.getCount(id);
                sell(id, 10);
                Condition.wait(new Condition.Check() {
                    public boolean poll() {
                        return previous != Inventory.getCount(id);
                    }
                }, 100, 50);
            }
        }
    }

}
