package org.rebotted.bot.interfaces;


import org.rebotted.bot.api.wrappers.Tile;

public interface Locatable {

    Tile getLocation();

    int distanceTo();
}
