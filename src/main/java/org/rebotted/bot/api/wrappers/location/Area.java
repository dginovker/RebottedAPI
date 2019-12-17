package org.rebotted.bot.api.wrappers.location;

import org.rebotted.bot.api.interactive.Players;
import org.rebotted.bot.interfaces.Locatable;

import java.awt.*;
import java.util.Random;

public class Area {

    private Tile bottomLeft;
    private Tile topRight;
    private int plane;

    /**
     * Creates a new rectangular area defined by the 2 given points
     *
     * @param bottomLeft The south-western corner tile
     * @param topRight   The north-eastern corner tile
     * @param plane      The game plane
     */
    public Area(Tile bottomLeft, Tile topRight, int plane) {
        setBottomLeft(bottomLeft);
        setTopRight(topRight);
        setplane(plane);
    }

    /**
     * Creates a new rectangular area defined by the 2 given points
     *
     * @param bottomLeft The south-western corner tile
     * @param topRight   The north-eastern corner tile
     */
    public Area(Tile bottomLeft, Tile topRight) {
        this(bottomLeft, topRight, 0);
    }

    /**
     * Creates a rectangular area defined like a rectangle
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param plane
     */
    public Area(int x, int y, int width, int height, int plane) {
        this(new Tile(x, y), new Tile(x + width, y + height), plane);
    }

    /**
     * Creates a rectangular area defined like a rectangle
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Area(int x, int y, int width, int height) {
        this(new Tile(x, y), new Tile(x + width, y + height), 0);
    }

    /**
     * Creates a rectangular area defined as a rectangle.
     *
     * @param dimensions The dimensions
     * @param plane
     */
    public Area(Rectangle dimensions, int plane) {
        this(dimensions.x, dimensions.y, dimensions.x + dimensions.width, dimensions.y + dimensions.height, plane);
    }

    /**
     * Creates a rectangular area defined as a rectangle.
     *
     * @param dimensions The dimensions
     */
    public Area(Rectangle dimensions) {
        this(dimensions.x, dimensions.y, dimensions.x + dimensions.width, dimensions.y + dimensions.height, 0);
    }

    /**
     * Checks if an actor is within this area
     *
     * @return true if the actor is inside, false if not
     */
    public boolean contains(Locatable actor) {
        return contains(actor.getLocation());
    }

    /**
     * Checks if a tile is within the area.
     *
     * @return <tt>true</tt> if the tile is inside, else <tt>false</tt>
     */
    public boolean contains(Tile loc) {
        return contains(loc.getX(), loc.getY());
    }

    /**
     * Checks if coordinates are within the area.
     *
     * @return <tt>true</tt> if the tile is inside, else <tt>false</tt>
     */
    public boolean contains(int x, int y) {
        return (x >= bottomLeft.getX() && x <= topRight.getX() && y >= bottomLeft.getY() && y <= topRight.getY());
    }

    /**
     * Gets the tile at the center of this area.
     *
     * @return The center of this area.
     */
    public Tile getCenter() {
        return new Tile((int) getDimensions().getCenterX(), (int) getDimensions().getCenterY());
    }

    /**
     * Gets tile nearest to local player.
     *
     * @return The nearest tile
     */
    public Tile getNearestTile() {
        return getTileNearestTo(Players.getMyPlayer().getLocation());
    }

    /**
     * Gets tile nearest to a specific location.
     *
     * @param loc The location
     * @return The nearest tile
     */
    public Tile getTileNearestTo(Tile loc) {
        Tile nearestTile = null;
        for (int x = bottomLeft.getX(); x < getWidth(); x++) {
            for (int y = bottomLeft.getY(); x < getHeight(); y++) {
                if (nearestTile == null || new Tile(x, y).distanceTo(loc) < nearestTile.distanceTo(loc)) {
                    nearestTile = new Tile(x, y);
                }

            }
        }
        return nearestTile;
    }

    /**
     * Gets a random tile from within the area.
     *
     * @return The tile
     */
    public Tile getRandomTile() {
        final Random r = new Random();
        return new Tile(bottomLeft.getX() + r.nextInt(getWidth()), topRight.getY() + r.nextInt(getHeight()));
    }

    /**
     * Calculates the size of the area
     *
     * @return The surface area size
     */
    public int getSize() {
        int width = topRight.getX() - bottomLeft.getX();
        int height = topRight.getY() - bottomLeft.getX();
        return width * height;
    }

    /**
     * Gets the tile dimensions of the area.
     *
     * @return the dimensions as a rectangle
     */
    public Rectangle getDimensions() {
        return new Rectangle(bottomLeft.getX(), bottomLeft.getY(), getWidth(), getHeight());
    }

    /**
     * Gets the width of the Area
     *
     * @return the width
     */
    public int getWidth() {
        return Math.abs(topRight.getX() - bottomLeft.getX());
    }

    /**
     * Gets the height of the Area
     *
     * @return the height
     */
    public int getHeight() {
        return Math.abs(topRight.getY() - bottomLeft.getY());
    }

    public Tile getBottomLeft() {
        return bottomLeft;
    }

    public void setBottomLeft(Tile bottomLeft) {
        this.bottomLeft = bottomLeft;
    }

    public Tile getTopRight() {
        return topRight;
    }

    public void setTopRight(Tile topRight) {
        this.topRight = topRight;
    }

    public int getplane() {
        return plane;
    }

    public void setplane(int plane) {
        this.plane = plane;
    }

    @Override
    public String toString() {
        return "[" + bottomLeft.getX() + ", " + bottomLeft.getY() + ", " + getWidth() + ", " + getHeight() + "]";
    }

    @Override
    public boolean equals(Object o) {
        return ((Area) o).getDimensions().equals(getDimensions());
    }

}