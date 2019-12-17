package org.rebotted.bot.interfaces;


import org.rebotted.bot.api.wrappers.location.Tile;

public interface Locatable {

    Tile getLocation();

    int distanceTo();
}
