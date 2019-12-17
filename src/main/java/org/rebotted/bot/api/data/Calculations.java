package org.rebotted.bot.api.data;

import org.rebotted.bot.api.interactive.Players;
import org.rebotted.bot.api.wrappers.location.Tile;


public class Calculations {

    public static final double distanceTo(Tile tile) {
        return distanceBetween(Players.getMyPlayer().getLocation(), tile);
    }

    public static final double distanceBetween(Tile a, Tile b) {
        int x = b.getX() - a.getX();
        int y = b.getY() - a.getY();

        return Math.sqrt((x * x) + (y * y));
    }

    public static boolean isSameTile(Tile first, Tile second) {
        return distanceBetween(first, second) == 0 && first.getPlane() == second.getPlane();
    }

    public static boolean atTile(Tile destination) {
        return isSameTile(Players.getMyPlayer().getLocation(), destination);
    }

}
