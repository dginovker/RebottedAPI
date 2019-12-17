package org.rebotted.bot.api.wrappers;


import org.rebotted.bot.api.data.Calculations;
import org.rebotted.bot.api.data.Game;
import org.rebotted.bot.interfaces.Locatable;
import org.rebotted.bot.interfaces.TileFlags;

public final class Tile implements TileFlags, Locatable {
    private int x;
    private int y;
    private int z;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Tile(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public final int getRegionX() {
        return x - Game.getBaseX();
    }

    public final int getRegionY() {
        return y - Game.getBaseY();
    }

    @Override
    public Tile getLocation() {
        return this;
    }

    public final int getPlane() {
        return z;
    }

    @Override
    public final int distanceTo() {
        return (int) Calculations.distanceTo(this);
    }

    public int distanceTo(Tile other) {
        return (int) Math.sqrt(Math.pow(other.getX() - x, 2) + Math.pow(other.getY() - y, 2));
    }

    public final boolean isOnMinimap() {
        return distanceTo() < 16;
    }

    @Override
    public String toString() {
        return "Tile: [" + getX() + ", " + getY() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        final Tile t = (Tile) obj;
        return t.getX() == this.getX() && t.getY() == this.getY()
                && t.getPlane() == this.getPlane();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.x;
        hash = 31 * hash + this.y;
        hash = 31 * hash + this.z;

        return hash;
    }
}