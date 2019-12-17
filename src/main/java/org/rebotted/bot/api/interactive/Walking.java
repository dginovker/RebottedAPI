package org.rebotted.bot.api.interactive;

import org.rebotted.bot.api.wrappers.Tile;
import org.rebotted.bot.core.Core;

public class Walking {

    public static void walkTo(Tile from, Tile to) {
        Core.getClient().doWalkTo(
                0,
                0,
                0,
                0,
                from.getRegionY(),
                0,
                0,
                to.getRegionY(),
                from.getRegionX(),
                true,
                to.getRegionX()
        );
    }

    public static void walkTo(Tile to) {
        walkTo(Players.getMyPlayer().getLocation(), to);
    }

}
