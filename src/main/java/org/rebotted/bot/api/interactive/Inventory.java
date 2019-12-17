package org.rebotted.bot.api.interactive;


import org.rebotted.bot.api.data.Bank;
import org.rebotted.bot.api.data.Shop;
import org.rebotted.bot.api.wrappers.items.Item;
import org.rebotted.bot.core.Core;
import org.rebotted.cache.graphics.Widget;
import org.rebotted.util.Filter;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private static final Filter<Item> ALL_FILTER = i -> true;
    private static int INVENTORY_INDEX = 3214;
    private static int INVENTORY_SHOP_INDEX = 3823;
    private static int INVENTORY_BANK_INDEX = 5064;

    private static Widget getInterface() {
        return Widget.interfaceCache[INVENTORY_INDEX];
    }

    private static Widget getShopInterface() {
        return Widget.interfaceCache[INVENTORY_SHOP_INDEX];
    }

    private static Widget getBankInterface() {
        return Widget.interfaceCache[INVENTORY_BANK_INDEX];
    }

    public static int getCount() {
        return getCount(false);
    }

    public static int getCount(int... ids) {
        return getCount(false, ids);
    }

    public static int getCount(final boolean includeStack) {
        Widget inventory;
        if (Shop.isOpen()) {
            inventory = getShopInterface();
        } else if(Bank.isOpen()) {
            inventory = getBankInterface();
        } else {
            inventory = getInterface();
        }

        if (inventory == null) {
            return -1;
        }
        int count = 0;
        final int[] items = inventory.inventoryItemId;
        final int[] stackSizes = includeStack ? inventory.inventoryAmounts : null;
        for (int i = 0; i < items.length; i++) {
            if (items[i] > 0) {
                count += includeStack ? stackSizes[i] : 1;
            }
        }
        return count;
    }

    public static int getCount(final boolean includeStack, int... ids) {
        Widget inventory;
        if (Shop.isOpen()) {
            inventory = getShopInterface();
        } else if(Bank.isOpen()) {
            inventory = getBankInterface();
        } else {
            inventory = getInterface();
        }

        if (inventory == null) {
            return -1;
        }
        int count = 0;
        final int[] items = inventory.inventoryItemId;
        final int[] stackSizes = includeStack ? inventory.inventoryAmounts : null;
        for (int i = 0; i < items.length; i++) {
            final int itemId = items[i];
            if (itemId > 0) {
                for (final int id : ids) {
                    if (id == itemId-1) {
                        count += includeStack ? stackSizes[i] : 1;
                        break;
                    }
                }
            }
        }
        return count;
    }

    public static Item[] getItems() {
        return getItems(ALL_FILTER);
    }

    public static Item[] getItems(final int... ids) {
        return getItems(e -> {
            for (int id : ids) {
                if (e.getId() == id) {
                    return true;
                }
            }
            return false;
        });
    }

    public static Item[] getItems(final String... names) {
        return getItems(e -> {
            for (String name : names) {
                if(e.getItemDefinition() != null && e.getItemDefinition().getName() != null) {
                    if (e.getItemDefinition().getName().equalsIgnoreCase(name)) {
                        return true;
                    }
                }
            }
            return false;
        });
    }

    public static Item[] getItems(final Filter<Item> filter) {
        Widget inventory;
        if (Shop.isOpen()) {
            inventory = getShopInterface();
        } else if(Bank.isOpen()) {
            inventory = getBankInterface();
        } else {
            inventory = getInterface();

        }

        if (inventory == null) {
            return null;
        }
        final int[] items = inventory.inventoryItemId;
        final int[] stackSizes = inventory.inventoryAmounts;
        final List<Item> invItems = new ArrayList<>(28);
        for (int i = 0; i < items.length; i++) {
            final int itemId = items[i];
            if (itemId < 1) {
                continue;
            }
            final int stackSize = stackSizes[i];
            final Item item = new Item(itemId-1, stackSize, i, inventory);
            if (filter.accept(item)) {
                invItems.add(item);
            }
        }
        return invItems.toArray(new Item[invItems.size()]);
    }

    public static boolean isFull() {
        return Inventory.getCount() == 28;
    }

    public static boolean isEmpty() {
        return Inventory.getCount() == 0;
    }

    public static boolean contains(int... ids) {
        return getCount(ids) > 0;
    }

    public static boolean contains(String... names) {
        final Item[] i = getItems(names);
        return i != null && i.length > 0;
    }

    public static Item getItem(int id) {
        for (Item i : getItems(id)) {
            if (i != null) {
                return i;
            }
        }
        return null;
    }

    public static Item getItem(String name) {
        for (Item i : getItems(name)) {
            if (i != null) {
                return i;
            }
        }
        return null;
    }
}
